//package it.polimi.se2019.virtual_view;
//
//public class LocalVirtualView extends AbstractVirtualView {
//  /**
//   * Reference to the push parent VirtualView
//   */
//  private VirtualViewInterface parentInterface;
//
//  /**
//   * Inits a new local virtual view.
//   *
//   * A local virtual view can be used as a child view of another virtual view.
//   * It doesn't have any remote logic, and transfer any work to the parent view.
//   * Therefore, while initializing a LocalVirtualView, you must pass to it
//   * a reference to the parent view
//   *
//   * @param namespace Namespace of the view
//   * @param parent Reference to the parent view
//   */
//  public LocalVirtualView(String namespace, VirtualViewInterface parent){
//    super(namespace);
//    this.parentInterface = parent;
//  }
//
//  /**
//   * Push a state to the view
//   *
//   * @param path Path for the view (the same as the model)
//   * @param data Data to push to the view
//   */
//  public void pushState(String path, String data){
//    path = this.addNamespace(path);
//
//    this.parentInterface.pushState(this.namespace + "." + path, data);
//  }
//
//  /**
//   * Ask the view to show another view
//   *
//   * @param path Path of the view to deliver the request to
//   * @param view Name of the view to show
//   */
//  public void setView(String path, String view){
//    path = this.addNamespace(path);
//
//    this.parentInterface.setView(path, view);
//  }
//}
