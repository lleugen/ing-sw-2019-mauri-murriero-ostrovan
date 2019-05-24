package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.model.player.Player;

import java.util.List;

public class HellionController extends OptionalEffectWeaponController {
  public HellionController() {
    name = "HellionController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    return null;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){

  }
}