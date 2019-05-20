package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

public class NormalStateController extends PlayerStateController {
  public NormalStateController(GameBoardController g, Player p, PlayerView c) {
    super(g, p, c);
    availableActions = 2;
  }

  @Override
  public String toString(){
    return "NormalState";
  }

  /**
   * Move three squares
   */
  @Override
  public void runAround() {
    boolean hasMoved;
    for(int i = 0; i<2; i++){
      hasMoved = false;
      while(!hasMoved){
        try{
          move();
          hasMoved = true;
        }
        catch(DirectionBlockedException e){
          System.out.println("blocked direction");
        }
      }
    }
  }

  /**
   * Grab what is on your square
   */
  @Override
  public void grabStuff() {
    Square position = player.getPosition();
    int pickUpIndex = client.chooseItemToGrab();
    if(position instanceof SpawnSquare){
      player.getInventory().addWeaponToInventory(position.grab(pickUpIndex));
    }
    else{
      player.getInventory().addAmmoTileToInventory(position.grab(0));
    }
  }

  /**
   * Use your action to fire a weapon
   */
  @Override
  public void shootPeople() {
    shoot();
  }
}
