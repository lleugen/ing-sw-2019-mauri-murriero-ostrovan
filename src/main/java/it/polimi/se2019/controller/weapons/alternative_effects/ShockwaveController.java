package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class ShockwaveController extends AlternativeEffectWeaponController {
  public ShockwaveController(GameBoardController g) {
    super(g);
    name = "ShockwaveController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    firingMode = selectFiringMode(client);
    if(firingMode.get(0)){
      //choose target squares
      List<Square> targetSquares = new ArrayList<>();
      Integer chosenDirection;
      for(int i = 0; i<2; i++){
        chosenDirection = client.chooseDirection(map.getOpenDirections(shooter.getPosition()));
        if(!targetSquares.contains(shooter.getPosition().getAdjacencies().get(chosenDirection).getSquare())){
          targetSquares.add(shooter.getPosition().getAdjacencies().get(chosenDirection).getSquare());
        }
      }
      //choose one target player for each target square
      for (Square targetSquare : targetSquares) {
        //choose one player from all those on the square and add it to the target list
        targets.add(gameBoardController.identifyPlayer
                (client.chooseTargets
                        (GameBoardController.getPlayerNames
                                (map.getPlayersOnSquares(
                                        map.getReachableSquares(
                                                targetSquare,
                                                0
                                        )
                                )))));
      }
    }
    else{
      targets.addAll(map.getPlayersOnSquares(
              map.getReachableSquares(shooter.getPosition(), 1)
      ));
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    //the effect on the targets is the same regardless of the firing mode, so there is no distinction
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
}