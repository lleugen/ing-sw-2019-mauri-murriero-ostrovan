/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network.server;

import it.polimi.se2019.engine.network.Connection;

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
   * Init a new ServerNetwork
   */
  protected ServerNetwork(){
    this.connectionHandler = ((Connection connection) -> {});
  }

  /**
   * Sets the connectionHandler for this ServerNetwork
   *
   * @param c ConnectionHandler to use for accepting new connection
   */
  public void setConnectionHandler(ConnectionHandler c){
    this.connectionHandler = c;
  }

  /**
   * Defines a connection handler
   * The parameter contains the, already init, connection accepted
   */
  @FunctionalInterface
  public interface ConnectionHandler extends Consumer<Connection> { }
}
