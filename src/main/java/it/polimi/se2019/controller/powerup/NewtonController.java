package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The newton powerup moves the targeted enemy player one or two squares
 * in any direction.
 */
public class NewtonController extends PowerUpController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public NewtonController() {
    name = "NewtonController";
  }

  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) throws UserTimeoutException {
    client = identifyClient(user);
    Boolean used = false;
    Map map = gameBoardController.getGameBoard().getMap();

    //choose target
    Player target = gameBoardController.identifyPlayer(client.chooseTargets
            (gameBoardController.getPlayerNames(gameBoardController.getPlayers())));

    //choose where to move the target
    List<List<Integer>> possibleSquares = new ArrayList<>();
    Square firstSquare;
    Square secondSquare;
    for(int i = 0; i<3; i++){
      firstSquare = target.getPosition().getAdjacencies().get(i).getSquare();
      secondSquare = firstSquare.getAdjacencies().get(i).getSquare();
      possibleSquares.add(map.getSquareCoordinates(firstSquare));
      possibleSquares.add(map.getSquareCoordinates(secondSquare));
    }
    List<Integer> squareCoordinates = client.chooseTargetSquare(possibleSquares);

    //move the target
    target.moveToSquare(map.getMapSquares()[squareCoordinates.get(0)][squareCoordinates.get(1)]);
    used = true;

    return used;
  }

}