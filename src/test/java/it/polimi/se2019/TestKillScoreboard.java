package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.KillScoreBoard;
import it.polimi.se2019.model.player.Player;
import org.junit.Test;

public class TestKillScoreboard {
    @Test
    public void testAddKill(){
        GameBoard gameBoard = new GameBoard(0);
        KillScoreBoard killScoreBoard = gameBoard.getKillScoreBoard();
        assert(killScoreBoard != null);
        Player player1 = new Player("player1", "character1", gameBoard);
        Player player2 = new Player("player2", "character2", gameBoard);
        player1.takeDamage(player2, 13);
        killScoreBoard.addKill(player1);
        killScoreBoard.resolveScoreboard();
        assert(player2.getPoints() == 8);
    }
}
