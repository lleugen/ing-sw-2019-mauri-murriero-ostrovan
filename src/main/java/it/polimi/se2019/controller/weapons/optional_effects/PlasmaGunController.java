package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlasmaGunController extends OptionalEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public PlasmaGunController(GameBoardController g) {
    super(g);
    name = "PlasmaGunController";
    numberOfOptionalEffects = 3;
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = new ArrayList<>();
    targets.add(chooseOneVisiblePlayer(shooter));
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    client = identifyClient(shooter);
    List<String> availableEffects = new ArrayList<>();
    if(firingMode.get(0)){
      availableEffects.add("basic effect");
    }
    if(firingMode.get(1)){
      availableEffects.add("ghase glide");
    }
    if(firingMode.get(2)){
      availableEffects.add("charged shot");
    }
    Integer chosenEffect;

    try{
      while(firingMode.contains(true)){
        //choose which effect to apply
        Player target = null;
        chosenEffect = identifyClient(shooter).chooseIndex(availableEffects);
        firingMode.set(chosenEffect, false);
        if(chosenEffect == 0){
          List<Player> foundTargets = findTargets(shooter);
          foundTargets.get(0).takeDamage(shooter, 2);
          //add one more point of damage if the player chooses to use a targeting scope
          if(useTargetingScope(shooter)){
            foundTargets.get(0).takeDamage(shooter, 1);
          }
          //if the damaged target has a tagback gredade, he/she can use it now
          useTagbackGrenade(targets.get(0));
          target = foundTargets.get(0);
        }
        else if(chosenEffect == 1){
          for(int i = 0; i<2; i++){
            shooter.move(shooter.getPosition().getAdjacencies().get
                    (identifyClient(shooter).chooseDirection(map.getOpenDirections(shooter.getPosition()))));
          }
        }
        else if(chosenEffect == 2){
          target.takeDamage(shooter, 1);
          //add one more point of damage if the player chooses to use a targeting scope
          if(useTargetingScope(shooter)){
            target.takeDamage(shooter, 1);
          }
          //if the damaged target has a tagback gredade, he/she can use it now
          useTagbackGrenade(target);
        }
      }
    }
    catch(UserTimeoutException e){
      
    Logger.getLogger(LOG_NAMESPACE).log(
        Level.WARNING,
        "Client Disconnected",
        e
    );
    }

  }
}