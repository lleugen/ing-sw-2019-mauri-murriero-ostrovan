package it.polimi.se2019.communication.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
   * Reference to the InputStream this connection is bound to
   */
  private DataInputStream inputStream;

  /**
   * Reference to the OutputStream this connection is bound to
   */
  private DataOutputStream outputStream;

  /**
   * Create a new connection
   *
   * @param in  InputStream this socket should receive data from
   * @param out OutputStream this socket should send data to
   */
  public Connection (InputStream in, OutputStream out){
    this.inputStream = new DataInputStream(in);
    this.outputStream = new DataOutputStream(out);
  }

  /**
   * Sends data to the other end
   *
   * @param data data to send to the other end
   *
   * @throws DisconnectedError if the underlying connection is broken
   */
  public void send(ByteBuffer data) throws DisconnectedError {
    try {
      this.outputStream.writeInt(data.capacity());
      this.outputStream.write(data.array());
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
  public ByteBuffer receive() throws DisconnectedError {
    try {
      int length = this.inputStream.readInt();
      byte[] data = new byte[length];

      if (length != this.inputStream.read(data)){
        throw new DisconnectedError();
      }

      return ByteBuffer.allocate(length).put(data);
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
  public void close(){
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
}
