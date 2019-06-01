package it.polimi.se2019.controller;

import it.polimi.se2019.controller.player_state_controller.*;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
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

//  private GameBoardController gameBoardController;
  private PlayerView client;
  private Player player;
  private PlayerStateController state;
  private List<PlayerStateController> stateControllerList;
  private GameBoardController gameBoardController;

  /**
   *
   */
  public PlayerController(GameBoardController g, Player p, PlayerView c) {
    gameBoardController = g;
    client = c;
    player = p;
    stateControllerList.add(new NormalStateController(g, p, c));
    stateControllerList.add(new Adrenaline1StateController(g, p, c));
    stateControllerList.add(new Adrenaline2StateController(g, p, c));
    stateControllerList.add(new FirstFreneticStateController(g, p, c));
    stateControllerList.add(new SecondFreneticStateController(g, p, c));
    state = stateControllerList.get(0);
  }

  public PlayerStateController getState(){
    return state;
  }

  /**
   * Take turn
   */
  public void playTurn(Integer availableActions){
    //use power up
    for(int i = 0; i<availableActions; i++){
      String chosenAction = client.chooseAction(state.toString());
      if(chosenAction.equals("run")){
        state.runAround();
        /*
        Integer direction;
        for(int j = 0; j<2; j++){
          direction = client.chooseDirection();
          player.move(player.getPosition().getAdjacencies().get(direction));
        }
        */
      }
      else if(chosenAction.equals("grab")){
        state.grabStuff();
      }
      else if(chosenAction.equals("shoot")){
        state.shootPeople();
      }
      else if(chosenAction.equals("powerUp")){
        i--;
        state.usePowerUp();
      }
    }
  }

  public PlayerView getClient(){
    return client;
  }
}
