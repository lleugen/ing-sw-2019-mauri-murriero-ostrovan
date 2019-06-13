package it.polimi.se2019.network.network.client;

import it.polimi.se2019.network.encodable.FunctionCall;
import it.polimi.se2019.network.encodable.ModelViewUpdate;

import java.util.function.Consumer;

public interface ClientNetworkInterface {
  /**
   * Call a function on the remote end
   *
   * @param ns   Namespace to call the function on
   * @param data Function to call
   */
  void call(String ns, FunctionCall data);

  /**
   * The id of the user currently logged in
   *
   * @return the user id this
   */
  String getUserId();

  /**
   * Get a message from the server.
   * Messages are guaranteed to be returned in the order they are sent by
   * the server.
   * If no message is available, this function blocks waiting for a message
   * coming from the server
   */
//  ModelViewUpdateg get();

  /**
   * You must ensure the callback is processed in the order it is called,
   * otherwise you may loose updates
   */
  void setOnUpdateCallback(UpdateCallback cb);

  @FunctionalInterface
  interface UpdateCallback extends Consumer<ModelViewUpdate> { }
}
