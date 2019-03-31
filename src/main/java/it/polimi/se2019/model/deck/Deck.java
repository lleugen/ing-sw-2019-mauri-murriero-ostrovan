package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.List;

/**
 *  An implementation for a deck of cards.
 *  Each card is represented by an ArrayList of {@link Grabbable}, and on
 *  each deck you can draw a card (a card is randomly chosen from the list of
 *  available cards in the deck) or discard a card (a card is inserted in the
 *  discarded area of the deck).
 *  When drawing a card from an empty available deck, the discarded deck is
 *  shuffled and placed as the available deck
 */
public class Deck<G extends Grabbable> {
    /**
     *  Init a new deck
     *
     *  @param elements The deck's content
     */
    public Deck(List<List<G>> elements) {

    }

    /**
     *  Contains the available elements of the deck
     */
    private List<List<G>> available;

    /**
     *  Contains the discarded elements of the deck
     */
    private List<List<G>> discarded;

    /**
     *  Draw a new element from the deck.
     *  If no element is available, the discarded zone is shuffled and placed as
     *  a new available list of elements.
     *
     *  @return An element from the available deck
     *
     *  @throws EmptyDeckException  if both available list and discarded list
     *  are empty
     */
    public List<G> draw() throws EmptyDeckException {
        /*
         *  TODO Remember to check if both the available deck and the discarded
         *  deck are empty, as drawing from a fully empty deck may result in
         *  errors, if the implementation is bad
         */
    }

    /**
     *  Discard an element.
     *  The element is inserted in the discarded list, which is shuffled when
     *  the available list becomes empty.
     *
     *  @param discarded The element to discard to the deck discarded zone
     */
    public void discard(List<G> discarded) {

    }

    /**
     *  Shuffle the AVAILABLE list
     */
    private void shuffle() {

    }

    /**
     *  Swap the content of the discarded list with the content of the
     *  available list.
     */
    private void swap() {

    }

    /**
     *  Thrown when attempting to draw an element from a completely empty deck
     *  (AKA both available list empty and discard list empty)
     */
    private class EmptyDeckException extends Exception {
        @Override
        public String toString() {
            return "You cannot draw an element from a completely empty deck";
        }
    }
}