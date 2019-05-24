package it.polimi.se2019.controller.weapons.ordered_effects;

import it.polimi.se2019.model.player.Player;

import java.util.List;

public class ThorController extends OrderedEffectsWeaponController {
  public ThorController() {
    name = "ThorController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    return null;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){

  }
}