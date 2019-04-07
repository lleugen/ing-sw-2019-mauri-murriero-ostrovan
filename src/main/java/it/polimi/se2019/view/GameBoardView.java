package it.polimi.se2019.view;

import it.polimi.se2019.view.map.MapView;
import it.polimi.se2019.view.player.PlayerView;

import java.util.List;

/**
 * The game board view displays everything that a player sees and uses to
 * interact with the game.
 */
public class GameBoardView {
  /**
   * The game board view is created at the start of the game.
   */
  public GameBoardView() {
  }

  /**
   * Map view displays the map: rooms, squares, players, ammo tiles, walls.
   */
  private MapView map;

  /**
   * Display the skull scoreboard and the list of double kills.
   */
  private ScoreBoardView scoreBoard;

  /**
   * Scoreboard view displays the skull scoreboard and the list of double kills
   */
  private List<SpawnPointView> spawnPoints;

  /**
   * Player view displays player information such as cards in hand and
   * player board
   */
  private List<PlayerView> players;

  /**
   *
   */
  public void displayMap() {
  }

  /**
   *
   */
  public void displayPlayers() {
  }

  /**
   *
   */
  public void displayScoreBoard() {
  }

  /**
   *
   */
  public void displaySpawnPoints() {
  }
}