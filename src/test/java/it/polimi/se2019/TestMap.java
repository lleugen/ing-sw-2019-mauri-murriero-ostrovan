package it.polimi.se2019;

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
    Map map = new Map("small");
    Square returnedSquare;

    try {
      returnedSquare = map.getRoot();
    }
    catch (Exception e){
      fail("something bad happened");
    }
  }

  @Test
  public void TestCreateMapShouldFailUnknownMapType() {
    try {
      Map map = new Map("this type is wrong");
      fail("Exception was not raised");
    }
    catch (UnknownMapTypeException e){
      // Test Passed
    }
  }
}