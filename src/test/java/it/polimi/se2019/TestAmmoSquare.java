package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import org.junit.Test;

public class TestAmmoSquare {
    @Test
    public void checkRefill(){
        GameBoard gameBoard = new GameBoard(0);
        Map map = gameBoard.getMap();
        AmmoSquare ammoSquare = (AmmoSquare) map.getMapSquares()[0][0];
        ammoSquare.refill();
        assert(ammoSquare.getItem() != null);
        assert(ammoSquare.getItem().get(0) != null);
    }

    @Test
    public void testRefillAtCreation(){
        GameBoard gameBoard = new GameBoard(0);
        AmmoSquare ammoSquare = (AmmoSquare) gameBoard.getMap().getMapSquares()[0][0];
        assert(ammoSquare.getItem() != null);
        //assert(ammoSquare.getAmmos() != null);
        assert(ammoSquare.getItem().get(0) != null);
    }
}
