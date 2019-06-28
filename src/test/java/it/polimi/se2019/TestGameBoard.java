package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import org.junit.Test;

public class TestGameBoard {
    @Test
    public void testDeckCreation() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        assert(gameBoard.getDecks() != null);
        Map map = gameBoard.getMap();
        assert(map.getGameBoard().getDecks() != null);
    }
}
