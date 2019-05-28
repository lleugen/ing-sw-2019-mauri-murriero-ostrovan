package it.polimi.se2019.model.grabbable;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.player.Player;
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
  private String description_eng;
  private String description_ita;
  private String description;
  private String name;

  public String getDescription_eng() {
    return description_eng;
  }

  public void setDescription_eng(String description_eng) {
    this.description_eng = description_eng;
  }

  public String getDescription_ita() {
    return description_ita;
  }

  public void setDescription_ita(String description_ita) {
    this.description_ita = description_ita;
  }

  public String getName_eng() {
    return name_eng;
  }

  public void setName_eng(String name_eng) {
    this.name_eng = name_eng;
  }

  public String getName_ita() {
    return name_ita;
  }

  public void setName_ita(String name_ita) {
    this.name_ita = name_ita;
  }

  /**
   * Name of the weapon
   */
  private String name_eng;
  private String name_ita;

  private String img;
  private String effect1Name_eng;
  private String effect1Name_ita;
  private String effect1Desc_eng;
  private String effect1Desc_ita;
  private String effect2Name_eng;
  private String effect2Name_ita;
  private String effect2Desc_eng;
  private String effect2Desc_ita;
  private String mode1Name_eng;
  private String mode1Name_ita;
  private String mode1Desc_eng;
  private String mode1Desc_ita;
  private String mode2Name_eng;
  private String mode2Name_ita;
  private String mode2Desc_eng;
  private String mode2Desc_ita;
  private WeaponController controller;

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getEffect1Name_eng() {
    return effect1Name_eng;
  }

  public void setEffect1Name_eng(String effect1Name_eng) {
    this.effect1Name_eng = effect1Name_eng;
  }

  public String getEffect1Name_ita() {
    return effect1Name_ita;
  }

  public void setEffect1Name_ita(String effect1Name_ita) {
    this.effect1Name_ita = effect1Name_ita;
  }

  public String getEffect1Desc_eng() {
    return effect1Desc_eng;
  }

  public void setEffect1Desc_eng(String effect1Desc_eng) {
    this.effect1Desc_eng = effect1Desc_eng;
  }

  public String getEffect1Desc_ita() {
    return effect1Desc_ita;
  }

  public void setEffect1Desc_ita(String effect1Desc_ita) {
    this.effect1Desc_ita = effect1Desc_ita;
  }

  public String getEffect2Name_eng() {
    return effect2Name_eng;
  }

  public void setEffect2Name_eng(String effect2Name_eng) {
    this.effect2Name_eng = effect2Name_eng;
  }

  public String getEffect2Name_ita() {
    return effect2Name_ita;
  }

  public void setEffect2Name_ita(String effect2Name_ita) {
    this.effect2Name_ita = effect2Name_ita;
  }

  public String getEffect2Desc_eng() {
    return effect2Desc_eng;
  }

  public void setEffect2Desc_eng(String effect2Desc_eng) {
    this.effect2Desc_eng = effect2Desc_eng;
  }

  public String getEffect2Desc_ita() {
    return effect2Desc_ita;
  }

  public void setEffect2Desc_ita(String effect2Desc_ita) {
    this.effect2Desc_ita = effect2Desc_ita;
  }

  public String getMode1Name_eng() {
    return mode1Name_eng;
  }

  public void setMode1Name_eng(String mode1Name_eng) {
    this.mode1Name_eng = mode1Name_eng;
  }

  public String getMode1Name_ita() {
    return mode1Name_ita;
  }

  public void setMode1Name_ita(String mode1Name_ita) {
    this.mode1Name_ita = mode1Name_ita;
  }

  public String getMode1Desc_eng() {
    return mode1Desc_eng;
  }

  public void setMode1Desc_eng(String mode1Desc_eng) {
    this.mode1Desc_eng = mode1Desc_eng;
  }

  public String getMode1Desc_ita() {
    return mode1Desc_ita;
  }

  public void setMode1Desc_ita(String mode1Desc_ita) {
    this.mode1Desc_ita = mode1Desc_ita;
  }

  public String getMode2Name_eng() {
    return mode2Name_eng;
  }

  public void setMode2Name_eng(String mode2Name_eng) {
    this.mode2Name_eng = mode2Name_eng;
  }

  public String getMode2Name_ita() {
    return mode2Name_ita;
  }

  public void setMode2Name_ita(String mode2Name_ita) {
    this.mode2Name_ita = mode2Name_ita;
  }

  public String getMode2Desc_eng() {
    return mode2Desc_eng;
  }

  public void setMode2Desc_eng(String mode2Desc_eng) {
    this.mode2Desc_eng = mode2Desc_eng;
  }

  public String getMode2Desc_ita() {
    return mode2Desc_ita;
  }

  public void setMode2Desc_ita(String mode2Desc_ita) {
    this.mode2Desc_ita = mode2Desc_ita;
  }

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
  public void reload(List<PowerUpCard> powerUpCards, Ammo playerAmmoBox) {
    if(!loaded)
    {
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
      try{
        playerAmmoBox.useRed(this.grabCost.getRed() - redPowerUpCards);
        playerAmmoBox.useBlue(this.grabCost.getBlue() - bluePowerUpCards);
        playerAmmoBox.useYellow(this.grabCost.getYellow() - yellowPowerUpCards);
      }
      catch(Ammo.InsufficientAmmoException exception){
        throw new UnableToReloadException();
      }
      this.loaded = true;
    }
    else{
      throw new WeaponAlreadyLoadedException();
    }
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

  public static class WeaponAlreadyLoadedException extends RuntimeException
  {
    @Override
    public String toString() {
      return "This weapon is already loaded.";
    }
  }

  public static class UnableToReloadException extends RuntimeException
  {
    @Override
    public String toString() {
      return "Not enough resources to reload the weapon.";
    }
  }
}


