package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.KillScoreBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
/**
 * @author Eugenio Ostrovan
 */
public class TestKillScoreboard {
    @Test
    public void testScoreBoard() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Integer scores [] = {8, 6, 4, 2, 1, 1};
        KillScoreBoard killScoreBoard = gameBoard.getKillScoreBoard();
        assert(killScoreBoard != null);
        Player player1 = new Player("player1", "character1", gameBoard);
        Player player2 = new Player("player2", "character1", gameBoard);
        killScoreBoard.addKill(player1);
        killScoreBoard.resolveScoreboard();
        assertTrue(player1.getPoints() == 8);
    }
}
