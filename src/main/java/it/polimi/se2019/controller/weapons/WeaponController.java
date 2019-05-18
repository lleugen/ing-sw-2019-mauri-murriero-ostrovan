package it.polimi.se2019.controller.weapons;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.GUIPlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponController is an abstract class with a template "fire" method
 * and non implemented findTargets and shootTargets
 */
public abstract class WeaponController {

  private GameBoardController gameBoardController;
  private String name;

  public String getName(){
    return name;
  }

  public GameBoardController getGameBoardController() {
    return gameBoardController;
  }

  /**
   * Create a list of valid targets, choose targets and shoot them.
   */
  public void fire(Player player, GUIPlayerView client) {
    List<String> possibleTargetNames = new ArrayList<>();
    List<Player> possibleTargets = new ArrayList<>();
    List<String> chosenTargetNames = new ArrayList<>();
    List<Player> chosenTargets = new ArrayList<>();

    possibleTargets = findTargets(player);
    for(Player p : possibleTargets){
      possibleTargetNames.add(p.getName());
    }

    chosenTargetNames = client.chooseTargets(possibleTargetNames);
    for(Player p : gameBoardController.getPlayers()){
      if(chosenTargetNames.contains(p.getName())){
        chosenTargets.add(p);
      }
    }

    shootTargets(chosenTargets);
  }

  /**
   * Find all possible targets
   */
  public abstract List<Player> findTargets(Player shooter);

  /**
   * Choose targets from the list of possible targets
   */
  public abstract List<Player> chooseTargets(List<Player> possibleTargets);

  /**
   * Apply the weapon's effects on selected targets.
   */
  public abstract void shootTargets(List<Player> targets);

}