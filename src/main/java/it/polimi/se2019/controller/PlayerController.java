package it.polimi.se2019.controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.player_state_controller.*;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class controls player actions, it contains the player's current state
 * which is determined by the amount of damage received or the state of
 * the game (regular turns or final frenzy round).
 * The class contains the implementation of all basic possible actions
 * (move, grab, shoot, reload, respawn) which are combined into different
 * complex actions that a player can make during a turn
 */
public class PlayerController {
  private PlayerViewOnServer client;
  private Player player;
  private PlayerStateController state;
  private List<PlayerStateController> stateControllerList;

  /**
   *
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
   */
  public void playTurn(Integer availableActions) throws UserTimeoutException{
    //use power up
    for(int i = 0; i<availableActions; i++){
      String chosenAction;
      chosenAction = client.chooseAction(state.toString());

      if(chosenAction.equals("run")){
        state.runAround();
      }
      else if(chosenAction.equals("grab")){
        state.grabStuff();
      }
      else if(chosenAction.equals("shoot")){
        state.shootPeople();
      }
      else if(chosenAction.equals("powerUp")){
        i--;
        state.usePowerUp();
      }
    }
  }

  public PlayerViewOnServer getClient(){
    return client;
  }
}
