package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.weapons.optional_effects.OptionalEffectWeaponController;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class ElectroscytheController extends OptionalEffectWeaponController {
  public ElectroscytheController(GameBoardController g) {
    super(g);
    name = "Electroscythe";
    numberOfOptionalEffects = 2;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> result = new ArrayList<>();
    result = map.getPlayersOnSquares(
            map.getReachableSquares(shooter.getPosition(), 0)
    );
    result.remove(shooter);
    return result;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    //firingMode = selectFiringMode(identifyClient(shooter));
    if(firingMode.get(0)){
      for(Player p : targets){
        p.takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          p.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(p);
      }
    }
    else{
      for(Player p : targets){
        p.takeDamage(shooter, 2);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          p.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(p);
      }
    }
  }
}