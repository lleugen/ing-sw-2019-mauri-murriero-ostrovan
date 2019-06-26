/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network.virtualview;

import it.polimi.se2019.engine.model.AbstractModel;
import it.polimi.se2019.engine.model.ViewUpdateModel;
import it.polimi.se2019.engine.network.Connection;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;

// YES - PACKAGE PRIVATE
class ConnectedUser {
  /**
   * List of connections this user is logged on
   */
  private List<Connection> connections;

  /**
   * Updated update to send to the clients
   */
  private ViewUpdateModel update;

  /**
   * Last update sent indexed by namespace for this user
   */
  private Map<String, byte[]> lastModelUpdate;

  /**
   * Create a new connected client
   */
  ConnectedUser(){
    this.connections = Collections.synchronizedList(new LinkedList<>());
    this.update = new ViewUpdateModel();
    this.lastModelUpdate = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Create a new connected client copying data from another client
   *
   * __WARN__ Only model data and current view are copied, connections are
   *          not duplicated
   */
  ConnectedUser(ConnectedUser base){
    this.update = new ViewUpdateModel(base.update);
    this.connections = Collections.synchronizedList(new LinkedList<>());

    this.lastModelUpdate = Collections.synchronizedMap(new HashMap<>());
    base.lastModelUpdate.forEach((String key, byte[] data) ->
      this.lastModelUpdate.put(
              key,
              AbstractModel.copyArr(data)
      )
    );
  }

  /**
   * See docs for ViewModelUpdate.addView
   *
   * @param view Namespace of the view to render
   */
  synchronized void addView(String view){
    this.update.addView(view);
    this.refreshConnections();
  }

  /**
   * See docs for ViewModelUpdate.replaceView
   *
   * @param view View to replace in the list of currently opened view
   */
  synchronized void replaceView(String view){
    this.update.replaceView(view);
    this.refreshConnections();
  }

  /**
   * Refresh connections by sending down the last available update
   */
  private synchronized void refreshConnections(){
    byte[] data = AbstractModel.bufToArray(this.update.encode());

    this.connections.forEach((Connection c) -> c.send(data));
  }

  /**
   * Send a model update to the client
   *
   * @param ns    Namespace to send the update to
   * @param model Updated Model
   */
  synchronized void updateModel(String ns, AbstractModel model){
    byte[] data = AbstractModel.bufToArray(model.encode());

    this.lastModelUpdate.put(ns, data);
    this.update.setModel(ns, data);

    this.refreshConnections();
  }

  /**
   * Register a new connection.
   * When a new connection is registered, the whole current state is sent down
   * to allow them to synchronize with the server
   *
   * @param conn Connection to register
   */
  synchronized void register(Connection conn){
    this.connections.add(conn);

    conn.setDisconnectionCb(this::deregister);

    this.lastModelUpdate.forEach((String ns, byte[] data) -> {
      this.update.setModel(ns, data);
      this.refreshConnections();
    });
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
