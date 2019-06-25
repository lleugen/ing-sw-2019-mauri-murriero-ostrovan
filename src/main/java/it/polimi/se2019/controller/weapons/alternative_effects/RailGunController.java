//package it.polimi.se2019.controller.weapons.alternative_effects;
//
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.model.map.Square;
//import it.polimi.se2019.model.player.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RailGunController extends AlternativeEffectWeaponController {
//  public RailGunController(GameBoardController g) {
//    super(g);
//    name = "RailGunController";
//  }
//
//  @Override
//  public List<Player> findTargets(Player shooter){
//    //choose where to shoot
//    List<Integer> directions = new ArrayList<>();
//    directions.add(0, 0);
//    directions.add(1, 1);
//    directions.add(2, 2);
//    directions.add(3, 3);
//    Integer direction = identifyClient(shooter).chooseDirection(directions);
//    //make a list of all players in that direction
//    List<Player> possibleTargets = new ArrayList<>();
//    Square currentSquare = shooter.getPosition();
//    while(currentSquare.getAdjacencies().get(direction).getSquare() != null){
//      possibleTargets.addAll(map.getPlayersOnSquare(currentSquare.getAdjacencies().get(direction).getSquare()));
//      currentSquare = currentSquare.getAdjacencies().get(direction).getSquare();
//    }
//    //get their names
//    List<String> possibleTargetNames = gameBoardController.getPlayerNames(possibleTargets);
//    //choose targets
//    List<Player> targets = new ArrayList<>();
//    if(firingMode.get(0)){
//      //basic mode, one target
//      targets.add(gameBoardController.identifyPlayer
//              (identifyClient(shooter).chooseTargets(possibleTargetNames)));
//    }
//    else{
//      //piercing mode, choose two targets
//      List<String> targetNames = new ArrayList<>();
//      targetNames.add(identifyClient(shooter).chooseTargets(possibleTargetNames));
//      possibleTargetNames.remove(targetNames.get(0));
//      targetNames.add(identifyClient(shooter).chooseTargets(possibleTargetNames));
//      targets.add(gameBoardController.identifyPlayer(targetNames.get(0)));
//      targets.add(gameBoardController.identifyPlayer(targetNames.get(1)));
//    }
//    return targets;
//  }
//
//  @Override
//  public void shootTargets(Player shooter, List<Player> targets){
//    if(firingMode.get(0)){
//      targets.get(0).takeDamage(shooter, 3);
//      //add one more point of damage if the player chooses to use a targeting scope
//      if(useTargetingScope(shooter)){
//        targets.get(0).takeDamage(shooter, 1);
//      }
//      //if the damaged target has a tagback gredade, he/she can use it now
//      useTagbackGrenade(targets.get(0));
//    }
//    else{
//      for(Player p : targets){
//        p.takeDamage(shooter, 2);
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