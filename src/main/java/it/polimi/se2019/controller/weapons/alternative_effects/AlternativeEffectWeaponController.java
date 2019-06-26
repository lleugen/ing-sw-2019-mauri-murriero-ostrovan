package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an abstract marker class, it doesn't have its own methods or
 * attributes and is used to group the weapons in the game based on
 * how they work.
 * Alternative effect weapons have two mutually exclusive firing methods.
 */
public abstract class AlternativeEffectWeaponController extends WeaponController {
  /**
   * Make a list of all possible targets.
   */
  @Override
  public abstract List<Player> findTargets(Player shooter);

  @Override
  public List<Boolean> selectFiringMode(PlayerViewOnServer client){
    List<Boolean> firingMode = new ArrayList<>();
    Boolean clientChoice = client.chooseFiringMode("insert 0 for basic, 1 for powered");
    if(clientChoice){
      firingMode.add(false);
      firingMode.add(true);
    }
    else{
      firingMode.add(true);
      firingMode.add(false);
    }
    return firingMode;
  }

  @Override
  public abstract void shootTargets(Player shooter, List<Player> targets);
}