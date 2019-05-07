package it.polimi.se2019.controller.player_state;

public abstract class PlayerStateController {
  /**
   * The player state controller is used to implement the state pattern:
   * actions available to a player depend on how much damage he/she has taken
   * and the state of the game. We have distinguish between five player states
   * based on those criteria:
   * - __normal state__: the player has taken less than 3 damage and the game
   * is not in the final frenzy round adrenaline 1 state: having taken 3 or
   * more damage, the player unlocks the first adrenaline action, the game is
   * not in the final frenzy round
   * - __adrenaline 2 state__: after taking 6 or more damage, the player has
   * access to an improved "shoot people" action first frenetic state:
   * actions available to a player taking his turn before the first player in
   * the final frenzy round second frenetic state: actions available to a
   * player taking his turn after the first player in the final frenzy round
   * <p>
   * The player controller class contains a reference to one implementation
   * of the player state class which defines the implementations of the
   * actions a player can take. The state can change when the player
   * takes damage.
   */

  public PlayerStateController() {

  }

  /**
   *
   */
  protected int turnActionLimit;

  /**
   *
   */
  protected int availableActions;

  /**
   *
   * @param maxDistance max distance from current square to the final square
   */
  protected void move(int maxDistance){

  }

  /**
   *
   */
  protected void grab(){

  }

  /**
   *
   * @param damages
   * @param marks
   */
  protected void attack(int damages, int marks){

  }

  /**
   *
   */
  public void doAction() {
  }

  /**
   * @return the number of actions the player can still make on the
   * current turn
   */
  public int getAvailableActions() {
    return availableActions;
  }

  /**
   *
   */
  private void resetActions() {
    this.availableActions = turnActionLimit;
  }

  /**
   *
   */
  public void doEndTurnAction() {
  }
}