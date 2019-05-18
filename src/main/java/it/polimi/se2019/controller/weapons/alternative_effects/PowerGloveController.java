package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PowerGloveController extends AlternativeEffectWeaponController {
  public PowerGloveController() {
    name = "PowerGloveController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = new ArrayList<>();
    if(firingMode.get(0)){
      //basic mode, one target one move away
      List<Player> possibleTargets = new ArrayList<>();
      List<String> possibleTargetNames = new ArrayList<>();
      //get all players one move away
      possibleTargets.addAll(map.getOneMoveAway(shooter.getPosition()));
      //get their names
      for(Player p : possibleTargets){
        possibleTargetNames.add(p.getName());
      }
      //make the client choose one target
      targets.add(gameBoardController.identifyPlayer
              (identifyClient(shooter).chooseTargets(possibleTargetNames).get(0)));
    }
    else{
      //rocket fist mode, one target one move away and another target two moves away, but in the same direction

      Integer direction = identifyClient(shooter).chooseDirection(map.getOpenDirections(shooter.getPosition()));
      //get all players one move away in "direction"
      Square targetSquare = shooter.getPosition().getAdjacencies().get(direction).getSquare();
      List<Player> firstPossibleTargets = map.getPlayersOnSquare(targetSquare);
      //get their names
      List<String> firstPossibleTargetsNames = new ArrayList<>();
      for(Player p: firstPossibleTargets){
        firstPossibleTargetsNames.add(p.getName());
      }
      //choose first target
      targets.add(gameBoardController.identifyPlayer
              (identifyClient(shooter).chooseTargets(firstPossibleTargetsNames).get(0)));
      //get all possible second targets
      List<Player> possibleSecondTargets = new ArrayList<>();
      if(!targetSquare.getAdjacencies().get(direction).isBlocked()){
        possibleSecondTargets = map.getPlayersOnSquare
                (targetSquare.getAdjacencies().get(direction).getSquare());
      }
      //get their names
      List<String> possibleSecondTargetNames = new ArrayList<>();
      for(Player p : possibleSecondTargets){
        possibleSecondTargetNames.add(p.getName());
      }
      //choose second target
      targets.add(gameBoardController.identifyPlayer
              (identifyClient(shooter).chooseTargets(possibleSecondTargetNames).get(0)));
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    if(firingMode.get(0)){
      for(Player p : targets){
        p.takeMarks(shooter, 2);
        p.takeDamage(shooter, 1);
      }
    }
    else{
      for(Player p : targets){
        p.takeDamage(shooter, 2);
      }
    }
  }
}