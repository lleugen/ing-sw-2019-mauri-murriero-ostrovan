package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class GrenadeLauncherController extends OptionalEffectWeaponController {
  public GrenadeLauncherController(GameBoardController g) {
    super(g);
    name = "GrenadeLauncher";
    numberOfOptionalEffects = 2;
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    //possible targets are all visible players
    List<Player> visiblePlayers = map.getPlayersOnSquares(
            map.getVisibleSquares(
                    shooter.getPosition()
            )
    );
    List<Player> targets = new ArrayList<>();
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets
                      (GameBoardController.getPlayerNames(visiblePlayers))));

    return targets;
  }

  private Square findTargetSquare(Player shooter) throws UserTimeoutException {
    client = identifyClient(shooter);
    //targettable squares are all those visible
    List<List<Integer>> targetSquareCoordinates = new ArrayList<>();
    List<Square> targetSquares = map.getVisibleSquares(shooter.getPosition());
    for(Square q : targetSquares){
      targetSquareCoordinates.add(map.getSquareCoordinates(q));
    }
    List<Integer> chosenSquareCoordinates;
    Square s = null;
      chosenSquareCoordinates = client.chooseTargetSquare(targetSquareCoordinates);
      s = map.getMapSquares()[chosenSquareCoordinates.get(0)][chosenSquareCoordinates.get(1)];
    return s;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets) throws UserTimeoutException {
    client = identifyClient(shooter);
    targets.get(0).takeDamage(shooter, 1);
    //add one more point of damage if the player chooses to use a targeting scope
    if(useTargetingScope(shooter)){
      targets.get(0).takeDamage(shooter, 1);
    }
    //if the damaged target has a tagback gredade, he/she can use it now
    useTagbackGrenade(targets.get(0));
    Integer direction = 0;
    direction = identifyClient(shooter).chooseDirection
              (map.getOpenDirections(shooter.getPosition()));

    if(direction != -1){
      targets.get(0).move(targets.get(0).getPosition().getAdjacencies().get(direction));
    }
    if(firingMode.get(1)){
      Square targetSquare = findTargetSquare(shooter);
      List<Player> playersOnSquare = map.getPlayersOnSquares(
              map.getReachableSquares(
                      targetSquare,
                      0
              )
      );
      for(Player p : playersOnSquare){
        p.takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          p.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(p);
      }
    }
  }
}