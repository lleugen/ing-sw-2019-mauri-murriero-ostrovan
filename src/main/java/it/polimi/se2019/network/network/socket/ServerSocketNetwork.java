package it.polimi.se2019.network.network.socket;

import it.polimi.se2019.network.network.InitializationError;
import it.polimi.se2019.network.network.Handler;
import it.polimi.se2019.network.network.ServerNetwork;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Socket network connection for server
 */
public class ServerSocketNetwork extends ServerNetwork implements Runnable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE =  "ServerSocketNetwork";

  /**
   * Reference to the listening socket currently running
   */
  private ServerSocketNetwork listener;

  /**
   * Working thread
   */
  private Thread worker;

  /**
   * Contains the reference to the currently open main socket
   */
  private ServerSocket ss;

  /**
   * Detects if the thread should be running or not
   */
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Init a new ServerSocketNetwork
   *
   * @param c     ConnectionHandler for this server
   * @param d     DisconnectionHandler for this server
   * @param port  Port this server should bind to
   *
   * @throws InitializationError If an error occurs while initializing
   *                             the server
   */
  public ServerSocketNetwork(
          Handler.Connection c, Handler.Disconnection d, Integer port
  ) throws InitializationError {

    super(c, d);

    try {
      this.ss = new ServerSocket(port);
    }
    catch (java.io.IOException e){
      throw new InitializationError(e);
    }

    this.worker = new Thread(this);
  }

  /**
   * Close the SocketServerNetwork, by stopping accepting connections.
   * Currently open connections will be left open
   */
  public void close(){
    this.running.set(false);

    try {
      this.ss.close();
    }
    catch (java.io.IOException e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to properly stop master socket",
              e
      );
    }
    finally {
      this.ss = null;
    }
  }

  /**
   * Cancel the current thread and prepares it for being garbage collected
   */
  public void interrupt() {
    this.close();
  }

  /**
   * Cancel the current thread and prepares it for being garbage collected
   */
  public void stop(){
    this.close();
  }

  /**
   * Starts the server (begins listening to connections)
   */
  public void start(){
    this.run();
  }

  /**
   * Runs the listening thread
   *
   * __WARN__ After cancelling this thread in any way, you cannot call run
   *          again. You must create a new thread
   */
  public void run(){
    while (this.running.get()){
      Socket s;

      try {
        s = ss.accept();

        SocketConnection newSocket = new SocketConnection(
                s,
                this.disconnectionHandler
        );

        this.connectionHandler.accept(
                newSocket.getUser(),
                newSocket
        );
      }
      catch (java.io.IOException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Error while accepting new connection",
                e
        );
      }
      catch (InitializationError e) {
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.INFO,
                "Unable to initialize new connection",
                e
        );
      }
    }
  }
}
