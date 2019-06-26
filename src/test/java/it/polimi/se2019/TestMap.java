package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestMap {

  /**
   * The map is fully enclosed in the class, we cannot completely test it
   * cause the logic is private.
   * We must trust it and just check if returned data is plausible
   */
  @Test
  public void TestGetRoot() {
    GameBoard gameBoard = new GameBoard(0);
    Square returnedSquare;

    try {
      returnedSquare = gameBoard.getMap().getRoot();
    }
    catch (Exception e){
      fail("something bad happened");
    }
  }


}