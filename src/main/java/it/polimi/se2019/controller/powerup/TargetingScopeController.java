package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The targeting scope allows a player to spend one ammo of any kind to
 * add one damage to an attack.
 */
public class TargetingScopeController extends PowerUpController {
  public TargetingScopeController() {
  }

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) {
    Boolean used = false;
    if(identifyClient(user).chooseBoolean("Do you want to use a targeting scope?")){
      List<String> availableTargetingScopes = new ArrayList<>();
      for(PowerUpCard card : user.getInventory().getPowerUps()){
        if((card.getDescription().equals("TargetingScopeRed"))
                | (card.getDescription().equals("TargetingScopeBlue"))
                | (card.getDescription().equals("TargetingScopeYellow"))){
          availableTargetingScopes.add(card.getDescription());
        }
      }
      Integer chosenCardIndex;
      PowerUpCard chosenCard = null;
      if(availableTargetingScopes.size() > 1){
        chosenCardIndex = identifyClient(user).chooseSpawnLocation(availableTargetingScopes);
      }
      else{
        chosenCardIndex = 0;
      }
      for(PowerUpCard p : user.getInventory().getPowerUps()){
        if(p.getDescription().equals(availableTargetingScopes.get(chosenCardIndex))){
          chosenCard = p;
        }
      }
      user.getInventory().discardPowerUp(chosenCard);
      used = true;
    }
    else{
      used = false;
    }

    return used;
  }

}