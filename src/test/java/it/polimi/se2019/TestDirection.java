package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestDirection {

//  @Test
//  public void TestGetSquare() {
//    ArrayList<Direction> dirs = new ArrayList<>();
//    Square square = new AmmoSquare(null,"room1", dirs);
//    Direction dir = new Direction(square, true);
//
//    Square gotSquare = dir.getSquare();
//
//    if (gotSquare.equals(square)){
//      // Test Passed
//    }
//    else {
//      fail("Wrong square returned");
//    }
//  }

//  @Test
//  public void TestIsBlockedTrue() {
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    List<Direction> dirs = map.getMapSquares()[0][0].getAdjacencies();
//    assert(dirs.get(0).isBlocked());
//    assert(!dirs.get(1).isBlocked());
//    assert(!dirs.get(2).isBlocked());
//    assert(dirs.get(3).isBlocked());
//  }
//
//  @Test
//  public void TestIsBlockedFalse() {
//    ArrayList<Direction> dirs = new ArrayList<>();
//    Square square = new AmmoSquare(null,"room1", dirs);
//    Direction dir = new Direction(square, false);
//
//    if (dir.isBlocked() == false){
//      // Test Passed
//    }
//    else {
//      fail("Wrong Blocking returned");
//    }
//  }
}