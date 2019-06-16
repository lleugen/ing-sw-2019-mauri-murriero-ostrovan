package it.polimi.se2019.communication.virtual_view;

import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
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
   * Get a function call from the inbound Transfer Queue
   * This function blocks till a valid function call is received, or till
   * timeout is reached.
   *
   * __WARN__ Timeout may be approximate.
   *
   * @param timeout   Timeout in second to wait before raising exception
   *
   * @param validUser List of valid users id for this call
   * @param validNs   List of valid namespaces for this call
   * @param validF    List of valid function names for this call
   *
   * @return The first received call
   *
   * @throws NoResponseError if no valid function call can be retrived in time
   */
  public AssociatedFunctionCall receive(
          int timeout, List<String> validUser,
          List<String> validNs, List<String> validF
  ) throws NoResponseError {
    return this.cm.receive(timeout, validUser, validNs, validF);
  }

  /**
   * Sends an update downto the client
   * Updates are sent in order they are received.
   *
   * @param user    User Id to send the update to
   * @param update  Update to send to the user
   */
  public synchronized void send(String user, ModelViewUpdateEncodable update){
    this.cm.send(user, update);
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
