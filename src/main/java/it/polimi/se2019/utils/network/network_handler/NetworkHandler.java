package it.polimi.se2019.utils.network.network_handler;

import it.polimi.se2019.utils.io.IOInterface;
import it.polimi.se2019.utils.model.FunctionCallModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.model.ModelUpdateModel;
import it.polimi.se2019.utils.model.ViewRequestModel;
import it.polimi.se2019.utils.network.Connection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
   * Contains the list of currently opened views
   */
  private static List<String> openedViews;

  /**
   * IO Interface to use for rendering views
   */
  private static IOInterface ioInterface;

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
   * @param vs    Map of available views, indexed by namespace
   * @param io    Io Interface to use for rendering views
   */
  public static synchronized void init(Connection conn,
          Map<String, AbstractView> vs, IOInterface io
  ){
    if (connection == null){
      views = new HashMap<>(vs);
      openedViews = new LinkedList<>();
      ioInterface = io;

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
    connection.send(AbstractModel.bufToArray(call.encode()));
  }

  /**
   * Sends an update to the callback for the given namespace
   *
   * @param data  Update to send to the namespace
   */
  private static synchronized void receiveCallback(byte[] data){
    ModelUpdateModel model;
    try {
      model = ModelUpdateModel.decode(
              AbstractModel.arrayToBuf(data)
      );

      if (views.containsKey(model.getNs())){
        views.get(model.getNs()).updateState(model.getModel());
        refreshViews();
      }
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.FINEST,
              "Received data is not a model",
              e
      );
    }

    try {
      openedViews = ViewRequestModel.decode(
                      AbstractModel.arrayToBuf(data)
              ).getViews();

      refreshViews();
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.FINEST,
              "Received data is not a view",
              e
      );
    }
  }

  /**
   * Refresh all opened views. (rerender them with the last received model)
   */
  private static synchronized void refreshViews(){
    openedViews.forEach((String v) -> {
      if (views.containsKey(v)){
        views.get(v).refresh(ioInterface);
      }
      else {
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Attempting to open a non-existing view {0}",
                v
        );
      }
    });
    ioInterface.render();
  }

  /**
   * Disconnection Callback
   *
   * Logs a SEVERE failure message
   *
   * @param c Is ignored
   */
  private static void disconnectCallback(Connection c){
    ioInterface.close();
  }
}
