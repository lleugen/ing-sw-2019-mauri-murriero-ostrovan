package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ShockwaveController extends AlternativeEffectWeaponController {
  public ShockwaveController(GameBoardController g) {
    name = "ShockwaveController";
    gameBoardController = g;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = new ArrayList<>();
    if(firingMode.get(0)){
      //choose target squares
      List<Square> targetSquares = new ArrayList<>();
      Integer chosenDirection = null;
      for(int i = 0; i<2; i++){
        chosenDirection = identifyClient(shooter).chooseDirection(map.getOpenDirections(shooter.getPosition()));
        if(!targetSquares.contains(shooter.getPosition().getAdjacencies().get(chosenDirection).getSquare())){
          targetSquares.add(shooter.getPosition().getAdjacencies().get(chosenDirection).getSquare());
        }
        chosenDirection = null;
      }
      //choose one target player for each target square
      for(int i = 0; i<targetSquares.size(); i++){
        //choose one player from all those on the square and add it to the target list
        targets.add(gameBoardController.identifyPlayer
                (identifyClient(shooter).chooseTargets
                        (gameBoardController.getPlayerNames
                                (map.getPlayersOnSquare(targetSquares.get(i))))));
      }
    }
    else{
      targets.addAll(map.getOneMoveAway(shooter.getPosition()));
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    //the effect on the targets is the same regardless of the firing mode, so there is no distinction
    for(Player p : targets){
      p.takeDamage(shooter, 1);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        p.takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(p);
    }
  }
}