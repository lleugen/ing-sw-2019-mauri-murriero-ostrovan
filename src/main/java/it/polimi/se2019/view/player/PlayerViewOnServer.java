package it.polimi.se2019.view.player;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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

  private String name;
  private String character;

  /**
   * Hostname the RMI registry is located to
   */
  private String host;

  /**
   * True if a connection to the other end exists, false otherwise
   */
  private boolean connected = true;

  public Boolean isConnected(){
    if (!this.connected){
      this.makeConnection();
    }

    return this.connected;
  }

  /**
   * Attempt to bind this player to a remote player on the RMI registry
   *
   * The host is on the property host, the user on the property name.
   * Property connected is set according to success
   */
  private void makeConnection(){
    try {
      this.connectedPlayer = (ViewFacadeInterfaceRMIClient) Naming.lookup(
              "//" + this.host + "/players/" + this.name
      );
      this.connected = true;
    }
    catch (MalformedURLException | NotBoundException | RemoteException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.INFO,
              "Unable to reconnect player <" + this.name + ">",
              e
      );
      this.connected = false;
    }
  }

  /**
   * RMI Reference to the connected player
   */
  private ViewFacadeInterfaceRMIClient connectedPlayer;

  /**
   *
   * @param user Nickname of the user
   * @param host Hostname the registry is located to
   *
   * @throws InitializationError If an error occurs while initializing the player
   */
  public PlayerViewOnServer(String user, String host) throws InitializationError {
    this.host = host;
    this.name = user;
    this.makeConnection();

    if (!this.connected){
      throw new InitializationError(
              "Unable to initialize player <" + this.name + ">"
      );
    }
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCharacter(String character) {
    this.character = character;
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
    WaitFor<String, String> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseAction,
            state
    );
  }

  /**
   * @return index of the power up card to discard
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps) throws UserTimeoutException {
    WaitFor<List<String>, Integer> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseSpawnLocation,
            powerUps
    );
  }

  /**
   * Choose map type for the match
   */
  @Override
  public int chooseMap() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>();
    return wf.waitForSupp(
            this,
            this.connectedPlayer::chooseMap
    );
  }

  /**
   * choose how many players will be in the game
   */
  @Override
  public int chooseNumberOfPlayers() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>();
    return wf.waitForSupp(
            this,
            this.connectedPlayer::chooseNumberOfPlayers
    );
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(List<String> weapons)
          throws UserTimeoutException {
    WaitFor<List<String>, String> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseWeapon,
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
    WaitFor<List<String>, String> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseTargets,
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
    WaitFor<List<String>, String> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseWeaponToReload,
            weapons
    );
  }

  /**
   * @return a list of integers indicating which cards from the player's inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps)
          throws UserTimeoutException {
    WaitFor<List<String>, List<Integer>> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::choosePowerUpCardsForReload,
            powerUps
    );
  }

  /**
   * @return the integer relative to the availableEffects list
   */
  @Override
  public Integer chooseIndex(List<String> availableEffects)
          throws UserTimeoutException {
    WaitFor<List<String>, Integer> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseIndex,
            availableEffects
    );
  }

  /**
   * @return int indicating which item to pick up from those available
   */
  @Override
  public int chooseItemToGrab() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>();
    return wf.waitForSupp(
            this,
            this.connectedPlayer::chooseItemToGrab
    );
  }

  /**
   * choose whether to use a firing mode
   */
  @Override
  public Boolean chooseFiringMode(String description)
          throws UserTimeoutException {
    WaitFor<String, Boolean> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseFiringMode,
            description
    );
  }

  /**
   *
   */
  @Override
  public Boolean chooseBoolean(String description) throws UserTimeoutException {
    WaitFor<String, Boolean> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseBoolean,
            description
    );
  }

  /**
   * choose a room from those proposed
   */
  @Override
  public String chooseRoom(List<String> rooms) throws UserTimeoutException {
    WaitFor<List<String>, String> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseRoom,
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
    WaitFor<List<List<Integer>>, List<Integer>> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseTargetSquare,
            targettableSquareCoordinates
    );
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections)
          throws UserTimeoutException {
    WaitFor<List<Integer>, Integer> wf = new WaitFor<>();
    return wf.waitForFunc(
            this,
            this.connectedPlayer::chooseDirection,
            possibleDirections
    );
  }

  @Override
  public void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<ArrayList<ArrayList<String>>>, Object> wf = new WaitFor<>();
    wf.waitForFunc(
            this,
            (List<ArrayList<ArrayList<String>>> i) -> {
              this.connectedPlayer.sendMapInfo(i);
              return new Object();
            },
            mapInfo
    );
  }

  @Override
  public void sendPlayerInfo(List<ArrayList<String>> playerInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<ArrayList<String>>, Object> wf = new WaitFor<>();
    wf.waitForFunc(
            this,
            (List<ArrayList<String>> i) -> {
              this.connectedPlayer.sendPlayerInfo(i);
              return new Object();
            },
            playerInfo
    );
  }

  @Override
  public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<ArrayList<String>>, Object> wf = new WaitFor<>();
    wf.waitForFunc(
            this,
            (List<ArrayList<String>> i) -> {
              this.connectedPlayer.sendKillScoreBoardInfo(i);
              return new Object();
            },
            killScoreBoardInfo
    );
  }

  @Override
  public void sendCharacterInfo(List<String> characterInfo)
          throws UserTimeoutException, RemoteException {
    WaitFor<List<String>, Object> wf = new WaitFor<>();
    wf.waitForFunc(
            this,
            (List<String> i) -> {
              this.connectedPlayer.sendCharacterInfo(i);
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
    private static final int TIMEOUT = 2;

    /**
     * TransferQueue for the object
     */
    private TransferQueue<R> tq = new LinkedTransferQueue<>();

    /**
     * Wait for the item generated by supp to become available
     *
     * @param me   Reference to a PlayerViewOnServer to operate on.
     *             If a timeout is reached, connected is set to false on this
     *             reference
     * @param func Supplier Function to wait for. The supplier function accept
     *             a parameter of type I (like a java.util.function.Function)
     * @param i    Input param for the supplier
     *
     * @return The generated item on success
     *
     * @throws UserTimeoutException If an item can not be obtained before
     *                              TIMEOUT expiration
     */
    R waitForFunc(PlayerViewOnServer me, RMIFunction<I, R> func, I i)
            throws UserTimeoutException {
      return this.handler(
              me,
              func,
              i
      );
    }

    /**
     * Wait for the item generated by supp to become available
     *
     * @param me   Reference to a PlayerViewOnServer to operate on.
     *             If a timeout is reached, connected is set to false on this
     *             reference
     * @param supp Supplier Function to wait for.
     *
     * @return The generated item on success
     *
     * @throws UserTimeoutException If an item can not be obtained before
     *                              TIMEOUT expiration
     */
    R waitForSupp(PlayerViewOnServer me, RMISupplier<R> supp)
            throws UserTimeoutException {
      return this.handler(
              me,
              (I i) -> supp.get(),
              null
      );
    }

    private R handler(PlayerViewOnServer me, RMIFunction<I, R> call, I i) throws UserTimeoutException {
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
                TIMEOUT,
                TimeUnit.SECONDS
        );

        if (toReturn == null){
          me.connected = false;
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

  public class InitializationError extends Exception {
    InitializationError(String msg){
      super(msg);
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
