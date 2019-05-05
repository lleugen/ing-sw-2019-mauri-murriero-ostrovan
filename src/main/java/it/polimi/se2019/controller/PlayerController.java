package it.polimi.se2019.controller;

import it.polimi.se2019.controller.player_state.PlayerStateController;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.server.GameBoard;

/**
 * This class controls player actions, it contains the player's current state
 * which is determined by the amount of damage received or the state of
 * the game (regular turns or final frenzy round).
 * The class contains the implementation of all basic possible actions
 * (move, grab, shoot, reload, respawn) which are combined into different
 * complex actions that a player can make during a turn
 */
public class PlayerController {

  /**
   *
   */
  public PlayerController() {
  }

  /**
   *
   */
  private PlayerStateController state;

  /**
   *
   */
  private GameBoard gameBoardReference;


  /**
   * Move the player 1 square in one of four directions
   *
   * @param direction is the direction that the players moves towards,
   *                  it is a class containing a reference the next
   *                  square and information on whether there is a wall or not
   */
  public void move(Player player, Direction direction) {
    if(direction.isBlocked()){
      //return cannot move exception
    }
    else {
      player.move(direction);
    }
  }

  /**
   * Make the player spawn at the start of the game or after being killed.
   * The player has to draw a power up card, then discard one and spawns in
   * the square corresponding to the discarded card's equivalent ammo colour.
   */
  public void spawn(Player player) {
    player.getInventory().addToInventory(gameBoardReference.getDecks().drawPowerUp());
    //ask player to choose a power up card to discard
    //playerView.chooseSpawnLocation??
  }

  /**
   * Make the player choose a weapon from his or her inventory and fire it.
   * Once the weapon is chosen, weapon specific methods will be invoked to
   * choose targets and fire.
   */
  public void shoot() {
  }

  /**
   * Spend ammo to reload an unloaded weapon from the player's inventory.
   * Choose which weapon to reload, subtract the reload cost from inventory
   * and set the weapon as loaded.
   */
  public void reload() {
  }

  /**
   * Grab the ammo tile or a weapon from the current square and add
   * the corresponding resources to the inventory.
   */
  public void grab(Player player) {
    player.getPosition().
  }
}
