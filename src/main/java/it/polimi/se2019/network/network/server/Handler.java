package it.polimi.se2019.network.network.server;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Handler {
  /**
   * Defines a connection handler
   * The first parameter is the user id who connected to the server
   * The second parameter contains details about the connection
   */
  @FunctionalInterface
  interface Connection extends
          BiConsumer<String, it.polimi.se2019.network.network.Connection> { }

  /**
   * Defines a disconnection handler
   * The only parameter is the id of the client that disconnected
   *
   * __WARN__ In some implementation, this callback may be called multiple
   *          times for a single disconnected client.
   *          This callback must be idempotent
   */
  @FunctionalInterface
  interface Disconnection extends Consumer<String> { }
}
