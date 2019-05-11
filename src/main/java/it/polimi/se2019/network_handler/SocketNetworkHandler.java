package it.polimi.se2019.network_handler;

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
public class SocketNetworkHandler extends AbstractNetworkHandler {
  /**
   * Inits a new socket virtual view.
   *
   * A socket network handler can be used to connect to a remote socket virtual
   * view, and receive/send updates to them using a TCP connection
   *
   * @param namespace Namespace of the view
   * @param ip Ip of the server
   * @param port Port of the server
   *
   * @throws SocketInitializationError when there is an error while
   *                                   initializing the socket
   */
  public SocketNetworkHandler(String namespace, String ip, Integer port) {
    super(namespace, (String path, String data) -> {/* TODO */});

    try {
      new NetworkReceiver(
              new ServerSocket(port),
              (String path, String data) -> {/* TODO */},
              (String path, String data) -> {/* TODO */},
      ).run();
    }
    catch (java.io.IOException e){
      throw new SocketInitializationError(e);
    }
  }

  private static class NetworkReceiver implements Runnable{
    private Socket socket;
    private BiConsumer<String, String> modelUpdateHandler;
    private BiConsumer<String, String> viewUpdateHandler;
    private DataInputStream inputStream;

    NetworkReceiver(
            Socket s, BiConsumer<String, String> m, BiConsumer<String, String> v
    ){
      this.socket = s;
      this.modelUpdateHandler = m;
      this.viewUpdateHandler = v;
      this.inputStream = new DataInputStream(this.socket.getInputStream());
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
      if ("MODEL_UPDATE".equals(data[0])) {
        this.modelUpdateHandler.accept(data[1], data[2]);
      }
      else if("VIEW_SELECT".equals(data[0])){
        this.viewUpdateHandler.accept(data[1], data[2]);
      }
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
            SocketNetworkHandler.class.getCanonicalName(),
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
}
