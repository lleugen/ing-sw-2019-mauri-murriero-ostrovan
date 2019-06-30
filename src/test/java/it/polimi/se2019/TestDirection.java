package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestDirection {

  @Test
  public void TestGetSquare() {
    try{
        GameBoard gameBoard = new GameBoard(0);
        ArrayList<Direction> dirs = new ArrayList<>();
        Square.RoomColor color = Square.RoomColor.BLUE;
        Square square = gameBoard.getMap().getMapSquares()[0][0];
        Direction dir = new Direction(square, true);

        Square gotSquare = dir.getSquare();

        if (gotSquare.equals(square)){
            // Test Passed
        }
        else {
            fail("Wrong square returned");
        }
    }
    catch(UnknownMapTypeException e){
      fail("could not build game board");
    }

  }

  @Test
  public void TestIsBlockedTrue() {
      try{
          GameBoard gameBoard = new GameBoard(0);
          Map map = gameBoard.getMap();
          List<Direction> dirs = map.getMapSquares()[0][0].getAdjacencies();
          assertTrue(dirs.get(0).isBlocked());
          assertTrue(!dirs.get(1).isBlocked());
          assertTrue(!dirs.get(2).isBlocked());
          assertTrue(dirs.get(3).isBlocked());
      }
      catch(UnknownMapTypeException e){
          fail("could not build game board");
      }

  }

  @Test
  public void TestIsBlockedFalse() {
      try{
          GameBoard gameBoard = new GameBoard(0);
          ArrayList<Direction> dirs = new ArrayList<>();
          Square.RoomColor color = Square.RoomColor.BLUE;
          Square square = gameBoard.getMap().getMapSquares()[0][0];
          Direction dir = new Direction(square, false);

          if (dir.isBlocked() == false){
              // Test Passed
          }
          else {
              fail("Wrong Blocking returned");
          }
      }
      catch(UnknownMapTypeException e){
          fail("could not build game board");
      }


  }
}