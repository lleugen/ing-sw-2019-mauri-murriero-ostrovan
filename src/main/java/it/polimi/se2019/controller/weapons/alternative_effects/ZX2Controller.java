//package it.polimi.se2019.controller.weapons.alternative_effects;
//
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.model.player.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ZX2Controller extends AlternativeEffectWeaponController {
//  public ZX2Controller(GameBoardController g) {
//    super(g);
//    name = "ZX2Controller";
//  }
//
//  @Override
//  public List<Player> findTargets(Player shooter){
//    List<Player> targets = new ArrayList<>();
//    List<Player> possibleTargets = new ArrayList<>();
//    if(firingMode.get(0)){
//      targets.add
//              (gameBoardController.identifyPlayer
//                      (identifyClient(shooter).chooseTargets
//                              (gameBoardController.getPlayerNames
//                                      (map.getVisiblePlayers(shooter.getPosition())))));
//    }
//    else{
//      possibleTargets.addAll(map.getVisiblePlayers(shooter.getPosition()));
//      for(int i = 0; i<2; i++){
//        if(possibleTargets.size()>0){
//          targets.add
//                  (i, gameBoardController.identifyPlayer
//                          (identifyClient(shooter).chooseTargets
//                                  (gameBoardController.getPlayerNames
//                                          (possibleTargets))));
//          possibleTargets.remove(targets.get(i));
//        }
//      }
//    }
//    return targets;
//  }
//
//  @Override
//  public void shootTargets(Player shooter, List<Player> targets){
//    if(firingMode.get(0)){
//      targets.get(0).takeDamage(shooter, 1);
//      //add one more point of damage if the player chooses to use a targeting scope
//      if(useTargetingScope(shooter)){
//        targets.get(0).takeDamage(shooter, 1);
//      }
//      //if the damaged target has a tagback gredade, he/she can use it now
//      useTagbackGrenade(targets.get(0));
//      targets.get(0).takeMarks(shooter, 2);
//    }
//    else{
//      for(Player p : targets){
//        p.takeMarks(shooter, 1);
//      }
//    }
//  }
//}