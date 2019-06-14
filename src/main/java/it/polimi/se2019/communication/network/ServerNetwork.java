package it.polimi.se2019.communication.network;

public class ServerNetwork {
  /**
   * Connection handler.
   *
   * The underlying implementation should call this each time a new connection
   * is received
   */
  protected Handler.Connection connectionHandler;

  /**
   * Disconnection handler.
   *
   * The underlying implementation should call this each time a connected client
   * disconnects
   */
  protected Handler.Disconnection disconnectionHandler;

  /**
   * Starts the server
   */
  public void start(){ }

  /**
   * Inits a new ServerNetwork
   *
   * @param c connection handler to use for accepting new connection
   * @param d disconnection handler to use to notify about lost clients
   */
  protected ServerNetwork (Handler.Connection c, Handler.Disconnection d){
    this.connectionHandler = c;
    this.disconnectionHandler = d;
  }
}
