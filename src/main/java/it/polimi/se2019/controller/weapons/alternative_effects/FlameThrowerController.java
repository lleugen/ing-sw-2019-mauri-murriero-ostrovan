package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class FlameThrowerController extends AlternativeEffectWeaponController {

  public FlameThrowerController() {
  }
  Map map = getGameBoardController().getGameBoard().getMap();
  @Override
  public List<Player> findTargets(Player shooter, boolean firingMode, PlayerView client){

    //targettable squares are all those at distance two or less in the same direction
    List<Square> targettableSquares = new ArrayList<>();
    List<Player> targets = new ArrayList<>();

    if(!shooter.getPosition().getAdjacencies().get(0).isBlocked()){
      targettableSquares.add(shooter.getPosition().getAdjacencies().get(0).getSquare());
    }
    if(!shooter.getPosition().getAdjacencies().get(1).isBlocked()){
      targettableSquares.add(shooter.getPosition().getAdjacencies().get(1).getSquare());
    }
    if(!shooter.getPosition().getAdjacencies().get(2).isBlocked()){
      targettableSquares.add(shooter.getPosition().getAdjacencies().get(2).getSquare());
    }
    if(!shooter.getPosition().getAdjacencies().get(3).isBlocked()){
      targettableSquares.add(shooter.getPosition().getAdjacencies().get(3).getSquare());
    }

    List<List<Integer>> targettableSquaresCoordinates = new ArrayList<>();
    for(Square s : targettableSquares){
      targettableSquaresCoordinates.add(map.getSquareCoordinates(s));
    }
    List<Integer> firstTargetSquareCoordinates = client.chooseTargetSquare(targettableSquaresCoordinates);
    Square firstTargetSquare =
            map.getMapSquares()[firstTargetSquareCoordinates.get(0)][firstTargetSquareCoordinates.get(1)];
    targettableSquares.clear();
    targettableSquares.add(firstTargetSquare);
    Square secondTargetSquare;
    if(firstTargetSquare.getAdjacencies().get(0).getSquare().equals(shooter.getPosition())){
      secondTargetSquare = firstTargetSquare.getAdjacencies().get(2).getSquare();
    }
    else if(firstTargetSquare.getAdjacencies().get(1).getSquare().equals(shooter.getPosition())){
      secondTargetSquare = firstTargetSquare.getAdjacencies().get(3).getSquare();
    }
    else if(firstTargetSquare.getAdjacencies().get(2).getSquare().equals(shooter.getPosition())){
      secondTargetSquare = firstTargetSquare.getAdjacencies().get(0).getSquare();
    }
    else{
      secondTargetSquare = firstTargetSquare.getAdjacencies().get(1).getSquare();
    }
    targettableSquares.add(secondTargetSquare);

    if(firingMode){
      for(Player p : getGameBoardController().getPlayers()){
        if(targettableSquares.contains(p.getPosition())){
          targets.add(p);
        }
      }
    }
    else{
      String firstTargetName, secondTargetName = null;
      Player firstTarget = null;
      Player secondTarget = null;
      List<String> firstTargetList = new ArrayList<>();
      List<String> secondTargetList = new ArrayList<>();
      for(Player p : getGameBoardController().getPlayers()){
        if(p.getPosition().equals(firstTargetSquare)){
          firstTargetList.add(p.getName());
        }
        if(p.getPosition().equals(secondTargetSquare)){
          secondTargetList.add(p.getName());
        }
      }
      firstTargetName = client.chooseTargets(firstTargetList).get(0);
      secondTargetName = client.chooseTarget(secondTargetList).get(0);
      for(Player p : getGameBoardController().getPlayers()){
        if(p.getName().equals(firstTargetName)){
          firstTarget = p;
        }
        if(p.getName().equals(secondTargetName)){
          secondTarget = p;
        }
      }
      targets.add(firstTarget);
      targets.add(secondTarget);
    }
    return targets;
  }
}