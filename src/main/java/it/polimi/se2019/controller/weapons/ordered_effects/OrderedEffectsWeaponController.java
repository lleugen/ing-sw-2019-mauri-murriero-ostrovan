package it.polimi.se2019.controller.weapons.ordered_effects;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an abstract marker class, it doesn't have its own methods or
 * attributes and is used to group the weapons in the game based on how they
 * work.
 * Ordered effects weapons have a primary effect and one or more effects that
 * can be applied only if they satisfy particular conditions.
 */
public abstract class OrderedEffectsWeaponController extends WeaponController {
  public OrderedEffectsWeaponController() {

  }
  protected Integer numberOfOptionalEffects;
  public List<Boolean> selectFiringMode(PlayerViewOnServer client){
    List<Boolean> firingModeFlags = new ArrayList<>();
    List<String> effects = new ArrayList<>();
    for(int i = 0; i<numberOfOptionalEffects; i++){
      effects.add("effect"+i);
    }
    Integer chosenEffect = client.chooseIndex("Thor", effects);
    for(int k = 0; k<numberOfOptionalEffects; k++){
      if(k<=chosenEffect){
        firingModeFlags.add(k, true);
      }
      else{
        firingModeFlags.add(k, false);
      }
    }
    return firingModeFlags;
  }
}