package it.polimi.se2019.model.grabbable;

/**
 * Ammo tile represents an ammo tile that can be picked up from a square. It contains an ammo box and optionally a power up card
 *
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public class AmmoTile extends Grabbable{
  /**
   * The ammunition box contained in the ammo tile
   */
  private Ammo ammo;

  /**
   * The boolean has value 1 if the ammo tile contains a power up card, 0 otherwise
   */
  private boolean hasPowerUp;

  /**
   * Get the ammo contained in the ammo tile
   * @return the ammo in the ammo tile
   */
  public Ammo getAmmo() {
    return new Ammo(this.ammo.getRed(), this.ammo.getBlue(), this.ammo.getYellow());
  }

  /**
   *
   * @return whether the ammo tile contains a power up card
   */
  public boolean getPowerUp(){
    return hasPowerUp;
  }

  /**
   * Create a new ammo tile
   * @param redAmount the amount of red ammunition that the ammo tile will contain
   * @param blueAmount the amount of blue ammunition that the ammo tile will contain
   * @param yellowAmount the amount of yellow ammunition that the ammo tile will contain
   * @param containsPowerup specifies whether the ammo tile will contain a power up card
   */
  public AmmoTile(int redAmount, int blueAmount, int yellowAmount, boolean containsPowerup) {
    this.ammo = new Ammo(redAmount, blueAmount, yellowAmount);
    this.hasPowerUp = containsPowerup;
  }


}
