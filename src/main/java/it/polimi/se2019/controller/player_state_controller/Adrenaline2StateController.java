package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class Adrenaline2StateController extends PlayerStateController {
  public Adrenaline2StateController(GameBoardController g, Player p, PlayerViewOnServer c) {
    super(g, p, c);
    availableActions = 2;
  }

  @Override
  public String toString(){
    return "Adrenaline2State";
  }

  /**
   *
   */
  public boolean runAround() throws UserTimeoutException {
    List<Square> threeMovesAway = map.getReachableSquares(player.getPosition(), 3);
    List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
    for(Square q : threeMovesAway){
      threeMovesAwayCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> moveToCoordinates = client.chooseTargetSquare(threeMovesAwayCoordinates);
    player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);

      return true;
  }

  /**
   *
   */
  public boolean grabStuff()  throws UserTimeoutException {
      boolean result = false;
      List<Square> twoMovesAway = map.getReachableSquares(player.getPosition(), 2);
      List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
      for(Square q : twoMovesAway){
        twoMovesAwayCoordinates.add(map.getSquareCoordinates(q));
      }
      List<Integer> moveToCoordinates = client.chooseTargetSquare(twoMovesAwayCoordinates);
      player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);
      Square position = player.getPosition();
      int pickUpIndex = client.chooseItemToGrab();
      if(position instanceof SpawnSquare){
        result = player.getInventory().addWeaponToInventory(position.grab(pickUpIndex));
      }
      else{
          if(position.getItem()!=null){
              player.getInventory().addAmmoTileToInventory(position.grab(0));
              result = true;
          }
          else{
              result = false;
          }

      }

      return result;
  }

  /**
   *
   */
  public boolean shootPeople() throws UserTimeoutException{
    Integer direction = client.chooseDirection(map.getOpenDirections(player.getPosition()));
    if(direction != -1){
      player.move(player.getPosition().getAdjacencies().get(direction));
    }

    shoot();

      return true;
  }

}