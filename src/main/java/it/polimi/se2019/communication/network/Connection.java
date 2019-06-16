package it.polimi.se2019.communication.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines a connection to a remote endpoint
 */
public class Connection {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "Connection";

  /**
   * Reference to the DataInputStream this connection is bound to
   */
  private DataInputStream inputStream;

  /**
   * Reference to the DataOutputStream this connection is bound to
   */
  private DataOutputStream outputStream;

  /**
   * Reference to che closing handler
   */
  private CloseHandler closeHandler;

  /**
   * Create a new connection
   *
   * @param in  InputStream this socket should receive data from
   * @param out OutputStream this socket should send data to
   * @param ch  Handler for closing the connection
   */
  public Connection (InputStream in, OutputStream out, CloseHandler ch){
    this.inputStream = new DataInputStream(in);
    this.outputStream = new DataOutputStream(out);

    this.closeHandler = ch;
  }

  /**
   * Sends data to the other end
   *
   * @param data data to send to the other end
   *
   * @throws DisconnectedError if the underlying connection is broken
   */
  public synchronized void send(ByteBuffer data) throws DisconnectedError {
    try {
      byte[] byteData = new byte[data.capacity()];
      data.get(byteData);

      this.outputStream.writeInt(byteData.length);
      this.outputStream.write(byteData);

      this.outputStream.flush();
    }
    catch (IOException e){
      throw new DisconnectedError(e);
    }
  }

  /**
   * Receive data from the connection.
   * This function block till a new data is received
   *
   * @return the received data
   *
   * @throws DisconnectedError if the underlying connection is broken
   */
  public synchronized ByteBuffer receive() throws DisconnectedError {
    ByteBuffer toReturn;
    try {
      int length = this.inputStream.readInt();
      byte[] data = new byte[length];

      if (length != this.inputStream.read(data)){
        throw new DisconnectedError();
      }

      toReturn =  ByteBuffer.allocate(length).put(data);
      toReturn.clear();
      return toReturn;
    }
    catch (IOException e){
      throw new DisconnectedError(e);
    }
  }

  /**
   * Close the currently open connection.
   * This function must be called each time you want to close the currently
   * opened connection.
   *
   * After calling this function, you must remove any reference to this
   * connection, cause every method stop to work properly.
   */
  public synchronized void close(){
    try {
      this.inputStream.close();
      this.outputStream.close();
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to clean up connection",
              e
      );
    }

    this.closeHandler.call();
  }

  /**
   * Thrown when the connection is broken (unable to send/receive data).
   * After catching this exception, the connection must be considered no longer
   * working, and therefore any reference to it must be removed.
   * If necessary, you must create a completely new connection
   */
  public static class DisconnectedError extends Exception {
    DisconnectedError(){
      super("The underlying connection is broken");
    }

    DisconnectedError(Throwable e){
      super("The underlying connection is broken", e);
    }
  }

  /**
   * This handler will be called to close the current connection.
   * It is not guaranteed that this caller will be called when a connection is
   * closed.
   * The purpose of this handler is to forcibly close the connection, not to
   * be notified when a connection is closed
   */
  @FunctionalInterface
  public interface CloseHandler {
    void call();
  }
}
