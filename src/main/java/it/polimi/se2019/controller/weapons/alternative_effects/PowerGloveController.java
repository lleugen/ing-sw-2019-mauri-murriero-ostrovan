package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class PowerGloveController extends AlternativeEffectWeaponController {
  public PowerGloveController(GameBoardController g) {
    super(g);
    name = "PowerGloveController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    try{
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
        //make the view choose one target
        //incompatible type error will be solved by change to the viewinterface
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets(possibleTargetNames)));
      }
      else{
        //rocket fist mode, one target one move away and another target two moves away, but in the same direction

        Integer direction = client.chooseDirection(map.getOpenDirections(shooter.getPosition()));
        //get all players one move away in "direction"
        Square targetSquare = shooter.getPosition().getAdjacencies().get(direction).getSquare();
        List<Player> firstPossibleTargets = map.getPlayersOnSquare(targetSquare);
        //get their names
        List<String> firstPossibleTargetsNames = new ArrayList<>();
        for(Player p: firstPossibleTargets){
          firstPossibleTargetsNames.add(p.getName());
        }
        //choose first target
        //incompatible type error will be solved by change to the viewinterface
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets(firstPossibleTargetsNames)));
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
        //incompatible type error will be solved by change to the viewinterface
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets(possibleSecondTargetNames)));
      }
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }

    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    if(firingMode.get(0)){
      for(Player p : targets){
        p.takeMarks(shooter, 2);
        p.takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          p.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(p);
      }
    }
    else{
      for(Player p : targets){
        p.takeDamage(shooter, 2);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          p.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(p);
      }
    }
  }
}