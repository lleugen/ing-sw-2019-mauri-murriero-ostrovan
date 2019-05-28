package it.polimi.se2019.controller.weapons.simple;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class WhisperController extends SimpleWeaponController {
  public WhisperController() {
    name = "WhisperController";
  }
  public List<Boolean> selectFiringMode(PlayerView client){
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
    targets.add(gameBoardController.identifyPlayer(identifyClient(shooter).chooseTargets
            (gameBoardController.getPlayerNames(visiblePlayers))));
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    for(Player p : targets){
      p.takeDamage(shooter, 3);
      p.takeMarks(shooter, 1);
    }
  }
}