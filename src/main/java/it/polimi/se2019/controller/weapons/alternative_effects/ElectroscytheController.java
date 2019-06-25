//package it.polimi.se2019.controller.weapons.alternative_effects;
//
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.controller.weapons.optional_effects.OptionalEffectWeaponController;
//import it.polimi.se2019.model.player.Player;
//
//import java.util.List;
//
//public class ElectroscytheController extends OptionalEffectWeaponController {
//  public ElectroscytheController(GameBoardController g) {
//    super(g);
//    name = "ElectroscytheController";
//  }
//
//  @Override
//  public List<Player> findTargets(Player shooter){
//    List<Player> targets = map.getPlayersOnSquare(shooter.getPosition());
//    return targets;
//  }
//
//  @Override
//  public void shootTargets(Player shooter, List<Player> targets){
//    if(firingMode.get(0)){
//      for(Player p : targets){
//        p.takeDamage(shooter, 1);
//        //add one more point of damage if the player chooses to use a targeting scope
//        if(useTargetingScope(shooter)){
//          p.takeDamage(shooter, 1);
//        }
//        //if the damaged target has a tagback gredade, he/she can use it now
//        useTagbackGrenade(p);
//      }
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