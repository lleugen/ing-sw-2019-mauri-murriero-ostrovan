package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * The teleporter can be used by a player on his/her turn to move to any square
 * on the map, regardless of distance or obstacles in the way.
 */
public class TeleporterController extends PowerUpController {
  public TeleporterController() {
  }

  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) {
    client = identifyClient(user);
    Boolean used = false;
    //choose where to move
    List<List<Integer>> squares = new ArrayList<>();
    List<Integer> currentSquare = new ArrayList<>();
    for(int i = 0; i<2; i++){
      for(int k = 0; k<3; k++){
        currentSquare.clear();
        currentSquare.add(0, i);
        currentSquare.add(1, k);
        squares.add(currentSquare);
      }
    }
    List<Integer> targetSquare;
    try{
      targetSquare = client.chooseTargetSquare(squares);
      //move to the chosen square
      user.moveToSquare(gameBoardController.getGameBoard().getMap().getMapSquares()[targetSquare.get(0)][targetSquare.get(1)]);
      used = true;
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }
    return used;
  }

}