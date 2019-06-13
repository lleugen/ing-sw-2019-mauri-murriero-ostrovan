package it.polimi.se2019.network.network.server.socket;
import it.polimi.se2019.network.network.InitializationError;
import it.polimi.se2019.network.network.server.Handler;
import it.polimi.se2019.network.network.server.ServerNetwork;

import java.net.ServerSocket;

public class SocketServerNetwork extends ServerNetwork {
  /**
   * Reference to the listening socket currently running
   */
  ConnectionListenerThread listener;

  /**
   * Init a new ServerSocketNetwork
   *
   * @param c     ConnectionHandler for this server
   * @param d     DisconnectionHandler for this server
   * @param port  Port this server should bind to
   *
   * @throws InitializationError If an error occurs while initializing
   *                             the server
   */
  public SocketServerNetwork(
          Handler.Connection c, Handler.Disconnection d, Integer port
  ) throws InitializationError {

    super(c, d);

    ServerSocket ss;

    try {
      ss = new ServerSocket(port);
    }
    catch (java.io.IOException e){
      throw new InitializationError(e);
    }

    this.listener = new ConnectionListenerThread(
            ss,
            this.connectionHandler,
            this.disconnectionHandler
    );

    this.listener.start();
  }

  /**
   * Close the SocketServerNetwork, by stopping accepting connections.
   * Currently open connections will be left open
   */
  public void close(){
    this.listener.close();
  }
}
