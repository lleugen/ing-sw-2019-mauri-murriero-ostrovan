package it.polimi.se2019.model.server;

import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;

import java.util.List;

/**
 * The game board is the root class of each game.
 * It contains and initializes every element of the game, and is responsible
 * for managing the highest tasks of the match (AKA the game manager)
 */
public class GameBoard {
  /**
   * Contains a reference to the Kill Scoreboard for the current game
   */
  private KillScoreBoard killScoreBoard;

  /**
   * Contains a reference to the selected Map for the current game
   */
  private Map map;

  /**
   * Contains the list of the players for the current game
   */
  private List<Player> players;

  /**
   * Contains the id of the first player in the players list
   */
  private Integer firstPlayer;

  /**
   * Contains the id of the player currently playing the turn
   */
  private Integer currentPlayer;

  /**
   * Inits the gameBoard
   *
   * @param mapType Type of the map to generate
   * @param players A list of already initialized players
   * @param firstPlayer Id of the first player in the players list
   *
   * @throws NullPointerException if players contains a null player
   */
  public GameBoard(String mapType, List<Player> players, Integer firstPlayer){
    this.killScoreBoard = new KillScoreBoard();
    this.firstPlayer = firstPlayer;
    this.currentPlayer = firstPlayer;
    this.map = new Map(mapType);

    Integer i;
    Player tmp;
    for (i = 0; i < players.size(); i++){
      tmp = players.get(i);
      this.players.add(tmp);
    }
  }

  /**
   * Updates the gameboard to set the next player as active
   */
  public synchronized void nextPlayer(){
    Integer nextPlayerId = this.currentPlayer + 1;

    // If last player, restarting from the beginning

    if (nextPlayerId == (this.players.size() - 1)){
      nextPlayerId = 0;
    }
    this.currentPlayer = nextPlayerId;
  }

  /**
   * @return the current player
   */
  public Player getCurrentPlayer(){
    return this.players.get(this.currentPlayer);
  }

  /**
   * @return the kill scoreboard
   */
  public KillScoreBoard getKillScoreBoard(){
    return this.killScoreBoard;
  }

  /**
   * @return the map
   */
  public Map getMap() {
    return this.map;
  }

  /**
   * Contains the list of decks associated to this GameBoard
   */
  private Decks decks;

  /**
   * @return the decks
   */
  public Decks getDecks(){
    return this.decks;
  }
}
