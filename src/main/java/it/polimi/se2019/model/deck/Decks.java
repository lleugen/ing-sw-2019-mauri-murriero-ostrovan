package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;

import java.util.List;

/**
 * Contains the implementation of the Decks' zone on the physical game board
 */
public class Decks {
  /**
   * Init a new decks zone
   */
  public Decks() {
  }

  /**
   * Contains the deck with the weapons not already placed to the board
   */
  private Deck<Weapon> weaponDeck;

  /**
   * Contains the deck with the Ammos not alredy placed to the board,
   * and with the Ammos discarded (taken from the board and added to the
   * player inventory)
   */
  private Deck<Ammo> ammoDeck;

  /**
   * Contains the deck with the PowerUps not alredy placed to the board, and
   * the PowerUps alredy used (and so discarded)
   */
  private Deck<PowerUpCard> powerUpDeck;

  /**
   * Draw a weapon from the WeaponController Deck
   *
   * @return the drawn weapon
   */
  public List<Weapon> drawWeapon() {
  }

  /**
   * Draw an Ammo set from the Ammos Deck
   *
   * @return the drawn Ammos
   */
  public List<Ammo> drawAmmoTile() {
    /*
     *  TODO Remember to check if a PowerUp has been drawn, cause it needs
     *  to be replaced with a real powerUp Drawn from the PowerUpDeck
     */
  }

  /**
   * Draw a PowerUp from the PowerUp Deck
   *
   * @return the drawn weapon
   */
  public List<PowerUpCard> drawPowerUp() {
  }

  /**
   * Return a weapon to the deck
   *
   * @param discarded The weapon to discard
   *                  <p>
   *                  __WARN__ According to game rules, during normal operation this
   *                  should not happen
   */
  public void discardWeapon(List<Weapon> discarded) {

  }

  /**
   * Return an AmmoTile to the deck
   *
   * @param discarded The AmmoTile to discard
   */
  public void discardAmmoTile(List<Ammo> discarded) {
    /*
     *  TODO Remember to check if a PowerUp has been discarded, cause it
     *  needs to be replaced with a placeholder
     */
  }

  /**
   * Return a power-up to the deck
   *
   * @param discarded The power-up to discard
   */
  public void discardPowerUp(List<PowerUpCard> discarded) {
  }

}