package it.polimi.se2019.model.server;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.Remote;
import java.util.*;
import java.util.stream.Collectors;

public class ServerLobby implements Remote {
  private Map<String, PlayerData> playersData;
  private Integer maxPlayers;
  private GameBoardController gameBoardController;

  ServerLobby(Integer playerCount, Integer mapType)
          throws UnknownMapTypeException {
    this.maxPlayers = playerCount;
    GameBoard gameBoard = new GameBoard(mapType);
    gameBoard.createKillScoreBoard(
            5,
            new Integer[]{8, 6, 4, 2, 1, 1}
    );
    this.gameBoardController = new GameBoardController(gameBoard);
    this.playersData = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Add a new player to the game, only if the nickname doesn't exists in the
   * current game
   *
   * @param name nickname of the player
   * @param data PlayerData of the player
   *
   * @return true if the player was added with success, false otherwise
   */
  private synchronized boolean addPlayer(String name, PlayerData data){
    if (this.playersData.containsKey(name)){
      return false;
    }
    else {
      this.playersData.put(name, data);
      return true;
    }
  }

  /**
   * Check if a room is full
   *
   * @return true if the room is full, false otherwise
   */
  synchronized boolean checkRoomFull(){
    return (this.playersData.size() >= this.maxPlayers);
  }

  /**
   * Handle connections of players.
   * A player must call this method to connect to to the server
   *
   * @param client player's view
   * @param name name of the player
   * @param character id of the player's character
   */
  public void connect(PlayerViewOnServer client, String name, String character) {
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


    if (this.addPlayer(name, player) && (this.checkRoomFull())){
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
