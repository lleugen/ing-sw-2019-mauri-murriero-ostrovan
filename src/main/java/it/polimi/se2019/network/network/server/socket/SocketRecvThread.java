package it.polimi.se2019.network.network.server.socket;

import it.polimi.se2019.network.network.server.Handler;
import it.polimi.se2019.utils.encoder.EncodableInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketRecvThread implements Runnable {
  /**
   * Working thread
   */
  private Thread worker;

  /**
   * Namespace this class logs to
   */
  public static final String LOG_NAMESPACE =
          "SocketRecvThread";

  /**
   * Detects if the thread should be running or not
   */
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Reference to the TransferQueue this thread sends data to
   */
  TransferQueue<char[]> tq;

  /**
   * Reference to the output stream this thread shoud receive data from
   */
  DataInputStream inputStream;

  /**
   * Disconnection callback
   */
  Handler.Disconnection disconnectCb;

  /**
   * Create a new ConnectionListenerThread.
   *
   * @param ist InputStream this thread should send data to
   * @param dcb Disconnection callback (it will be called if the underlying
   *            socket fails to close). Param will always be an empty string
   */
  public SocketRecvThread(DataInputStream ist, Handler.Disconnection dcb){
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
  public char[] recv(){
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
      StringBuilder data = new StringBuilder();

      try {
        for (int i = 0; i < this.inputStream.readLong(); i++){
          data.append(this.inputStream.readChar());
        }
      }
      catch (java.io.IOException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Unable to receive data from socket",
                e
        );
        this.disconnectCb.accept("");
        System.out.println("Calling cb");
        this.close();
      }
    }
  }
}
