package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class SecondFreneticStateController extends PlayerStateController {
  public SecondFreneticStateController(GameBoardController g, Player p, PlayerViewOnServer c) {
    super(g, p, c);
    availableActions = 1;
  }

  @Override
  public String toString(){
    return "SecondFreneticState";
  }

  /**
   *
   */
  @Override
  public void grabStuff() throws UserTimeoutException {
    List<Square> threeMovesAway = map.getReachableSquares(player.getPosition(), 3);
    List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
    for(Square q : threeMovesAway){
      threeMovesAwayCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> moveToCoordinates;
      moveToCoordinates = client.chooseTargetSquare(threeMovesAwayCoordinates);
      player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);
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
   *
   */
  @Override
  public void runAround(){
    // Not implemented in game
  }

  /**
   *
   */
  @Override
  public void shootPeople() throws UserTimeoutException {
    List<Square> twoMovesAway = map.getReachableSquares(player.getPosition(), 2);
    List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
    for(Square q : twoMovesAway){
      twoMovesAwayCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> moveToCoordinates;
    moveToCoordinates = client.chooseTargetSquare(twoMovesAwayCoordinates);
    player.moveToSquare(
            map.getMapSquares()
                    [moveToCoordinates.get(0)]
                    [moveToCoordinates.get(1)]
    );

    player.reloadWeapon(client);

    shoot();
  }
}