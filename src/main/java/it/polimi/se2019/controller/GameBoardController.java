package it.polimi.se2019.controller;

import it.polimi.se2019.model.player.Player;

/**
 * The game board controller is the game manager, it initializes a game by
 * generating a map and instantiating the players, it then runs the turns until
 * the number of skulls reaches 0, after that it runs the final frenzy round and
 * ends the game.
 */
public class GameBoardController {
  public GameBoardController() {
  }

  /**
   * The player who's turn it is currently
   */
  private Player currentPlayer;

  /**
   * This method generates one of the four possible maps for the game based
   * on the first player's input
   *
   * @param mapType is an integer that specifies the map to be generated
   */
  public void generateMap(int mapType) {
  }

  /**
   * This method creates a player class (model) for each player who joins the
   * game and adds it to the "players" list in the game board model.
   * A player is created with an inventory containing only one power up
   * card and a player board with empty damage and marks lists,
   * 0 deaths, "8 6 4 2 1 1" death value and board side '0'.
   */
  public void initializePlayers() {
  }

  /**
   * This method is the main game loop, it makes players do actions on their
   * turn, replaces resources that have been picked up during a turn and
   * resolves player deaths.
   */
  public void playTurns() {
  }

  /**
   * This method starts the game by generating a map, initializing players
   * and starting the main game loop
   */
  public void startGame() {
  }

  /**
   * This method manages the final frenzy round which is played when the
   * number of skulls on the scoreboard reaches 0 after this final round the
   * scoreboard will be resolved and the game will end
   */
  public void playFrenzyTurn() {
  }

}