package java.it.polimi.se2019.model.grabbable;

/**
 * PowerUpCard represent a PowerUp, AKA an object that modifies the normal
 * flow of in-game operations
 */
public class PowerUpCard extends Grabbable {
  /**
   * Generates a new PowerUpCard
   *
   * @param eq   The value of this card in Ammos
   * @param desc The description of the PowerUp
   */
  public PowerUpCard(Ammo eq, String desc) {
    super();
    this.ammoEquivalent = eq;
    this.description = desc;
  }

  /**
   * A power-up can be discarded in place of an Ammo.
   * This variable represents the equivalence of the power-up in Ammos
   */
  private Ammo ammoEquivalent;

  /**
   * The description of the power-up
   */
  private String description;
  
  /**
   * @return The value of the power-up in Ammos
   */
  public Ammo getAmmoEquivalent() {
    return this.ammoEquivalent;
  }

  /**
   * @return The description of the power-up
   */
  public String getDescription() {
    return this.description;
  }
}

