/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines a connection to a remote endpoint
 */
public final class Connection {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "Connection";

  /**
   * Reference to the DataInputStream this connection is bound to
   */
  private final DataInputStream inputStream;

  /**
   * Reference to the DataOutputStream this connection is bound to
   */
  private final DataOutputStream outputStream;

  /**
   * Transfer queue for data to send
   */
  private final TransferQueue<byte[]> downstreamTq;

  /**
   * Contains buffered data. See receive callback null
   */
  private final List<byte[]> recvBuffer;

  /**
   * Callback for received data
   */
  private Consumer<byte[]> receiveCb;

  /**
   * Callback for disconnection
   */
  private Consumer<Connection> disconnectionCb;

  /**
   * True if the connection is in a clean state, false if it crashes
   */
  private AtomicBoolean ok = new AtomicBoolean(true);

  /**
   * Create a new connection
   *
   * @param in  InputStream this socket should receive data from
   * @param out OutputStream this socket should send data to
   */
  public Connection (InputStream in, OutputStream out){
    this.inputStream = new DataInputStream(in);
    this.outputStream = new DataOutputStream(out);

    this.downstreamTq = new LinkedTransferQueue<>();
    this.recvBuffer = Collections.synchronizedList(new LinkedList<>());

    this.setDisconnectionCb(null);
    this.setReceiveCallback(null);

    new Thread(
            this::sendThread
    ).start();

    new Thread(
            this::receiveThread
    ).start();
  }

  /**
   * Sends data to the other end
   *
   * @param data data to send to the other end. Data are sent async
   */
  public void send(byte[] data) {
    if (!this.downstreamTq.offer(data)){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to transfer data to the tq"
      );
    }
  }

  /**
   * Close the currently open connection.
   */
  public void close(){
    this.ok.set(false);
    this.disconnectionCb.accept(this);
  }

  /**
   * Set the callback for received data.
   * This callback will be called synchronously (referenced to this connection)
   * each time a new datum is received.
   *
   * The first param of the callback is the received datum
   *
   * @param cb The callback to set. Null to disable the callback
   *
   * __WARN__: When the callback is disabled, received data is buffered.
   *           When a new callback is set, unprocessed data is sent to the
   *           new callback before sending new data.
   *           To discard data, set a callback that does no processing
   */
  public synchronized void setReceiveCallback(Consumer<byte[]> cb){
    this.receiveCb = cb;
    this.dispatchReceivedData();
  }

  /**
   * Attempt to dispatch buffered received data.
   * Data are dispatched only if the receive callback is not null
   */
  private synchronized void dispatchReceivedData(){
    if (this.receiveCb != null){
      while (!this.recvBuffer.isEmpty()){
        byte[] data = this.recvBuffer.get(0);
        this.recvBuffer.remove(0);

        // Remove before dispatch to avoid loop (the called function register a
        // new handler, the new handler process this data
        this.receiveCb.accept(data);
      }
      this.recvBuffer.forEach(this.receiveCb);
      this.recvBuffer.clear();
    }
  }

  /**
   * Set the callback for received data.
   * This callback will be called (synchronously referenced to this connection)
   * each time a new datum is received.
   *
   * The first param of the callback is the received datum
   *
   * @param cb The callback to set. Null to disable the callback
   */
  public synchronized void setDisconnectionCb(Consumer<Connection> cb){
    if (cb == null){
      this.disconnectionCb = (Connection conn) -> {};
    }
    else {
      this.disconnectionCb = cb;
    }
  }

  /**
   * Thread for receiving data.
   *
   * Receive data from the inputStream, parses them as a ByteArray, then
   * transfer them using the receiveCallback.
   *
   * If the stream is broken it closes the connection
   */
  private void receiveThread(){
    while (this.ok.get()){
      byte[] toReturn;
      try {
        int length = this.inputStream.readInt();

        toReturn = new byte[length];

        int received = 0;

        while (received < length) {
          received += this.inputStream.read(
                  toReturn,
                  received,
                  (length - received)
          );
        }

        this.recvBuffer.add(toReturn);
        this.dispatchReceivedData();
      }
      catch (IOException e) {
        Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "InputStream is broken",
              e
        );
        this.close();
      }
    }

    try {
      this.inputStream.close();
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to properly close InputStream",
              e
      );
    }
  }

  /**
   * Thread for sending data.
   *
   * Wait for data in the TransferQueue and send them through the OutputStream
   *
   * If the stream is broken it closes the connection
   */
  private void sendThread(){
    while (this.ok.get()){
      try {
        byte[] data = this.downstreamTq.poll();

        if (data != null) {
          this.outputStream.writeInt(data.length);
          this.outputStream.write(data);

          this.outputStream.flush();
        }
      }
      catch (IOException e) {
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "OutputStream is broken",
                e
        );

        this.close();
      }
    }

    try {
      this.outputStream.close();
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to properly close OutputStream",
              e
      );
    }
  }
}
