package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZX2Controller extends AlternativeEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public ZX2Controller(GameBoardController g) {
    super(g);
    name = "ZX2Controller";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    List<Player> possibleTargets = new ArrayList<>();
    try{
      if(firingMode.get(0)){
        targets.add
                (gameBoardController.identifyPlayer
                        (client.chooseTargets
                                (gameBoardController.getPlayerNames
                                        (map.getVisiblePlayers(shooter.getPosition())))));
      }
      else{
        possibleTargets.addAll(map.getVisiblePlayers(shooter.getPosition()));
        for(int i = 0; i<2; i++){
          if(possibleTargets.size()>0){
            targets.add
                    (i, gameBoardController.identifyPlayer
                            (client.chooseTargets
                                    (gameBoardController.getPlayerNames
                                            (possibleTargets))));
            possibleTargets.remove(targets.get(i));
          }
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

    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    if(firingMode.get(0)){
      targets.get(0).takeDamage(shooter, 1);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        targets.get(0).takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(targets.get(0));
      targets.get(0).takeMarks(shooter, 2);
    }
    else{
      for(Player p : targets){
        p.takeMarks(shooter, 1);
      }
    }
  }
}