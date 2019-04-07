package it.polimi.se2019.controller.weapons.ordered_effects;

import it.polimi.se2019.controller.weapons.WeaponController;

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
}