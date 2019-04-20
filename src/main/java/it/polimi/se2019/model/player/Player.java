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
   * The player's name
   */
  private String name;

  /**
   * The player's playable character
   */
  private String character;

  /**
   * The player's board, containing damage received, marks, deaths and death value.
   */
  private PlayerBoard board;

  /**
   * The player's inventory, containing ammo, weapons and power up cards
   */
  private Inventory inventory;

  /**
   * The current square the player is located on
   */
  private Square position;

  /**
   * @return the player's character
   */
  public String getCharacter() {
    return this.character;
  }

  /**
   * @return the player's state, which determines the available actions
   */
  public void getState() {
  }

  /**
   * Set the player's state, which determines the available actions
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
    for (int i = 0; i < amount; i++) {
      this.board.setDamage(sender);
    }
  }

  /**
   * Add marks to the player
   *
   * @param sender The player that did marks to the current player
   * @param amount The amount of marks taken
   */
  public void takeMarks(Player sender, int amount) {
    for (int i = 0; i < amount; i++) {
      this.board.setMark(sender);
    }
  }

  /**
   * Move the player
   */
  public void move() {

  }

  /**
   * Give points to the players who dealt damage to the player
   */
  public void resolveDeath() {
  }

  /**
   * Bring the player back into the game : set damage and marks to 0, draw a power up card, discard a power up card
   * and spawn in the spawn point of the card's equivalent ammo colour
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
   * @return the player's board
   */
  public PlayerBoard getBoard() {
  }

  /**
   * @return the player's inventory
   */
  public Inventory getInventory() {
  }

  /**
   * @return the current position of the player
   */
  public Square getPosition() {
  }
}

