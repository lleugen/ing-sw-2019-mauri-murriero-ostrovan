package it.polimi.se2019.model.player;

import it.polimi.se2019.model.map.Square;

/**
 * Player contains all the player data, and related collections (eg: Inventory)
 */
public class Player {
  public Player(String name, String character) {
    this.name = name;
    this.character = character;
  }

  /**
   *
   */
  private String name;

  /**
   *
   */
  private String character;

  /**
   *
   */
  private PlayerBoard board;

  /**
   *
   */
  private Inventory inventory;

  /**
   * The current square the player is located into
   */
  private Square position;

  /**
   * @return The player character
   */
  public String getCharacter() {
    return this.character;
  }

  /**
   *
   */
  public void getState() {
  }

  /**
   *
   */
  public void setState() {
  }

  /**
   * Add damage to the player
   *
   * @param sender The player that did damage to the current player
   * @param amount The amount of damage taken
   */
  public void takeDamege(Player sender, int amount) {
  }

  /**
   * Add marks to the player
   *
   * @param sender The player that did marks to the current player
   * @param amount The amount of marks taken
   */
  public void takeMarks(Player sender, int amount) {
  }

  /**
   * Move the player
   */
  public void move() {
  }

  /**
   *
   */
  public void resolveDeath() {
  }

  /**
   *
   */
  public void respawn() {
  }

  /**
   * @return the name of the player
   */
  public String getName() {
    return this.name;
  }

  /**
   *
   */
  public PlayerBoard getBoard() {
  }

  /**
   *
   */
  public Inventory getInventory() {
  }

  /**
   * @return the current position of the player
   */
  public Square getPosition() {
  }

}