package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The teleporter can be used by a player on his/her turn to move to any square
 * on the map, regardless of distance or obstacles in the way.
 */
public class TeleporterController extends PowerUpController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public TeleporterController() {
  }

  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) throws UserTimeoutException {
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
    targetSquare = client.chooseTargetSquare(squares);
    //move to the chosen square
    user.moveToSquare(gameBoardController.getGameBoard().getMap().getMapSquares()[targetSquare.get(0)][targetSquare.get(1)]);
    used = true;

    return used;
  }

}