package it.polimi.se2019.controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.player_state_controller.*;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class controls player actions, it contains the player's current state
 * which is determined by the amount of damage received or the state of
 * the game (regular turns or final frenzy round).
 * The class contains the implementation of all basic possible actions
 * (move, grab, shoot, reload, respawn) which are combined into different
 * complex actions that a player can make during a turn
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class PlayerController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "PlayerController";

  private PlayerViewOnServer client;
  private Player player;
  private PlayerStateController state;
  private List<PlayerStateController> stateControllerList;

  /**
   *
   * @param g the controller of the board that the player is playing on
   * @param p the player associated to this controller
   * @param c the client that commands the player
   */
  public PlayerController(GameBoardController g, Player p, PlayerViewOnServer c) {
    client = c;
    player = p;
    stateControllerList = new ArrayList<>();
    stateControllerList.add(0, new NormalStateController(g, p, c));
    stateControllerList.add(1, new Adrenaline1StateController(g, p, c));
    stateControllerList.add(2, new Adrenaline2StateController(g, p, c));
    stateControllerList.add(3, new FirstFreneticStateController(g, p, c));
    stateControllerList.add(4, new SecondFreneticStateController(g, p, c));
    state = stateControllerList.get(0);
  }

  /**
   * Ask a client if he wants to reload a weapon, and reload it
   * Available weapons are referred to this user
   *
   * @param c Reference to the client
   * @param p Model of the player
   * @throws UserTimeoutException if the player takes too long to respond or disconnects
   */
  public static void reloadWeapon(PlayerViewOnServer c, Player p) throws UserTimeoutException {
    if(c.chooseBoolean("Do you want to reload a weapon?")){
      List<String> emptyWeapons = p.getInventory().getWeapons().stream()
              .filter(Weapon::isUnloaded)
              .map(Weapon::getName)
              .collect(Collectors.toList());

      String selectedWeapon = c.chooseWeaponToReload(emptyWeapons);

      List<Weapon> weaponsToReload = p.getInventory().getWeapons().stream()
              .filter((Weapon w) -> selectedWeapon.equals(w.getName()))
              .collect(Collectors.toList());

      for (Weapon w : weaponsToReload) {
        w.reload(
                getPowerUpsForReload(c, p),
                p.getInventory().getAmmo()
        );
      }
    }
  }

  /**
   * Ask the client to select power ups for reload a weapon.
   * Available powerups are referred to this client
   *
   * @param c Reference to the client
   * @param p Model of the player
   *
   * @return The list of selected PowerUps
   * @throws UserTimeoutException if the player takes too long to respond or disconnects
   */
  private static List<PowerUpCard> getPowerUpsForReload(PlayerViewOnServer c, Player p)
          throws UserTimeoutException {

    List<String> descs = p.getInventory().getPowerUps().stream()
            .map(PowerUpCard::getDescription)
            .collect(Collectors.toList());

    return c.choosePowerUpCardsForReload(descs).stream()
            .map(p.getInventory().getPowerUps()::get)
            .collect(Collectors.toList());
  }

  /**
   * @param index indicates one state in the stateControllerList
   *              0 : normal state
   *              1 : adrenaline 1 state
   *              2 : adrenaline 2 state
   *              3 : first frenetic state (taking frenzy turn before player 0)
   *              4 : second frenetic state (taking frenzy turn after player 0)
   */
  public void setState(Integer index){
    state = stateControllerList.get(index);
  }

  /**
   *
   * @return the state of the player which determines the available actions
   */
  public PlayerStateController getState(){
    return state;
  }

  /**
   * @return the name of the player linked to this controller
   */
  public String getName(){
    return this.player.getName();
  }

  /**
   * @return A reference to the player linked to this controller
   */
  public Player getPlayer(){
    return this.player;
  }

  /**
   * Take turn
   * @param availableActions the number of actions that the player can take on his or her turn
   * @throws UserTimeoutException if the player takes too long to respond or disconnects
   */
  public void playTurn(Integer availableActions) throws UserTimeoutException{
    while (availableActions > 0){
      switch (
              client.chooseAction(
                      state.toString()
              )
      ) {
        case "run":
          state.runAround();
          availableActions--;
          break;
        case "grab":
          state.grabStuff();
          availableActions--;
          break;
        case "shoot":
          state.shootPeople();
          availableActions--;
          break;
        case "powerUp":
          state.usePowerUp();
          break;
        default:
          Logger.getLogger(LOG_NAMESPACE).log(
                  Level.INFO,
                  "User selected a wrong action {0}",
                  state
          );
      }
    }
  }

  /**
   *
   * @return the client that is commanding this player
   */
  public PlayerViewOnServer getClient(){
    return this.client;
  }
}
