package it.polimi.se2019.communication.network;

public class ClientNetwork {
  /**
   * Id of the currently connected client
   */
  private String id;

  /**
   * Currently open connection
   */
  private Connection connection;

  /**
   * Reference to the disconnection callback.
   */
  private Handler.Notification disconnectionCallback;

  /**
   * Inits a new generic ClientNetwork
   *
   *
   *
   * @throws InitializationError if an error occurs while initializing the
   *                             connection
   */
  protected ClientNetwork() throws InitializationError {
    this.disconnectionCallback = () -> {};
  }

  /**
   * Get the id of the currently connected user
   *
   * @return the id of the currently connected user
   */
  public String getUserId() {
    return this.id;
  }

  /**
   * Get the connection associated with this client
   *
   * @return the connection to the server
   */
  Connection getConnection(){
    return this.connection;
  }

  /**
   * Get the id of the currently connected player
   *
   * @return the id of the currently connected player
   */
  public String getId(){
    return this.id;
  }

  /**
   * Sets the disconnection callback
   *
   * The disconnection callback will be called when the a connection to the
   * server is lost.
   * The underlying implementation may decide to handle automatic reconnection.
   * In that case, this function will be called when the implementation decides
   * to give up.
   * Client must be considered dead (and closed using close) when the
   * disconnection callback is called
   */
  protected void setDisconnectionCallback(Handler.Notification dcb) {
    this.disconnectionCallback = dcb;
  }
}
