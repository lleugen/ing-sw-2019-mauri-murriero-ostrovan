package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.map.Square;
import org.junit.Test;

public class TestSquare {
    @Test
    public void testGetDecks(){
        GameBoard gameBoard = new GameBoard(0);
        Square square = gameBoard.getMap().getMapSquares()[0][0];
        Decks decks;
        decks = square.getDecks();
        assert(decks != null);
    }
}
