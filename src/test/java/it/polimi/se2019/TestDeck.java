package it.polimi.se2019;

import it.polimi.se2019.model.deck.Deck;
import it.polimi.se2019.model.deck.EmptyDeckException;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.Grabbable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class TestDeck {

  @Test
  public void testDrawShouldFailEmptyDeck() {
    List<List<Grabbable>> list = new ArrayList<>();

    Deck<Grabbable> testDeck = new Deck<Grabbable>(list);

    try {
      testDeck.draw();
      fail("Exception was not raised");
    }
    catch (EmptyDeckException exception){
      // Test Passed
    }
  }

  @Test
  public void testDrawShouldReturnItem() {
    List<List<Grabbable>> list = new ArrayList<>();
    List<Grabbable> elements = new ArrayList<>();
    Ammo element = new Ammo(1,2,3);
    List<Grabbable> returnedElement;

    elements.add(element);
    list.add(elements);

    Deck<Grabbable> testDeck = new Deck<Grabbable>(list);

    try {
      returnedElement = testDeck.draw();

      if (returnedElement.equals(element)){
        // Test Passed
      }
      else {
        fail("Draw should return exactly the same item passed in constructor");
      }
    }
    catch (EmptyDeckException exception){
      fail("Raised EmptyDeckException on a nonempty deck");
    }
  }

  /**
   * Assuming testDrawShouldReturnItem was successfully passed, otherwise
   * this test may return false results
   */
  @Test
  public void testDiscardNoShuffle() {
    List<List<Grabbable>> list = new ArrayList<>();
    List<Grabbable> els1 = new ArrayList<>();
    List<Grabbable> els2 = new ArrayList<>();
    Ammo element = new Ammo(1,2,3);

    List<Grabbable> returnedElement;

    els1.add(element);
    els2.add(element);

    list.add(els1);
    list.add(els2);

    Deck<Grabbable> testDeck = new Deck<Grabbable>(list);

    try {
      returnedElement = testDeck.draw();
      testDeck.discard(returnedElement);

      if (returnedElement.equals(els1)){
        if (testDeck.draw().equals(els2)){
          // Test Passed
        }
        else {
          fail("Deck Was Shuffled while it had still some elements available");
        }
      }
      else if (returnedElement.equals(els2)){
        if (testDeck.draw().equals(els1)){
          // Test Passed
        }
        else {
          fail("Deck Was Shuffled while it had still some elements available");
        }
      }
      else {
        fail("Something not insert in deck was returned");
      }
    }
    catch (EmptyDeckException exception){
      fail("Raised EmptyDeckException on a nonempty deck");
    }
  }

  /**
   * Assuming testDrawShouldReturnItem was successfully passed, otherwise
   * this test may return false results
   */
  @Test
  public void testDiscardWithShuffle() {
    List<List<Grabbable>> list = new ArrayList<>();
    List<Grabbable> elements = new ArrayList<>();
    Ammo element = new Ammo(1,2,3);

    List<Grabbable> returnedElement1;
    List<Grabbable> returnedElement2;

    elements.add(element);

    list.add(elements);

    Deck<Grabbable> testDeck = new Deck<Grabbable>(list);

    try {
      returnedElement1 = testDeck.draw();
      testDeck.discard(returnedElement1);
      try {
        returnedElement2 = testDeck.draw();
      } catch (EmptyDeckException exception) {
        fail("You must shuffle the deck when it is empty");
      }

      if (returnedElement2.equals(elements)) {
        // Test Passed
      } else {
        fail("Deck was not shuffled");
      }
    }
    catch(EmptyDeckException exception){
      fail("The deck is empty.");
    }
  }
}