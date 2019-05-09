package it.polimi.se2019.virtual_view;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.logging.Logger;

public abstract class AbstractVirtualView implements VirtualViewInterface {
  /**
   * Namespace of the current virtual view
   */
  private String namespace;

  /**
   * List of method mapped to the virtual view, and so callable by the view
   */
  private HashMap<String, Consumer<String>> methodAssoc;

  /**
   * List of related view associated to this virtual view
   */
  private HashMap<String, VirtualViewInterface> viewAssoc;

  /**
   * Inits a new virtual view
   *
   * @param namespace the namespace this virtual view refers to
   */
  AbstractVirtualView(String namespace) {
    this.namespace = namespace;
  }

  /**
   * Call a method on the current view or in related virtual views.
   * You can access related view by using namespaces.
   * For example, if you have the main view initialized as v1, with method v1m1,
   * and you have the related view v2 added to v1 with name v2, you can access
   * method v2m1 (method on view 2), by passing as path to this function v1.v2.m1
   *
   * __WARN__: Namespace is required
   * __WARN__: If a method doesn't exists, no exception is returned
   *
   * @param path Path of the method to call
   * @param data Data to pass to the called method as parameter
   */
  public void call(String path, String data) {
    List<String> parsedPath;

    try {
      parsedPath = this.stripNamespace(path);

      if (parsedPath.size() > 1) {
        if (this.viewAssoc.containsKey(parsedPath.get(0))) {
          this.viewAssoc
                .get(parsedPath.get(0))
                .call(String.join(".", path), data);
        }
      }
      else {
        if (this.methodAssoc.containsKey(parsedPath.get(0))) {
          this.methodAssoc.get(parsedPath.get(0)).accept(data);
        }
      }
    }
    catch (NotInThisNamespaceException e){
      Logger.getGlobal().throwing(
              SocketVirtualView.class.getCanonicalName(),
              "call",
              e
      );
    }
  }

  /**
   *
   * @param path
   * @return
   */
  String addNamespace(String path){
    if (!("".equals(path))){
      path = "." + path;
    }

    return (this.namespace + path);
  }

  /**
   *
   * @param path
   * @return
   * @throws NotInThisNamespaceException
   */
  List<String> stripNamespace(String path){
    ArrayList<String> splitPath;
    splitPath = new ArrayList<>(Arrays.asList(path.split("\\.")));

    if (splitPath.get(0).equals(this.namespace)) {
      splitPath.remove(0);
      return splitPath;
    }
    else {
      throw new NotInThisNamespaceException();
    }
  }

  /**
   * Add a new callable method to the virtual view
   *
   * @param name     Name of the method to expose to the view
   * @param function Method to call on the controller
   */
  public void addMethod(String name, Consumer<String> function) {
    methodAssoc.put(name, function);
  }

  /**
   * Add a new related view to the virtual view
   *
   * @param name Name of the view to add
   * @param view Related virtual view to add to this view
   */
  public void addRelatedView(String name, VirtualViewInterface view) {
    viewAssoc.put(name, view);
  }

  /**
   * Raised by stripNamespace when the passed path is not in the namespace of
   * this class
   */
  private static class NotInThisNamespaceException extends RuntimeException {
    @Override
    public String toString() {
      return "This path is not in this namespace";
    }
  }
}
