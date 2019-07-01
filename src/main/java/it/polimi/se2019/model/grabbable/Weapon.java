package it.polimi.se2019.model.grabbable;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponController represent the model for a physical WeaponController card.
 * The effect of the card will be implemented in the controller of the weapon
 */
public class Weapon extends Grabbable {
  /**
   * Cost to pay for reloading the weapon
   */
  private Ammo reloadCost;

  /**
   * Cost to be paid for grabbing the weapon from a square
   */
  private Ammo grabCost;

  /**
   * True if the weapon is loaded and ready to use, false otherwise
   */
  private boolean loaded;

  /**
   * Current owner of the WeaponController
   */
  private Player owner;

  /**
   * Description of the weapon
   */
  private String name;

  private WeaponController controller;
  private String description;

  public WeaponController getController() {
    return controller;
  }

  public void setController(WeaponController controller) {
    this.controller = controller;
  }

  /**
   * Inits a new Weapon
   *
   * @param n
   * @param gCost   Cost to be paid for grabbing the weapon from a square
   * @param rCost Cost to pay for reloading the weapon
   *                   <p>
   *                   __WARN__ A weapon is ALWAYS initialized as loaded
   */
  public Weapon(String n, Ammo gCost, Ammo rCost) {
    this.grabCost = gCost;
    this.reloadCost = rCost;
    this.loaded = true;
    name = n;
  }

  /**
   * Unload a weapon (a weapon unloaded cannot be used)
   */
  public void unload() {
    this.loaded = false;
  }

  /**
   * Reload a weapon (a weapon reloaded can be used)
   */
  public boolean reload(List<PowerUpCard> powerUpCards, Ammo playerAmmoBox) {
    boolean result = false;
    if(!loaded)
    {
      int redPowerUpCards = 0;
      int bluePowerUpCards = 0;
      int yellowPowerUpCards = 0;
      List<PowerUpCard> powerUpCardsToDiscard = new ArrayList<>();
      for(PowerUpCard powerUp : powerUpCards){
        if((powerUp.getAmmoEquivalent().getRed() == 1)&&(powerUp.getAmmoEquivalent().getBlue() == 0)&&(powerUp.getAmmoEquivalent().getYellow() == 0)){
          redPowerUpCards ++;
        }
        else if((powerUp.getAmmoEquivalent().getBlue() == 1)&&(powerUp.getAmmoEquivalent().getRed() == 0)&&(powerUp.getAmmoEquivalent().getYellow() == 0)){
          bluePowerUpCards ++;
        }
        else if((powerUp.getAmmoEquivalent().getYellow() == 1)&&(powerUp.getAmmoEquivalent().getRed() == 0)&&(powerUp.getAmmoEquivalent().getBlue() == 0)){
          yellowPowerUpCards ++;
        }
        powerUpCardsToDiscard.add(powerUp);
      }
      powerUpCardsToDiscard.forEach(this.owner.getInventory()::discardPowerUp);

      if(
              playerAmmoBox.getRed() >= (reloadCost.getRed() - redPowerUpCards) &&
              playerAmmoBox.getBlue() >= (reloadCost.getBlue() - bluePowerUpCards) &&
              playerAmmoBox.getYellow() >= (reloadCost.getYellow() - yellowPowerUpCards)
      ){
        playerAmmoBox.useRed(reloadCost.getRed() - redPowerUpCards);
        playerAmmoBox.useBlue(reloadCost.getBlue() - bluePowerUpCards);
        playerAmmoBox.useYellow(reloadCost.getYellow() - yellowPowerUpCards);
        this.loaded = true;
        result = true;
      }
    }
    return result;
  }

  /**
   * @return The cost to be paid for grabbing the WeaponController
   */
  public Ammo getGrabCost() {
    return new Ammo(
            this.grabCost.getRed(),
            this.grabCost.getBlue(),
            this.grabCost.getYellow()
    );
  }

  /**
   * @return The cost to be paid for reloading the WeaponController
   */
  public Ammo getReloadCost() {
    return new Ammo(
            this.reloadCost.getRed(),
            this.reloadCost.getBlue(),
            this.reloadCost.getYellow()
    );
  }

  /**
   * @return true if the weapon is loaded, false otherwise
   */
  public boolean isLoaded() {
    boolean loadedCopy;
    loadedCopy = this.loaded;
    return loadedCopy;
  }

  /**
   * @return true if the weapon is unloaded, false if it is loaded
   */
  public boolean isUnloaded() {
    return !(this.loaded);
  }
  /**
   * @return The current owner of the WeaponController
   */
  public Player getOwner() {
    return this.owner;
  }

  /**
   * @return The description of the weapon
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @return The name of the WeaponController
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set a new owner for the weapon
   *
   * @param player The new owner of the weapon
   */
  public void setOwner(Player player) {
    this.owner = player;
  }
}


