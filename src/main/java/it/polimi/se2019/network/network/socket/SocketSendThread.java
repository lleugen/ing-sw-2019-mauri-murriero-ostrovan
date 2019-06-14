package it.polimi.se2019.network.network.socket;

import it.polimi.se2019.network.encodable.Encodable;
import it.polimi.se2019.network.network.Handler;

import java.io.DataOutputStream;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

// YES - PACKAGE PRIVATE
class SocketSendThread implements Runnable {
  /**
   * Working thread
   */
  private Thread worker;

  /**
   * Namespace this class logs to
   */
  public static final String LOG_NAMESPACE = "SocketSendThread";

  /**
   * Detects if the thread should be running or not
   */
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Reference to the TransferQueue this thread takes data from
   */
  TransferQueue<Encodable> tq;

  /**
   * Reference to the output stream this thread shoud send data to
   */
  DataOutputStream outputStream;

  /**
   * Disconnection callback
   */
  Handler.Notification disconnectCb;

  /**
   * Create a new ConnectionListenerThread.
   *
   * @param ost Outputstream this thread should send data to
   * @param dcb Disconnection callback (it will be called if the underlying
   *            socket fails to close).
   */
  public SocketSendThread(DataOutputStream ost, Handler.Notification dcb){
    this.worker = new Thread(this);
    this.tq = new LinkedTransferQueue<>();
    this.disconnectCb = dcb;
    this.outputStream = ost;
  }

  /**
   * Sends data to the transfer queue
   *
   * @param data Data to send to the other end
   */
  public void send(Encodable data){
    if (!this.tq.add(data)){
      Logger.getLogger(LOG_NAMESPACE).log(
             Level.WARNING,
             "Unable to insert data "
      );
    }
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
      this.outputStream.close();
    }
    catch (java.io.IOException e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to properly close output stream",
              e
      );
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
      Encodable data;

      data = this.tq.poll();
      if (data != null){
        try {
          byte[] encodedData = data.encode().array();
          this.outputStream.writeInt(encodedData.length);
          this.outputStream.write(encodedData);
        }
        catch (java.io.IOException e){
          Logger.getLogger(LOG_NAMESPACE).log(
                  Level.WARNING,
                  "Unable to send data to the socket",
                  e
          );
          this.disconnectCb.ping();
          this.close();
        }
      }
    }
  }
}
