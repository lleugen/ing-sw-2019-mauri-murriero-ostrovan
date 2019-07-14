package it.polimi.se2019.controller.weapons.simple;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class WhisperController extends SimpleWeaponController {
  public WhisperController(GameBoardController g) {
    super(g);
    name = "Whisper";

  }
  public List<Boolean> selectFiringMode(PlayerViewOnServer client){
    List<Boolean> firingMode = new ArrayList<>();
    firingMode.add(true);
    return firingMode;
  }

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    Map map = getGameBoardController().getGameBoard().getMap();
    List<Player> visiblePlayers = map.getPlayersOnSquares(
            map.getVisibleSquares(
                    shooter.getPosition()
            )
    );
    List<Integer> positionCoordinates = map.getSquareCoordinates(shooter.getPosition());
    List<Player> toRemove = new ArrayList<>();
    for(Player p : visiblePlayers){
      if((map.getSquareCoordinates(p.getPosition()).get(0) > positionCoordinates.get(0) - 1) ||
              (map.getSquareCoordinates(p.getPosition()).get(0) < positionCoordinates.get(0) + 1) ||
              (map.getSquareCoordinates(p.getPosition()).get(1) > positionCoordinates.get(1) - 1) ||
              (map.getSquareCoordinates(p.getPosition()).get(1) < positionCoordinates.get(1) + 1)){
        toRemove.add(p);
      }
    }
    visiblePlayers.removeAll(toRemove);
    List<Player> targets = new ArrayList<>();
    PlayerViewOnServer client = identifyClient(shooter);
    visiblePlayers.remove(shooter);
    if(!visiblePlayers.isEmpty()){
      targets.add(gameBoardController.identifyPlayer(client.chooseTargets
              (GameBoardController.getPlayerNames(visiblePlayers))));
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    for(Player p : targets){
      p.takeDamage(shooter, 3);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        p.takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(p);
      p.takeMarks(shooter, 1);
    }
  }
}