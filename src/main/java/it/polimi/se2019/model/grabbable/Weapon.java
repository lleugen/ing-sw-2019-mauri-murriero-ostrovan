package it.polimi.se2019.model.grabbable;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.Inventory;
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
  private String description;

  /**
   * Name of the weapon
   */
  private String name;

  /**
   * Inits a new WeaponController
   *
   * @param name       Name of the WeaponController
   * @param desc       Description of the weapon
   * @param grabCost   Cost to be paid for grabbing the weapon from a square
   * @param reloadCost Cost to pay for reloading the weapon
   *                   <p>
   *                   __WARN__ A weapon is ALWAYS initialized as loaded
   */
  public Weapon(
          String name, String desc,
          Ammo grabCost, Ammo reloadCost
  ) {
    super();
    this.name = name;
    this.description = desc;
    this.grabCost = grabCost;
    this.reloadCost = reloadCost;
    this.loaded = true;
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
  public void reload(List<PowerUpCard> powerUpCards, Ammo playerAmmoBox) {
    int redPowerUpCards = 0;
    int bluePowerUpCards = 0;
    int yellowPowerUpCards = 0;
    for(PowerUpCard powerUp : powerUpCards){
      if((powerUp.getAmmoEquivalent().getRed() == 1)&(powerUp.getAmmoEquivalent().getBlue() == 0)&(powerUp.getAmmoEquivalent().getYellow() == 0)){
        redPowerUpCards ++;
      }
      else if((powerUp.getAmmoEquivalent().getBlue() == 1)&(powerUp.getAmmoEquivalent().getRed() == 0)&(powerUp.getAmmoEquivalent().getYellow() == 0)){
        bluePowerUpCards ++;
      }
      else if((powerUp.getAmmoEquivalent().getYellow() == 1)&(powerUp.getAmmoEquivalent().getRed() == 0)&(powerUp.getAmmoEquivalent().getBlue() == 0)){
        yellowPowerUpCards ++;
      }
      this.owner.getInventory().discardPowerUp(powerUp);
    }
    playerAmmoBox.useRed(this.grabCost.getRed() - redPowerUpCards);
    playerAmmoBox.useBlue(this.grabCost.getBlue() - bluePowerUpCards);
    playerAmmoBox.useYellow(this.grabCost.getYellow() - yellowPowerUpCards);
    this.loaded = true;
  }

  /**
   * @return The cost to be paid for grabbing the WeaponController
   */
  public Ammo getGrabCost() {
    Ammo grabCostCopy = new Ammo(this.grabCost.getRed(), this.grabCost.getBlue(), this.grabCost.getYellow());
    return grabCostCopy;
  }

  /**
   * @return The cost to be paid for reloading the WeaponController
   */
  public Ammo getReloadCost() {
    Ammo reloadCostCopy = new Ammo(this.reloadCost.getRed(), this.reloadCost.getBlue(), this.reloadCost.getYellow());
    return reloadCostCopy;
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
