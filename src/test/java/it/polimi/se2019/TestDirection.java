package it.polimi.se2019;

import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.Square;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class TestDirection {

  @Test
  public void TestGetSquare() {
    ArrayList<Direction> dirs = new ArrayList<>();
    Square square = new AmmoSquare("room1", dirs);
    Direction dir = new Direction(square, true);

    Square gotSquare = dir.getSquare();

    if (gotSquare.equals(square)){
      // Test Passed
    }
    else {
      fail("Wrong square returned");
    }
  }

  @Test
  public void TestIsBlockedTrue() {
    ArrayList<Direction> dirs = new ArrayList<>();
    Square square = new AmmoSquare("room1", dirs);
    Direction dir = new Direction(square, true);

    if (dir.isBlocked() == true){
      // Test Passed
    }
    else {
      fail("Wrong Blocking returned");
    }
  }

  @Test
  public void TestIsBlockedFalse() {
    ArrayList<Direction> dirs = new ArrayList<>();
    Square square = new AmmoSquare("room1", dirs);
    Direction dir = new Direction(square, false);

    if (dir.isBlocked() == false){
      // Test Passed
    }
    else {
      fail("Wrong Blocking returned");
    }
  }
}