package it.polimi.se2019;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

public class TestGameBoardController {
    @Test
    public void test(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            Player player1 = new Player("player1", "character1", gameBoard);
            List<Player> players = new ArrayList<>();
            players.add(player1);
            gameBoard.addPlayers(players);
            //gameBoardController.getPlayers()
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }

    }
}
