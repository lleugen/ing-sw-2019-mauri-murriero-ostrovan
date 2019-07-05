package it.polimi.se2019.model.server;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.Remote;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
/**
 * @author Fabio Mauri
 */
public class ServerLobby implements Remote {
  /**
   * Maximum number of player that can join a room
   */
  private static final int MAX_PLAYERS = 3;

  /**
   * Minimum players for starting the game
   */
  private static final int MIN_PLAYERS = 2;

  /**
   * Map of connected players, indexed by id
   */
  private Map<String, PlayerData> playersData;

  /**
   * Reference to the gameBoardController linked to this lobby
   */
  private GameBoardController gameBoardController;

  /**
   * True if the game was already started, false otherwise.
   * This flag ensures that a game is not started twice, first by the lobby
   * completion, then by the timeout
   */
  private boolean gameStarted = false;

  /**
   * Create a new lobby
   *
   * @param mapType Id of the map to generate
   * @param timeout Timeout (in seconds) before starting game EVEN if
   *                MAX_PLAYERS is not reached
   *
   * @throws UnknownMapTypeException If the selected map doesn't exist
   */
  ServerLobby(Integer mapType, int timeout) throws UnknownMapTypeException {
    GameBoard gameBoard = new GameBoard(mapType);
    this.gameBoardController = new GameBoardController(gameBoard);
    this.playersData = Collections.synchronizedMap(new HashMap<>());
    Executors.newScheduledThreadPool(1)
            .schedule(
                    this::startGame,
                    timeout,
                    TimeUnit.SECONDS
            );
  }

  /**
   * Starts the game, only if the game was not already started
   */
  private synchronized void startGame(){
    if (!this.gameStarted) {
      this.gameStarted = true;
      if (this.playersData.size() >= MIN_PLAYERS) {
        this.gameBoardController.getGameBoard().addPlayers(
                this.playersData.values().stream()
                        .map(PlayerData::getModel)
                        .collect(Collectors.toList())
        );

        this.gameBoardController.startGame(
                this.playersData.values().stream()
                        .map(PlayerData::getController)
                        .collect(Collectors.toList())
        );
      }
    }
  }

  /**
   * Add a player to the lobby, only if the lobby is still open and there is
   * available space
   *
   * @param client player's view
   * @param name name of the player
   * @param character id of the player's character
   *
   * @return true if the player was added with success (or was already
   *         registered to the server), false otherwise
   */
  synchronized boolean addPlayer(PlayerViewOnServer client, String name, String character) {
    if (
            (this.playersData.size() < MAX_PLAYERS) &&
            (!this.gameStarted)
    ) {
      PlayerData player = new PlayerData();
      Player playerModel = new Player(
              name,
              character,
              this.gameBoardController.getGameBoard()
      );
      player.setModel(playerModel);
      player.setController(
              new PlayerController(
                      this.gameBoardController,
                      playerModel,
                      client
              )
      );
      player.setView(client);

      if (!this.playersData.containsKey(name)){
        this.playersData.put(name, player);
      }

      if (this.playersData.size() == MAX_PLAYERS){
        this.startGame();
      }

      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Contains MVC infos about a player
   */
  private static class PlayerData {
    /**
     * VirtualView associated with the player
     */
    private PlayerViewOnServer view;

    /**
     * Model of the player
     */
    private Player model;

    /**
     * Controller of the player
     */
    private PlayerController controller;

    /**
     * Create a new PlayerData
     */
    PlayerData() {
      this.view = null;
      this.model = null;
      this.controller = null;
    }

    /**
     * @return The Virtual View associated with the player
     */
    public PlayerViewOnServer getView() {
      return view;
    }

    /**
     * Set the VirtualView for a player
     *
     * @param view VV to associate to the player
     */
    public void setView(PlayerViewOnServer view) {
      this.view = view;
    }

    /**
     * @return The model associated with the player
     */
    public Player getModel() {
      return model;
    }

    /**
     * Set the model for the player
     *
     * @param model Model of the player
     */
    public void setModel(Player model) {
      this.model = model;
    }

    /**
     * @return The controller associated with the player
     */
    public PlayerController getController() {
      return controller;
    }

    /**
     * Set the controller for the player
     * @param controller Controller of the player
     */
    public void setController(PlayerController controller) {
      this.controller = controller;
    }
  }
}
