package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.weapons.optional_effects.OptionalEffectWeaponController;
import it.polimi.se2019.model.player.Player;

import java.util.List;

public class ElectroscytheController extends OptionalEffectWeaponController {
  public ElectroscytheController() {
    name = "ElectroscytheController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = map.getPlayersOnSquare(shooter.getPosition());
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    if(firingMode.get(0)){
      for(Player p : targets){
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