package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class SledgeHammerController extends AlternativeEffectWeaponController {
  public SledgeHammerController(GameBoardController g) {
    super(g);
    name = "SledgeHammerController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets
                      (gameBoardController.getPlayerNames
                              (map.getPlayersOnSquare(shooter.getPosition())))));


    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    client = identifyClient(shooter);
    if(firingMode.get(0)){
      targets.get(0).takeDamage(shooter, 2);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        targets.get(0).takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(targets.get(0));
    }
    else{
      targets.get(0).takeDamage(shooter, 3);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        targets.get(0).takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(targets.get(0));
      //optionally move the target 0, 1 or 2 squares in one direction
      List<List<Integer>> possibleSquaresCoordinates = new ArrayList<>();
      List<Square> possibleSquares = new ArrayList<>(map.getReachableSquares(targets.get(0).getPosition(), 1));
      possibleSquares.add(targets.get(0).getPosition());

      while(possibleSquares.iterator().hasNext()){
        possibleSquaresCoordinates.add(map.getSquareCoordinates(possibleSquares.iterator().next()));
      }
      List<Integer> targetSquareCoordinates;
      targetSquareCoordinates = identifyClient(shooter).chooseTargetSquare(possibleSquaresCoordinates);
      Square targetSquare = map.getMapSquares()[targetSquareCoordinates.get(0)][targetSquareCoordinates.get(1)];
      targets.get(0).moveToSquare(targetSquare);
    }
  }
}