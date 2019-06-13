package it.polimi.se2019.network.network.server.socket;

import it.polimi.se2019.network.network.InitializationError;
import it.polimi.se2019.network.network.server.Handler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread for accepting new connections
 */
public class ConnectionListenerThread implements Runnable {
  /**
   * Working thread
   */
  private Thread worker;

  /**
   * Namespace this class logs to
   */
  public static final String LOG_NAMESPACE =
          "ConnectionListenerThread";

  /**
   * Contains the reference to the currently open main socket
   */
  private ServerSocket ss;

  /**
   * Detects if the thread should be running or not
   */
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Connection Handler to be used to notify on new connections
   */
  private Handler.Connection connectionHandler;

  /**
   * Disconnection Handler to be used to notify about disconnected clients
   */
  private Handler.Disconnection disconnectionHandler;

  /**
   * Create a new ConnectionListenerThread.
   *
   * @param ss Main socket to listen to (already initialized)
   */
  public ConnectionListenerThread(
          ServerSocket ss, Handler.Connection c, Handler.Disconnection d
  ){
    this.ss = ss;
    this.connectionHandler = c;
    this.disconnectionHandler = d;
    this.worker = new Thread(this);
  }

  /**
   * Starts the thread
   */
  public void start(){
    this.worker.start();
  }

  /**
   * Cancel the current thread and prepares it for being garbage collected
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
   * Run the listener.
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
                (user) -> this.disconnectionHandler.accept(user)
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
