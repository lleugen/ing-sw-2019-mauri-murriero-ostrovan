package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TagbackGrenadeController extends PowerUpController {
  /**
   * The tagback grenade can be used when taking damage to assign a mark
   * to the offender.
   */
  public TagbackGrenadeController() {
  }

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) {
    Boolean used = false;

    if(identifyClient(user).chooseBoolean("Do you want to use a tagback grenade?")){
      List<String> tagbackGrenadesAvailable = new ArrayList<>();
      for(PowerUpCard p : user.getInventory().getPowerUps()){
        if(p.getDescription().equals("TagbackGrenadeRed")
                | p.getDescription().equals("TagbackGrenadeBlue")
                | p.getDescription().equals("TagbackGrenadeYellow")){
          tagbackGrenadesAvailable.add(p.getDescription());
        }
      }
      Integer chosenCardIndex;
      PowerUpCard chosenCard = null;
      if(tagbackGrenadesAvailable.size() > 1){
        //choose which one to use if more than one is available
        chosenCardIndex = identifyClient(user).chooseSpawnLocation(tagbackGrenadesAvailable);
      }
      else{
        chosenCardIndex = 0;
      }
      for(PowerUpCard p : user.getInventory().getPowerUps()){
        if(p.getDescription().equals(tagbackGrenadesAvailable.get(chosenCardIndex))){
          chosenCard = p;
        }
      }
      user.getBoard().getDamageReceived().get
              (user.getBoard().getDamageReceived().size()-1).takeMarks(user, 1);
      user.getInventory().discardPowerUp(chosenCard);
      used = true;
    }
    else{
      used = false;
    }

    return used;
  }
}