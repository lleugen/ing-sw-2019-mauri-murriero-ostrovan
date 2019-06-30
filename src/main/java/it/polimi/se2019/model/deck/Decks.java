package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;

import java.util.List;

/**
 * Contains the implementation of the Decks' zone on the physical game board
 */
public class Decks {
  /**
   * Contains the deck with the weapons not already placed to the board
   */
  private final Deck<Weapon> weaponDeck;

  /**
   * Contains the deck with the Ammo not already placed to the board,
   * and with the Ammos discarded (taken from the board and added to the
   * player inventory)
   */
  private final Deck<AmmoTile> ammoDeck;

  /**
   * Contains the deck with the PowerUps not already placed to the board, and
   * the PowerUps alredy used (and so discarded)
   */
  private final Deck<PowerUpCard> powerUpDeck;

  /**
   * Init a new decks zone
   *
   * @param weapons  List of weapons to add to the game
   * @param powerUps List of powerUps to add to the game
   * @param ammoTiles     List of ammo to add to the game
   *                 <p>
   *                 __WARN__ Lists are NOT cloned
   */
  public Decks(
          List<Weapon> weapons,
          List<PowerUpCard> powerUps,
          List<AmmoTile> ammoTiles
  ) {
    this.weaponDeck = new Deck<>(weapons);
    this.ammoDeck = new Deck<>(ammoTiles);
    this.powerUpDeck = new Deck<>(powerUps);
  }

  /**
   * Draw a weapon from the Weapon Deck
   *
   * @return the drawn weapon
   * @throws EmptyDeckException if no more card are available to draw
   */
  public Weapon drawWeapon() {
    return this.weaponDeck.draw();
  }

  /**
   * Draw an Ammo set from the Ammo Deck
   *
   * @return the drawn Ammos
   * @throws EmptyDeckException if no more card are available to draw
   */
  public AmmoTile drawAmmoTile() {
    AmmoTile toReturn;
    toReturn = this.ammoDeck.draw();
    return toReturn;
  }

  /**
   * Draw a PowerUp from the PowerUp Deck
   *
   * @return the drawn weapon
   * @throws EmptyDeckException if no more card are available to draw
   */
  public PowerUpCard drawPowerUp() {
    return this.powerUpDeck.draw();
  }

  /**
   * Return a weapon to the deck
   *
   * @param discarded The weapon to discard
   *                  <p>
   *                  __WARN__ According to game rules, during normal operation this
   *                  should not happen
   */
  public void discardWeapon(Weapon discarded) {
    this.weaponDeck.discard(discarded);
  }

  /**
   * Return an AmmoTile to the deck
   *
   * @param discarded The AmmoTile to discard
   */
  public void discardAmmoTile(AmmoTile discarded) {
    this.ammoDeck.discard(discarded);
  }

  /**
   * Return a power-up to the deck
   *
   * @param discarded The power-up to discard
   */
  public void discardPowerUp(PowerUpCard discarded) {
    this.powerUpDeck.discard(discarded);
  }
}
