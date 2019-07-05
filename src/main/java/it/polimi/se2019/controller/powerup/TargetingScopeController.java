package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * The targeting scope allows a player to spend one ammo of any kind to
 * add one damage to an attack.
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class TargetingScopeController extends PowerUpController {
  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) throws UserTimeoutException{
    client = identifyClient(user);
    boolean used = false;
    if(client.chooseBoolean("Do you want to use a targeting scope?")){
      List<String> availableTargetingScopes = new ArrayList<>();
      for(PowerUpCard card : user.getInventory().getPowerUps()){
        if((card.getDescription().equals("TargetingScopeRed"))
                || (card.getDescription().equals("TargetingScopeBlue"))
                || (card.getDescription().equals("TargetingScopeYellow"))){
          availableTargetingScopes.add(card.getDescription());
        }
      }
      int chosenCardIndex;
      PowerUpCard chosenCard = null;
      if(availableTargetingScopes.size() > 1){
        chosenCardIndex = client.chooseSpawnLocation(availableTargetingScopes);
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