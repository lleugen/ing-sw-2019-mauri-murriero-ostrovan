package it.polimi.se2019.controller.weapons.simple;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;

import java.util.List;

public class WhisperController extends SimpleWeaponController {
  public WhisperController() {
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
    return visiblePlayers;
  }
}