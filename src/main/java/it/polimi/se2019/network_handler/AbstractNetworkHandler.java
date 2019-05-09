package it.polimi.se2019.network_handler;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public abstract class AbstractNetworkHandler implements NetworkHandlerInterface {
  /**
   * Namespace of the current network handler
   */
  private String namespace;

  /**
   * List of related handler associated to this network handler
   */
  private HashMap<String, NetworkHandlerInterface> handlerAssoc;

  /**
   * Handler used to send data to the remote view
   */
  private BiConsumer<String, String> callHandler;

  /**
   * Inits a new network handler
   *
   * @param namespace the namespace this network handler refers to
   * @param ch The handler to use to send data to the virtual view
   */
  AbstractNetworkHandler(String namespace, BiConsumer<String, String> ch) {
    this.namespace = namespace;
    this.callHandler = ch;
  }

  /**
   * Call a method on the remote controller or in controller linked to related
   * network handler.
   * You can access related network handlers by using namespaces.
   * For example, if you have the main network handler initialized as h1, with
   * method h1m1, and you have the related network handler h2 added to h1
   * with name h2, you can access method h2m1 (method on controller 2),
   * by passing as path to this function h1.h2.m1
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
        if (this.handlerAssoc.containsKey(parsedPath.get(0))) {
          this.handlerAssoc
                .get(parsedPath.get(0))
                .call(String.join(".", path), data);
        }
      }
      else {
        this.callHandler.accept(parsedPath.get(0), parsedPath.get(1));
      }
    }
    catch (NotInThisNamespaceException e){
      Logger.getGlobal().throwing(
              SocketNetworkHandler.class.getCanonicalName(),
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
   * Add a new related handler to the network handler
   *
   * @param name Name of the handler to add
   * @param view Related network handler to add to this handler
   */
  public void addRelatedHandler(String name, NetworkHandlerInterface view) {
    this.handlerAssoc.put(name, view);
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
