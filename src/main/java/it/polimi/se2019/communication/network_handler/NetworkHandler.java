package it.polimi.se2019.communication.network_handler;

import it.polimi.se2019.communication.encodable.FunctionCallEncodable;
import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
import it.polimi.se2019.communication.network.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkHandler {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "NetworkHandler";

  /**
   * Contains the state of the model for each known namespace
   */
  private Map<String, ModelViewUpdateEncodable> curState;

  /**
   * Contains the set of callbacks for each known namespace
   */
  private Map<String, MVUpdateCb> cbs;

  /**
   * Contains the reference to the receiving thread
   */
  private ReceiveThread recvThread;

  /**
   * Reference to the network connection is bound to
   */
  private Connection connection;

  /**
   * Reference to the disconnect callback
   */
  private DisconnectCb disconnectCb;

  /**
   * Init a new NetworkHandler.
   *
   * Remember to call the start method for receive data from the server
   *
   * @param conn NetworkInterface this NetworkHandler should bind to
   * @param dcb  DisconnectCallback for this NetworkHandler
   */
  public NetworkHandler(Connection conn, DisconnectCb dcb){
    this.connection = conn;
    this.disconnectCb = dcb;
    this.curState = new HashMap<>();
    this.cbs = new HashMap<>();
    this.recvThread = new ReceiveThread(
            conn,
            dcb,
            (ModelViewUpdateEncodable data) ->
                    this.update(
                            data.getNs(),
                            data
                    )
    );
  }

  /**
   * Starts the network handler for receiving data from the server
   */
  public void start(){
    new Thread(
            this.recvThread
    ).start();
  }

  /**
   * Subscribe a callback to the selected namespace.
   * Only one callback can be subscribed to a namespace on the same time.
   * If there is already a registered callback, the old one is automatically
   * unsubscribed.
   *
   * __NOTE__: After subscribing to a namespace, the callback is immediately
   *           invoked with the last received update for that namespace.
   */
  public synchronized void subscribe(String ns, MVUpdateCb cb){
    this.cbs.put(ns, cb);
    if (this.curState.containsKey(ns)){
      this.update(ns, this.curState.get(ns));
    }
  }

  /**
   * Unsubscribe a callback from a namespace
   *
   * @param ns  Namespace to unsubscribe from
   */
  public synchronized void unsubscribe(String ns){
    this.cbs.remove(ns);
  }

  /**
   * Sends a FunctionCall up to the server
   *
   * @param call Call to send to the server
   */
  public void send(FunctionCallEncodable call){
    try {
      this.connection.send(call.encode());
    }
    catch (Connection.DisconnectedError e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Server Disconnected",
              e
      );
      this.disconnectCb.ping();
    }
  }

  /**
   * Sends an update to the callback for the given namespace
   *
   * @param ns    Namespace to send the update to
   * @param data  Update to send to the namespace
   */
  private synchronized void update(String ns, ModelViewUpdateEncodable data){
    this.curState.put(ns, data);
    if (this.cbs.containsKey(ns)){
      this.cbs.get(ns).accept(data);
    }
  }
}
