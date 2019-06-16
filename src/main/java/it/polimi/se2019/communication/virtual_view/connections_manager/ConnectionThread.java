package it.polimi.se2019.communication.virtual_view.connections_manager;

import it.polimi.se2019.communication.network.Connection;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

//YES - PACKAGE PRIVATE
class ConnectionThread implements Runnable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ConnectionThread";

  /**
   * Reference to the connection this thread handles
   */
  private Connection connection;

  /**
   * True if the thread is alive, or not
   */
  private AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Receiving callback for incoming messages
   */
  private RecvCallback recvCallback;

  /**
   * Init a new ConnectionThread
   *
   * @param conn Connection this thread must handle
   * @param cb   Receiving callback for new data
   */
  ConnectionThread(Connection conn, RecvCallback cb){
    this.connection = conn;
    this.recvCallback = cb;
  }

  /**
   * Stop the ConnectionThread, by stopping sending data to the
   * receiveCallback, and closing the currently opened connection
   */
  public void stop(){
    this.running.set(false);
  }

  /**
   * Check if the thread is alive
   * @return true if the thread is alive, false if not (can therefore be
   *         dereference)
   */
  public boolean isRunning(){
    return this.running.get();
  }

  /**
   *  Listens for incoming message on the receiving end of the conneciton,
   *  and sends them to the recvCallback
   */
  public void run(){
    while (this.running.get()){
      try {
        this.recvCallback.accept(
                this.connection.receive()
        );
      }
      catch (Connection.DisconnectedError e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Client Disconnected",
                e
        );
        this.running.set(false);
      }
    }

    this.connection.close();
  }

  /**
   * Send data to the other end of the connection
   *
   * @param data Data to send to the other end
   */
  public void send(ByteBuffer data){
    try {
      this.connection.send(data);
    }
    catch (Connection.DisconnectedError e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Client Disconnected",
              e
      );
      this.running.set(false);
    }
  }

  /**
   * Receiving callback.
   * The first parameter of the function is the received message
   */
  @FunctionalInterface
  public interface RecvCallback extends Consumer<ByteBuffer> { }
}
