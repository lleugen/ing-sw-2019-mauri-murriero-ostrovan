package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.PlayerBoard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPlayerBoard {
    @Test
    public void testTurnAround()
    {
        PlayerBoard board = new PlayerBoard();
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockName","mockChar", gameBoard);
        board.setMark(player);
        board.turnAround();
        List<Integer> expectedDeathValue = new ArrayList<Integer>();
        expectedDeathValue = Arrays.asList(2,1,1,1,1,1);
        assert((board.getMarksAssigned().contains(player)) & (board.getDeathValue().equals(expectedDeathValue)));
    }
}
