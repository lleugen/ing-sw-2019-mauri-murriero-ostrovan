package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CyberBladeController extends OptionalEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public CyberBladeController(GameBoardController g) {
    super(g);
    name = "CyberBladeController";
    numberOfOptionalEffects = 3;

  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    //choose one target from all the players on the same square
    List<Player> targets = new ArrayList<>();
    List<Player> playersOnSquare = map.getPlayersOnSquare(shooter.getPosition());
    try{
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets
                      (gameBoardController.getPlayerNames(playersOnSquare))));
    }
    catch(UserTimeoutException e){
      
    Logger.getLogger(LOG_NAMESPACE).log(
    Level.WARNING,
    "Client Disconnected",
    e
);
    }

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
      availableEffects.add("shadowstep");
    }
    if(firingMode.get(2)){
      availableEffects.add("slice and dice");
    }
    Integer chosenEffect;

    try{
      while(firingMode.contains(true)){
        //choose which effect to apply
        chosenEffect = client.chooseIndex(availableEffects);
        firingMode.set(chosenEffect, false);
        if(chosenEffect == 0 || chosenEffect == 2){
          List<Player> foundTargets = findTargets(shooter);
          for(Player p : foundTargets){
            p.takeDamage(shooter, 2);
            //add one more point of damage if the player chooses to use a targeting scope
            if(useTargetingScope(shooter)){
              p.takeDamage(shooter, 1);
            }
            //if the damaged target has a tagback gredade, he/she can use it now
            useTagbackGrenade(p);
          }
        }
        else if(chosenEffect == 1){
          shooter.move(shooter.getPosition().getAdjacencies().get
                  (client.chooseDirection
                          (map.getOpenDirections(shooter.getPosition()))));
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