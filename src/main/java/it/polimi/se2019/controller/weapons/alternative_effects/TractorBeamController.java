package it.polimi.se2019.controller.weapons.alternative_effects;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.weapons.optional_effects.OptionalEffectWeaponController;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public class TractorBeamController extends OptionalEffectWeaponController {
  public TractorBeamController(GameBoardController g) {
    super(g);
    name = "TractorBeamController";
  }

  PlayerViewOnServer client;

  @Override
  public List<Player> findTargets(Player shooter){
    client = identifyClient(shooter);
    List<Player> possibletargets = new ArrayList<>();
    List<Player> currentSquareTwoMovesAway = new ArrayList<>();
    List<Player> targets = new ArrayList<>();
    if(firingMode.get(0)){
      List<Square> visibleSquares = map.getVisibleSquares(shooter.getPosition());
      for(Square q : visibleSquares){
        currentSquareTwoMovesAway.clear();
        currentSquareTwoMovesAway.addAll(map.getTwoMovesAway(q));
        for(Player p : currentSquareTwoMovesAway){
          if(!possibletargets.contains(p)){
            possibletargets.add(p);
          }
        }
      }
    }
    else{
      possibletargets = map.getTwoMovesAway(shooter.getPosition());
    }
    try{
      targets.add(gameBoardController.identifyPlayer
              (client.chooseTargets
                      (gameBoardController.getPlayerNames(possibletargets))));
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
    try{
      if(firingMode.get(0)){
        List<Square> visibleSquares = map.getVisibleSquares(shooter.getPosition());
        List<Square> squaresTwoMovesFromtarget = map.getTwoMovesAwaySquares(shooter.getPosition());
        List<Square> destinationSquares = new ArrayList<>();
        List<List<Integer>> destinationSquareCoordinates = new ArrayList<>();
        for(Square q : squaresTwoMovesFromtarget){
          if(visibleSquares.contains(q)){
            destinationSquares.add(q);
            destinationSquareCoordinates.add(map.getSquareCoordinates(q));
          }
        }

        List<Integer> chosenDestinationSquareCoordinates = client.chooseTargetSquare
                (destinationSquareCoordinates);
        Square chosenDestinationSquare =
                map.getMapSquares()[chosenDestinationSquareCoordinates.get(0)][chosenDestinationSquareCoordinates.get(1)];
        targets.get(0).moveToSquare(chosenDestinationSquare);
        targets.get(0).takeDamage(shooter,1);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          targets.get(0).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(targets.get(0));
      }
      else{
        targets.get(0).moveToSquare(shooter.getPosition());
        targets.get(0).takeDamage(shooter, 3);
        //add one more point of damage if the player chooses to use a targeting scope
        if(useTargetingScope(shooter)){
          targets.get(0).takeDamage(shooter, 1);
        }
        //if the damaged target has a tagback gredade, he/she can use it now
        useTagbackGrenade(targets.get(0));
      }
    }
    catch(UserTimeoutException e){
      //remove player from game
      client.setConnected(false);
    }

  }
}