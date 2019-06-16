package it.polimi.se2019.communication.virtual_view;

import it.polimi.se2019.communication.network.Connection;
import it.polimi.se2019.communication.network.server.ServerNetwork;
import it.polimi.se2019.communication.virtual_view.connections_manager.ConnectionsManager;

import java.util.List;

public class VirtualView {
  /**
   * Reference to the currently opened network interface
   */
  private ServerNetwork ni;

  /**
   *  Contains a reference to the ConnectionManager associated to this VV
   */
  private ConnectionsManager cm;

  /**
   * Init a new VirtualView
   *
   * @param ni NetworkInterface to use to accept new connection
   */
  public VirtualView(ServerNetwork ni){
    this.ni = ni;
    this.ni.setConnectionHandler(this::connectionHandler);
    this.cm = new ConnectionsManager();
  }

  /**
   * Gets the list of currently connected players
   *
   * @return The list of currently connected users id
   */
  public List<String> getConnectedPlayer(){
    return this.cm.getConnected();
  }

  /**
   * Connection Handler for the network interface
   */
  private void connectionHandler(Connection connection){
    new Thread(
            new ConnectionHandler(
                    connection,
                    this::loggedinHandler
            )
    ).start();
  }

  /**
   * Handler for successfully logged in connections
   *
   * @param user  Id of the user the connection belongs to
   * @param conn  Connection of the user
   */
  private void loggedinHandler(String user, Connection conn){
    this.cm.register(user, conn);
  }
}
