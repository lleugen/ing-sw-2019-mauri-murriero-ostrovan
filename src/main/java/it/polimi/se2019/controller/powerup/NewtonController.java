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
    System.out.println("using newton");
    client = identifyClient(user);
    boolean used = false;
    Map map = gameBoardController.getGameBoard().getMap();

    //choose target
    System.out.println("Asking Player");
    Player target = gameBoardController.identifyPlayer(client.chooseTargets
            (GameBoardController.getPlayerNames(gameBoardController.getPlayers())));
    System.out.println("Got Player");
    //choose where to move the target
    List<List<Integer>> possibleSquares = new ArrayList<>();
    Square firstSquare;
    Square secondSquare;

    for(int i = 0; i<4; i++) {
      System.out.println("loop " + i);
      if (target.getPosition().getAdjacencies().get(i) != null && target.getPosition().getAdjacencies().get(i).getSquare() != null){
        System.out.println("ok");
        firstSquare = target.getPosition().getAdjacencies().get(i).getSquare();
        if (firstSquare.getAdjacencies() != null && firstSquare.getAdjacencies().get(i) != null) {
          System.out.println("ook");
          secondSquare = firstSquare.getAdjacencies().get(i).getSquare();
          if (secondSquare != null) {
            System.out.println("got squares");
            possibleSquares.add(map.getSquareCoordinates(firstSquare));
            possibleSquares.add(map.getSquareCoordinates(secondSquare));
            System.out.println("got square coordinates");
          }
        }
      }
    }
    List<Integer> squareCoordinates = client.chooseTargetSquare(possibleSquares);

    //move the target
    if (squareCoordinates.get(0) != null && squareCoordinates.get(1) != null) {
      if (map.getMapSquares()[squareCoordinates.get(0)][squareCoordinates.get(1)] != null) {
        System.out.println("Trying Move");
        target.moveToSquare(map.getMapSquares()[squareCoordinates.get(0)][squareCoordinates.get(1)]);
        System.out.println("Moved");
        used = true;
      }
    }
    return used;
  }

}