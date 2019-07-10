package it.polimi.se2019.model.player;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;

import java.util.List;

/**
 * Player contains all the player data, and related collections (eg: Inventory)
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public class Player {
  public Player(String name, String character, GameBoard g) {
    this.name = name;
    this.character = character;
    this.points = 0;
    this.state = 0;
    gameBoardReference = g;
    board = new PlayerBoard();
    inventory = new Inventory(this, gameBoardReference.getDecks());
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
   * The state the players is in, which determines the possible actions
   */
  private int state;

  /**
   * The reference to the game board that this player is playing on
   */
  private GameBoard gameBoardReference;

  /**
   *
   * @return the state that this player is currently in
   */
  public int getState(){
    return state;
  }

  /**
   * Set the player's state
   */
  public void setState(int s){
    if(s>=0 && s <=4){
      state = s;
    }
  }

  /**
   * @return the player's character
   */
  public String getCharacter() {
    return this.character;
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
    if(!gameBoardReference.isFrenzy()){
      if(board.getDamageReceived().size()>=3 && board.getDamageReceived().size() < 6){
        setState(1);
      }
      else if(board.getDamageReceived().size() >=6){
        setState(2);
      }
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
   * Move the player in the specified direction
   * @param direction the direction towards which to move:
   *                  0 : north
   *                  1 : east
   *                  2 : south
   *                  3 : west
   */
  public void move(Direction direction) {
    this.position = direction.getSquare();
  }

  /**
   * Move the player to the argument square
   * @param destination the square where the player will move
   */
  public void moveToSquare(Square destination){
    position = destination;
    if(destination instanceof SpawnSquare){
      System.out.println("moved to spawnsquare");
    }
    else{
      System.out.println("moved to ammo sqaure");
    }

  }
  /**
   * Give points to the players who dealt damage to the player
   */
  public void resolveDeath() {
    List<Player> damages = board.getDamageReceived();
    List<Integer> pointsList = board.getDeathValue();

    //*** death to be resolved
    if(damages.size() > 10){
      gameBoardReference.getKillScoreBoard().addKill(this.board.getDamageReceived()
              .get(this.board.getDamageReceived().size()-1));
      if(damages.size()>11){
        gameBoardReference.getKillScoreBoard().addOverKill(true);
      }
      else{
        gameBoardReference.getKillScoreBoard().addOverKill(false);
      }
      //how many player the routine has already paid with points
      int playerAlreadyPaid = 0;

      //first blood available only in non-frenzy mode
      if(!board.getIfIsFrenzy()){
        damages.get(0).addPoints(1);
      }
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
        while(damages.contains(currentMostWorthyPlayer)){
          damages.remove(currentMostWorthyPlayer);
        }
      }
    }
    if(gameBoardReference.isFrenzy()){
      board.turnAround();
    }
  }

  /**
   * Bring the player back into the game : set damage to 0, draw a power up card, discard a power up card
   * and spawn in the spawn point of the card's equivalent ammo colour
   * @param spawnPoint the spawn square on which to respawn, possible values are
   *                   map.getRedSpawnPoint
   *                   map.getBlueSpawnPoint
   *                   map.getYellowSpawnPoint
   */
  public void respawn(Square spawnPoint) {
    board.getDamageReceived().clear();
    position = spawnPoint;
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
   *
   *
   * @param value: the amount of points just collected
   */
  public void addPoints(Integer value) {
    points += value;
  }
}

