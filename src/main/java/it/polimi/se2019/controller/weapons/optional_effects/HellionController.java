package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class HellionController extends OptionalEffectWeaponController {
  public HellionController() {
    name = "HellionController";
    numberOfOptionalEffects = 2;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    //choose one target player at least one move away
    List<Player> possibleTargets = map.getVisiblePlayers(shooter.getPosition());
    for(Player p : possibleTargets){
      if(p.getPosition().equals(shooter.getPosition())){
        possibleTargets.remove(p);
      }
    }
    List<Player> targets = new ArrayList<>();
    targets.add(gameBoardController.identifyPlayer
            (identifyClient(shooter).chooseTargets
                    (gameBoardController.getPlayerNames(possibleTargets))));
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    targets.get(0).takeDamage(shooter, 1);
    List<Player> playersOnSquare = map.getPlayersOnSquare(targets.get(0).getPosition());
    for(Player p : playersOnSquare){
      p.takeMarks(shooter, 1);
    }
    if(firingMode.get(1)){
      for(Player p : playersOnSquare){
        p.takeMarks(shooter, 1);
      }
    }
  }
}