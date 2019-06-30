package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.ArrayList;
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
   * List of available cards, those that have not been drawn yet
   */
  private List<G> discarded;

  /**
   * List of the cards than have been drawn
   */
  private List<G> available;

  /**
   * Initialise a new deck
   *
   * @param elements The deck's content.
   * @throws NullPointerException if elements is not a valid list
   *                              __WARN__ Elements are NOT cloned
   */
  public Deck(List<G> elements) {
    this.discarded = new ArrayList<>();
    this.available = new ArrayList<>();

    this.discarded.addAll(elements);
    this.available.clear();
    this.shuffle();
    this.swap();
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
  public synchronized G draw() {
    if (this.available.isEmpty()) {
      if (this.discarded.isEmpty()) {
        throw new EmptyDeckException();
      }
      else {
        this.shuffle();
        this.swap();
      }
    }
    G card = available.get(0);
    available.remove(0);
    return card;
  }

  public void discard(G card){
    discarded.add(card);
  }
  /**
   * Shuffle the discarded list
   */
  private synchronized void shuffle() {
    Collections.shuffle(this.discarded);
  }

  /**
   * Swap the content of the discarded list with the content of the
   * available list.
   */
  private synchronized void swap() {
    List<G> tmp;
    tmp = this.discarded;
    this.discarded = this.available;
    this.available = tmp;
  }
}

