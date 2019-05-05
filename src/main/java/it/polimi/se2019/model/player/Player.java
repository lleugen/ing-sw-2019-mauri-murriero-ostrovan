package it.polimi.se2019.model.player;

import it.polimi.se2019.controller.player_state.PlayerStateController;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;

import it.polimi.se2019.model.server.GameBoard;
import java.util.List;

/**
 * Player contains all the player data, and related collections (eg: Inventory)
 */
public class Player {
  public Player(String name, String character, GameBoard gameBoard) {
    this.name = name;
    this.character = character;
    this.points = 0;
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
   * The current amount of points fo the player.
   */
  private Integer points;

  /**
   *
   */
  private GameBoard gameBoardReference;

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
  public void setState(PlayerStateController state) {
  }

  /**
   * Add damage to the player
   *
   * @param sender The player that did damage to the current player
   * @param amount The amount of damage taken
   */
  public void takeDamage(Player sender, int amount) {
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
  public void move(Direction direction) {
    this.position = direction.getSquare();
  }

  /**
   * Give points to the players who dealt damage to the player
   */
  public void resolveDeath() {
    List<Player> damages = board.getDamageReceived();
    List<Integer> pointsList = board.getDeathValue();

    //*** death to be resolved
    if(damages.size() <= 11){
      int playerAlreadyPaid = 0; //how many player the routine has already paid with points

      //first blood avaiilable only in non-frenzy mode
      if(!board.getIfIsFrenzy())
        damages.get(0).addPoints(1);

      //get the player who dealt max damage, then add points and remove those damage from the vector
      //and continue until the damages vector is empty
      while(!damages.isEmpty()){
        Player currentMostWorthyPlayer = board.getMostWorthyPlayer();
        currentMostWorthyPlayer.addPoints(pointsList.get(playerAlreadyPaid));

        //the points given to the player are never less than 1, so 1 is the last element of the DamagesPoints list
        //so if its the case, you stop from incrementing the index of the list when you reach 1
        if(pointsList.get(0) != 1){
          playerAlreadyPaid++;
        }

        //remove paid player from the list of points
        Integer i = 0;
        while(i < damages.size())
          if(damages.get(i) == currentMostWorthyPlayer)
            damages.remove(i);
          else
            i++;
      }
      respawn();
    }
  }

  /**
   * Bring the player back into the game : set damage to 0, draw a power up card, discard a power up card
   * and spawn in the spawn point of the card's equivalent ammo colour
   */
  public void respawn() {
    board.getDamageReceived().clear();

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
    return board;
  }

  /**
   * @return the player's inventory
   */
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * @return the current position of the player
   */
  public Square getPosition() {
    return position;
  }

  /**
   * @return the current points of the player
   */
  public Integer getPoints() {
    return points;
  }

  /**
   * @return the current position of the player
   *
   * @param value: the amount of points just collected
   */
  public void addPoints(Integer value) {
    points += value;
  }
}

