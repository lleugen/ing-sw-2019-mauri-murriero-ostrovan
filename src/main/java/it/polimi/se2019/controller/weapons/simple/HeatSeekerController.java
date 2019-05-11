package it.polimi.se2019.controller.weapons.simple;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class HeatSeekerController extends SimpleWeaponController {
  public HeatSeekerController() {
  }
  Map map = getGameBoardController().getGameBoard().getMap();
  @Override
  public List<Player> findTargets(Player shooter){
    List<Player> visiblePlayers = map.getVisiblePlayers(shooter.getPosition());
    List<Player> targettablePlayers = new ArrayList<>();
    for(Player p : getGameBoardController().getGameBoard().getPlayers()){
      if( ! visiblePlayers.contains(p)){
        targettablePlayers.add(p);
      }
    }
    return targettablePlayers;
  }
}