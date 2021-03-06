package it.polimi.se2019.controller.powerup;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The tagback grenade can be used when taking damage to assign a mark
 * to the offender.
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
public class TagbackGrenadeController extends PowerUpController {
  public TagbackGrenadeController(GameBoardController g){
    super(g);
    name="TagbackGrenade";
  }
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "TagbackGrenadeController";

  PlayerViewOnServer client;

  /**
   *
   */
  @Override
  public Boolean usePowerUp(Player user) {
    client = identifyClient(user);
    try {
      if (client.chooseBoolean("Do you want to use a tagback grenade?")) {
        List<String> tagbackGrenadesAvailable = new ArrayList<>();
        for (PowerUpCard p : user.getInventory().getPowerUps()) {
          if (p.getDescription().equals("TagbackGrenadeRed")
                  || p.getDescription().equals("TagbackGrenadeBlue")
                  || p.getDescription().equals("TagbackGrenadeYellow")) {
            tagbackGrenadesAvailable.add(p.getDescription());
          }
        }
        int chosenCardIndex;
        PowerUpCard chosenCard = null;
        if (tagbackGrenadesAvailable.size() > 1) {
          //choose which one to use if more than one is available
          chosenCardIndex = client.chooseSpawnLocation(tagbackGrenadesAvailable);
        } else {
          chosenCardIndex = 0;
        }
        for (PowerUpCard p : user.getInventory().getPowerUps()) {
          if (p.getDescription().equals(tagbackGrenadesAvailable.get(chosenCardIndex))) {
            chosenCard = p;
          }
        }
        user.getBoard().getDamageReceived().get
                (user.getBoard().getDamageReceived().size() - 1).takeMarks(user, 1);
        user.getInventory().discardPowerUp(chosenCard);
        return true;
      }
      else {
        return false;
      }
    }
    catch (UserTimeoutException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.INFO,
              "User Disconnected",
              e
      );
      return false;
    }
  }
}