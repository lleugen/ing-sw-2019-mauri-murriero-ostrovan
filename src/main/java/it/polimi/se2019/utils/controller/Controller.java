package it.polimi.se2019.utils.controller;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;
import it.polimi.se2019.utils.network.virtual_view.VirtualView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @param <T> The type of the model this controller controls
 */
public class Controller<T extends AbstractModel> {
  /**
   * Namespace controlled by this controller
   */
  private String ns;

  /**
   * Reference to the model for this controller.
   */
  private Supplier<T> model;

  /**
   * Callback for received call from the view
   */
  private Map<String, FunctionCallCallback> cbs;

  /**
   * Create a new controller.
   * Remember to call init to initialize callbacks
   *
   * @param ns  Namespace controlled by this controller
   * @param m   Reference to the model controlled by this controller
   */
  protected Controller(String ns, Supplier<T> m){
    this.ns = ns;
    this.model = m;
  }

  /**
   * Init the Controller, registering all the callbacks.
   * It is the opposite of close
   *
   * @param cbs Callback for received call from the view. Null if no callback
   *            should be registered for this controller
   */
  protected synchronized void init(Map<String, FunctionCallCallback> cbs){
    this.cbs = new ConcurrentHashMap<>();

    if (cbs != null) {
      this.cbs.putAll(cbs);
      this.cbs.forEach((String f, FunctionCallCallback call) ->
              VirtualView.subscribeCall(
                      this.ns,
                      f,
                      call
              )
      );
    }
  }

  /**
   * Send a notification about the new updated model to the clients.
   *
   * @param user  User to notify the update about. Null to notify all users
   */
  protected void notifyModelUpdate(String user){
    VirtualView.updateModel(user, this.ns, this.model.get());
  }

  /**
   * Change the currently selected view
   *
   * @param user User to ask to change view, Null to ask all users
   * @param view View to change. If this string doesn't contains any '.', the
   *             changed view is relative to the namespace controlled by this
   *             controller
   */
  protected void showView(String user, String view){
    if (!view.contains(".")){
      view = this.ns.concat("." + view);
    }

    VirtualView.changeView(user, view);
  }

  /**
   * Get a reference to the current model
   *
   * @return a reference to the model for this controller
   */
  protected T getModel(){
    return this.model.get();
  }

  /**
   * Close the model, de-registering all registered callbacks
   */
  public void close(){
    this.cbs.keySet().forEach((String f) ->
            VirtualView.unsubscribeCall(this.ns, f)
    );
  }
}
