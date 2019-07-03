package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class FurnaceController extends AlternativeEffectWeaponController {
  public FurnaceController(GameBoardController g) {
    super(g);
    name = "FurnaceController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    List<Player> targets;
    firingMode = selectFiringMode(client);
    if(firingMode.get(0)){
      //basic mode, all players in a room you're not in
      //get visible rooms
      List<String> visibleRooms = this.map.getVisibleSquares(shooter.getPosition()).stream()
              .map(Square::getIdRoom)
              .filter(shooter.getPosition().getIdRoom()::equals)
              .distinct()
              .map(Square.RoomColor::toString)
              .collect(Collectors.toList());
      //choose one room
      Square.RoomColor targetRoom = Square.RoomColor.valueOf(
              this.client.chooseRoom(visibleRooms)
      );

      //all players in the chosen room are targets
      targets = this.gameBoardController.getPlayers().stream()
              .filter((Player p) ->
                      p.getPosition().getIdRoom().equals(targetRoom)
              )
              .collect(Collectors.toList());
    }
    else{
      //cosy fire, all players in a square one move away
      //get all adjacent squares
      targets = new LinkedList<>();
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
      List<Integer> targetSquareCoordinates = client.chooseTargetSquare(adjacentSquaresCoordinates);
      Square targetSquare = map.getMapSquares()[targetSquareCoordinates.get(0)][targetSquareCoordinates.get(1)];
      //all players on the chosen square are targets
      for(Player p : gameBoardController.getPlayers()){
        if(p.getPosition().equals(targetSquare)){
          targets.add(p);
        }
      }
    }


    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
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