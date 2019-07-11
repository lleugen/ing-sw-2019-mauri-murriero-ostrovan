package it.polimi.se2019;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Eugenio Ostrovan
 */
//@RunWith(MockitoJUnitRunner.class)
    @Ignore
public class TestFindTargets {
    @Mock
    PlayerViewOnServer client;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindTargets(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Player player1 = new Player("player1", "character1", gameBoard);
            player1.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            List<Player> players = new ArrayList<>();
            players.add(player1);
            gameBoard.addPlayers(players);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            PlayerController playerController1 = new PlayerController(gameBoardController, player1, client);
            List<PlayerController> playerControllers = new ArrayList<>();
            playerControllers.add(playerController1);
            gameBoardController.addPlayerControllers(playerControllers);
            assert gameBoardController.getPlayers().contains(player1);
            List<Player> targets = new ArrayList<>();
            List<String> possibleTargetNames = new ArrayList<>();
            possibleTargetNames.add("player1");
            Mockito.when(client.getName()).thenReturn("player1");
            Mockito.when(client.chooseTargets(possibleTargetNames)).thenReturn("player1");
            Mockito.when(client.chooseFiringMode("insert 0 for basic, 1 for powered")).thenReturn(false);
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(0);
            coordinates.add(0);
            List<List<Integer>> visibleSquaresCoordinates = new ArrayList<>();
            List<Square> visibleSquares = gameBoard.getMap().getVisibleSquares(gameBoard.getMap().getMapSquares()[0][0]);
            for(Square s : visibleSquares){
                visibleSquaresCoordinates.add(gameBoard.getMap().getSquareCoordinates(s));
            }
            Mockito.when(client.chooseTargetSquare(visibleSquaresCoordinates)).thenReturn(coordinates);

            List<String> effects = new ArrayList<>();
            for(int i = 0; i<3; i++){
                effects.add("effect"+i);
            }
            Mockito.when(client.chooseIndex(effects)).thenReturn(0);

            List<String> visibleRooms = gameBoard.getMap().getVisibleSquares(player1.getPosition()).stream()
                    .map(Square::getIdRoom)
                    .filter(player1.getPosition().getIdRoom()::equals)
                    .distinct()
                    .map(Square.RoomColor::toString)
                    .collect(Collectors.toList());
            Mockito.when(client.chooseRoom(visibleRooms)).thenReturn("BLUE");

            List<Integer> possibleDirections = new ArrayList<>();
            for(int i = 0; i<3; i++){
                if(!player1.getPosition().getAdjacencies().get(i).isBlocked()){
                    possibleDirections.add(i);
                }
            }
            Mockito.when(client.chooseDirection(possibleDirections)).thenReturn(1);


            List<Square> targetSquares = new ArrayList<>();
            targetSquares.add(player1.getPosition().getAdjacencies().get(1).getSquare());
            targetSquares.add(targetSquares.get(0).getAdjacencies().get(1).getSquare());
            List<Player> possiblePrimaryTargets = new ArrayList<>(gameBoard.getMap().getPlayersOnSquares(
                    gameBoard.getMap().getReachableSquares(targetSquares.get(0), 0)
            ));
            List<Player> possibleSecondaryTargets = new ArrayList<>(gameBoard.getMap().getPlayersOnSquares(
                    gameBoard.getMap().getReachableSquares(targetSquares.get(1), 0)
            ));
            List<String> possiblePrimaryTargetsNames = new ArrayList<>();
            List<String> possibleSecondaryTargetsNames = new ArrayList<>();
            for(Player p : possiblePrimaryTargets){
                possiblePrimaryTargetsNames.add(p.getName());
            }
            for(Player p : possibleSecondaryTargets){
                possibleSecondaryTargetsNames.add(p.getName());
            }
            Mockito.when(client.chooseTargets(possiblePrimaryTargetsNames)).thenReturn("player1");
            Mockito.when(client.chooseTargets(possibleSecondaryTargetsNames)).thenReturn("player1");
            for(WeaponController w : gameBoardController.getWeaponControllers()){
                targets = w.findTargets(player1);
                assertTrue(targets != null);
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
