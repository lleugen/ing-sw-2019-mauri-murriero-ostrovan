package it.polimi.se2019.communication.virtual_view;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.LoginDataEncodable;
import it.polimi.se2019.communication.network.Connection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

//YES - PACKAGE PRIVATE
class ConnectionHandler implements Runnable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE =
          "VirtualView.connectionHandlerThread";

  /**
   * Reference to the connection to handle
   */
  private Connection connection;

  /**
   * Timeout for login in seconds.
   * On expiration, the connection is closed
   */
  private static final int CONNECTION_TIMEOUT = 5;

  /**
   * True if the user successfully logged in. False if not.
   * The disconnection thread checks this flags to decide if the connection
   * should be closed or not
   */
  private AtomicBoolean successfullyLoggedIn;

  /**
   * Handler for successfully logged in connections
   */
  private ConnectedHandler ch;

  /**
   * Init a new connection handler (the callback is called in a sync ways,
   * so a client could prevent new users from logging in by just connecting
   * and not sending data
   *
   * @param connection connection to handle
   * @param ch         The handler to call when the connection is successfully
   *                   logged in
   */
  public ConnectionHandler(Connection connection, ConnectedHandler ch){
    this.connection = connection;
    this.successfullyLoggedIn = new AtomicBoolean(false);
    this.ch = ch;
    ScheduledExecutorService kickingExecutor =
            Executors.newScheduledThreadPool(1);
    kickingExecutor.schedule(
            this::kick,
            CONNECTION_TIMEOUT,
            TimeUnit.SECONDS
    );
  }

  /**
   * Handles login, and, on success, adds the client to the known
   * clients map.
   */
  public void run(){
    try {
      LoginDataEncodable loginData = new LoginDataEncodable("");
      loginData.decode(this.connection.receive());
      this.successfullyLoggedIn.set(true);
      this.ch.accept(loginData.getUser(), connection);
    }
    catch (Connection.DisconnectedError | AbstractEncodable.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to log the user in",
              e
      );
      this.kick();
    }
  }

  /**
   * Kicks out the currently connected player.
   * Called after CONNECTION_TIMEOUT from the initialization of this class
   */
  private void kick(){
    if (!this.successfullyLoggedIn.get()){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.INFO,
              "Kicking the user out"
      );
      this.connection.close();
    }
  }

  /**
   * Callback called when a connection is successfully logged in
   *
   * The first param is the user id the connection belongs to
   * The second param is the connection itself
   */
  public interface ConnectedHandler extends BiConsumer<String, Connection> { }
}
