package it.polimi.se2019.utils.network.network_handler;

import it.polimi.se2019.utils.model.FunctionCallModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.model.ModelUpdateModel;
import it.polimi.se2019.utils.model.ViewRequestModel;
import it.polimi.se2019.utils.network.Connection;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * __WARN__ This class is a singleton.
 */
public final class NetworkHandler {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "NetworkHandler";

  /**
   * Reference to the network connection is bound to
   */
  private static Connection connection;

  /**
   * Contains all available views, indexed by namespace
   */
  private static Map<String, AbstractView> views;

  /**
   * Contains the last update sent to each view
   */
  private static Map<String, ByteBuffer> lastUpdate;

  /**
   * Currently selected view.
   * For performance, only view changes that produces a result are accepted.
   */
  private static String currentView;

  /**
   * Hides public constructor
   */
  private NetworkHandler(){
    // Hides public constructor
  }

  /**
   * Init the Network Handler
   * It is up to you to ensure that this function will be called only once in
   * your program.
   *
   * If this function is called twice, the second call is ignored
   *
   * @param conn  Connection to the server (already opened)
   * @param vs Map of available views, indexed by namespace
   */
  public static synchronized void init(
          Connection conn, Map<String, AbstractView> vs
  ){
    if (connection == null){
      views = new HashMap<>(vs);
      lastUpdate = new HashMap<>();
      currentView = "";

      connection = conn;
      connection.setDisconnectionCb(NetworkHandler::disconnectCallback);
      connection.setReceiveCallback(NetworkHandler::receiveCallback);

    }
  }

  /**
   * Sends a FunctionCall up to the controller
   *
   * @param call Call to send to the controller
   */
  public static void send(FunctionCallModel call){
    connection.send(call.encode());
  }

  /**
   * Sends an update to the callback for the given namespace
   *
   * @param data  Update to send to the namespace
   */
  private static synchronized void receiveCallback(ByteBuffer data){
    ModelUpdateModel model;
    try {
      model = ModelUpdateModel.decode(data);
      renderView(model.getNs(), model.getModel());
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.FINE,
              "Received data is not a model",
              e
      );
    }

    data.clear();

    ViewRequestModel view;
    try {
      view = ViewRequestModel.decode(data);
      changeView(view.getView());
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.FINE,
              "Received data is not a view",
              e
      );
    }
  }

  /**
   * Change the current rendered view.
   * When the rendered view is changed, all subviews of the new rendered view
   * are re-rendered, too
   *
   * @param view Namespace of the new view to show
   */
  private static synchronized void changeView(String view){
    if (!(currentView.equals(view))){
      currentView = view;

      List<String> viewsToRender = views.keySet().stream()
              .filter((String key) ->
                      key.startsWith(view)
              )
              .collect(Collectors.toList());

      viewsToRender.add(view);

      viewsToRender.forEach(
              (String v) -> {
                if (lastUpdate.containsKey(v)) {
                  renderView(v, lastUpdate.get(v));
                }
              }
      );
    }
    else {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.FINE,
              "Discarding duplicate view change request"
      );
    }
  }

  /**
   * Render a single view.
   * Only the selected view is rendered (not all subviews)
   *
   * @param view Namespace of the view to render.
   * @param data Model data to render the view with
   */
  private static synchronized void renderView(String view, ByteBuffer data){
    lastUpdate.put(view, data);
    if (views.containsKey(view)){
      views.get(view).accept(data);
    }
    else {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Selected view <{0}> does not exist",
              view
      );
    }
  }

  /**
   * Disconnection Callback
   *
   * Logs a SEVERE failure message
   *
   * @param c Is ignored
   */
  private static void disconnectCallback(Connection c){
    Logger.getLogger(LOG_NAMESPACE).log(
            Level.SEVERE,
            "Disconnected from server. You can safely kill this application"
    );
  }
}
