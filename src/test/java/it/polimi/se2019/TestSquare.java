package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
/**
 * @author Eugenio Ostrovan
 */
public class TestSquare {
    @Test
    public void testGetDecks() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Square square = gameBoard.getMap().getMapSquares()[0][0];
        Decks decks;
        decks = square.getDecks();
        assertTrue(decks != null);
    }

    @Test
    public void testSetAdjacencies(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Square square = gameBoard.getMap().getMapSquares()[0][0];
            List<Direction> adjacencies = new ArrayList<>();
            adjacencies.add(new Direction(null, true));
            adjacencies.add(new Direction(gameBoard.getMap().getMapSquares()[0][1], false));
            adjacencies.add(new Direction(gameBoard.getMap().getMapSquares()[1][0], false));
            adjacencies.add(new Direction(null, true));
            square.setAdjacencies(adjacencies);
            for(int i = 0; i<4; i++){
                assertTrue(square.getAdjacencies().get(i) != null);
            }
        }
        catch(UnknownMapTypeException e){
            fail("could not generate square");
        }
    }
}
