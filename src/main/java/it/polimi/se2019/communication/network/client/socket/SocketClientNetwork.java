package it.polimi.se2019.communication.network.client.socket;

import it.polimi.se2019.communication.network.client.ClientNetwork;
import it.polimi.se2019.communication.network.Connection;
import it.polimi.se2019.communication.network.InitializationError;

import java.io.IOException;
import java.net.Socket;

/**
 * Implementation of the Socket Network for a client
 */
public class SocketClientNetwork extends ClientNetwork {
  /**
   * Init a new socket client network
   *
   * @param host  Hostname to connect to
   * @param port  Port to connect to
   *
   * @throws InitializationError if there is an error initializing the socket
   */
  public SocketClientNetwork(String host, int port)
          throws InitializationError {
    super();
    Socket s = initSocket(host, port);
    try {
      this.setConnection(
              new Connection(
                      s.getInputStream(),
                      s.getOutputStream(),
                      () -> {}
              )
      );
    }
    catch (IOException e){
      throw new InitializationError(e);
    }
  }

  /**
   * Init the socket connection
   *
   * @param host Hostname to connect to
   * @param port Port to connect to
   *
   * @return The opened socket
   *
   * @throws InitializationError if errors occurs while initializing the socket
   */
  private static Socket initSocket(String host, int port)
          throws InitializationError {
    try {
      Socket socket = new Socket(host, port);
      socket.setKeepAlive(true);
      return socket;
    }
    catch (IOException e) {
      throw new InitializationError(e);
    }
  }
}
