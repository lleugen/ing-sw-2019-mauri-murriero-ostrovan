package it.polimi.se2019.model.server;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerLobby implements Remote {
  private List<PlayerView> playerViewList;
  private List<Player> playerList;
  private List<PlayerController> playerControllerList;
  private Integer maxPlayers;
  private Integer registeredPlayers;
  private Integer mapType;

  ServerLobby(Integer playerCount, Integer mapType){
    this.maxPlayers = playerCount;
    this.registeredPlayers = 0;
    this.mapType = mapType;

    this.playerViewList = Collections.synchronizedList(new ArrayList<>());
    this.playerList = Collections.synchronizedList(new ArrayList<>());
    this.playerControllerList = Collections.synchronizedList(new ArrayList<>());
  }

  private synchronized boolean reservePlace() {
    if (this.registeredPlayers < this.maxPlayers){
      this.registeredPlayers++;
      return true;
    }
    else {
      return false;
    }
  }

  private synchronized boolean checkLobbyFull(){
    return this.registeredPlayers >= this.maxPlayers;
  }

  public void connect(PlayerView client, String name, String character){
    if (this.reservePlace()) {
      Player tmp = new Player(name, character);
      this.playerViewList.add(client);
      this.playerList.add(tmp);
      this.playerControllerList.add(new PlayerController(tmp, client));
      if (this.checkLobbyFull()){
        new GameBoardController(
                this.playerList,
                this.playerViewList,
                new GameBoard(
                        this.mapType,
                        this.playerList
                )
        ).startGame();
      }
    }
  }
}
