package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CyberBladeController extends OptionalEffectWeaponController {
  public CyberBladeController() {
    name = "CyberBladeController";
    numberOfOptionalEffects = 3;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    //choose one target from all the players on the same square
    List<Player> targets = new ArrayList<>();
    List<Player> playersOnSquare = map.getPlayersOnSquare(shooter.getPosition());
    targets.add(gameBoardController.identifyPlayer
            (identifyClient(shooter).chooseTargets
                    (gameBoardController.getPlayerNames(playersOnSquare))));
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    List<String> availableEffects = new ArrayList<>();
    if(firingMode.get(0)){
      availableEffects.add("basic effect");
    }
    if(firingMode.get(1)){
      availableEffects.add("shadowstep");
    }
    if(firingMode.get(2)){
      availableEffects.add("slice and dice");
    }
    Integer chosenEffect;
    while(firingMode.contains(true)){
      //choose which effect to apply
      chosenEffect = identifyClient(shooter).chooseIndex("CyberBlade", availableEffects);
      firingMode.set(chosenEffect, false);
      if(chosenEffect == 0 || chosenEffect == 2){
        targets = findTargets(shooter);
        for(Player p : targets){
          p.takeDamage(shooter, 2);
        }
      }
      else if(chosenEffect == 1){
        shooter.move(shooter.getPosition().getAdjacencies().get
                (identifyClient(shooter).chooseDirection
                        (map.getOpenDirections(shooter.getPosition()))));
      }
    }
  }
}