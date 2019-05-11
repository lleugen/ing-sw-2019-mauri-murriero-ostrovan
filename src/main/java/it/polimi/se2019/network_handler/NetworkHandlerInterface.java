package it.polimi.se2019.network_handler;

import java.util.function.BiConsumer;

public interface NetworkHandlerInterface {
  /**
   * Call a method on the network handler (remote controller or related handler)
   *
   * @param path Path of the controller where the method is present
   * @param data Data to pass to the method
   */
  void call(String path, String data);

  /**
   * Subscribe a callback for views updates
   *
   * @param callback A callback to listen to updates with
   */
  void subscribeViewUpdate(BiConsumer<String, String> callback);

  /**
   * Subscribe a callback for models updates
   *
   * @param callback A callback to listen to updates with
   */
  void subscribeModelUpdate(BiConsumer<String, String> callback);
}
