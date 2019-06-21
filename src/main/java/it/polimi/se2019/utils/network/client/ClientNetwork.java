package it.polimi.se2019.utils.network.client;

import it.polimi.se2019.utils.network.Connection;

/**
 * General definition of a Client Network interface
 */
public class ClientNetwork {
  /**
   * Reference to the currently active connection
   */
  private Connection connection;

  /**
   * Init a new ClientNetwork
   */
  protected ClientNetwork(){
  }

  /**
   * Gets the currently opened connection
   *
   * @return the currently opened connection
   */
  public Connection getConnection(){
    return this.connection;
  }

  /**
   * Sets the currently opened connection.
   * Underlying implementations should call this function after opening a
   * new connection
   *
   * @param connection the currently opened connection
   */
  protected void setConnection(Connection connection){
    this.connection = connection;
  }
}
