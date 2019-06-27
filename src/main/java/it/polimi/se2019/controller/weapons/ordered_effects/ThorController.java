package it.polimi.se2019.controller.weapons.ordered_effects;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.PlayerBoard;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThorController extends OrderedEffectsWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "FlameThrowerController";

  public ThorController(GameBoardController g) {
    super(g);
    name = "ThorController";
    numberOfOptionalEffects = 3;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> targets = new ArrayList<>();
    targets.add(chooseOneVisiblePlayer(shooter));
    PlayerViewOnServer client = identifyClient(shooter);

    Integer chainLength = 0;
    for(int i = 0; i<numberOfOptionalEffects; i++){
      if(firingMode.get(i)){
        chainLength++;
      }
    }
    for(int k = 1; k<chainLength; k++){
      try{
        targets.add(k, gameBoardController.identifyPlayer
                (client.chooseTargets(gameBoardController.getPlayerNames
                        (map.getVisiblePlayers(targets.get(k-1).getPosition())))));
      }
      catch(Exception e){
        Logger.getLogger(LOG_NAMESPACE).log(
                    Level.WARNING,
                    "Client Disconnected",
                    e
            );
      }
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    targets.get(0).takeDamage(shooter, 2);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(0).takeDamage(shooter, 1);
    }
    //if the damaged target has a tagback gredade, he/she can use it now
    useTagbackGrenade(targets.get(0));

    targets.get(1).takeDamage(shooter, 1);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(1).takeDamage(shooter, 1);
    }
    useTagbackGrenade(targets.get(1));

    targets.get(2).takeDamage(shooter, 2);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(2).takeDamage(shooter, 1);
    }
    useTagbackGrenade(targets.get(2));
  }
}