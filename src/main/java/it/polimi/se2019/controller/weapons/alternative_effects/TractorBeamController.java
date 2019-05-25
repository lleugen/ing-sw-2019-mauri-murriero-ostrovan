package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.weapons.optional_effects.OptionalEffectWeaponController;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TractorBeamController extends OptionalEffectWeaponController {
  public TractorBeamController() {
    name = "TractorBeamController";
  }

  @Override
  public List<Player> findTargets(Player shooter){
    return null;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){

  }
}