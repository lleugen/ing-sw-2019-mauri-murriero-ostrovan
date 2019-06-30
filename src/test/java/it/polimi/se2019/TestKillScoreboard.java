package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.KillScoreBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestKillScoreboard {
    @Test
    public void testScoreBoard() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Integer scores [] = {8, 6, 4, 2, 1, 1};
        gameBoard.createKillScoreBoard(1, scores);
        KillScoreBoard killScoreBoard = gameBoard.getKillScoreBoard();
        assert(killScoreBoard != null);
        Player player1 = new Player("player1", "character1", gameBoard);
        Player player2 = new Player("player2", "character1", gameBoard);
        killScoreBoard.addKill(player1);
        killScoreBoard.resolveScoreboard();
        System.err.println(player1.getPoints());
        System.err.println(player2.getPoints());
        assert(player1.getPoints() == 8);
    }
}
