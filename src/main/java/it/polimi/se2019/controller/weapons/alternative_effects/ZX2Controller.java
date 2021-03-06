package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class ZX2Controller extends AlternativeEffectWeaponController {
  public ZX2Controller(GameBoardController g) {
    super(g);
    name = "ZX2";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    //firingMode = selectFiringMode(client);
    if(firingMode.get(0)){
        List<String> names = new ArrayList<>();
        names = GameBoardController.getPlayerNames
                (map.getPlayersOnSquares(
                        map.getVisibleSquares(
                                shooter.getPosition()
                        )
                ));
        names.remove(shooter.getName());
        if(!names.isEmpty()){
          targets.add
                  (gameBoardController.identifyPlayer
                          (client.chooseTargets
                                  (names)));
        }

      }
      else{
      List<Player> possibleTargets = map.getPlayersOnSquares(
              map.getVisibleSquares(
                      shooter.getPosition()
              )
      );
        for(int i = 0; i<2; i++){
          if(!possibleTargets.isEmpty()){
            targets.add
                    (i, gameBoardController.identifyPlayer
                            (client.chooseTargets
                                    (GameBoardController.getPlayerNames
                                            (possibleTargets))));
            possibleTargets.remove(targets.get(i));
          }
        }
      }


    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
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