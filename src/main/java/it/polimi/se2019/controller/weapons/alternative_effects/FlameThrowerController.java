package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.controller.GameBoardController;
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

  List<Player> primaryTargets = new ArrayList<>();
  List<Player> secondaryTargets = new ArrayList<>();

  @Override
  public List<Player> findTargets(Player shooter, List<Boolean> firingMode){
    primaryTargets.clear();
    secondaryTargets.clear();
    PlayerView client = identifyClient(shooter);
    Integer direction = client.chooseDirection();
    List<Square> targetSquares = new ArrayList<>();
    targetSquares.add(shooter.getPosition().getAdjacencies().get(direction).getSquare());
    targetSquares.add(targetSquares.get(0).getAdjacencies().get(direction).getSquare());
    List<Player> targets = new ArrayList<>();
    if(firingMode.get(1)){
      //choose one adjacent square, deal 2 damage to everybody in it and deal 1 damage to everybody on the next square in the same direction
      for(Player p : getGameBoardController().getPlayers()){
        if(targetSquares.get(0).equals(p.getPosition())){
          primaryTargets.add(p);
          targets.add(p);
        }
        else if(targetSquares.get(1).equals(p.getPosition())){
          secondaryTargets.add(p);
          targets.add(p);
        }
      }
    }
    else{
      //basic firing mode
      List<Player> possiblePrimaryTargets = new ArrayList<>();
      List<Player> possibleSecondaryTargets = new ArrayList<>();
      possiblePrimaryTargets.addAll(map.getPlayersOnSquare(targetSquares.get(0)));
      possibleSecondaryTargets.addAll(map.getPlayersOnSquare(targetSquares.get(1)));

      List<String> possiblePrimaryTargetsNames = new ArrayList<>();
      List<String> possibleSecondaryTargetsNames = new ArrayList<>();
      for(Player p : possiblePrimaryTargets){
        possiblePrimaryTargetsNames.add(p.getName());
      }
      for(Player p : possibleSecondaryTargets){
        possibleSecondaryTargetsNames.add(p.getName());
      }

      primaryTargets.add(getGameBoardController().identifyPlayer(client.chooseTargets(possiblePrimaryTargetsNames).get(0)));
      secondaryTargets.add(getGameBoardController().identifyPlayer(client.chooseTargets(possibleSecondaryTargetsNames).get(0)));

    }
    targets.addAll(primaryTargets);
    targets.addAll(secondaryTargets);
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    for(Player p : primaryTargets){
      if()
    }
  }
}