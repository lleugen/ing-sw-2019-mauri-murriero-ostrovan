package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.PlayerBoard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
/**
 * @author Eugenio Ostrovan
 */
public class TestPlayerBoard {
    @Test
    public void testCreation() throws UnknownMapTypeException {
        PlayerBoard playerBoard = new PlayerBoard();
        assertTrue(!playerBoard.getIfIsFrenzy());
        assertTrue(playerBoard.getDeathValue().size() == 5);
        assertTrue(playerBoard.getDeathValue().get(0) == 8);
        assertTrue(playerBoard.getDeathValue().get(1) == 6);
        assertTrue(playerBoard.getDeathValue().get(2) == 4);
        assertTrue(playerBoard.getDeathValue().get(3) == 2);
        assertTrue(playerBoard.getDeathValue().get(4) == 1);
        assertTrue(playerBoard.getMarksAssigned().isEmpty());
        assertTrue(playerBoard.getDamageReceived().isEmpty());
    }

    @Test
    public void testMarkAssignment() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player defender1 = new Player("defender1", "character1", gameBoard);
        Player attacker1 = new Player("attacker1", "character2", gameBoard);
        defender1.getBoard().setMark(attacker1);
        assertTrue(defender1.getBoard().getMarksAssigned().contains(attacker1));
        assertTrue(defender1.getBoard().getMarksAssigned().size() == 1);
    }

    @Test
    public void testDamageAssignment() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player defender1 = new Player("defender1", "character1", gameBoard);
        Player attacker1 = new Player("attacker1", "character2", gameBoard);
        defender1.takeDamage(attacker1, 1);
        assertTrue(defender1.getBoard().getDamageReceived().contains(attacker1));
        assertTrue(defender1.getBoard().getDamageReceived().size() == 1);
    }

    @Test
    public void testGetMostWorthyPLayer() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player1 = new Player("player1", "character", gameBoard);
        Player player2 = new Player("player2", "character", gameBoard);
        Player player3 = new Player("player3", "character", gameBoard);
        player1.takeDamage(player2, 2);
        player1.takeDamage(player3, 2);
        Player mostWorthy = player1.getBoard().getMostWorthyPlayer();
        assertTrue(mostWorthy.equals(player2));
    }

    @Test
    public void testTurnAround() throws UnknownMapTypeException
    {
        PlayerBoard board = new PlayerBoard();
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockName","mockChar", gameBoard);
        board.setMark(player);
        board.turnAround();
        List<Integer> expectedDeathValue = new ArrayList<Integer>();
        expectedDeathValue = Arrays.asList(2,1,1,1,1,1);
        assertTrue((board.getMarksAssigned().contains(player)) & (board.getDeathValue().equals(expectedDeathValue)));
    }
}
