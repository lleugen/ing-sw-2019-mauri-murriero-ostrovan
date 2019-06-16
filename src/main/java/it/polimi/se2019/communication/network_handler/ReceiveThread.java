package it.polimi.se2019.communication.network_handler;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
import it.polimi.se2019.communication.network.Connection;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

// YES - PACKAGE PRIVATE
class ReceiveThread implements Runnable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ReceiveThread";

  /**
   * Reference to the receive callback for this ReceiveThread
   */
  private ReceiveCallback receiveCallback;

  /**
   * Reference to the DisconnectCallback for this ReceiveThread
   */
  private DisconnectCb disconnectCb;

  /**
   * Reference to the connection this thread is handling
   */
  private Connection connection;

  /**
   * True if the thread should be running, false otherwise
   */
  private AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Init a new receiving thread.
   * A receiving thread listens for updates from the server, and delivers them
   * through rcb
   * @param conn
   * @param dcb
   */
  ReceiveThread(
          Connection conn, DisconnectCb dcb, ReceiveCallback rcb) {
    this.connection = conn;
    this.disconnectCb = dcb;
    this.receiveCallback = rcb;
  }

  /**
   * Stops the current thread from receiving data.
   * After stopping the thread, you must create a new thread for accepting
   * data again
   */
  public void stop(){
    this.running.set(false);
  }

  /**
   * Listens for data on the receiving end of the connection and, if they
   * can be decoded as a ModelViewUpdate, decodes them then delivers them via
   * the rcb
   */
  public void run(){
    while (this.running.get()){
      ModelViewUpdateEncodable update = new ModelViewUpdateEncodable();

      try {
        update.decode(this.connection.receive());
        this.receiveCallback.accept(update);
      }
      catch (Connection.DisconnectedError e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Server Disconnected",
                e
        );
        this.running.set(false);
      }
      catch (AbstractEncodable.InvalidDataException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Received Invalid Data",
                e
        );
      }
    }

    this.connection.close();
    this.disconnectCb.ping();
  }

  /**
   * Definition of the ReceiveCallback.
   * This function is called each time a new message is received from the
   * network
   */
  @FunctionalInterface
  public interface ReceiveCallback extends Consumer<ModelViewUpdateEncodable> {}
}
