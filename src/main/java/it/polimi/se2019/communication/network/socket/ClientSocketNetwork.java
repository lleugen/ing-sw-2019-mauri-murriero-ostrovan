package it.polimi.se2019.communication.network.socket;

import it.polimi.se2019.communication.network.ClientNetwork;
import it.polimi.se2019.communication.network.Handler;
import it.polimi.se2019.communication.network.InitializationError;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocketNetwork extends ClientNetwork {
  /**
   * Init a new socket client network
   *
   * @param dcb   Disconnection callback for this socket
   * @param host  Hostname to connect to
   * @param port  Port to connect to
   * @param login Login name
   *
   * @throws InitializationError if there is an error initializing the socket
   */
  public ClientSocketNetwork(
          Handler.Notification dcb, String host, int port, String login)
          throws InitializationError {
    super();
    this.setDisconnectionCallback(dcb);
    Socket s = this.initSocket(host, port);
    this.doLogin(s, login);
  }

  /**
   * Inits the socket connection
   *
   * @param host Hostname to connect to
   * @param port Port to connecto to
   *
   * @return The opened socket
   *
   * @throws InitializationError if errors occurs while initializing the socket
   */
  private Socket initSocket(String host, int port) throws InitializationError {
    System.out.println("Initing Socket");
    try {
      Socket s = new Socket(host, port);
      System.out.println("Socket Created Successfully");
      return s;
    }
    catch (IOException e) {
      throw new InitializationError(e);
    }
  }

  /**
   * Does login on the new opened socket. This function must be called on a
   * clean socket (no data already sent or received)
   *
   * @param s     Socket to login to
   * @param login UserId to login with
   *
   * @throws InitializationError if errors occurs while logging in
   */
  private void doLogin(Socket s, String login) throws InitializationError {
    System.out.println("Doing Login");
    DataOutputStream outputStream;
    DataInputStream inputStream;

    try {
      outputStream = new DataOutputStream(s.getOutputStream());
      inputStream = new DataInputStream(s.getInputStream());

      outputStream.writeChars(login + "\n");
      outputStream.flush();
      System.out.println("Data Sent");
      if (!inputStream.readBoolean()){
        throw new InitializationError(
                "Unable to login"
        );
      }
    }
    catch (IOException e) {
      throw new InitializationError(e);
    }
    System.out.println("Login Done");
  }
}
