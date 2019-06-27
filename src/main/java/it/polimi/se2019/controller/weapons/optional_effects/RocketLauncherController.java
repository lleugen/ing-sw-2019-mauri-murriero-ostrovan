package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RocketLauncherController extends OptionalEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public RocketLauncherController(GameBoardController g) {
    super(g);
    name = "RocketLauncherController";
    numberOfOptionalEffects = 3;
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException{
    client = identifyClient(shooter);
    //choose one target player at least one move away
    List<Player> possibleTargets = map.getVisiblePlayers(shooter.getPosition());
    for(Player p : possibleTargets){
      if(p.getPosition().equals(shooter.getPosition())){
        possibleTargets.remove(p);
      }
    }
    List<Player> targets = new ArrayList<>();
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets
                      (gameBoardController.getPlayerNames(possibleTargets))));

    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    List<String> availableEffects = new ArrayList<>();
    if(firingMode.get(0)){
      availableEffects.add("basic effect");
    }
    if(firingMode.get(1)){
      availableEffects.add("rocket jump");
    }
    Integer chosenEffect;

    while(firingMode.contains(true)){
      //choose which effect to apply
      chosenEffect = client.chooseIndex(availableEffects);
      firingMode.set(chosenEffect, false);
      if(chosenEffect == 0){
        //basic effect
        targets = findTargets(shooter);
        targets.get(0).takeDamage(shooter, 2);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          targets.get(0).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(targets.get(0));
        if(firingMode.get(2)){
          //fragmenting warhead, has to take place during the first action and before moving the target(cfr:game rules)
          List<Player> t = map.getPlayersOnSquare(targets.get(0).getPosition());
          for(Player p : t){
            p.takeDamage(shooter, 1);
            //add one more point of damage if the player chooses to use a targeting scope
            if(useTargetingScope(shooter)){
              p.takeDamage(shooter, 1);
            }
            //if the damaged target has a tagback gredade, he/she can use it now
            useTagbackGrenade(p);
          }
        }
        Integer direction = client.chooseDirection
                (map.getOpenDirections(shooter.getPosition()));
        if(direction != -1){
          targets.get(0).move(targets.get(0).getPosition().getAdjacencies().get(direction));
        }
      }
      else if(chosenEffect == 1){
        Integer direction = null;
        for(int i = 0; i<2; i++) {
          direction = client.chooseDirection(map.getOpenDirections
                  (shooter.getPosition()));
          if(direction != -1){
            shooter.move(shooter.getPosition().getAdjacencies().get(direction));
          }
        }

      }
    }

  }
}
