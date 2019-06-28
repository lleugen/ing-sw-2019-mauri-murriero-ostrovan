package it.polimi.se2019.model.player;

import it.polimi.se2019.model.grabbable.*;
import it.polimi.se2019.model.deck.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory contains the inventory of each player.
 * Every Ammo, WeaponController or power-up he has, is in this class.
 * In addition, methods for easily add elements to the inventory are exposed,
 * using the same interface Grabbable
 */
public class Inventory {
  public Inventory(Decks decks) {
    decksReference = decks;
    ammo = new Ammo(1, 1, 1);
    weapons = new ArrayList<>();
    powerUps = new ArrayList<>();
    addPowerUpToInventory(decks.drawPowerUp());
  }

  public Decks getDecksReference() {
    return decksReference;
  }

  /**
   * Reference to the decks
   */
  private Decks decksReference;
  /**
   * The player's ammo box
   */
  private Ammo ammo;

  /**
   * The player's weapons
   */
  private ArrayList<Weapon> weapons;

  /**
   * The player's power up cards
   */
  private ArrayList<PowerUpCard> powerUps;

  /**
   * @return a copy of the player's ammo box
   */
  public Ammo getAmmo() {
    //return new Ammo(getAmmo().getRed(), getAmmo().getBlue(), getAmmo().getYellow());
    return ammo;
  }

  /**
   * @return a copy of the weapons in the player's inventory
   */
  public List<Weapon> getWeapons() {
    /*
    ArrayList<Weapon> weaponsCopy = null;
    Collections.copy(weaponsCopy, weapons);
    return weaponsCopy;
    */
    return weapons;
  }

  /**
   * @return a copy of the powerUps in the player's inventory
   */
  public List<PowerUpCard> getPowerUps() {
    /*
    ArrayList<PowerUpCard> powerUpsCopy = null;
    Collections.copy(powerUpsCopy, powerUps);
    return powerUpsCopy;
    */
    return powerUps;
  }

  public void addItemToInventory(Grabbable item){

  }

  /**
   * Add a new weapon to the inventory
   */
  public void addWeaponToInventory(Weapon item){
    if(weapons.size()<3){
      weapons.add(item);
    }
  }

  /**
   * Add a power up card to the inventory
   */
  public void addPowerUpToInventory(PowerUpCard item){
    if(powerUps.size()<4){
      powerUps.add(item);
    }
  }

  /**
   * Add ammo tile to inventory
   */
  public void addAmmoTileToInventory(AmmoTile item){
    ammo.addRed(item.getAmmo().getRed());
    ammo.addBlue(item.getAmmo().getBlue());
    ammo.addYellow(item.getAmmo().getYellow());
    if(item.getPowerUp()){
      addPowerUpToInventory(decksReference.drawPowerUp());
    }
  }

  /**
   * @param powerUp is the power up card to be discarded from the player's inventory
   */
  public void discardPowerUp(PowerUpCard powerUp) {
      this.powerUps.remove(powerUp);
  }

  /**
   * @param ammunition is the ammo to be subtracted from the players inventory
   */
  public void useAmmo(Ammo ammunition) {
      this.ammo.useRed(ammunition.getRed());
      this.ammo.useBlue(ammunition.getBlue());
      this.ammo.useYellow(ammunition.getYellow());
  }

  /**
   * @param weapon is the weapon to be discarded from the inventory : a weapon can be placed down on a spawn point
   */
  public void discardWeapon(Weapon weapon) {
      this.weapons.remove(weapon);
  }


  public static class InventoryFullException extends RuntimeException
  {
    @Override
    public String toString() {
      return "Inventory is full.";
    }
  }
}
