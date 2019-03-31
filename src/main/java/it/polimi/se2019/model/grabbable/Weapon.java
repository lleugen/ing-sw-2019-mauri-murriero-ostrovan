package it.polimi.se2019.model.grabbable;

import it.polimi.se2019.model.player.Player;

import java.util.List;

/**
 * Weapon represent the model for a physical Weapon card.
 * The effect of the card will be implemented in the controller of the weapon
 */
public class Weapon extends Grabbable {

    /**
     * Inits a new Weapon
     *
     * @param name Name of the Weapon
     * @param desc Description of the weapon
     * @param grabCost Cost to be paid for grabbing the weapon from a square
     * @param reloadCost Cost to pay for reloading the weapon
     *
     * __WARN__ A weapon is ALWAYS initialized as loaded
     */
    public Weapon(
        String name, String desc,
        List<Ammo> grabCost, List<Ammo> reloadCost
    ) {
        super();
        this.name = name;
        this.description = desc;
        this.grabCost = grabCost;
        this.reloadCost = reloadCost;
        this.loaded = true;
    }

    /**
     * Cost to pay for reloading the weapon
     */
    private List<Ammo> reloadCost;

    /**
     * Cost to be paid for grabbing the weapon from a square
     */
    private List<Ammo> grabCost;

    /**
     * True if the weapon is loaded and ready to use, false otherwise
     */
    private boolean loaded;

    /**
     * Current owner of the Weapon
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
     * Unload a weapon (a weapon unloaded cannot be used)
     */
    public void unload() {
        this.loaded = false;
    }

    /**
     * Reload a weapon (a weapon reloaded can be used)
     */
    public void reload() {
        this.loaded = true;
    }

    /**
     * @return The cost to be paid for grabbing the Weapon
     */
    public List<Ammo> getGrabCost() {
    }

    /**
     * @return The cost to be paid for reloading the Weapon
     */
    public List<Ammo> getReloadCost() {
    }

    /**
     * @return true if the weapon is loaded, false otherwise
     */
    public boolean isLoaded() {
        return this.loaded;
    }

    /**
     * @return The current owner of the Weapon
     */
    public Player getOwner() {
    }

    /**
     * @return The description of the weapon
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return The name of the Weapon
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
    }
}