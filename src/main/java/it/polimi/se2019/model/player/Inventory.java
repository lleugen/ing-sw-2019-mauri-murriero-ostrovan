package java.it.polimi.se2019.model.player;

import java.it.polimi.se2019.model.grabbable.Ammo;
import java.it.polimi.se2019.model.grabbable.Grabbable;
import java.it.polimi.se2019.model.grabbable.PowerUpCard;
import java.it.polimi.se2019.model.grabbable.Weapon;
import java.it.polimi.se2019.model.deck.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Inventory contains the inventory of each player.
 * Every Ammo, WeaponController or power-up he has, is in this class.
 * In addition, methods for easily add elements to the inventory are exposed,
 * using the same interface Grabbable
 */
public class Inventory {
  public Inventory(Decks decks) {
      ammo = new Ammo();//il costruttore di ammo deve accettare 3 parametri, ma mettendoli da errore
      weapons = new ArrayList<Weapon>();
      powerUps = new ArrayList<PowerUpCard>();
      this.addToInventory(decks.drawPowerUp());
  }

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
    Ammo ammoCopy = null;
    /*clone ammo*/
    return ammoCopy;
  }

  /**
   * @return a copy of the weapons in the player's inventory
   */
  public ArrayList<Weapon> getWeapons() {
    ArrayList<Weapon> weaponsCopy = null;
    Collections.copy(weaponsCopy, weapons);
    return weaponsCopy;
  }

  /**
   * @return a copy of the powerUps in the player's inventory
   */
  public ArrayList<PowerUpCard> getPowerUps() {
    ArrayList<PowerUpCard> powerUpsCopy = null;
    Collections.copy(powerUpsCopy, powerUps);
    return powerUpsCopy;
  }

  /**
   * Add a new element to the inventory
   * The right function for each type of item is automatically chosen
   *
   * @param item Element to add to the inventory
   */
  public void addToInventory(Grabbable item) {


  }

  /**
   * @param powerUpCard is the card to be added to the inventory
   */
  private void addPowerUp(PowerUpCard powerUpCard) {
    powerUps.add(powerUpCard);
  }

  /**
   * @param weapon is the weapon to be added to the player's inventory
   */
  private void addWeapon(Weapon weapon) {
    weapons.add(weapon);
  }

  /**
   * @param ammunition is the ammo to be added to the player's ammo box
   */
  private void addAmmo(Ammo ammunition) {
    /*bisogna definire i metodi addColour di ammo*/
    ammo.addRed(ammunition.getRed());
    ammo.addBlue(ammunition.getBlue());
    ammo.addYellow(ammunition.getYellow());
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
      /*bisogna definire i metodi useColour di ammo*/
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
}
