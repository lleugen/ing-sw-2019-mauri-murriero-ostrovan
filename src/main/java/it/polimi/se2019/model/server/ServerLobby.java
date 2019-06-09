package it.polimi.se2019.model.server;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.Remote;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerLobby implements Remote {
  /**
   * Timeout in seconds between the creation of the lobby and the start of the
   * games, to allow players to close connection to the server and connect to
   * the lobby.
   * If a player doesn't connect to the lobby before the expiration of this
   * timeout, the game will start without him.
   *
   * Default: 30
   */
  private static final int CONNECTION_TIMEOUT = 30;

  /**
   * Maximum number of player allowed to connect to the lobby at the same time
   *
   * Default 5
   */
  public static final int MAX_PLAYERS = 5;
//
//  private int remainingTime;
//  private Boolean active;
//
//  private Map<String, PlayerData> playersData;
//  private Integer maxPlayers;
//  private GameBoardController gameBoardController;

  private Map<String, Player> players;
  private Integer mapType;
  private Integer skulls;

  ServerLobby(List<String> allowedPlayers, Integer mapType, Integer skulls){
    this.players = Collections.synchronizedMap(new HashMap<>());

    allowedPlayers.forEach(
            item -> this.players.put(item, null)
    );

    this.mapType = mapType;
    this.skulls = skulls;
  }
//
//  /**
//   * Check if the lobby is still open ({@see PRECLOSE_TIMEOUT})
//   *
//   * @return true if the lobby is opened, false otherwise
//   */
//  public synchronized Boolean isOpen(){
//    return (this.remainingTime > PRECLOSE_TIMEOUT);
//  }
//
//  /**
//   * Check if the lobby is still active, or can be garbage collected
//   *
//   * @return true if the lobby is active, false otherwise
//   */
//  public synchronized Boolean isActive(){
//    return this.active;
//  }
//
//  /**
//   * Add a new player to the game, only if the nickname doesn't exists in the
//   * current game
//   *
//   * @param name nickname of the player
//   *
//   * @return a reference to the created player, to be initialized
//   *         null if the player already exists
//   *
//   * @throws RoomFullException if the player can't be added cause the room
//   *                           is full
//   */
//  private synchronized PlayerData addPlayer(String name)
//          throws RoomFullException {
//    if (this.checkRoomFull() && this.checkPlayerNotExists(name)){
//      if (this.checkPlayerNotExists(name)){
//        PlayerData toReturn = new PlayerData();
//        this.playersData.put(name, toReturn);
//        return toReturn;
//      }
//      else {
//        return null;
//      }
//    }
//    else {
//      throw new RoomFullException();
//    }
//  }
//
//  /**
//   * Check if a room is full
//   *
//   * @return true if the room is full, false otherwise
//   */
//  private boolean checkRoomFull(){
//    return (this.playersData.size() >= this.maxPlayers);
//  }
//
//  /**
//   * Check if a player is already registered to the lobby
//   *
//   * @param name name of the player to check
//   *
//   * @return true if the player is already registered, false otherwise
//   */
//  private boolean checkPlayerNotExists(String name){
//    return this.playersData.containsKey(name);
//  }
//
//  /**
//   * Handle connections of players.
//   * A player must call this method to connect to to the server
//   *
//   * @param client player's view
//   * @param name name of the player
//   * @param character id of the player's character
//   */
//  public void connect(PlayerView client, String name, String character) {
//    PlayerData player;
//
//    try {
//      player = this.addPlayer(name);
//
//      if (player != null) {
//        player.setModel(new Player(name, character, this.gameBoardController.getGameBoard()));
//        player.setView(client);
//        player.setController(
//                new PlayerController(
//                        this.gameBoardController,
//                        player.getModel(),
//                        player.getView()
//                )
//        );
//
//        if (this.checkRoomFull()){
//
//          this.gameBoardController.startGame(
//                  this.playersData.values().stream()
//                  .map(
//                          PlayerData::getController
//                  )
//                  .collect(Collectors.toList())
//          );
//        }
//      }
//      else {
//        // Player is already registered to the game
//      }
//    }
//    catch (RoomFullException e){
//      // Nothing to do, the room is full
//      // TODO (if necesary)
//    }
//  }
//
//  private static class PlayerData {
//    private PlayerView view;
//    private Player model;
//    private PlayerController controller;
//
//    PlayerData() {
//      this.view = null;
//      this.model = null;
//      this.controller = null;
//    }
//
//    public PlayerView getView() {
//      return view;
//    }
//
//    public void setView(PlayerView view) {
//      this.view = view;
//    }
//
//    public Player getModel() {
//      return model;
//    }
//
//    public void setModel(Player model) {
//      this.model = model;
//    }
//
//    public PlayerController getController() {
//      return controller;
//    }
//
//    public void setController(PlayerController controller) {
//      this.controller = controller;
//    }
//  }
//
//  public static class RoomFullException extends Exception {
//    @Override
//    public String toString() {
//      return "Room is full, please start the game";
//    }
//  }
}
