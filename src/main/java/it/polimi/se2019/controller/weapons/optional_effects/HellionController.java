package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class HellionController extends OptionalEffectWeaponController {
  public HellionController(GameBoardController g) {
    super(g);
    name = "Hellion";
    numberOfOptionalEffects = 2;
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    //choose one target player at least one move away
    List<Player> selectedPlayers = map.getPlayersOnSquares(
            map.getVisibleSquares(
                    shooter.getPosition()
            )
    );

    selectedPlayers = selectedPlayers.stream()
            .filter((Player p) ->
                    !(p.getPosition().equals(shooter.getPosition()))
            )
            .collect(Collectors.toList());

    List<Player> toReturn = new ArrayList<>();
    selectedPlayers.remove(shooter);
    if(!selectedPlayers.isEmpty()){
      toReturn.add(
              gameBoardController.identifyPlayer(
                      identifyClient(shooter).chooseTargets(
                              GameBoardController.getPlayerNames(selectedPlayers)
                      )
              )
      );
    }
    return toReturn;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    targets.get(0).takeDamage(shooter, 1);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(0).takeDamage(shooter, 1);
    }
    //if the damaged target has a tagback gredade, he/she can use it now
    useTagbackGrenade(targets.get(0));
    List<Player> playersOnSquare = map.getPlayersOnSquares(
            map.getReachableSquares(
                    targets.get(0).getPosition(),
                    0
            )
    );
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