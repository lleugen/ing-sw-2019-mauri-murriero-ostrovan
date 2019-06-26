package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

/**
 * Powerups are cards that can be used to produce an effect
 * or to pay an ammo cost.
 */
public abstract class PowerUpController {
  public PowerUpController() {
  }

  protected GameBoardController gameBoardController;
  protected String name;

  public String getName(){
    return name;
  }

  public PlayerViewOnServer identifyClient(Player player){
    PlayerViewOnServer client = null;
    for(PlayerViewOnServer c : gameBoardController.getClients()){
      try{
        if(c.getName().equals(player.getName())){
          client = c;
        }
      }
      catch(UserTimeoutException e){
        //remove player from game
        client.setConnected(false);
      }
    }
    return client;
  }

  /**
   * Method prototype, it will be implemented by its subclasses.
   */
  public abstract Boolean usePowerUp(Player user);

}