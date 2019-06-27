package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

  @Test
  public void testSquareAdjacencies(){
    GameBoard gameBoard = new GameBoard(0);
    Map map = gameBoard.getMap();
    List<Direction> currentAdjacencies = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(map.getMapSquares()[i][j] != null){
          assert(map.getMapSquares()[i][j].getAdjacencies() != null);

          assert(!map.getMapSquares()[i][j].getAdjacencies().contains(null));

          currentAdjacencies = map.getMapSquares()[i][j].getAdjacencies();
          for(int k = 0; k<currentAdjacencies.size(); k++){
            if(!currentAdjacencies.get(k).isBlocked()){
              assert(currentAdjacencies.get(k).getSquare() != null);
            }
          }

          assert(map.getMapSquares()[i][j].getDecks() != null);
        }
      }
    }
  }
}