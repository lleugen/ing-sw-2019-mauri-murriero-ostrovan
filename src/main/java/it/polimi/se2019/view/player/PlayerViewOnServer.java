package it.polimi.se2019.view.player;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerViewOnServer implements ViewFacadeInterfaceRMIServer {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "PlayerViewOnServer";

  /**
   * Disconnection Timeout in seconds
   */
  private int timeout;

  /**
   * Name of the player
   */
  private String name;

  /**
   * Character of the player
   */
  private String character;

  /**
   * Contains the last RMI reference for each getPlayer(), indexed by name
   */
  private static final ConcurrentHashMap<String, ViewFacadeInterfaceRMIClient> connections =
          new ConcurrentHashMap<>();

  /**
   * Create a new wrapper for a player on the server.
   * The wrapper allows to call syncronous operation on the client with a
   * timeout
   *
   * @param view      View of the player (already initialized)
   * @param timeout   Disconnection Timeout in seconds
   *
   * @throws RemoteException If an error occurs while utilizing RMI
   */
  public PlayerViewOnServer(ViewFacadeInterfaceRMIClient view, int timeout)
          throws RemoteException {
    this.name = view.getName();
    this.character = view.getCharacter();
    registerPlayer(view.getName(), view);
    this.timeout = timeout;
  }

  /**
   * Register a connection for a client. If a client is already registered, the
   * connection is overwritten (only one client can be connected with the same
   * nickname)
   *
   * @param name    Name of the client
   * @param client  Reference to an active RMI connection for the client
   */
  public static void registerPlayer(String name, ViewFacadeInterfaceRMIClient client){
    connections.put(name, client);
  }

  /**
   * @return the last registered client reference for the user this view is registered
   *         to.
   *         Null if there is no user registered for a given name
   */
  private ViewFacadeInterfaceRMIClient getPlayer(){
    return connections.get(this.name);
  }

  public String getCharacter() {
    return character;
  }

  public String getName() {
    return this.name;
  }

  /**
   *
   */
  @Override
  public String chooseAction(String state) throws UserTimeoutException {
    WaitFor<String, String> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseAction,
            state
    );
  }

  /**
   * @return index of the power up card to discard
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps) throws UserTimeoutException {
    WaitFor<List<String>, Integer> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseSpawnLocation,
            powerUps
    );
  }

  /**
   * Choose map type for the match
   */
  @Override
  public int chooseMap() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>(this.timeout);
    return wf.waitForSupp(
            this.getPlayer()::chooseMap
    );
  }

  /**
   * choose how many players will be in the game
   */
  @Override
  public int chooseNumberOfPlayers() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>(this.timeout);
    return wf.waitForSupp(
            this.getPlayer()::chooseNumberOfPlayers
    );
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(List<String> weapons)
          throws UserTimeoutException {
    WaitFor<List<String>, String> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseWeapon,
            weapons
    );
  }

  /**
   * @param possibleTargets is a list of the players who can be targeted(their names)
   * @return a list of chosen targets(names)
   */
  @Override
  public String chooseTargets(List<String> possibleTargets)
          throws UserTimeoutException {
    WaitFor<List<String>, String> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseTargets,
            possibleTargets
    );
  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons)
          throws UserTimeoutException {
    WaitFor<List<String>, String> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseWeaponToReload,
            weapons
    );
  }

  /**
   * @return a list of integers indicating which cards from the player's inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps)
          throws UserTimeoutException {
    WaitFor<List<String>, List<Integer>> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::choosePowerUpCardsForReload,
            powerUps
    );
  }

  /**
   * @return the integer relative to the availableEffects list
   */
  @Override
  public Integer chooseIndex(List<String> availableEffects)
          throws UserTimeoutException {
    WaitFor<List<String>, Integer> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseIndex,
            availableEffects
    );
  }

  /**
   * Send a string message to a client
   */
  @Override
  public void sendGenericMessage(String message) throws RemoteException{
    this.getPlayer().sendGenericMessage(message);
  }

  /**
   * @return int indicating which item to pick up from those available
   */
  @Override
  public int chooseItemToGrab() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>(this.timeout);
    return wf.waitForSupp(
            this.getPlayer()::chooseItemToGrab
    );
  }

  /**
   * choose whether to use a firing mode
   */
  @Override
  public Boolean chooseFiringMode(String description)
          throws UserTimeoutException {
    WaitFor<String, Boolean> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseFiringMode,
            description
    );
  }

  /**
   *
   */
  @Override
  public Boolean chooseBoolean(String description) throws UserTimeoutException {
    WaitFor<String, Boolean> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseBoolean,
            description
    );
  }

  /**
   * choose a room from those proposed
   */
  @Override
  public String chooseRoom(List<String> rooms) throws UserTimeoutException {
    WaitFor<List<String>, String> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseRoom,
            rooms
    );
  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates)
          throws UserTimeoutException {
    WaitFor<List<List<Integer>>, List<Integer>> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseTargetSquare,
            targettableSquareCoordinates
    );
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections)
          throws UserTimeoutException {
    WaitFor<List<Integer>, Integer> wf = new WaitFor<>(this.timeout);
    return wf.waitForFunc(
            this.getPlayer()::chooseDirection,
            possibleDirections
    );
  }

  @Override
  public void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<ArrayList<ArrayList<String>>>, Object> wf = new WaitFor<>(this.timeout);
    wf.waitForFunc(
            (List<ArrayList<ArrayList<String>>> i) -> {
              this.getPlayer().sendMapInfo(i);
              return new Object();
            },
            mapInfo
    );
  }

  @Override
  public void sendPlayerInfo(List<ArrayList<String>> playerInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<ArrayList<String>>, Object> wf = new WaitFor<>(this.timeout);
    wf.waitForFunc(
            (List<ArrayList<String>> i) -> {
              this.getPlayer().sendPlayerInfo(i);
              return new Object();
            },
            playerInfo
    );
  }

  @Override
  public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<ArrayList<String>>, Object> wf = new WaitFor<>(this.timeout);
    wf.waitForFunc(
            (List<ArrayList<String>> i) -> {
              this.getPlayer().sendKillScoreBoardInfo(i);
              return new Object();
            },
            killScoreBoardInfo
    );
  }

  @Override
  public void sendCharacterInfo(List<String> characterInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<String>, Object> wf = new WaitFor<>(this.timeout);
    wf.waitForFunc(
            (List<String> i) -> {
              this.getPlayer().sendCharacterInfo(i);
              return new Object();
            },
            characterInfo
    );
  }

  /**
   * Wait for a call on the remote end, throwing an exception if the user is
   * disconnected, or a response can not be obtained in time
   *
   * @param <I> Param Type
   * @param <R> Return Type
   */
  private final class WaitFor<I extends Object, R extends Object> {
    /**
     * Timeout (in second) before UserTimeoutException is raised
     */
    private int timeout;

    /**
     * TransferQueue for the object
     */
    private TransferQueue<R> tq = new LinkedTransferQueue<>();

    /**
     * Create a new WaitFor
     *
     * @param timeout Timeout in seconds to wait for a response
     */
    WaitFor(int timeout){
      this.timeout = timeout;
    }

    /**
     * Wait for the item generated by supp to become available
     *
     * @param func Supplier Function to wait for. The supplier function accept
     *             a parameter of type I (like a java.util.function.Function)
     * @param i    Input param for the supplier
     *
     * @return The generated item on success
     *
     * @throws UserTimeoutException If an item can not be obtained before
     *                              TIMEOUT expiration
     */
    R waitForFunc(RMIFunction<I, R> func, I i)
            throws UserTimeoutException {
      return this.handler(
              func,
              i
      );
    }

    /**
     * Wait for the item generated by supp to become available
     *
     * @param supp Supplier Function to wait for.
     *
     * @return The generated item on success
     *
     * @throws UserTimeoutException If an item can not be obtained before
     *                              TIMEOUT expiration
     */
    R waitForSupp(RMISupplier<R> supp)
            throws UserTimeoutException {
      return this.handler(
              (I i) -> supp.get(),
              null
      );
    }

    /**
     * Handler for the call. Makes the call on the other end, blocks till a
     * response is received, but unlock if the timeout is reached
     *
     * @param call  Function to call
     * @param i     Input param of the function
     *
     * @return The result of the call
     * @throws UserTimeoutException If timeout is reached
     */
    private R handler(RMIFunction<I, R> call, I i) throws UserTimeoutException {
      new Thread(
              () -> {
                try {
                  if (!this.tq.offer(call.apply(i))){
                    Logger.getLogger(LOG_NAMESPACE).log(
                            Level.SEVERE,
                            "Unable to pass response to the TQ"
                    );
                  }
                }
                catch (RemoteException e){
                  // Ignore exception, triggers timeout
                }
              }
      ).start();

      try {
        R toReturn = this.tq.poll(
                this.timeout,
                TimeUnit.SECONDS
        );

        if (toReturn == null){
          throw new UserTimeoutException();
        }
        else {
          return toReturn;
        }
      }
      catch (InterruptedException e){
        Thread.currentThread().interrupt();
        throw new UserTimeoutException(e);
      }
    }
  }

  @FunctionalInterface
  public interface RMIFunction<I extends Object, R extends Object> {
    R apply(I i) throws RemoteException;
  }

  @FunctionalInterface
  public interface RMISupplier<R extends Object> {
    R get() throws RemoteException;
  }
}
