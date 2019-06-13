package it.polimi.se2019.network;

import it.polimi.se2019.network.encodable.FunctionCall;
import it.polimi.se2019.network.encodable.ModelViewUpdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class VirtualView {
//  /**
//   * Contains every connected user, indexed by his name
//   */
//  private Map<String, ConnectedUser> users;
//
//  /**
//   * Transfer Queue used to synchronize callback reception and filtering
//   */
//  private TransferQueue<CallRequest> tq;
//
//  /**
//   * Tester for approving or discarding FunctionCall deliveries
//   */
//  private Predicate<CallRequest> test;
//
//  /**
//   * Init a new virtual view
//   *
//   * @param ni Network interface this wiew should connect to
//   */
//  public VirtualView(ServerNetworkInterface ni){
//    this.users = Collections.synchronizedMap(new HashMap<>());
//    this.tq = new LinkedTransferQueue<>();
//    this.test = (item -> false);
//    ni.setOnConnectHandler(this::connectionHandler);
//    ni.setOnRecvHandler(this::recvHandler);
//    ni.setUpdateSupplier();
//  }
//
//  /**
//   * Sends an update to every connected client
//   *
//   * @param ns  Namespace to send the update to
//   * @param v   Name of the new view to select
//   * @param m   Model update to propagate
//   */
//  public void sendGlobalUpdate(String ns, String v, ModelInterface m){
//    this.users.keySet().forEach(
//            user -> this.sendUpdate(user, ns, v, m)
//    );
//  }
//
//  /**
//   * Sends an update to a specific client
//   *
//   * @param u   Id of the user to send the update to
//   * @param ns  Namespace to send the update to
//   * @param v   Name of the view to select
//   * @param m   Model update to propagate
//   */
//  public void sendUpdate(String u, String ns, String v, ModelInterface m){
//    ConnectedUser target;
//
//    target = this.users.get(u);
//
//    if (target != null){
//      target.offer(new ModelViewUpdate(ns, v, m));
//    }
//  }
//
//  /**
//   * Wait for a valid call request from the client, for the given amount of
//   * time
//   *
//   * @param tester  Predicate to use to test validity of a call request
//   * @param timeout Timeout in seconds before raising
//   *
//   * @return
//   *
//   * @throws TimeoutException If a response cannot be optained before timeout
//   *                          expiration
//   */
//  public CallRequest waitFor(Predicate<CallRequest> tester, Integer timeout)
//    throws TimeoutException {
//    this.setFilter(test);
//
//    try {
//      return this.tq.poll(timeout, TimeUnit.SECONDS);
//    }
//    catch (java.lang.InterruptedException e){
//      Thread.currentThread().interrupt();
//      throw new TimeoutException();
//    }
//    finally {
//      this.setFilter(item -> false);
//    }
//  }
//
//  /**
//   * Set a new filter for incoming data.
//   * When a filter is set, the transfer queue is also emptied, to prevent any
//   * non valid message to leak out
//   *
//   * @param filter Filter to set
//   */
//  private void setFilter(Predicate<CallRequest> filter){
//    this.test = filter;
//    this.tq.clear();
//  }
//
//  /**
//   * Handles the connection of the user, registering it to the users map,
//   * and resenting every update sent from the initialization of the virtual
//   * view.
//   *
//   * @param user Id of the user that connected to the server
//   *
//   * @return A supplier that can be used by the network handler to retrieve
//   *         updates to push down to the real view
//   */
//  private ServerNetworkInterface.UpdateSupplier connectionHandler(String user){
//    this.users.putIfAbsent(user, new ConnectedUser(user));
//    this.users.get(user).reconnect();
////    return this.users.get(user)::waitForTq;
//  }
//
//  /**
//   * Handles receiving call request from the real view.
//   * Requests are filtered using the test function of the class, then, if
//   * valid, delivered to the transfer queue to be retrived
//   *
//   * @param user User who requested the call
//   * @param call Call made by the user
//   */
//  private void recvHandler(String user, FunctionCall call){
//    CallRequest request = new CallRequest(user, call);
//    if (this.test.test(request)){
//      boolean ok = this.tq.offer(request);
//      if (!ok){
//        Logger.getLogger("VirtualView").log(
//                Level.WARNING,
//                "Unable to propagate call request"
//        );
//      }
//    }
//  }
//
  public class ConnectedUser {
    private String id;
    private List<ModelViewUpdate> log;
    private TransferQueue<ModelViewUpdate> tq;

    ConnectedUser(String id){
      this.id = id;
      this.log = Collections.synchronizedList(new ArrayList<>());
      this.tq = new LinkedTransferQueue<>();
    }

    public String getId(){
      return this.id;
    }

    public ModelViewUpdate waitForTq(){
      return this.tq.poll();
    }

    public synchronized void offer(ModelViewUpdate data){
      this.log.add(data);
      this.tq.offer(data);
    }

    public synchronized void reconnect(){
      this.log.forEach(this.tq::offer);
    }
  }

  public class CallRequest {
    private String user;
    private FunctionCall call;

    CallRequest(String user, FunctionCall call) {
      this.user = user;
      this.call = call;
    }

    public String getUser() {
      return this.user;
    }

    public FunctionCall getCall() {
      return this.call;
    }
  }

  public static class TimeoutException extends Exception {
    TimeoutException(Throwable e){
      super(e);
    }

    TimeoutException(){
      super();
    }

    @Override
    public String toString() {
      return "Client did not respond on time";
    }
  }
}
