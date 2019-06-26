package it.polimi.se2019.controller.weapons;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponController is an abstract class with a template "fire" method
 * and non implemented findTargets and shootTargets
 */
public abstract class WeaponController {

  protected GameBoardController gameBoardController;
  protected String name;
  protected List<Boolean> firingMode;

  public WeaponController(){
  }

  protected Map map = getGameBoardController().getGameBoard().getMap();

  public PlayerViewOnServer identifyClient(Player player){
      PlayerViewOnServer client = null;
      for(PlayerViewOnServer c : gameBoardController.getClients()){
          if(c.getName().equals(player.getName())){
              client = c;
          }
      }
      return client;
  }

  protected void useTagbackGrenade(Player p){
      for(PowerUpCard card : p.getInventory().getPowerUps()){
          if(card.getDescription().equals("TagbackGrenadeRed")
                  | card.getDescription().equals("TagbackGrenadeBlue")
                  | card.getDescription().equals("TagbackGrenadeYellow")){
              gameBoardController.getPowerUpControllers().get(1).usePowerUp(p);
          }
      }
  }

  protected boolean useTargetingScope(Player p){
      Boolean used = false;
      for(PowerUpCard card : p.getInventory().getPowerUps()){
          if((card.getDescription().equals("TargetingScopeRed"))
                  | (card.getDescription().equals("TargetingScopeBlue"))
                  | (card.getDescription().equals("TargetingScopeYellow"))){
              used = gameBoardController.getPowerUpControllers().get(2).usePowerUp(p);
          }
      }
      return used;
  }

  public String getName(){
    return name;
  }

  public GameBoardController getGameBoardController() {
    return gameBoardController;
  }


  /**
   * Create a list of valid targets, choose targets and shoot them.
   */
  public void fire(Player player, PlayerViewOnServer client) {
    List<Player> chosenTargets = new ArrayList<>();

    firingMode = selectFiringMode(client);

    chosenTargets = findTargets(player);

    shootTargets(player, chosenTargets);
  }

  protected Player chooseOneVisiblePlayer(Player shooter){
      List<Player> possibleTargets = map.getVisiblePlayers(shooter.getPosition());
      return gameBoardController.identifyPlayer
              (identifyClient(shooter).chooseTargets
                      (gameBoardController.getPlayerNames(possibleTargets)));
  }

  public abstract List<Boolean> selectFiringMode(PlayerViewOnServer client);

  /**
   * Find all possible targets
   */
  public abstract List<Player> findTargets(Player shooter);

  /*
   * Choose targets from the list of possible targets
   *
  public abstract List<Player> chooseTargets(List<Player> possibleTargets);
  */

  /**
   * Apply the weapon's effects on selected targets.
   */
  public abstract void shootTargets(Player shooter, List<Player> targets);

}