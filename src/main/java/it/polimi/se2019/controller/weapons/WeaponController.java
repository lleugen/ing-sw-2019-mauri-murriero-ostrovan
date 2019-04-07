package it.polimi.se2019.controller.weapons;

import it.polimi.se2019.model.player.Player;

import java.util.List;

/**
 * WeaponController is an abstract class with a template "fire" method
 * and non implemented findTargets and shootTargets
 */
public abstract class WeaponController {
  /**
   * The list of all possible targets
   */
  private List<Player> possibleTargets;

  /**
   * Targets chosen by the player from the list of all possible targets.
   */
  private List<Player> targets;

  /**
   * Weapons are created at the start of the game.
   */
  public WeaponController() {
  }

  /**
   * Create a list of valid targets, choose targets and shoot them.
   */
  public void fire() {
    possibleTargets = this.findTargets();
    targets = this.chooseTargets(possibleTargets);
    this.shootTargets(targets);
  }

  /**
   * Make a list of all possible targets.
   */
  public List<Player> findTargets() {
  }

  /**
   * Choose which targets to shoot.
   */
  public List<Player> chooseTargets(List<Player> possibleTargets) {

  }

  /**
   * Apply the weapon's effects on selected targets.
   */
  public void shootTargets(List<Player> targets) {
  }

}