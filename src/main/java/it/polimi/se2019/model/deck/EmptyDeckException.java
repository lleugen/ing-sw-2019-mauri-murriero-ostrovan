package it.polimi.se2019.model.deck;
/**
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public class EmptyDeckException extends RuntimeException {
  @Override
  public String toString() {
    return "Deck is completely empty!";
  }
}
