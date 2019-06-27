package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HellionController extends OptionalEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public HellionController(GameBoardController g) {
    super(g);
    name = "HellionController";
    numberOfOptionalEffects = 2;
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    //choose one target player at least one move away
    List<Player> possibleTargets = map.getVisiblePlayers(shooter.getPosition());
    for(Player p : possibleTargets){
      if(p.getPosition().equals(shooter.getPosition())){
        possibleTargets.remove(p);
      }
    }
    List<Player> targets = new ArrayList<>();
    try{
      targets.add(gameBoardController.identifyPlayer
              (identifyClient(shooter).chooseTargets
                      (gameBoardController.getPlayerNames(possibleTargets))));
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
    targets.get(0).takeDamage(shooter, 1);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(0).takeDamage(shooter, 1);
    }
    //if the damaged target has a tagback gredade, he/she can use it now
    useTagbackGrenade(targets.get(0));
    List<Player> playersOnSquare = map.getPlayersOnSquare(targets.get(0).getPosition());
    for(Player p : playersOnSquare){
      p.takeMarks(shooter, 1);
    }
    if(firingMode.get(1)){
      for(Player p : playersOnSquare){
        p.takeMarks(shooter, 1);
      }
    }
  }
}