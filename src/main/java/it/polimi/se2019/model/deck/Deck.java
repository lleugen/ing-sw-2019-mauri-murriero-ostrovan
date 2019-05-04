package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.Collections;
import java.util.List;

/**
 * An implementation for a deck of cards.
 * Each card is represented by an ArrayList of {@link Grabbable}, and on
 * each deck you can draw a card (a card is randomly chosen from the list of
 * available cards in the deck) or discard a card (a card is inserted in the
 * discarded area of the deck).
 * When drawing a card from an empty available deck, the discarded deck is
 * shuffled and placed as the available deck
 */
public class Deck<G extends Grabbable> {
  /**
   * Init a new deck
   *
   * @param elements The deck's content.
   * @throws NullPointerException if elements is not a valid list
   *                              __WARN__ Elements are NOT cloned
   */
  public Deck(List<List<G>> elements) {
    this.discarded.addAll(elements);
    this.available.clear();
    this.swap();
    this.shuffle();
  }

  /**
   * Draw a new element from the deck.
   * If no element is available, the discarded zone is shuffled and placed as
   * a new available list of elements.
   *
   * @return An element from the available deck
   *
   * @throws EmptyDeckException if both available list and discarded list
   *                            are empty
   */
  public synchronized List<G> draw() {
    if (this.available.isEmpty()) {
      if (this.discarded.isEmpty()) {
        throw new EmptyDeckException();
      }
      else {
        this.swap();
        this.shuffle();
      }
    }

    return this.available.remove(0);
  }

  /**
   * Discard an element.
   * The element is inserted in the discarded list, which is shuffled when
   * the available list becomes empty.
   *
   * @param discarded The element to discard to the deck discarded zone
   */
  public synchronized void discard(List<G> discarded) {
    this.discarded.add(discarded);
  }

  /**
   * Shuffle the AVAILABLE list
   */
  private synchronized void shuffle() {
    Collections.shuffle(this.discarded);
  }

  /**
   * Swap the content of the discarded list with the content of the
   * available list.
   */
  private synchronized void swap() {
    List<List<G>> tmp;
    tmp = this.discarded;
    this.discarded = this.available;
    this.available = tmp;
  }
}

class EmptyDeckException extends RuntimeException {
  @Override
  public String toString() {
    return "Deck is completely empty!";
  }
}
