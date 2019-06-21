package it.polimi.se2019.utils.network.virtual_view;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.model.FunctionCallModel;
import it.polimi.se2019.utils.model.LoginDataModel;
import it.polimi.se2019.utils.network.Connection;
import it.polimi.se2019.utils.network.server.ServerNetwork;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * __WARN__ This class is a singleton.
 */
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
  private static ServerNetwork ni;

  /**
   * Contains every connection currently active, indexed by user id.
   */
  private static ConcurrentHashMap<String, ConnectedUser> connections =
          new ConcurrentHashMap<>();

  /**
   * Contains the virtual broadcast user.
   * There are no connections registered here, but it is used to save broadcast
   * data.
   * New ConnectedUser for unknown user will be initialized using those data
   */
  private static ConnectedUser broadcast = new ConnectedUser();

  /**
   * Contains the list of callbacks associated to each function and namespace
   */
  private static ConcurrentHashMap<String, ConcurrentHashMap<String, FunctionCallCallback>> cbs =
          new ConcurrentHashMap<>();

  /**
   * Hides public constructor
   */
  private VirtualView(){
    // Hides public constructor
  }

  /**
   * Init the Virtual View.
   * It is up to you to ensure that this function will be called only once in
   * your program.
   *
   * If this function is called twice, the second call is ignored
   *
   * @param networkInterface Network
   */
  public static synchronized void init(ServerNetwork networkInterface){
    if (ni == null){
      ni = networkInterface;
      ni.setConnectionHandler(VirtualView::connectionHandler);
    }
  }

  /**
   * Sends a model update downto the view
   * Updates are sent in order they are received.
   *
   * @param u  User to send the update to. Null for a broadcast update
   * @param ns Namespace to send the update to
   * @param m  Updated Model
   */
  public static void updateModel(String u, String ns, AbstractModel m){
    List<String> users = new LinkedList<>();

    if (u == null) {
      broadcast.updateModel(ns, m);
      users = java.util.Collections.list(connections.keys());
    }
    else {
      users.add(u);
    }

    users.forEach((String user) ->
      connections.getOrDefault(user, new ConnectedUser()).updateModel(ns, m)
    );
  }

  /**
   * Sends a view change downto the view
   * Requests are sent in order they are received.
   *
   * @param u  User to send the request to. Null for a broadcast update
   * @param v  Requested View
   */
  public static void changeView(String u, String v){

    List<String> users = new LinkedList<>();

    if (u == null) {
      broadcast.changeView(v);
      users = java.util.Collections.list(connections.keys());
    }
    else {
      users.add(u);
    }

    users.forEach((String user) ->
      connections.getOrDefault(u, new ConnectedUser()).changeView(v)
    );

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
  public static void subscribeCall(
          String ns, String f, FunctionCallCallback cb
  ) {
    cbs.putIfAbsent(ns, new ConcurrentHashMap<>());
    cbs.get(ns).put(f, cb);
  }

  /**
   * Unsubscribe a callback
   *
   * @param ns  Namespace to unsubscribe from
   * @param f   Function to unsubscribe from
   */
  public static void unsubscribeCall(String ns, String f){
    cbs.getOrDefault(ns, new ConcurrentHashMap<>()).remove(f);
  }

  /**
   * Dispatch a call through all listeners
   */
  private static synchronized void dispatchCall(String user, ByteBuffer data){
    // Syncronized to expose a single-thread like environment
    data.clear();
    FunctionCallModel call;
    try {
      call = FunctionCallModel.decode(data);

      cbs
              .getOrDefault(call.getNs(), new ConcurrentHashMap<>())
              .getOrDefault(call.getF(), (String u, String d) -> {})
              .accept(user, call.getData());
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to decode function call",
              e
      );
    }
  }

  /**
   * Connection Handler for the network interface.
   *
   * Attempt to login the user.
   * If a login cannot be completed before LOGIN_TIMEOUT expires, the connection
   * is closed
   */
  private static void connectionHandler(Connection connection){
    ScheduledFuture<?> kickingExecutor =
            Executors.newScheduledThreadPool(1).schedule(
                    connection::close,
                    LOGIN_TIMEOUT,
                    TimeUnit.SECONDS
            );

    connection.setReceiveCallback((ByteBuffer data) -> {
      String user = checkLogin(data);
      if (user != null) {
        kickingExecutor.cancel(false);

        connection.setReceiveCallback((ByteBuffer call) ->
                dispatchCall(user, call)
        );

        connections.putIfAbsent(user, new ConnectedUser(broadcast));
        connections.get(user).register(connection);
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
  private static String checkLogin(ByteBuffer data){
    LoginDataModel loginInfo;
    try {
      loginInfo = LoginDataModel.decode(data);
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
