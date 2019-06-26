package it.polimi.se2019.controller.weapons.optional_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class MachineGunController extends OptionalEffectWeaponController {
  public MachineGunController(GameBoardController g) {
    super(g);
    name = "MachineGunController";
    numberOfOptionalEffects = 3;
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    //choose one or two target players that you can see
    List<Player> targets = new ArrayList<>();
    List<Player> visiblePlayers = map.getVisiblePlayers(shooter.getPosition());
    List<String> possibleTargetNames = gameBoardController.getPlayerNames(visiblePlayers);
    try{
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets(possibleTargetNames)));
      possibleTargetNames.remove(targets.get(0));
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets(possibleTargetNames)));
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }
    return targets;
  }

  @Override
  public void shootTargets(Player shooter, List<Player> targets){
    client = identifyClient(shooter);
    String firstTarget = null;
    String secondTarget = null;
    List<Player> visiblePlayers = map.getVisiblePlayers(shooter.getPosition());
    List<String> targetNames = gameBoardController.getPlayerNames(targets);
    for(Player p : targets){
      p.takeDamage(shooter, 1);
      //add one more point of damage if the player chooses to use a targeting scope
      if(useTargetingScope(shooter)){
        p.takeDamage(shooter, 1);
      }
      //if the damaged target has a tagback gredade, he/she can use it now
      useTagbackGrenade(p);
    }
    try{
      if(firingMode.get(1) || firingMode.get(2)){

        firstTarget = identifyClient(shooter).chooseTargets(targetNames);
        targetNames.remove(firstTarget);
        secondTarget = targetNames.get(0);
        visiblePlayers.remove(gameBoardController.identifyPlayer(firstTarget));
        visiblePlayers.remove(gameBoardController.identifyPlayer(secondTarget));
      }
      if(firingMode.get(1)){
        gameBoardController.identifyPlayer(firstTarget).takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          gameBoardController.identifyPlayer(firstTarget).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(gameBoardController.identifyPlayer(firstTarget));
      }
      if(firingMode.get(2)){
        gameBoardController.identifyPlayer(secondTarget).takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          gameBoardController.identifyPlayer(secondTarget).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(gameBoardController.identifyPlayer(secondTarget));
        Player thirdTargetPlayer = gameBoardController.identifyPlayer
                (identifyClient(shooter).chooseTargets(gameBoardController.getPlayerNames(visiblePlayers)));
        thirdTargetPlayer.takeDamage(shooter, 1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          thirdTargetPlayer.takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(thirdTargetPlayer);
      }
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }

  }
}