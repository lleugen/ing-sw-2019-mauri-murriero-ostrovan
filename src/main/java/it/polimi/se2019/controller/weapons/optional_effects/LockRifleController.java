package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class LockRifleController extends OptionalEffectWeaponController {
  public LockRifleController(GameBoardController g) {
    super(g);
    name = "LockRifleController";
    numberOfOptionalEffects = 2;
  }

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    List<Player> targets = new ArrayList<>();
    targets.add(chooseOneVisiblePlayer(shooter));
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    targets.get(0).takeMarks(shooter, 1);
    targets.get(0).takeDamage(shooter, 2);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(0).takeDamage(shooter, 1);
    }
    //if the damaged target has a tagback gredade, he/she can use it now
    useTagbackGrenade(targets.get(0));
    if(firingMode.get(1)){
      chooseOneVisiblePlayer(shooter).takeMarks(shooter, 1);
    }
  }
}