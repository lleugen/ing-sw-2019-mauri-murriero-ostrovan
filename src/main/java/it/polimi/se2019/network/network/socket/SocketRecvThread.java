package it.polimi.se2019.network.network.socket;

import it.polimi.se2019.network.network.Handler;

import java.io.DataInputStream;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

// YES - PACKAGE PRIVATE
class SocketRecvThread implements Runnable {
  /**
   * Working thread
   */
  private Thread worker;

  /**
   * Namespace this class logs to
   */
  public static final String LOG_NAMESPACE = "SocketRecvThread";

  /**
   * Detects if the thread should be running or not
   */
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Reference to the TransferQueue this thread sends data to
   */
  TransferQueue<byte[]> tq;

  /**
   * Reference to the output stream this thread shoud receive data from
   */
  DataInputStream inputStream;

  /**
   * Disconnection callback
   */
  Handler.Notification disconnectCb;

  /**
   * Create a new ConnectionListenerThread.
   *
   * @param ist InputStream this thread should send data to
   * @param dcb Disconnection callback (it will be called if the underlying
   *            socket fails to close).
   */
  public SocketRecvThread(DataInputStream ist, Handler.Notification dcb){
    this.worker = new Thread(this);
    this.tq = new LinkedTransferQueue<>();
    this.disconnectCb = dcb;
    this.inputStream = ist;
  }

  /**
   * Receive data from the transfer queue.
   *
   * __WARN__: this function blocks till new data is available
   *
   * @return the received data
   */
  public byte[] recv(){
    return this.tq.poll();
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
      this.inputStream.close();
    }
    catch (java.io.IOException e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to properly close input stream",
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
      try {
        int size = this.inputStream.readInt();
        byte[] data = new byte[size];

        if (size == this.inputStream.read(data)){
          throw new java.io.IOException();
        }

        this.tq.add(data);
      }
      catch (java.io.IOException | IllegalStateException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Unable to receive data from socket, or send data to tq",
                e
        );
        this.disconnectCb.ping();
        this.close();
      }
    }
  }
}
