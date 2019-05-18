package it.polimi.se2019.controller.weapons;

import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponController is an abstract class with a template "fire" method
 * and non implemented findTargets and shootTargets
 */
public abstract class WeaponController {

  protected GameBoardController gameBoardController;
  protected String name;

  public WeaponController(String n, GameBoardController g){
      name = n;
      gameBoardController = g;
  }

  public PlayerView identifyClient(Player player){
      PlayerView client = null;
      for(PlayerView c : gameBoardController.getClients()){
          if(c.getName().equals(player.getName())){
              client = c;
          }
      }
      return client;
  }

  public String getName(){
    return name;
  }

  public GameBoardController getGameBoardController() {
    return gameBoardController;
  }

  /**
   * Create a list of valid targets, choose targets and shoot them.
   */
  public void fire(Player player, PlayerView client) {
    List<String> possibleTargetNames = new ArrayList<>();
    List<Player> possibleTargets = new ArrayList<>();
    List<String> chosenTargetNames = new ArrayList<>();
    List<Player> chosenTargets = new ArrayList<>();

    possibleTargets = findTargets(player, new ArrayList<Boolean>());
    for(Player p : possibleTargets){
      possibleTargetNames.add(p.getName());
    }

    chosenTargetNames = client.chooseTargets(possibleTargetNames);
    for(Player p : gameBoardController.getPlayers()){
      if(chosenTargetNames.contains(p.getName())){
        chosenTargets.add(p);
      }
    }

    shootTargets(player, chosenTargets);
  }

  /**
   * Find all possible targets
   */
  public abstract List<Player> findTargets(Player shooter, List<Boolean> firingMode);

  /*
   * Choose targets from the list of possible targets
   *
  public abstract List<Player> chooseTargets(List<Player> possibleTargets);
  */

  /**
   * Apply the weapon's effects on selected targets.
   */
  public abstract void shootTargets(Player shooter, List<Player> targets);

}