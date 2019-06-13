package it.polimi.se2019.network;

import it.polimi.se2019.network.encodable.FunctionCall;
import it.polimi.se2019.network.encodable.ModelViewUpdate;
import it.polimi.se2019.network.encodable.model.ModelInterface;
import it.polimi.se2019.network.network.client.ClientNetworkInterface;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;

public class NetworkHandler {
  /**
   * Contains the state of the model for each known namespace
   */
  private ConcurrentHashMap<String, ModelViewState> curState;

  /**
   * Contains the transfers queue for the state of each known namespace
   */
  private ConcurrentHashMap<String, LinkedTransferQueue<ModelViewState>> stq;

  /**
   * Reference to the network interface this NetworkHandler is bound to
   */
  ClientNetworkInterface ni;

  /**
   * Init a new virtual view
   *
   * @param ni Network interface this wiew should connect to
   */
  public NetworkHandler (ClientNetworkInterface ni){
    this.curState = new ConcurrentHashMap<>();
    this.stq = new ConcurrentHashMap<>();
    this.ni = ni;
    this.ni.setOnUpdateCallback(this::processUpdate);
  }

  /**
   * Gets the last available state for a namespace
   *
   * @param ns   Namespace to get data about
   * @param def  Default data to return in case no data is available
   *
   * @return The state to render
   */
  public ModelViewState getState(String ns, ModelViewState def){
    return this.curState.getOrDefault(ns, def);
  }

  /**
   * Call a function on the remote end
   *
   * @param ns    Namespace to call the function on
   * @param data  Data of the function call
   */
  public void call(String ns, FunctionCall data){
    this.ni.call(ns, data);
  }

  /**
   * Internal syncronized function for processing updates received from
   * the network.
   * Updates are processed in the order they are received
   *
   * @param update Update to process
   */
  private synchronized void processUpdate(ModelViewUpdate update){
    curState.put(update.getNamespace(), new ModelViewState(update));
  }

  public class ModelViewState {
    private ModelInterface model;
    private String view;

    public ModelViewState(ModelInterface model, String view) {
      this.model = model;
      this.view = view;
    }

    public ModelViewState(ModelViewUpdate update){
      this.model = update.getModel();
      this.view = update.getView();
    }

    public ModelInterface getModel() {
      return model;
    }

    public String getView() {
      return view;
    }
  }
}
