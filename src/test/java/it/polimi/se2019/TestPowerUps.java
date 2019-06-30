//package it.polimi.se2019;
//
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.controller.PlayerController;
//import it.polimi.se2019.model.GameBoard;
//import it.polimi.se2019.model.map.UnknownMapTypeException;
//import it.polimi.se2019.model.player.Player;
//import it.polimi.se2019.view.player.PlayerViewOnServer;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TestPowerUps {
//    @Mock
//    PlayerViewOnServer client;
//
//    GameBoard gameBoard = new GameBoard(0);
//    Player player = new Player("playerName", "playerCharacter", gameBoard);
//    Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
//    GameBoardController gameBoardController = new GameBoardController(gameBoard);
//    PlayerController playerController = new PlayerController(gameBoardController, player, client);
//
//  public TestPowerUps() throws UnknownMapTypeException {}
//
//  @Test
//    public void newtonTest(){
//
//    }
//}
