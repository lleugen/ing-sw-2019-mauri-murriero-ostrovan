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
public class HeatSeekerController extends SimpleWeaponController {
  public HeatSeekerController(GameBoardController g) {
    super(g);
    name = "HeatSeeker";
  }
  Map mapReference = getGameBoardController().getGameBoard().getMap();

  @Override
  public List<Boolean> selectFiringMode(PlayerViewOnServer client){
    List<Boolean> firingMode = new ArrayList<>();
    firingMode.add(true);
    return firingMode;
  }
  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    List<Player> visiblePlayers = mapReference.getPlayersOnSquares(
            mapReference.getVisibleSquares(
                    shooter.getPosition()
            )
    );
    List<Player> targettablePlayers = new ArrayList<>();
    for(Player p : getGameBoardController().getGameBoard().getPlayers()){
      if( ! visiblePlayers.contains(p)){
        targettablePlayers.add(p);
      }
    }
    //incompatible type error will be solved by change to the viewinterface
    List<Player> targets = new ArrayList<>();
    PlayerViewOnServer client = identifyClient(shooter);
    targets.add(gameBoardController.identifyPlayer(client.chooseTargets
              (GameBoardController.getPlayerNames(targettablePlayers))));

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
      //if the damaged target has a tagback gredade, he can use it now
      useTagbackGrenade(p);
    }
  }
}