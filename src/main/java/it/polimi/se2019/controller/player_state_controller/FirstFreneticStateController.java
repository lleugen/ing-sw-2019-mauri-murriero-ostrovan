package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class FirstFreneticStateController extends PlayerStateController {
  public FirstFreneticStateController(GameBoardController g, Player p, PlayerViewOnServer c) {
    super(g,p,c);
    availableActions = 2;
  }

  @Override
  public String toString(){
    return "FirstFreneticState";
  }

  /**
   *
   */
  public void runAround() throws UserTimeoutException {
    List<Square> fourMovesAway = map.getReachableSquares(player.getPosition(), 3);
    for(Square q : fourMovesAway){
      for(Direction d : q.getAdjacencies()){
        if((!d.isBlocked()) && (!fourMovesAway.contains(q))){
          fourMovesAway.add(q);
        }
      }
    }
    List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
    for(Square q : fourMovesAway){
      threeMovesAwayCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> moveToCoordinates = client.chooseTargetSquare(threeMovesAwayCoordinates);
    player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);

  }

  /**
   *
   */
  public void grabStuff() throws UserTimeoutException  {
    List<Square> twoMovesAway = map.getReachableSquares(player.getPosition(), 2);
    List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
    for(Square q : twoMovesAway){
      twoMovesAwayCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> moveToCoordinates;
    Square position;
    int pickUpIndex;
      moveToCoordinates = client.chooseTargetSquare(twoMovesAwayCoordinates);
      player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);
      position = player.getPosition();
      pickUpIndex = client.chooseItemToGrab();
      if(position instanceof SpawnSquare){
        player.getInventory().addWeaponToInventory(position.grab(pickUpIndex));
      }
      else{
        player.getInventory().addAmmoTileToInventory(position.grab(0));
      }
  }

  /**
   *
   */
  public void shootPeople() throws UserTimeoutException {
    Integer direction = client.chooseDirection(
            map.getOpenDirections(
                    player.getPosition()
            )
    );
    if(direction != -1){
      player.move(player.getPosition().getAdjacencies().get(direction));
    }

    player.reloadWeapon(client);

    shoot();
  }

}