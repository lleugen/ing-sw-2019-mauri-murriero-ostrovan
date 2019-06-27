package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FurnaceController extends AlternativeEffectWeaponController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public FurnaceController(GameBoardController g) {
    super(g);
    name = "FurnaceController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    List<Player> targets = new ArrayList<>();
    try{
      if(firingMode.get(0)){
        //basic mode, all players in a room you're not in
        //get visible rooms
        List<String> visibleRooms = new ArrayList<>();
        List<Square> visibleSquares = map.getVisibleSquares(shooter.getPosition());
        for(Square q : visibleSquares){
          if((!visibleRooms.contains(q.getIdRoom())) & (!shooter.getPosition().getIdRoom().equals(q.getIdRoom()))){
            visibleRooms.add(q.getIdRoom());
          }
        }
        //choose one room
        String targetRoom = client.chooseRoom(visibleRooms);
        //all players in the chosen room are targets
        for(Player p : gameBoardController.getPlayers()){
          if(p.getPosition().getIdRoom().equals(targetRoom)){
            targets.add(p);
          }
        }
      }
      else{
        //cosy fire, all players in a square one move away
        //get all adjacent squares
        List<Square> adjacentSquares = new ArrayList<>();
        for(int i = 0; i<3; i++){
          adjacentSquares.add(shooter.getPosition().getAdjacencies().get(i).getSquare());
        }
        //get their coordinates
        List<List<Integer>> adjacentSquaresCoordinates = new ArrayList<>();
        for(Square q : adjacentSquares){
          adjacentSquaresCoordinates.add(map.getSquareCoordinates(q));
        }
        //choose one square
        List<Integer> targetSquareCoordinates = new ArrayList<>();
        targetSquareCoordinates = client.chooseTargetSquare(adjacentSquaresCoordinates);
        Square targetSquare = map.getMapSquares()[targetSquareCoordinates.get(0)][targetSquareCoordinates.get(1)];
        //all players on the chosen square are targets
        for(Player p : gameBoardController.getPlayers()){
          if(p.getPosition().equals(targetSquare)){
            targets.add(p);
          }
        }
      }
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
    if(firingMode.get(0)){
      for(Player p : targets){
        p.takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          p.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(p);
      }
    }
    else{
      for(Player p : targets){
        p.takeDamage(shooter, 1);
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
}