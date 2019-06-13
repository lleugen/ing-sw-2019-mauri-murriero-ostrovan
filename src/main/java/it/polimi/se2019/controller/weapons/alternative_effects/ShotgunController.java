package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ShotgunController extends AlternativeEffectWeaponController {
  public ShotgunController(GameBoardController g) {
    super(g);
    name = "ShotgunController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = new ArrayList<>();
    if(firingMode.get(0)){
      //basic mode, shoot one target on your square
      targets.add(gameBoardController.identifyPlayer
              (identifyClient(shooter).chooseTargets
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
              (identifyClient(shooter).chooseTargets
                      (gameBoardController.getPlayerNames(possibleTargets))));
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
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
      List<Integer> targetSquareCoordinates = identifyClient(shooter).chooseTargetSquare(possibleSquaresCoordinates);
      Square targetSquare = map.getMapSquares()[targetSquareCoordinates.get(0)][targetSquareCoordinates.get(1)];
      targets.get(0).moveToSquare(targetSquare);
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