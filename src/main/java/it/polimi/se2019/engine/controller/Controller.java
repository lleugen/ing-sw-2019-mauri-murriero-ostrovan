/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.controller;

import it.polimi.se2019.engine.model.AbstractModel;
import it.polimi.se2019.engine.network.virtualview.FunctionCallCallback;
import it.polimi.se2019.engine.network.virtualview.VirtualView;

import java.util.LinkedList;
import java.util.List;
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
   * Registered callbacks on the VirtualView
   */
  private List<String> cbs;

  /**
   * VirtualView the controller is bound to
   */
  private VirtualView virtualView;

  /**
   * Create a new controller.
   * Remember to call init to initialize callbacks
   *
   * @param ns  Namespace controlled by this controller
   * @param m   Supplier for a model this controller is in charge of
   * @param vv  VirtualView to bind the controller to
   */
  protected Controller(String ns, Supplier<T> m, VirtualView vv){
    this.ns = ns;
    this.model = m.get();
    this.virtualView = vv;
    this.cbs = new LinkedList<>();
  }

  /**
   * Register a new callback on the VirtualView.
   *
   * __NOTE__ Extension should avoid to register callbacks directly to the
   *          virtual view and use instead this function as it guarantees that
   *          registered callbacks are properly deregistered when the
   *          controller is closed
   *
   * @param f  Function to subscribe to
   * @param cb Callback to subscribe.
   */
  protected synchronized void subscribeCall(String f, FunctionCallCallback cb){
    this.cbs.add(f);
    this.virtualView.subscribeCall(this.ns, f, cb);
  }

  /**
   * Deregister a callback on the VirtualView.
   *
   * __NOTE__ Extension should avoid to deregister callbacks directly to the
   *          virtual view and use instead this function as it guarantees that
   *          registered callbacks are properly deregistered when the
   *          controller is closed
   *
   * @param f  Function to unsubscribe from
   */
  protected synchronized void unsubscribeCall(String f){
    this.virtualView.unsubscribeCall(this.ns, f);
  }

  /**
   * See docs for ViewModelUpdate.replaceView
   * Apply to all user
   *
   * @param v Namespace of the view
   */
  protected void replaceViewAllUsers(String v){
    this.virtualView.replaceViewAllUsers(v);
  }

  /**
   * See docs for ViewModelUpdate.replaceView
   * Apply to single user
   *
   * @param v    Namespace of the view
   * @param user User to replace view to
   */
  protected void replaceViewSingleUser(String v, String user){
    this.virtualView.replaceViewSingleUser(v, user);
  }

  /**
   * See docs for ViewModelUpdate.addView
   * Apply to all user
   *
   * @param v Namespace of the view
   */
  protected void addViewAllUsers(String v){
    this.virtualView.addViewAllUsers(v);
  }

  /**
   * See docs for ViewModelUpdate.addView
   * Apply to single user
   *
   * @param v    Namespace of the view
   * @param user User to add view to
   */
  protected void addViewSingleUser(String v, String user){
    this.virtualView.addViewSingleUser(v, user);
  }

  /**
   * See docs for ViewModelUpdate.updateModel
   * Apply to all user
   */
  protected void notifyModelAllUsers(){
    this.virtualView.updateModelAllUsers(this.ns, this.model);
  }

  /**
   * See docs for ViewModelUpdate.updateModel
   * Apply to single user
   *
   * @param user User to update model to
   */
  protected void notifyModelSingleUsers(String user){
    this.virtualView.updateModelSingleUser(this.ns, this.model, user);
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
   * Get a reference to the VirtualView this controller is bound to
   *
   * __WARN__ Extension should avoid direct access to the VirtualView and use
   *          this reference only to create sub-controllers
   *
   * @return a reference to the model for this controller
   */
  protected VirtualView getVirtualView(){
    return this.virtualView;
  }

  /**
   * Close the model, de-registering all registered callbacks
   */
  public void close(){
    this.cbs.forEach(this::unsubscribeCall);
  }
}
