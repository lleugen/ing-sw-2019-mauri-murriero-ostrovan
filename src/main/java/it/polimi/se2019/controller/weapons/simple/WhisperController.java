package it.polimi.se2019.controller.weapons.simple;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhisperController extends SimpleWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public WhisperController(GameBoardController g) {
    super(g);
    name = "WhisperController";

  }
  public List<Boolean> selectFiringMode(PlayerViewOnServer client){
    List<Boolean> firingMode = new ArrayList<>();
    firingMode.add(true);
    return firingMode;
  }

  @Override
  public List<Player> findTargets(Player shooter){
    Map map = getGameBoardController().getGameBoard().getMap();
    List<Player> visiblePlayers = map.getVisiblePlayers(shooter.getPosition());
    List<Integer> positionCoordinates = map.getSquareCoordinates(shooter.getPosition());
    for(Player p : visiblePlayers){
      if((map.getSquareCoordinates(p.getPosition()).get(0) > positionCoordinates.get(0) - 1) ||
              (map.getSquareCoordinates(p.getPosition()).get(0) < positionCoordinates.get(0) + 1) ||
              (map.getSquareCoordinates(p.getPosition()).get(1) > positionCoordinates.get(1) - 1) ||
              (map.getSquareCoordinates(p.getPosition()).get(1) < positionCoordinates.get(1) + 1)){
        visiblePlayers.remove(p);
      }
    }
    //incompatible type error will be solved by change to the viewinterface
    List<Player> targets = new ArrayList<>();
    PlayerViewOnServer client = identifyClient(shooter);
    try{
      targets.add(gameBoardController.identifyPlayer(client.chooseTargets
              (gameBoardController.getPlayerNames(visiblePlayers))));
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