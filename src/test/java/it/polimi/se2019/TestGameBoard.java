package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Map;
import org.junit.Test;

public class TestGameBoard {
    @Test
    public void testDeckCreation(){
        GameBoard gameBoard = new GameBoard(0);
        assert(gameBoard.getDecks() != null);
        Map map = gameBoard.getMap();
        assert(map.getGameBoard().getDecks() != null);
    }
}
