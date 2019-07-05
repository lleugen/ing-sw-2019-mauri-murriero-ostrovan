package it.polimi.se2019;

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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
/**
 * @author Eugenio Ostrovan
 */
@RunWith(MockitoJUnitRunner.class)
public class TestWeaponController {
    @Mock
    PlayerViewOnServer client;

//    @Test
//    public void testUseTagBackGrenade(){
//        try{
//            GameBoard gameBoard = new GameBoard(0);
//            GameBoardController gameBoardController = new GameBoardController(gameBoard);
//            Player player1 = new Player("player1", "character1", gameBoard);
//            Player player2 = new Player("player2", "character2", gameBoard);
//            PlayerController playerController1 = new PlayerController(gameBoardController, player1, client);
//            try{
//                gameBoardController.getWeaponControllers().get(0).findTargets(player1);
//            }
//            catch(UserTimeoutException f){
//                fail("user timeout exception");
//            }
//        }
//        catch(UnknownMapTypeException e){
//            fail("could not create game board");
//        }
//    }

    @Test
    public void testIdentifyClient(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Player player = new Player("player", "character", gameBoard);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            PlayerController playerController = new PlayerController(gameBoardController, player, client);
            List<PlayerController> playerControllers = new ArrayList<>();
            playerControllers.add(playerController);
            gameBoardController.addPlayerControllers(playerControllers);
            Mockito.when(client.getName()).thenReturn("player");
            for(int i = 0; i<gameBoardController.getWeaponControllers().size(); i++){
                PlayerViewOnServer result = gameBoardController.getWeaponControllers().get(i).identifyClient(player);
                assert result.equals(client);
            }

        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }

    }

}
