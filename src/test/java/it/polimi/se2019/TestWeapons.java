package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TestWeapons {
    @Mock
    PlayerViewOnServer client;

    @Test
    public void testFindTargets(){
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
            List<Player> targets = new ArrayList<>();
            Mockito.when(client.getName()).thenReturn("player1");
            Mockito.when(client.chooseTargets(any())).thenReturn("player1");
            for(WeaponController w : gameBoardController.getWeaponControllers()){
                targets = w.findTargets(player1);
            }
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
        catch (UserTimeoutException f){
            fail("user time out exception");
        }
    }
}
