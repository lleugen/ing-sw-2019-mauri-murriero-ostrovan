package it.polimi.se2019.model.grabbable;

/**
 * Ammo tile represents an ammo tile that can be picked up from a square. It contains an ammo box and optionally a power up card
 */
public class AmmoTile extends Grabbable {
  /**
   * The ammunition box contained in the ammo tile
   */
  private Ammo ammo;

  /**
   * The boolean has value 1 if the ammo tile contains a power up card, 0 otherwise
   */
  private boolean hasPowerUp;

  public Ammo getAmmo(){
    return new Ammo(this.ammo.getRed(), this.ammo.getBlue(), this.ammo.getYellow());
  }

  public boolean getPowerUp(){
    boolean hasPowerUpCopy = hasPowerUp;
    return hasPowerUpCopy;
  }

  public AmmoTile(int redAmount, int blueAmount, int yellowAmount, boolean containsPowerup) {
    ammo = new Ammo(redAmount, blueAmount, yellowAmount);
    hasPowerUp = containsPowerup;
  }


}
