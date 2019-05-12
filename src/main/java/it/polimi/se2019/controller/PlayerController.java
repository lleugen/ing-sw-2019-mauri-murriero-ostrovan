package it.polimi.se2019.controller;

import it.polimi.se2019.controller.player_state_controller.*;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.List;

/**
 * This class controls player actions, it contains the player's current state
 * which is determined by the amount of damage received or the state of
 * the game (regular turns or final frenzy round).
 * The class contains the implementation of all basic possible actions
 * (move, grab, shoot, reload, respawn) which are combined into different
 * complex actions that a player can make during a turn
 */
public class PlayerController {

  private GameBoardController gameBoardController;
  private PlayerView client;
  private Player player;
  private PlayerStateController state;
  private List<PlayerStateController> stateControllerList;

  /**
   *
   */
  public PlayerController(GameBoardController g, Player p, PlayerView c) {
    gameBoardController = g;
    client = c;
    player = p;
    stateControllerList.add(new NormalStateController());
    stateControllerList.add(new Adrenaline1StateController());
    stateControllerList.add(new Adrenaline2StateController());
    stateControllerList.add(new FirstFreneticStateController());
    stateControllerList.add(new SecondFreneticStateController());
    state = stateControllerList.get(0);
  }

  /*
   * Identify a player given his name
   //non serve più questo metodo
  public Player identifyPlayer(String playerName){
    Player player = null;
    for(Player p : gameBoardController.getPlayers()){
      if(p.getName().equals(playerName)){
        player = p;
      }
    }
    if(player != null){
      return player;
    }
    else{
      throw new NonExistingPlayerException();
    }
  }*/


}
