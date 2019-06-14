package it.polimi.se2019.virtual_view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

// TODO: Optimization, not ready yet for production

/**
 * Socket protocol is really simple
 *
 * Every message is sent over a TCP connection, opened at the beginning of the
 * session, and kept open.
 * Every message is sent encoded in base64, and ends with the character `~`
 *
 * A message is formed by three pieces of data, separated by the character `~`:
 * - An action identifier, chosen between:
 * -- MODEL_UPDATE  (server -> client)
 * -- EXCEPTION     (server -> client)
 * -- VIEW_SELECT   (server -> client)
 * -- METHOD_INVOC  (client -> server)
 * - The path of the generator
 * - The real message
 * An example of a message not encoded is:
 * ```MODEL_UPDATE~server.map.cells~data to pass```
 * An example of a message encoded (the same as above) is:
 * ```TU9ERUxfVVBEQVRFfnNlcnZlci5tYXAuY2VsbHN+ZGF0YSB0byBwYXNz~```.
 * Please note the `~` at the end of the message
 */
public class SocketVirtualView extends AbstractVirtualView {
  /**
   * Sequence of updates sent down to the client.
   * Useful to handle client disconnection and reconnection
   */
  private ObservableList<byte[]> downstreamLog;

  /**
   * Inits a new socket virtual view
   *
   * A socket virtual view can be used to expose a controller on a server to a
   * remote communication handler on a client, using a TCP connection
   *
   * @param namespace Namespace of the view
   * @param port Port to which bind the socket
   *
   * @throws SocketInitializationError when there is an error while
   *                                   initializing the socket
   */
  public SocketVirtualView(String namespace, Integer port) {
    super(namespace);

    this.downstreamLog = new ObservableList<>();

    try {
      new ConnectionHandler(
              new ServerSocket(port),
              (Consumer<byte[]> h) -> this.downstreamLog.subscribe(h),
              this::call
      ).run();
    }
    catch (java.io.IOException e){
      throw new SocketInitializationError(e);
    }
  }

  /**
   * Push a state to the view
   *
   * @param path Path for the view (the same as the model)
   * @param data Data to push to the view
   */
  public void pushState(String path, String data){
    this.broadcastMessage("MODEL_UPDATE", path, data);
  }

  /**
   * Push a state to the view.
   * This function can be used to push updates from child views.
   *
   * @param path Path of the view to deliver the request to
   * @param view Name of the view to show
   */
  public void setView(String path, String view){
    this.broadcastMessage("VIEW_SELECT", path, view);
  }

  /**
   * Broadcast a message to all connected client
   * @param action
   * @param path  Path relative, namespace is added automatically
   * @param data
   */
  private void broadcastMessage(String action, String path, String data){
    String packet = action + "~" + this.addNamespace(path) + "~" + data;
    this.downstreamLog.add(
            java.util.Base64.getEncoder().encode(packet.getBytes())
    );
  }

  private static class ConnectionHandler implements Runnable {
    /**
     * The socket used to accept connections
     */
    private ServerSocket serverSocket;

    /**
     * True if connection loop should be enabled, false otherwise
     */
    private Boolean looping;

    private Consumer<Consumer<byte[]>> downstramHandler;

    private BiConsumer<String, String> upstreamHandler;

    /**
     *
     * @param ss
     * @param dh
     */
    ConnectionHandler(
            ServerSocket ss, Consumer<Consumer<byte[]>> dh, BiConsumer<String, String> uh){
      this.serverSocket = ss;
      this.looping = true;
      this.downstramHandler = dh;
      this.upstreamHandler = uh;
    }

    /**
     * Stop looping the connection listening
     */
    public void stopLoop(){
      looping = false;
    }

    public void run(){
      while (this.looping){
        try (Socket socket = serverSocket.accept()){
          new UpstreamClientHandler(
                  new DataInputStream(socket.getInputStream()),
                  this.upstreamHandler
          ).run();
          this.downstramHandler.accept(
                  DownstreamClientHandler.getHandler(
                          new DataOutputStream(socket.getOutputStream()),
                          socket
                  )
          );
        }
        catch (java.io.IOException e){
          SocketVirtualView.logSocketException(e);
        }
      }
    }
  }

  private static class UpstreamClientHandler implements Runnable{
    private DataInputStream inputStream;
    private BiConsumer<String, String> processor;

    UpstreamClientHandler(DataInputStream in, BiConsumer<String, String> f){
      this.inputStream = in;
      this.processor = f;
    }

    public void run(){
      char c;
      StringBuilder gotString = new StringBuilder();
      byte[] decoded;
      String[] decodedParsed;
      boolean loop = true;

      while (loop) {
        try {
          c = this.inputStream.readChar();

          if ('~' == c){
            // Packet ended
            decoded = java.util.Base64.getDecoder().decode(gotString.toString());
            decodedParsed = Arrays.toString(decoded).split("~");
            this.processDataHandler(decodedParsed);

            gotString.setLength(0);
          }
          else {
            gotString.append(c);
          }
        }
        catch (java.io.IOException e){
          loop = false;
          logSocketException(e);
        }
      }

      // Leaving thread to the garbage collector
    }

    private void processDataHandler(String[] data){
      if ("METHOD_INVOC".equals(data[0])) {
        this.processor.accept(data[1], data[2]);
      }
    }
  }

  private static class DownstreamClientHandler {
    static Consumer<byte[]> getHandler(OutputStream out, Socket s){
      return (byte[] data) -> {
        try {
          out.write(data);
        }
        catch (java.io.IOException e){
          logSocketException(s, e);
        }
      };
    }
  }

  /**
   * Thrown when there is an error while initializing the socket for this
   * virtual view
   */
  public static class SocketInitializationError extends RuntimeException {
    SocketInitializationError(Exception e){
      super(e);
    }

    @Override
    public String toString() {
      return "Error while initializing the socket";
    }
  }

  private static void logSocketException(Exception e){
    Logger.getGlobal().throwing(
            SocketVirtualView.class.getCanonicalName(),
            "logSocketException",
            e
    );
  }

  private static void logSocketException(Socket s, Exception e){
    logSocketException(e);

    try {
      s.close();
    }
    catch (java.io.IOException ex){
      logSocketException(ex);
    }
  }

  public static class ObservableList<P extends Object> {
    private List<P> list;
    private List<Consumer<P>> handlerList;

    ObservableList() {
      this.list = Collections.synchronizedList(new ArrayList<>());
      this.handlerList = Collections.synchronizedList(new ArrayList<>());
    }

    public void add(P data){
      int listStart = this.list.size();

      this.list.add(data);

      this.handlerList.forEach(
              (Consumer<P> handler) -> this.notify(listStart, handler)
      );
    }

    void subscribe(Consumer<P> handler){
      this.handlerList.add(handler);
      this.notify(0, handler);
    }

    private void notify(int listId, Consumer<P> handler){
      this.list.subList(listId, this.list.size()).forEach(
              handler
      );
    }
  }
}
