package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class LockRifleController extends OptionalEffectWeaponController {
  public LockRifleController() {
    name = "LockRifleController";
    numberOfOptionalEffects = 2;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = new ArrayList<>();
    targets.add(chooseOneVisiblePlayer(shooter));
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    targets.get(0).takeMarks(shooter, 1);
    targets.get(0).takeDamage(shooter, 2);
    if(firingMode.get(1)){
      chooseOneVisiblePlayer(shooter).takeMarks(shooter, 1);
    }
  }
}