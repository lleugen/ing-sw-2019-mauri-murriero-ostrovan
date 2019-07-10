package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class ShotgunController extends AlternativeEffectWeaponController {
  public ShotgunController(GameBoardController g) {
    super(g);
    name = "Shotgun";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    //firingMode = selectFiringMode(client);
    if(firingMode.get(0)){
      List<String> names = new ArrayList<>();
      names = GameBoardController.getPlayerNames
              (gameBoardController.getGameBoard().getMap().getPlayersOnSquares(
                      map.getReachableSquares(
                              shooter.getPosition(),
                              0
                      )
              ));
      if(!names.isEmpty()){
        //basic mode, shoot one target on your square
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets
                        (names)));
      }

    }
    else{
      //long barrel mode, shoot one target one move away
      List<Square> adjacentSquares =
              gameBoardController.getGameBoard().getMap().getReachableSquares(shooter.getPosition(), 1);
      List<Player> possibleTargets = new ArrayList<>();
      while(adjacentSquares.iterator().hasNext()){
        possibleTargets.addAll(map.getPlayersOnSquares(
                map.getReachableSquares(
                        adjacentSquares.iterator().next(),
                        0
                )
        ));
      }

      if(!possibleTargets.isEmpty()){
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets
                        (GameBoardController.getPlayerNames(possibleTargets))));
      }

    }

    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    client = identifyClient(shooter);
    //firingMode = selectFiringMode(client);
    if(targets.size()>0){
      if(firingMode.get(0)){

        targets.get(0).takeDamage(shooter, 3);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          targets.get(0).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(targets.get(0));
        //optionally move the target by one square
        List<List<Integer>> possibleSquaresCoordinates = new ArrayList<>();
        List<Square> possibleSquares = new ArrayList<>(map.getReachableSquares(targets.get(0).getPosition(), 1));
        possibleSquares.add(targets.get(0).getPosition());
        while(possibleSquares.iterator().hasNext()){
          possibleSquaresCoordinates.add(map.getSquareCoordinates(possibleSquares.iterator().next()));
        }
        List<Integer> targetSquareCoordinates;
        if(!possibleSquaresCoordinates.isEmpty()){
          targetSquareCoordinates = client.chooseTargetSquare(possibleSquaresCoordinates);
          Square targetSquare = map.getMapSquares()[targetSquareCoordinates.get(0)][targetSquareCoordinates.get(1)];
          targets.get(0).moveToSquare(targetSquare);
        }
      }
      else{
        targets.get(0).takeDamage(shooter, 2);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          targets.get(0).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback grenade, he/she can use it now
        useTagbackGrenade(targets.get(0));
      }
    }

  }
}