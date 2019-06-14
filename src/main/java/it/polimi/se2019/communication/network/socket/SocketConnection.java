package it.polimi.se2019.communication.network.socket;

import it.polimi.se2019.communication.encodable.Encodable;
import it.polimi.se2019.communication.network.Connection;
import it.polimi.se2019.communication.network.InitializationError;
import it.polimi.se2019.communication.network.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

// YES - PACKAGE PRIVATE
class SocketConnection implements Connection {
  /**
   * Namespace this class logs to
   */
  public static final String LOG_NAMESPACE = "SocketConnection";

  /**
   * Id of the user this socket belongs to
   */
  private String user;

  /**
   * Contains a reference to the currently connected socket
   * Null if the socket is not connected
   */
  private Socket s;

  /**
   * Contains the reference to the receiving thread
   */
  SocketRecvThread recvThread;

  /**
   * Contains the reference to the sending thread
   */
  SocketSendThread sendThread;

  /**
   * Init a new ConnectedSocket.
   *
   * Login will be handled by this class (first message passed on the socket
   * must be login data).
   *
   * @param s Socket to handle
   * @param d Handler for socket disconnection
   *
   * @throws InitializationError if an error occurs while initializing
   *                             the socket
   */
  public SocketConnection(Socket s, Handler.Disconnection d) throws InitializationError {
    this.s = s;
    this.user = this.doLogin(s);
    try {
      this.sendThread = new SocketSendThread(
              new DataOutputStream(s.getOutputStream()),
              () -> d.accept(this.user)
      );

      this.recvThread = new SocketRecvThread(
              new DataInputStream(s.getInputStream()),
              () -> d.accept(this.user)
      );

      this.sendThread.start();
      this.recvThread.start();
    }
    catch (java.io.IOException e){
      throw new InitializationError(e);
    }
  }

  /**
   * Logs the user in.
   * The socket passed as parameter must be new (just inited, no data sent
   * or received).
   *
   * @param s Socket to handle login on
   *
   * @return the id of the user logged in
   *
   * @throws InitializationError if login failed.
   */
  private String doLogin(Socket s) throws InitializationError {
    StringBuilder userSb = new StringBuilder();
    char c;
    System.out.println("Doing Login");
    try {
      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());

      while ((c = in.readChar()) != '\n') {
        userSb.append(c);
      }

      out.writeBoolean(true);
    }
    catch (java.io.IOException e) {
      throw new InitializationError(e);
    }

    return userSb.toString();
  }

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
  public void close() {
    this.recvThread.stop();
    this.sendThread.stop();
    try {
      this.s.close();
    }
    catch (java.io.IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to propertly close socket",
              e
      );
    }
  }

  /**
   * Get the id of the currently connected user
   *
   * @return the id of the currently connected user
   */
  public String getUser(){
    return this.user;
  }

  /**
   * Sends data to the other end
   *
   * @param data data to send to the other end
   *
   * __WARN__ If the underlying connection is broken, this function fails
   *          silently. Please use the connection and disconnection handler to
   *          detect disconnections
   */
  public void send(Encodable data) {
    this.sendThread.send(data);
  }

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
  public byte[] receive() {
    return this.recvThread.recv();
  }
}
