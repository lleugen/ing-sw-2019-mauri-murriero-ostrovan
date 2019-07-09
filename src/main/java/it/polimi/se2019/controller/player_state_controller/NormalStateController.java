package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.RemoteException;
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
    boolean result = false;
    System.out.println("grabbing something");
    List<Integer> openDirections = new ArrayList<>();
    openDirections = map.getOpenDirections(player.getPosition());
    Integer direction = client.chooseDirection(openDirections);
    if(
            (openDirections.contains(direction))&&
            player.getPosition() != null &&
            player.getPosition().getAdjacencies() != null &&
            player.getPosition().getAdjacencies().get(direction) != null
    ) {
        System.out.println("player is moving while grabbing");
      player.move(player.getPosition().getAdjacencies().get(direction));
    }

    Square position = player.getPosition();
    if(position != null){
      int pickUpIndex = client.chooseItemToGrab();
//    List<Square> spawnSquares = new ArrayList<>();
//    spawnSquares.add(map.getRedSpawnPoint());
//    spawnSquares.add(map.getBlueSpawnPoint());
//    spawnSquares.add(map.getYellowSpawnPoint());
      if(position!=null){
        if(position instanceof SpawnSquare){
            boolean addWepResult = false;
            addWepResult = player.getInventory().addWeaponToInventory(position.grab(pickUpIndex));
            if(addWepResult){
                result = true;
            }
            //if(player.getInventory().getWeapons().size()>0){
            //System.out.println("player grabbed " + player.getInventory().getWeapons().get(player.getInventory().getWeapons().size()-1).toString());
            //}
            //else{
            //System.out.println("something happened when grabbing a weapon");
            //}
        }
        else{
          player.getInventory().addAmmoTileToInventory(position.grab(0));
          System.out.println("grabbed an ammo tile");
          result = true;
        }
      }
      System.out.println("grabbed something");

      //System.out.printf(printInventory());

    }
    else{
      System.out.println("something wrong with player position in grab normal state");
    }
    try{
        if(result){
            client.sendGenericMessage("grab succesful");
        }
        else{
            client.sendGenericMessage("failed to grab");
        }
    }
    catch(RemoteException f){
        //whatever
    }

    return result;
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
