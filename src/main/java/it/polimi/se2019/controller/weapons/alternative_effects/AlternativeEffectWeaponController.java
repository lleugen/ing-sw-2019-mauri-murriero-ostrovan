package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.weapons.WeaponController;

/**
 * This is an abstract marker class, it doesn't have its own methods or
 * attributes and is used to group the weapons in the game based on
 * how they work.
 * Alternative effect weapons have two mutually exclusive firing methods.
 */
public abstract class AlternativeEffectWeaponController extends WeaponController {
  public AlternativeEffectWeaponController() {
  }
}