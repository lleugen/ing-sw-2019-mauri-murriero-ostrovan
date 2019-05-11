package it.polimi.se2019.controller.weapons;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

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
   * game board to which this weaponController belongs
   */
  private GameBoardController gameBoardController;

  public GameBoardController getGameBoardController() {
    return gameBoardController;
  }

  /**
   *
   */
  private String name;
  public String getName(){
    return name;
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
   * Choose which targets to shoot.
   *
   * @param possibleTargets ???
   */
  //!non è necessario, va eliminato
  public List<Player> chooseTargets(List<Player> possibleTargets) {

  }

  /**
   * Apply the weapon's effects on selected targets.
   *
   * @param targets ???
   */
  public void shootTargets(List<Player> targets) {
  }

}