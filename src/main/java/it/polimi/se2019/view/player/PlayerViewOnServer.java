package it.polimi.se2019.view.player;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMI;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIServer;
import it.polimi.se2019.view.PlayerOnClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlayerViewOnServer implements ViewFacadeInterfaceRMIServer {
  private String name;
  private String character;

  /**
   * RMI Reference to the connected player
   */
  private ViewFacadeInterfaceRMI connectedPlayer;

  /**
   *
   * @param user Nickname of the user
   * @param host Hostname the registry is located to
   *
   * @throws InitializationError If an error occurs while initializing the player
   */
  public PlayerViewOnServer(String user, String host) throws InitializationError {
    try {
      this.connectedPlayer = (ViewFacadeInterfaceRMI) Naming.lookup(
              "//" + host + "/players/" + user
      );
    }
    catch (MalformedURLException | NotBoundException | RemoteException e){
      throw new InitializationError(e);
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
            this.connectedPlayer::chooseMap
    );
  }

  /**
   * choose how many players will be in the game
   */
  @Override
  public int chooseNumberOfPlayers() throws UserTimeoutException {
    WaitFor<Void, Integer> wf = new WaitFor<>();
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
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
    return wf.waitFor(
            this.connectedPlayer::chooseDirection,
            possibleDirections
    );
  }

  /**
   *
   * @param <I> Param Type
   * @param <R> Return Type
   */
  private final class WaitFor<I extends Object, R extends Object> {
    /**
     * Timeout (in second) before UserTimeoutException is raised
     */
    private static final int TIMEOUT = 15;

    /**
     * TransferQueue for the object
     */
    private TransferQueue<R> tq = new LinkedTransferQueue<>();

    /**
     * Wait for the item generated by supp to become available
     *
     * @param supp Supplier Function to wait for. The supplier function can
     *             optionally accept a parameter
     * @param i    Input param for the supplier
     *
     * @return The generated item on success
     *
     * @throws UserTimeoutException If an item can not be obtained before
     *                              TIMEOUT expiration
     */
    public R waitFor(RMIFunction<I, R> supp, I i) throws UserTimeoutException {
      new Thread(
              () -> {
                try {
                  this.tq.offer(supp.apply(i));
                }
                catch (RemoteException e){
                  // Ignore exception, triggers timeout
                }
              }
      ).start();

      try {
        return this.tq.poll(
                TIMEOUT,
                TimeUnit.SECONDS
        );
      }
      catch (InterruptedException e){
        throw new UserTimeoutException(e);
      }
    }

    /**
     * Wait for the item generated by supp to become available
     *
     * @param supp Supplier Function to wait for. The supplier function can
     *             optionally accept a parameter
     *
     * @return The generated item on success
     *
     * @throws UserTimeoutException If an item can not be obtained before
     *                              TIMEOUT expiration
     */
    public R waitFor(RMISupplier<R> supp) throws UserTimeoutException {
      new Thread(
              () -> {
                try {
                  this.tq.offer(supp.get());
                }
                catch (RemoteException e){
                  // Ignore exception, triggers timeout
                }
              }
      ).start();

      try {
        return this.tq.poll(
                TIMEOUT,
                TimeUnit.SECONDS
        );
      }
      catch (InterruptedException e){
        throw new UserTimeoutException(e);
      }
    }
  }

  public class InitializationError extends Exception {
    public InitializationError(Throwable e){
      super(e);
    }
  }

  public interface RMIFunction<I extends Object, R extends Object> {
    R apply(I i) throws RemoteException;
  }

  public interface RMISupplier<R extends Object> {
    R get() throws RemoteException;
  }
}
