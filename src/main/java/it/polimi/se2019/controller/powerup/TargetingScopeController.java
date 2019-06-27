package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The targeting scope allows a player to spend one ammo of any kind to
 * add one damage to an attack.
 */
public class TargetingScopeController extends PowerUpController {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ddd"; // TODO

  public TargetingScopeController() {
  }

  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) throws UserTimeoutException{
    client = identifyClient(user);
    Boolean used = false;
    if(client.chooseBoolean("Do you want to use a targeting scope?")){
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