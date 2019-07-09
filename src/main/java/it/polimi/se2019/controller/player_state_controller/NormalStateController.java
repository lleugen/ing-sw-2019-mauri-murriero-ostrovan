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
public class NormalStateController extends PlayerStateController {
  public NormalStateController(GameBoardController g, Player p, PlayerViewOnServer c) {
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
  public boolean runAround() throws UserTimeoutException {
    List<Square> threeMovesAway = map.getReachableSquares(player.getPosition(), 3);
    System.out.println("the player is running around");
    List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
    for(Square q : threeMovesAway){
      threeMovesAwayCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> moveToCoordinates = client.chooseTargetSquare(threeMovesAwayCoordinates);
    player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);
    return true;
  }

  /**
   * Grab what is on your square, optionally move one square
   */
  @Override
  public boolean grabStuff() throws UserTimeoutException {
    Integer direction = client.chooseDirection(map.getOpenDirections(player.getPosition()));
    if(
            direction != -1 &&
            player.getPosition() != null &&
            player.getPosition().getAdjacencies() != null &&
            player.getPosition().getAdjacencies().get(direction) != null
    ) {
      player.move(player.getPosition().getAdjacencies().get(direction));
    }

    Square position = player.getPosition();
    int pickUpIndex = client.chooseItemToGrab();
    if(position instanceof SpawnSquare){
      player.getInventory().addWeaponToInventory(position.grab(pickUpIndex));
    }
    else{
      if (position != null) {
        player.getInventory().addAmmoTileToInventory(position.grab(0));
      }
    }

    return true;
  }

  /**
   * Use your action to fire a weapon
   */
  @Override
  public boolean shootPeople() throws UserTimeoutException {
    shoot();

    return true;
  }
}
