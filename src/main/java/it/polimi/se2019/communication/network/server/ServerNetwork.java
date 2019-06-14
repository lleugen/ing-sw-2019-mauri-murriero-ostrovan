package it.polimi.se2019.communication.network.server;

import it.polimi.se2019.communication.network.Connection;

import java.util.function.Consumer;

/**
 * General definition of a Server Network interface
 */
public class ServerNetwork {
  /**
   * Connection handler.
   *
   * The underlying implementation should call this each time a new connection
   * is received
   */
  protected ConnectionHandler connectionHandler;

  /**
   * Inits a new ServerNetwork
   *
   * @param c ConnectionHandler to use for accepting new connection
   */
  protected ServerNetwork (ConnectionHandler c){
    this.connectionHandler = c;
  }

  /**
   * Defines a connection handler
   * The parameter contains the, already init, connection accepted
   */
  @FunctionalInterface
  public interface ConnectionHandler extends Consumer<Connection> { }
}
