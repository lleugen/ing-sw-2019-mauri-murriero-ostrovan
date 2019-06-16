package it.polimi.se2019.communication.virtual_view.connections_manager;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.FunctionCallEncodable;
import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
import it.polimi.se2019.communication.network.Connection;
import it.polimi.se2019.communication.virtual_view.AssociatedFunctionCall;
import it.polimi.se2019.communication.virtual_view.NoResponseError;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConnectionsManager {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ConnectionsManager";

  /**
   * Contains the association between every connected client and its connection
   */
  private Map<String, ConnectionThread> connections;

  /**
   * Contains the log of data sent to each user.
   * Each time a user logs in, all its history is resent down, to let him
   * resync again to the state he left
   */
  private Map<String, List<ByteBuffer>> history;

  /**
   * TransferQueue for incoming data
   */
  private TransferQueue<AssociatedFunctionCall> inboundTq;

  /**
   * Create a new connection manager
   */
  public ConnectionsManager(){
    this.connections = new HashMap<>();
    this.history = new HashMap<>();
    this.inboundTq = new LinkedTransferQueue<>();
  }

  /**
   * Register a new connection.
   * If there are no connections active for this player, this connection will
   * be registered for the player and added to the manager.
   * If a connection for this player is already being handled by the manager,
   * the NEW connection will be closed, and the old connection will be kept
   * opened.
   *
   * Please note that connection are automatically garbage collected when they
   * crash, so there is no need to add a new connection when a connection is
   * already running for a player
   *
   * @param user Id of the user this connection belongs to
   * @param conn Connection to the user
   */
  public synchronized void register(String user, Connection conn){
    ConnectionThread currentConnection = this.connections.get(user);

    if (currentConnection != null && currentConnection.isRunning()){
      conn.close();
    }
    else {
      ConnectionThread connectionThread = new ConnectionThread(
              conn,
              this.genRecvCallbackForUser(user)
      );
      this.connections.put(
              user,
              connectionThread
      );

      this.history.putIfAbsent(user, new LinkedList<>());
      this.history.get(user).forEach(
            connectionThread::send
      );
    }
  }

  /**
   * Sends an update downto the client
   * Updates are sent in order they are received.
   *
   * @param user    User Id to send the update to
   * @param update  Update to send to the user
   */
  public synchronized void send(String user, ModelViewUpdateEncodable update){
    ByteBuffer encodedUpdate = update.encode();

    this.history.putIfAbsent(user, new LinkedList<>());
    this.history.get(user).add(encodedUpdate);

    ConnectionThread conn = this.connections.get(user);
    if (conn != null){
      conn.send(encodedUpdate);
    }
  }

  /**
   * Gets the list of the currently connected players' id.
   *
   * @return The list of connected players' id
   */
  public synchronized List<String> getConnected(){
    return this.connections.entrySet().stream()
            .filter((Map.Entry<String, ConnectionThread> entry) ->
                    entry.getValue().isRunning()
            )
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
  }

  /**
   * Creates a ReceiveCallback for an user.
   * It accepts only encoded FunctionCalls
   *
   * @param user The id of the user this callback belongs to
   *
   * @return The generated callback
   */
  private ConnectionThread.RecvCallback genRecvCallbackForUser(String user){
    return (ByteBuffer data) -> {
      try {
        FunctionCallEncodable fc = new FunctionCallEncodable();
        fc.decode(data);
        AssociatedFunctionCall afc = new AssociatedFunctionCall(fc, user);
        if (!this.inboundTq.offer(afc)){
          Logger.getLogger(LOG_NAMESPACE).log(
                  Level.WARNING,
                  "Unable to transfer decoded data to the TransferQueue"
          );
        }
      }
      catch (AbstractEncodable.InvalidDataException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Unable to decode function call",
                e
        );
      }
    };
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

    Predicate<AssociatedFunctionCall> filter = this.genFcFilter(
            validUser, validNs, validF
    );
    AssociatedFunctionCall receivedAfc;

    do {
      try {
        receivedAfc = this.inboundTq.poll(
          timeout,
          TimeUnit.SECONDS
        );
      }
      catch (InterruptedException e){
        Thread.currentThread().interrupt();
        throw new NoResponseError(e);
      }
    } while (filter.test(receivedAfc));

    return receivedAfc;
  }

  /**
   * Generates a FunctionCallFilter
   *
   * @param validUser List of valid users for the call
   * @param validNs   List of valid namespaces for the call
   * @param validF    List of valid functions for the call
   *
   * @return true if the call is valid, false otherwise
   */
  private Predicate<AssociatedFunctionCall> genFcFilter(
          List<String> validUser, List<String> validNs, List<String> validF
  ){
    return (AssociatedFunctionCall f) -> {
      boolean ok;

      ok = fcFilterTester(
              f.getUser(), validUser
      );

      ok = ok && fcFilterTester(
              f.getFc().getNs(), validNs
      );

      ok = ok && fcFilterTester(
              f.getFc().getF(), validF
      );

      return ok;
    };
  }

  /**
   * Helper function for the FcFilter
   *
   * Return true if either valid is null, or if test is equal to at least one
   * element of valid
   *
   * @param test  String to test against the valid list
   * @param valid List of valid strings
   *
   * @return true if the test is valid, false otherwise
   */
  private static boolean fcFilterTester(String test, List<String> valid){
    boolean ok = false;

    if (valid == null){
      ok = true;
    }
    else {
      for (String s : valid) {
        if (test.equals(s)){
          ok = true;
        }
      }
    }

    return ok;
  }
}
