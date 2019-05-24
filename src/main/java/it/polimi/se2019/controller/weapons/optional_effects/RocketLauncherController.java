package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.model.player.Player;

import java.util.List;

public class RocketLauncherController extends OptionalEffectWeaponController {
  public RocketLauncherController() {
    name = "RocketLauncherController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    return null;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){

  }
}