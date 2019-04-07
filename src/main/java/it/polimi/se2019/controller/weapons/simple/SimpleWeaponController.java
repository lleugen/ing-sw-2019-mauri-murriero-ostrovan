package it.polimi.se2019.controller.weapons.simple;

import it.polimi.se2019.controller.weapons.WeaponController;

/**
 * This is an abstract marker class, it doesn't have its own methods
 * or attributes and is used to group the weapons in the game based on how they
 * work.
 * Simple weapons only have one possible effect.
 */
public abstract class SimpleWeaponController extends WeaponController {
  public SimpleWeaponController() {
  }
}