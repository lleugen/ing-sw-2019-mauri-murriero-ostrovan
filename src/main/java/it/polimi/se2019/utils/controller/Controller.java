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
   * Model this controller is in charge for
   */
  private T model;

  /**
   * Callback for received call from the view
   */
  private Map<String, FunctionCallCallback> cbs;

  /**
   * Create a new controller.
   * Remember to call init to initialize callbacks
   *
   * @param ns  Namespace controlled by this controller
   * @param m   Supplier for a model this controller is in charge of
   */
  protected Controller(String ns, Supplier<T> m){
    this.ns = ns;
    this.model = m.get();
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
   * Send a notification about the new updated model to all clients
   */
  protected void notifyModelUpdateAll(){
    this.notifyModelUpdate(null);
  }

  /**
   * Send a notification about a new updated model to a single user
   *
   * @param user User to notify about
   */
  protected void notifyModelUpdateUser(String user){
    this.notifyModelUpdate(user);
  }

  /**
   * Send a notification about the new updated model users.
   *
   * @param user  User to notify the update about, null to notify all users
   */
  private void notifyModelUpdate(String user){
    VirtualView.updateModel(user, this.ns, this.model);
  }

  /**
   * Change the currently selected view for a single user
   *
   * @param user User to ask to change view
   * @param view View to change. If this string doesn't contains any '.', the
   *             changed view is relative to the namespace controlled by this
   *             controller
   */
  protected void showViewUser(String user, String view){
    this.showView(user, view);
  }

  /**
   * Change the currently selected view for all users
   *
   * @param view View to change. If this string doesn't contains any '.', the
   *             changed view is relative to the namespace controlled by this
   *             controller
   */
  protected void showViewAll(String view){
    this.showView(null, view);
  }

  /**
   * Change the currently selected view
   *
   * @param user User to ask to change view, Null to ask all users
   * @param view View to change. If this string doesn't contains any '.', the
   *             changed view is relative to the namespace controlled by this
   *             controller. If the view is an empty string, it is the namespace
   *             of the controller
   */
  private void showView(String user, String view){
    if (!view.contains(".")){
      if ("".equals(view)){
        view = this.ns;
      }
      else {
        view = this.ns.concat("." + view);
      }
    }

    VirtualView.changeView(user, view);
  }

  /**
   * Get a reference to the current model
   *
   * @return a reference to the model for this controller
   */
  protected T getModel(){
    return this.model;
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
