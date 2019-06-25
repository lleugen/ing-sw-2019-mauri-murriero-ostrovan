//package it.polimi.se2019.controller.weapons.optional_effects;
//
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.model.map.Square;
//import it.polimi.se2019.model.player.Player;
//import org.omg.PortableInterceptor.INACTIVE;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GrenadeLauncherController extends OptionalEffectWeaponController {
//  public GrenadeLauncherController(GameBoardController g) {
//    super(g);
//    name = "GrenadeLauncherController";
//    numberOfOptionalEffects = 2;
//  }
//
//  @Override
//  public List<Player> findTargets(Player shooter){
//    //possible targets are all visible players
//    List<Player> visiblePlayers = map.getVisiblePlayers(shooter.getPosition());
//    List<Player> targets = new ArrayList<>();
//    targets.add(gameBoardController.identifyPlayer
//            (identifyClient(shooter).chooseTargets
//                    (gameBoardController.getPlayerNames(visiblePlayers))));
//    return targets;
//  }
//
//  private Square findTargetSquare(Player shooter){
//    //targettable squares are all those visible
//    List<List<Integer>> targetSquareCoordinates = new ArrayList<>();
//    List<Square> targetSquares = map.getVisibleSquares(shooter.getPosition());
//    for(Square q : targetSquares){
//      targetSquareCoordinates.add(map.getSquareCoordinates(q));
//    }
//    List<Integer> chosenSquareCoordinates = identifyClient(shooter).chooseTargetSquare(targetSquareCoordinates);
//    return map.getMapSquares()[chosenSquareCoordinates.get(0)][chosenSquareCoordinates.get(1)];
//  }
//
//  @Override
//  public void shootTargets(Player shooter, List<Player> targets){
//    targets.get(0).takeDamage(shooter, 1);
//    //add one more point of damage if the player chooses to use a targeting scope
//    if(useTargetingScope(shooter)){
//      targets.get(0).takeDamage(shooter, 1);
//    }
//    //if the damaged target has a tagback gredade, he/she can use it now
//    useTagbackGrenade(targets.get(0));
//    Integer direction = identifyClient(shooter).chooseDirection
//            (map.getOpenDirections(shooter.getPosition()));
//    if(direction != -1){
//      targets.get(0).move(targets.get(0).getPosition().getAdjacencies().get(direction));
//    }
//    if(firingMode.get(1)){
//      Square targetSquare = findTargetSquare(shooter);
//      List<Player> playersOnSquare = map.getPlayersOnSquare(targetSquare);
//      for(Player p : playersOnSquare){
//        p.takeDamage(shooter, 1);
//        //add one more point of damage if the player chooses to use a targeting scope
//        if(useTargetingScope(shooter)){
//          p.takeDamage(shooter, 1);
//        }
//        //if the damaged target has a tagback gredade, he/she can use it now
//        useTagbackGrenade(p);
//      }
//    }
//  }
//}