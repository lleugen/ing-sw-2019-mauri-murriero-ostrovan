package it.polimi.se2019.model.player;

import it.polimi.se2019.model.grabbable.*;
import it.polimi.se2019.model.deck.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Inventory contains the inventory of each player.
 * Every Ammo, WeaponController or power-up he has, is in this class.
 * In addition, methods for easily add elements to the inventory are exposed,
 * using the same interface Grabbable
 *
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public class Inventory {
  public Inventory(Player p, Decks decks) {
    owner = p;
    decksReference = decks;
    ammo = new Ammo(1, 1, 1);
    weapons = new ArrayList<>();
    powerUps = new ArrayList<>();
    addPowerUpToInventory(decks.drawPowerUp());
  }

  /**
   *
   * @return the reference to the decks
   */
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
   * The owner of this inventory
   */
  private Player owner;

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
    return ammo;
  }

  /**
   * @return a copy of the weapons in the player's inventory
   */
  public List<Weapon> getWeapons() {
    return new LinkedList<>(this.weapons);
  }

  /**
   * @return a copy of the powerUps in the player's inventory
   */
  public List<PowerUpCard> getPowerUps() {
    return new LinkedList<>(this.powerUps);
  }

  /**
   * Add a new weapon to the inventory
   * @param item the weapon to add to the inventory
   */
  public boolean addWeaponToInventory(Weapon item){
    boolean result = false;
    if(weapons.size()<3){
      result = useAmmo(item.getGrabCost());
      if(result){
        weapons.add(item);
        item.setOwner(owner);
        return true;
      }
    }
    return false;
  }

  /**
   * Add a power up card to the inventory
   * @param item the power up card to add to the inventory
   */
  public void addPowerUpToInventory(PowerUpCard item){
    if(powerUps.size()<3){
      powerUps.add(item);
    }
  }

  /**
   * Add ammo tile to inventory
   * @param item the ammo tile to add to the inventory
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
  public boolean useAmmo(Ammo ammunition) {
    boolean red = false;
    boolean blue = false;
    boolean yellow = false;
    red = this.ammo.useRed(ammunition.getRed());
    blue = this.ammo.useBlue(ammunition.getBlue());
    yellow = this.ammo.useYellow(ammunition.getYellow());
    if(red && blue && yellow){
      return true;
    }
    else{
      if(red){
        this.ammo.addRed(ammunition.getRed());
      }
      if(blue){
        this.ammo.addRed(ammunition.getBlue());
      }
      if(yellow){
        this.ammo.addRed(ammunition.getYellow());
      }
      return false;
    }
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
