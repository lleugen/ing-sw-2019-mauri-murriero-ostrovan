package it.polimi.se2019.controller.weapons.ordered_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class ThorController extends OrderedEffectsWeaponController {
  public ThorController(GameBoardController g) {
    super(g);
    name = "ThorController";
    numberOfOptionalEffects = 3;
  }

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    List<Player> targets = new ArrayList<>();
    targets.add(chooseOneVisiblePlayer(shooter));
    PlayerViewOnServer client = identifyClient(shooter);
    firingMode = selectFiringMode(client);
    int chainLength = 0;
    for(int i = 0; i<numberOfOptionalEffects; i++){
      if(firingMode.get(i)){
        chainLength++;
      }
    }
    for(int k = 1; k<chainLength; k++){
        targets.add(k, gameBoardController.identifyPlayer
                (client.chooseTargets(GameBoardController.getPlayerNames
                        (map.getPlayersOnSquares(
                                map.getVisibleSquares(
                                        targets.get(k-1).getPosition()
                                )
                        )))));
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    for(int i = 0; i<targets.size(); i++){
      targets.get(i).takeDamage(shooter, 2);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        targets.get(i).takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(targets.get(i));
    }
  }
}