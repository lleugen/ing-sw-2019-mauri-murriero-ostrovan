package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShotgunController extends AlternativeEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ShotgunController";


  public ShotgunController(GameBoardController g) {
    super(g);
    name = "ShotgunController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    try{
      if(firingMode.get(0)){
        //basic mode, shoot one target on your square
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets
                        (gameBoardController.getPlayerNames
                                (gameBoardController.getGameBoard().getMap().getPlayersOnSquare
                                        (shooter.getPosition())))));
      }
      else{
        //long barrel mode, shoot one target one move away
        List<Square> adjacentSquares =
                gameBoardController.getGameBoard().getMap().getAdjacentSquares(shooter.getPosition());
        List<Player> possibleTargets = new ArrayList<>();
        while(adjacentSquares.iterator().hasNext()){
          possibleTargets.addAll(map.getPlayersOnSquare(adjacentSquares.iterator().next()));
        }
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets
                        (gameBoardController.getPlayerNames(possibleTargets))));
      }
    }
    catch(UserTimeoutException e){
      
    Logger.getLogger(LOG_NAMESPACE).log(
    Level.WARNING,
    "Client Disconnected",
    e
);
    }

    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    client = identifyClient(shooter);
    if(firingMode.get(0)){
      targets.get(0).takeDamage(shooter, 3);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        targets.get(0).takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(targets.get(0));
      //optionally move the target by one square
      List<Square> possibleSquares = new ArrayList<>();
      List<List<Integer>> possibleSquaresCoordinates = new ArrayList<>();
      possibleSquares.addAll(map.getAdjacentSquares(targets.get(0).getPosition()));
      possibleSquares.add(targets.get(0).getPosition());
      while(possibleSquares.iterator().hasNext()){
        possibleSquaresCoordinates.add(map.getSquareCoordinates(possibleSquares.iterator().next()));
      }
      List<Integer> targetSquareCoordinates;
      try{
        targetSquareCoordinates = client.chooseTargetSquare(possibleSquaresCoordinates);
        Square targetSquare = map.getMapSquares()[targetSquareCoordinates.get(0)][targetSquareCoordinates.get(1)];
        targets.get(0).moveToSquare(targetSquare);
      }
      catch(UserTimeoutException e){
            Logger.getLogger(LOG_NAMESPACE).log(
                    Level.WARNING,
                    "Client Disconnected",
                    e
            );
      }

    }
    else{
      targets.get(0).takeDamage(shooter, 2);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        targets.get(0).takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(targets.get(0));
    }
  }
}