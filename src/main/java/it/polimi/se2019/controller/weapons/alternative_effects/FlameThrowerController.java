package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class FlameThrowerController extends AlternativeEffectWeaponController {
  public FlameThrowerController(GameBoardController g) {
    super(g);
    name = "FlameThrower";
  }
  Map mapReference = getGameBoardController().getGameBoard().getMap();

  List<Player> primaryTargets = new ArrayList<>();
  List<Player> secondaryTargets = new ArrayList<>();

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException{
    List<Player> targets = new ArrayList<>();
    primaryTargets.clear();
    secondaryTargets.clear();
    PlayerViewOnServer client = identifyClient(shooter);
    List<Integer> possibleDirections = new ArrayList<>();
    for(int i = 0; i<4; i++){
      if(!shooter.getPosition().getAdjacencies().get(i).isBlocked()){
        possibleDirections.add(i);
      }
    }
    Integer direction = 0;
    if(!possibleDirections.isEmpty()){
      direction = client.chooseDirection(possibleDirections);
    }
    else{
      return targets;
    }



    List<Square> targetSquares = new ArrayList<>();
    if(shooter.getPosition().getAdjacencies().get(direction).getSquare() != null){
      targetSquares.add(shooter.getPosition().getAdjacencies().get(direction).getSquare());
      if(targetSquares.get(0)
              .getAdjacencies()
              .get(direction)
              .getSquare() != null){
        targetSquares.add(targetSquares.get(0)
                .getAdjacencies()
                .get(direction)
                .getSquare());
      }
    }


    //firingMode = selectFiringMode(client);
    if(firingMode.get(1)){
      //choose one adjacent square, deal 2 damage to everybody in it and deal 1 damage to everybody on the next square in the same direction
      for(Player p : getGameBoardController().getPlayers()){
        if(targetSquares.get(0) != null && targetSquares.get(0).equals(p.getPosition())){
          primaryTargets.add(p);
          targets.add(p);
        }
        else if(targetSquares.get(1) != null && targetSquares.get(1).equals(p.getPosition())){
          secondaryTargets.add(p);
          targets.add(p);
        }
      }
    }
    else{
      //basic firing mode
      List<Player> possiblePrimaryTargets = new ArrayList<>();
      if(targetSquares.get(0) != null){
        List<Square> temp = new ArrayList<>();
        temp.add(targetSquares.get(0));
        possiblePrimaryTargets = (mapReference.getPlayersOnSquares(temp));
      }
      List<Player> possibleSecondaryTargets = new ArrayList<>();
      if(targetSquares.get(1) != null){
        List<Square> temp = new ArrayList<>();
        temp.add(targetSquares.get(1));
        possibleSecondaryTargets = (mapReference.getPlayersOnSquares(temp));
      }

      List<String> possiblePrimaryTargetsNames = new ArrayList<>();
      List<String> possibleSecondaryTargetsNames = new ArrayList<>();
      for(Player p : possiblePrimaryTargets){
        possiblePrimaryTargetsNames.add(p.getName());
      }
      for(Player p : possibleSecondaryTargets){
        possibleSecondaryTargetsNames.add(p.getName());
      }
      possiblePrimaryTargetsNames.remove(shooter.getName());
      if(!possiblePrimaryTargetsNames.isEmpty()){
        primaryTargets.add(getGameBoardController().identifyPlayer(client.chooseTargets
                (possiblePrimaryTargetsNames)));
      }
      possibleSecondaryTargetsNames.remove(shooter.getName());
      if(!possibleSecondaryTargetsNames.isEmpty()){
        secondaryTargets.add(getGameBoardController().identifyPlayer(client.chooseTargets
                (possibleSecondaryTargetsNames)));
      }
    }
    targets.addAll(primaryTargets);
    targets.addAll(secondaryTargets);
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    for(Player p : primaryTargets){
      if(firingMode.get(0)){
        //basic
        for(Player a : targets){
          a.takeDamage(shooter, 1);
          //add one more point of damage if the player chooses to use a targeting scope
          if(useTargetingScope(shooter)){
            a.takeDamage(shooter, 1);
          }
          //if the damaged target has a tagback gredade, he/she can use it now
          useTagbackGrenade(a);
        }
      }
      else if(firingMode.get(1)){
        //bbq
        for(Player q : primaryTargets){
          q.takeDamage(shooter, 2);
          //add one more point of damage if the player chooses to use a targeting scope
          if(useTargetingScope(shooter)){
            q.takeDamage(shooter, 1);
          }
          //if the damaged target has a tagback gredade, he/she can use it now
          useTagbackGrenade(q);
        }
        for(Player r : secondaryTargets){
          r.takeDamage(shooter, 1);
          //add one more point of damage if the player chooses to use a targeting scope
          if(useTargetingScope(shooter)){
            r.takeDamage(shooter, 1);
          }
          //if the damaged target has a tagback gredade, he/she can use it now
          useTagbackGrenade(r);
        }
      }
    }
  }
}