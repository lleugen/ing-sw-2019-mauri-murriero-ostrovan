package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

/**
 * Powerups are cards that can be used to produce an effect
 * or to pay an ammo cost.
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
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
      if(c.getName().equals(player.getName())){
        client = c;
      }
    }
    return client;
  }

  /**
   * Method prototype, it will be implemented by its subclasses.
   * @param user the player who is using the power up
   * @return the result of the execution
   * @throws UserTimeoutException if the user takes too long to respond or disconnects
   */
  public abstract Boolean usePowerUp(Player user) throws UserTimeoutException;

}