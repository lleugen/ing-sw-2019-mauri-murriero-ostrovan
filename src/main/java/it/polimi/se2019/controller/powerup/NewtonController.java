package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * The newton powerup moves the targeted enemy player one or two squares
 * in any direction.
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class NewtonController extends PowerUpController {
  public NewtonController(GameBoardController g) {
    super(g);
    name = "Newton";
  }

  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) throws UserTimeoutException {
    client = identifyClient(user);
    boolean used = false;
    Map map = gameBoardController.getGameBoard().getMap();

    //choose target
    Player target = gameBoardController.identifyPlayer(client.chooseTargets
            (GameBoardController.getPlayerNames(gameBoardController.getPlayers())));

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