package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import org.junit.Test;

public class TestAmmoSquare {
    @Test
    public void checkDecks(){
        GameBoard gameBoard = new GameBoard(0);
        Map map = gameBoard.getMap();
        Square square = new AmmoSquare(map, "color", null);
        assert(square.getDecks() != null);
    }
}
