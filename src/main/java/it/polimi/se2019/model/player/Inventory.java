package it.polimi.se2019.model.player;

import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;

import java.util.List;

/**
 * Inventory contains the inventory of each player.
 * Every Ammo, Weapon or power-up he has, is in this class.
 * In addition, methods for easily add elements to the inventory are exposed,
 * using the same interface Grabbable
 */
public class Inventory {
    public Inventory() {
    }

    /**
     * 
     */
    private List<Ammo> ammo;

    /**
     * 
     */
    private List<Weapon> weapons;

    /**
     * 
     */
    private List<PowerUpCard> powerUps;

    /**
     * @return
     */
    public List<Ammo> getAmmo() {
    }

    /**
     * @return
     */
    public List<Weapon> getWeapons() {
    }

    /**
     * @return
     */
    public List<PowerUpCard> getPowerUps() {
    }

    /**
     * Add a new element to the inventory
     * The right function for each type of item is automatically chosen
     *
     * @param items Elements to add to the inventory
     */
    public void add(List<Grabbable> items) {
    }

    /**
     * @param el
     */
    private void addItem(PowerUpCard el) {
    }

    /**
     * @param el
     */
    private void addItem(Weapon el) {
    }

    /**
     * @param el
     */
    private void addItem(Ammo el) {
    }

    /**
     * @param powerUp
     */
    public void discardPowerUp(PowerUpCard powerUp) {
    }

    /**
     * @param ammoList
     */
    public void useAmmo(List<Ammo> ammoList) {
    }

    /**
     * @param weapon
     */
    public void discardWeapon(Weapon weapon) {
    }

}