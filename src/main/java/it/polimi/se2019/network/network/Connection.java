package it.polimi.se2019.network.network;

import it.polimi.se2019.utils.encoder.EncodableInterface;

/**
 * Defines a new connection
 */
public interface Connection {
  /**
   * Sends data to the other end
   *
   * @param data data to send to the other end
   *
   * __WARN__ If the underlying connection is broken, this function fails
   *          silently. Please use the connection and disconnection handler to
   *          detect disconnections
   */
  void send(EncodableInterface data);

  /**
   * Receive data from the connection.
   * This function block till a new data is received
   *
   * @return the received data
   *
   * __WARN__ If the underlying connection is broken, this function fails
   *          silently. Please use the connection and disconnection handler to
   *          detect disconnections
   */
  char[] receive();

  /**
   * Close the currently open connection.
   * This function must be called each time you want to close the currently
   * opened connection.
   *
   * In addition, this function must be called each time a disconnected
   * handler is called about this connection.
   *
   * After calling this function, you must remove any reference to this
   * connection, cause every method stop to work properly
   */
  void close();
}
