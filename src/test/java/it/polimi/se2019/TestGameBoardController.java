package it.polimi.se2019;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

public class TestGameBoardController {
    @Mock
    PlayerViewOnServer client;

    @Test
    public void testGetPlayers(){
        try{
            GameBoard gameBoard = new GameBoard(0);

            Player player1 = new Player("player1", "character1", gameBoard);
            List<Player> players = new ArrayList<>();
            players.add(player1);
            gameBoard.addPlayers(players);

            GameBoardController gameBoardController = new GameBoardController(gameBoard);

            List<Player> result = gameBoardController.getPlayers();
            assert result.contains(player1);
            assert result.size() == 1;
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }

    @Test
    public void testGetClientsANDControllers(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            Player player1 = new Player("player1", "character1", gameBoard);
            List<Player> players = new ArrayList<>();
            players.add(player1);
            gameBoard.addPlayers(players);
            PlayerController playerController = new PlayerController(gameBoardController, player1, client);
            List<PlayerController> playerControllers = new ArrayList<>();
            playerControllers.add(playerController);
            assert playerControllers != null;
            assert !playerControllers.isEmpty();
            gameBoardController.addPlayerControllers(playerControllers);
            List<PlayerViewOnServer> result = gameBoardController.getClients();
            assert result.contains(client);
            assert result.size() == 1;

            List<PlayerController> result2 = gameBoardController.getPlayerControllers();
            assert result2.contains(playerController);
            assert result2.size() == 1;
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }



    @Test
    public void testAddPlayerControllers(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            Player player1 = new Player("player1", "character1", gameBoard);
            PlayerController playerController1 = new PlayerController(gameBoardController, player1, client);
            List<PlayerController> playerControllers = new ArrayList<>();
            playerControllers.add(playerController1);
            gameBoardController.addPlayerControllers(playerControllers);
            assert gameBoardController.getPlayerControllers().contains(playerController1);
            assert gameBoardController.getPlayerControllers().size() == 1;
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }

    @Test
    public void testIdentifyPlayer(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Player player1 = new Player("player1", "character1", gameBoard);
            List<Player> players = new ArrayList<>();
            players.add(player1);
            gameBoard.addPlayers(players);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            PlayerController playerController1 = new PlayerController(gameBoardController, player1, client);
            List<PlayerController> playerControllers = new ArrayList<>();
            playerControllers.add(playerController1);
            gameBoardController.addPlayerControllers(playerControllers);
            Player result = gameBoardController.identifyPlayer("player1");
            assert result.equals(player1);


        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }

    }
}
