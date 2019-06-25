/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network.virtualview;

import it.polimi.se2019.engine.model.AbstractModel;
import it.polimi.se2019.engine.model.FunctionCallModel;
import it.polimi.se2019.engine.model.LoginDataModel;
import it.polimi.se2019.engine.network.Connection;
import it.polimi.se2019.engine.network.server.ServerNetwork;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VirtualView {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "VirtualView";

  /**
   * Timeout for login in seconds.
   * On expiration, the connection is closed
   */
  private static final int LOGIN_TIMEOUT = 5;

  /**
   * Reference to the currently opened network interface
   */
  private ServerNetwork ni;

  /**
   * Contains every connection currently active, indexed by user id.
   */
  private final Map<String, ConnectedUser> connections = new HashMap<>();

  /**
   * Contains the virtual broadcast user.
   * There are no connections registered here, but it is used to save broadcast
   * data.
   * New ConnectedUser for unknown user will be initialized using those data
   */
  private final ConnectedUser broadcast = new ConnectedUser();

  /**
   * Contains the list of callbacks associated to each function and namespace
   */
  private final Map<String, Map<String, FunctionCallCallback>> cbs = new HashMap<>();

  /**
   * Create a new VirtualView
   *
   * @param ni NetworkInterface this VirtualView should bind to.
   *           It is up to you to ensure this ni is dedicated to this VV only
   */
  public VirtualView(ServerNetwork ni){
    this.ni = ni;
    this.ni.setConnectionHandler(this::connectionHandler);
  }

  /**
   * See docs for ViewModelUpdate.replaceView
   * Apply to all user
   *
   * @param v Namespace of the view
   */
  public void replaceViewAllUsers(String v){
    this.sendToUser(
            null,
            (ConnectedUser c) -> c.replaceView(v)
    );
  }

  /**
   * See docs for ViewModelUpdate.replaceView
   * Apply to single user
   *
   * @param v    Namespace of the view
   * @param user User to replace view to
   */
  public void replaceViewSingleUser(String v, String user){
    this.sendToUser(
            user,
            (ConnectedUser c) -> c.replaceView(v)
    );
  }

  /**
   * See docs for ViewModelUpdate.addView
   * Apply to all user
   *
   * @param v Namespace of the view
   */
  public void addViewAllUsers(String v){
    this.sendToUser(
            null,
            (ConnectedUser c) -> c.addView(v)
    );
  }

  /**
   * See docs for ViewModelUpdate.addView
   * Apply to single user
   *
   * @param v    Namespace of the view
   * @param user User to add view to
   */
  public void addViewSingleUser(String v, String user){
    this.sendToUser(
            user,
            (ConnectedUser c) -> c.addView(v)
    );
  }

  /**
   * See docs for ViewModelUpdate.updateModel
   * Apply to all user
   *
   * @param ns    Namespace of the model
   * @param model Update to send to the user
   */
  public void updateModelAllUsers(String ns, AbstractModel model){
    this.sendToUser(
            null,
            (ConnectedUser c) -> c.updateModel(ns, model)
    );
  }

  /**
   * See docs for ViewModelUpdate.updateModel
   * Apply to single user
   *
   * @param user User to update model to
   * @param ns    Namespace of the model
   * @param model Update to send to the user
   */
  public void updateModelSingleUser(String ns, AbstractModel model, String user){
    this.sendToUser(
            user,
            (ConnectedUser c) -> c.updateModel(ns, model)
    );
  }

  /**
   * Contains logic for sending data to users.
   *
   * @param u User to send data to. Null to send to all users
   * @param f Function which send data.
   */
  private void sendToUser(String u, Consumer<ConnectedUser> f){
    synchronized (connections) {
      List<String> users = new LinkedList<>();

      if (u == null) {
        f.accept(broadcast);
        users = new LinkedList<>(connections.keySet());
      }
      else {
        if (connections.containsKey(u)) {
          users.add(u);
        }
      }

      users.forEach((String user) ->
              f.accept(connections.get(user))
      );
    }
  }

  /**
   * Subscribed callback are invoked in a synchronous way.
   * This means that, if you let the virtual view controls the flow of the
   * program, as long as you don't introduce additional concurrency, you can
   * forget about synchronization and others problem related to concurrency.
   *
   * For example, if you subscribe to the callback for a chat messages, and
   * two players send a chat message together, your callback will be firstly
   * invoked for the first message, and only when the first invocation returns,
   * a new invocation will be made with the second message.
   * It never happen that two callback are invoked at the same time (the
   * callback for the second message is invoked before the first callback
   * returns).
   *
   * __WARN__ Only a callback can be subscribed for a namespace and function at
   *          the same time. If you attempt to subscribe a new callback for
   *          a function on a namespace where a callback for the same function
   *          is already registered, the old callback is unsubscribed
   *
   * __INFO__ You can trust the user param in the returned FunctionCallEncodable
   *
   * @param ns Namespace to subscribe to
   * @param f  Function to subscribe to
   * @param cb Callback to subscribe.
   */
  public void subscribeCall(String ns, String f, FunctionCallCallback cb) {
    synchronized (this.cbs) {
      if (!(this.cbs.containsKey(ns))) {
        this.cbs.put(ns, new HashMap<>());
      }
      this.cbs.get(ns).put(f, cb);
    }
  }

  /**
   * Unsubscribe a callback
   *
   * @param ns  Namespace to unsubscribe from
   * @param f   Function to unsubscribe from
   */
  public void unsubscribeCall(String ns, String f){
    synchronized (this.cbs) {
      if (this.cbs.containsKey(ns)){
        this.cbs.get(ns).remove(f);
      }
    }
  }

  /**
   * Dispatch a call through all listeners
   */
  private void dispatchCall(String user, byte[] data){
    synchronized (this.cbs) {
      // Syncronized to expose a single-thread like environment
      FunctionCallModel call;
      try {
        call = FunctionCallModel.decode(
                AbstractModel.arrayToBuf(data)
        );

        if (
                this.cbs.containsKey(call.getNs()) &&
                this.cbs.get(call.getNs()).containsKey(call.getF())
        ){
          this.cbs.get(call.getNs()).get(call.getF()).accept(user, call.getData());
        }
      }
      catch (AbstractModel.InvalidDataException e) {
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Unable to decode function call",
                e
        );
      }
    }
  }

  /**
   * Connection Handler for the network interface.
   *
   * Attempt to login the user.
   * If a login cannot be completed before LOGIN_TIMEOUT expires, the connection
   * is closed
   */
  private void connectionHandler(Connection connection){
    ScheduledFuture<?> kickingExecutor =
            Executors.newScheduledThreadPool(1).schedule(
                    connection::close,
                    LOGIN_TIMEOUT,
                    TimeUnit.SECONDS
            );

    connection.setReceiveCallback((byte[] data) -> {
      String user = checkLogin(data);
      if (user != null) {
        kickingExecutor.cancel(false);

        connection.setReceiveCallback((byte[] call) ->
                dispatchCall(user, call)
        );
        synchronized (this.connections) {
          this.connections.putIfAbsent(user, new ConnectedUser(broadcast));
          this.connections.get(user).register(connection);
        }
      }
    });
  }

  /**
   * Contains the logic for logging in an user
   *
   * @param data The first packet received from the user (contains login data)
   *
   * @return The id of the user logged in on success, null on failure
   */
  private static String checkLogin(byte[] data){
    LoginDataModel loginInfo;
    try {
      loginInfo = LoginDataModel.decode(
              AbstractModel.arrayToBuf(data)
      );
      return loginInfo.getUser();
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Login Failed",
              e
      );
      return null;
    }
  }
}
