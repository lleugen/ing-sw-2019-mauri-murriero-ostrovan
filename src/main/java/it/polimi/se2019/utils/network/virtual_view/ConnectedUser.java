package it.polimi.se2019.utils.network.virtual_view;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.model.ModelUpdateModel;
import it.polimi.se2019.utils.model.ViewRequestModel;
import it.polimi.se2019.utils.network.Connection;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

// YES - PACKAGE PRIVATE
class ConnectedUser {
  /**
   * List of connections this user is logged on
   */
  private List<Connection> connections;

  /**
   * Last selected view on this user
   */
  private ByteBuffer view;

  /**
   * Last update sent indexed by namespace for this user
   */
  private Map<String, ByteBuffer> lastUpdate;

  /**
   * Create a new connected client
   */
  ConnectedUser(){
    this.connections = Collections.synchronizedList(new LinkedList<>());
    this.view = new ViewRequestModel("").encode();
    this.lastUpdate = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Create a new connected client copying data from another client
   *
   * __WARN__ Only model data and current view are copied, connections are
   *          not duplicated
   */
  ConnectedUser(ConnectedUser base){
    this.connections = Collections.synchronizedList(new LinkedList<>());

    this.view = ByteBuffer.allocate(base.view.capacity()).put(base.view);

    this.lastUpdate = Collections.synchronizedMap(new HashMap<>());
    base.lastUpdate.forEach((String key, ByteBuffer data) ->
      this.lastUpdate.put(
              key,
              ByteBuffer
                      .allocate(data.capacity())
                      .put(data)
      )
    );
  }
  /**
   * Change the selected view on the client
   *
   * @param view Name of the view to select on the client
   */
  synchronized void changeView(String view){
    this.view = new ViewRequestModel(view).encode();

    this.connections.forEach(this::refreshView);
  }

  /**
   * Sent the last view update to a single connection
   *
   * @param conn Connection to send the last update to
   */
  private synchronized void refreshView(Connection conn){
    conn.send(this.view);
  }

  /**
   * Send a model update to the client
   *
   * @param ns    Namespace to send the update to
   * @param model Updated Model
   */
  synchronized void updateModel(String ns, AbstractModel model){
    this.lastUpdate.put(
            ns,
            new ModelUpdateModel(
                    ns,
                    model.encode()
            ).encode()
    );

    this.connections.forEach(
            (Connection conn) -> this.refreshModel(ns, conn)
    );
  }

  /**
   * Send a model update to a single connection
   *
   * @param ns    Namespace of the model to send
   * @param conn  Connection to send the update to
   */
  private synchronized void refreshModel(String ns, Connection conn){
    if (this.lastUpdate.containsKey(ns)){
      conn.send(this.lastUpdate.get(ns));
    }
  }

  /**
   * Register a new connection.
   * When a new connection is registered, the whole current state is sent down
   * to allow them to syncronize with the server
   *
   * @param conn Connection to register
   */
  synchronized void register(Connection conn){
    this.connections.add(conn);

    conn.setDisconnectionCb(this::deregister);

    this.refreshView(conn);
    this.lastUpdate.keySet().forEach(
            (String ns) -> this.refreshModel(ns, conn)
    );
  }

  /**
   * Deregister a connection
   *
   * __WARN__ The connection will not be closed
   *
   * @param conn Connection to deregister
   */
  private synchronized void deregister(Connection conn){
    this.connections.remove(conn);
  }
}
