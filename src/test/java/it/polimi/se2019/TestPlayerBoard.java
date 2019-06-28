package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.PlayerBoard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPlayerBoard {
    @Test
    public void testCreation() throws UnknownMapTypeException {
        PlayerBoard playerBoard = new PlayerBoard();
        assert(!playerBoard.getIfIsFrenzy());
        assert(playerBoard.getDeathValue().size() == 5);
        assert(playerBoard.getDeathValue().get(0) == 8);
        assert(playerBoard.getDeathValue().get(0) == 6);
        assert(playerBoard.getDeathValue().get(0) == 4);
        assert(playerBoard.getDeathValue().get(0) == 2);
        assert(playerBoard.getDeathValue().get(0) == 1);
        assert(playerBoard.getMarksAssigned().isEmpty());
        assert(playerBoard.getDamageReceived().isEmpty());
    }

    @Test
    public void testMarkAssignment() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player defender1 = new Player("defender1", "character1", gameBoard);
        Player attacker1 = new Player("attacker1", "character2", gameBoard);
        defender1.getBoard().setMark(attacker1);
        assert(defender1.getBoard().getMarksAssigned().contains(attacker1));
        assert(defender1.getBoard().getMarksAssigned().size() == 1);
    }

    @Test
    public void testDamageAssignment() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player defender1 = new Player("defender1", "character1", gameBoard);
        Player attacker1 = new Player("attacker1", "character2", gameBoard);
        defender1.takeDamage(attacker1, 1);
        assert(defender1.getBoard().getDamageReceived().contains(attacker1));
        assert(defender1.getBoard().getDamageReceived().size() == 1);
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
        assert(mostWorthy.equals(player2));
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
        assert((board.getMarksAssigned().contains(player)) & (board.getDeathValue().equals(expectedDeathValue)));
    }
}
