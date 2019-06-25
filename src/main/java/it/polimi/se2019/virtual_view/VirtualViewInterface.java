//package it.polimi.se2019.virtual_view;
//
//import java.util.function.Consumer;
//
//public interface VirtualViewInterface {
//  /**
//   * Ask the view to show another view
//   *
//   * @param path Path of the view to deliver the request to
//   * @param view Name of the view to show
//   */
//  void setView(String path, String view);
//
//  /**
//   * Push a state to the view.
//   * This function can be used to push updates from child views.
//   *
//   * @param path    Path to which send the update (relative)
//   * @param state   The state to push to the view
//   */
//  void pushState(String path, String state);
//
//  /**
//   * Add a new callable method to the virtual view
//   *
//   * @param name     Name of the method to expose to the view
//   * @param function Method to call on the controller
//   */
//  void addMethod(String name, Consumer<String> function);
//
//  /**
//   * Add a new related view to the virtual view
//   *
//   * @param name Name of the view to add
//   * @param view Related virtual view to add to this view
//   */
//  void addRelatedView(String name, VirtualViewInterface view);
//
//  /**
//   * Call a method on the current view associated controller or in related
//   * virtual views.
//   * You can access related view by using namespaces.
//   * For example, if you have the main view initialized as v1, with method v1m1,
//   * and you have the related view v2 added to v1 with name v2, you can access
//   * method v2m1 (method on view 2), by passing as path to this function v1.v2.m1
//   *
//   * __WARN__: Namespace is required
//   * __WARN__: If a method doesn't exists, no exception is returned
//   *
//   * @param path Path of the method to call
//   * @param data Data to pass to the called method as parameter
//   */
//  void call(String path, String data);
//}
