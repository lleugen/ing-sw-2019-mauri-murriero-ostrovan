package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class VortexCannonController extends OptionalEffectWeaponController {
  public VortexCannonController(GameBoardController g) {
    super(g);
    name = "VortexCannonController";
    numberOfOptionalEffects = 2;
  }

  Square vortex = null;
  List<Player> oneMoveAwayFromvortex = new ArrayList<>();
  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    List<Integer> vortexCoordinates = new ArrayList<>();
    List<Square> visibleSquares = map.getVisibleSquares(shooter.getPosition());
    List<List<Integer>> visibleSquareCoordinates = new ArrayList<>();
    for(Square q : visibleSquares){
      visibleSquareCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Player> targets = new ArrayList<>();
    try{
      vortexCoordinates = client.chooseTargetSquare(visibleSquareCoordinates);
      vortex = map.getMapSquares()[vortexCoordinates.get(0)][vortexCoordinates.get(1)];
      oneMoveAwayFromvortex = map.getOneMoveAway(vortex);

      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets
                      (gameBoardController.getPlayerNames
                              (map.getOneMoveAway(vortex)))));
      oneMoveAwayFromvortex.remove(targets.get(0));
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    client = identifyClient(shooter);
    targets.get(0).moveToSquare(vortex);
    targets.get(0).takeDamage(shooter, 2);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(0).takeDamage(shooter, 1);
    }
    //if the damaged target has a tagback gredade, he/she can use it now
    useTagbackGrenade(targets.get(0));
    try{
      if(firingMode.get(1)){
        Player target1 = gameBoardController.identifyPlayer
                (client.chooseTargets
                        (gameBoardController.getPlayerNames(oneMoveAwayFromvortex)));
        target1.moveToSquare(vortex);
        target1.takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          target1.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(target1);
        oneMoveAwayFromvortex.remove(target1);

        Player target2 = gameBoardController.identifyPlayer
                (client.chooseTargets
                        (gameBoardController.getPlayerNames(oneMoveAwayFromvortex)));
        target2.moveToSquare(vortex);
        target2.takeDamage(shooter, 2);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          target2.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(target2);
      }
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }

  }
}