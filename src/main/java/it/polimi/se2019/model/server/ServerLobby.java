package it.polimi.se2019.model.server;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.Remote;
import java.util.*;

public class ServerLobby implements Remote {
  private Map<String, PlayerData> playersData;
  private Integer maxPlayers;
  private GameBoardController gameBoardController;

  ServerLobby(Integer playerCount, Integer mapType){
    this.maxPlayers = playerCount;
    GameBoard gameBoard = new GameBoard(mapType);
    this.gameBoardController = new GameBoardController(gameBoard);
    this.playersData = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Add a new player to the game, only if the nickname doesn't exists in the
   * current game
   *
   * @param name nickname of the player
   *
   * @return a reference to the created player, to be initialized
   *         null if the player already exists
   *
   * @throws RoomFullException if the player can't be added cause the room
   *                           is full
   */
  private synchronized PlayerData addPlayer(String name)
          throws RoomFullException {
    if (this.checkRoomFull() && this.checkPlayerNotExists(name)){
      if (this.checkPlayerNotExists(name)){
        PlayerData toReturn = new PlayerData();
        this.playersData.put(name, toReturn);
        return toReturn;
      }
      else {
        return null;
      }
    }
    else {
      throw new RoomFullException();
    }
  }

  /**
   * Check if a room is full
   *
   * @return true if the room is full, false otherwise
   */
  public synchronized boolean checkRoomFull(){
    return (this.playersData.size() >= this.maxPlayers);
  }

  /**
   * Check if a player is already registered to the lobby
   *
   * @param name name of the player to check
   *
   * @return true if the player is already registered, false otherwise
   */
  private boolean checkPlayerNotExists(String name){
    return this.playersData.containsKey(name);
  }

  /**
   * Handle connections of players.
   * A player must call this method to connect to to the server
   *
   * @param client player's view
   * @param name name of the player
   * @param character id of the player's character
   *
   * @throws RoomFullException if the room is full
   */
  public void connect(PlayerViewOnServer client, String name, String character) throws RoomFullException{
    PlayerData player;
    List<PlayerController> playerControllers = new ArrayList<>();
    PlayerController currentPlayerController = null;

    player = this.addPlayer(name);

    if (player != null) {
      player.setModel(new Player(name, character, this.gameBoardController.getGameBoard()));
      player.setView(client);
      currentPlayerController = null;
      currentPlayerController = new PlayerController(this.gameBoardController, player.getModel(), player.getView());
      player.setController(currentPlayerController);
      playerControllers.add(currentPlayerController);

      if (this.checkRoomFull()){
        this.gameBoardController.startGame(playerControllers);
      }
    }
    else {
      // Player is already registered to the game
    }
  }

  private static class PlayerData {
    private PlayerViewOnServer view;
    private Player model;
    private PlayerController controller;

    PlayerData() {
      this.view = null;
      this.model = null;
      this.controller = null;
    }

    public PlayerViewOnServer getView() {
      return view;
    }

    public void setView(PlayerViewOnServer view) {
      this.view = view;
    }

    public Player getModel() {
      return model;
    }

    public void setModel(Player model) {
      this.model = model;
    }

    public PlayerController getController() {
      return controller;
    }

    public void setController(PlayerController controller) {
      this.controller = controller;
    }
  }

  public static class RoomFullException extends Exception {
    @Override
    public String toString() {
      return "Room is full, please start the game";
    }
  }
}
