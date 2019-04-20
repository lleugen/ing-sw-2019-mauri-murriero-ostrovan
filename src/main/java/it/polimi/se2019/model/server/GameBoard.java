package it.polimi.se2019.model.server;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;

import java.util.List;

/**
 * The game board is the root class of each game.
 * It contains and initializes every element of the game, and is responsible
 * for managing the highest tasks of the match (AKA the game manager)
 */
public class GameBoard {
  public GameBoard() {
  }

  /**
   *
   */
  private GameBoard gameBoard;

  /**
   *
   */
  private KillScoreBoard killScoreBoard;

  /**
   *
   */
  private Map map;

  /**
   *
   */
  private List<Player> players;

  /**
   *
   */
  private Player firstPlayer;

  /**
   *
   */
  private Player currentPlayer;

  /**
   *
   */
  public GameBoard getGameBoard() {
  }
}
