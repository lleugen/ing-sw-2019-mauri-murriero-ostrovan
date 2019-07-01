//package it.polimi.se2019;
//
//import it.polimi.se2019.RMI.UserTimeoutException;
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.controller.PlayerController;
//import it.polimi.se2019.model.GameBoard;
//import it.polimi.se2019.model.map.UnknownMapTypeException;
//import it.polimi.se2019.model.player.Player;
//import it.polimi.se2019.view.player.PlayerViewOnServer;
//import org.junit.Test;
//import org.mockito.Mock;
//
//import static junit.framework.TestCase.fail;
//
//public class TestWeaponController {
//    @Mock
//    PlayerViewOnServer client;
//
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
//
//}
