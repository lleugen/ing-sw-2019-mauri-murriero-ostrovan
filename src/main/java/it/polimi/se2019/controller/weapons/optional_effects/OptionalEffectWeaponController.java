package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an abstract marker class, it doesn't have its own methods or
 * attributes and is used to group the weapons in the game based on how they
 * work.
 * Optional effect weapons have one primary effect and one or more optional
 * effects that can be applied in no particular order.
 */
public abstract class OptionalEffectWeaponController extends WeaponController {
  public OptionalEffectWeaponController() {
  }

  protected Integer numberOfOptionalEffects;

  public List<Boolean> selectFiringMode(PlayerView client){
    List<Boolean> optionalEffectFlags = new ArrayList<>();
    for(int i = 0; i<numberOfOptionalEffects; i++){
      optionalEffectFlags.add
              (client.chooseFiringMode("select effect" + i));
    }
    return optionalEffectFlags;
  }
}