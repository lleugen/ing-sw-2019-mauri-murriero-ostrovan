package it.polimi.se2019;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
/**
 * @author Eugenio Ostrovan
 */
@RunWith(MockitoJUnitRunner.class)
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
            assertTrue(gameBoardController.getPlayerControllers().contains(playerController1));
            assertTrue(gameBoardController.getPlayerControllers().size() == 1);
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
            assertTrue(result.equals(player1));


        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }




    @Test
    public void testGameLoop(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            Player player = new Player("player", "character", gameBoard);
            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            List<Player> players = new ArrayList<>();
            players.add(player);
            gameBoard.addPlayers(players);
            PlayerController playerController = new PlayerController(gameBoardController, player, client);
            List<PlayerController> playerControllers = new ArrayList<>();
            playerControllers.add(playerController);
            gameBoardController.addPlayerControllers(playerControllers);

            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(0);
            coordinates.add(0);

            try{
                Mockito.doNothing().when(client).sendMapInfo(any());
                Mockito.when(client.chooseAction(any())).thenReturn("run");
                Mockito.when(client.chooseTargetSquare(any())).thenReturn(coordinates);
            }
            catch(UserTimeoutException f){
                fail("exception UserTimeoutException");
            }
            catch(RemoteException g){
                fail("exception RemoteException");
            }


            gameBoardController.playTurns();
        }
        catch(UnknownMapTypeException e){
            fail("could not build game board");
        }
    }
}
