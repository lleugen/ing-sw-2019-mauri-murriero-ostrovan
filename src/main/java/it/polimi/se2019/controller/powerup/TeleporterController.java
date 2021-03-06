package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * The teleporter can be used by a player on his/her turn to move to any square
 * on the map, regardless of distance or obstacles in the way.
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class TeleporterController extends PowerUpController {
  PlayerViewOnServer client;

  public TeleporterController(GameBoardController g){
    super(g);
    name="Teleporter";
  }


  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) throws UserTimeoutException {
    client = identifyClient(user);
    boolean used = false;
    //choose where to move
    List<List<Integer>> squareCoordinates = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int k = 0; k<4; k++){
        squareCoordinates.add(gameBoardController.getGameBoard().getMap().getSquareCoordinates
                (gameBoardController.getGameBoard().getMap().getMapSquares()[i][k]));
      }
    }
    List<Integer> targetSquare;
    targetSquare = client.chooseTargetSquare(squareCoordinates);
    //move to the chosen square
    user.moveToSquare(gameBoardController.getGameBoard().getMap().getMapSquares()[targetSquare.get(0)][targetSquare.get(1)]);
    used = true;

    return used;
  }

}