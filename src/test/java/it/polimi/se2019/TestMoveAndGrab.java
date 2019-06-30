package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.controller.player_state_controller.Adrenaline1StateController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.*;
import it.polimi.se2019.model.map.*;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TestMoveAndGrab {
    @Mock
    PlayerViewOnServer client;
    @Mock
    Decks decksReference;

    @Test
    public void runAroundTest() throws UnknownMapTypeException {
        try {
            GameBoard gameBoard = new GameBoard(0);
            Player player = new Player("playerName", "playerCharacter", gameBoard);
            Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
            List<Player> players = new ArrayList<>();
            players.add(player);
            players.add(shooter);
            gameBoard.addPlayers(players);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            PlayerController playerController = new PlayerController(gameBoardController, player, client);


            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            List<Square> threeMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 3);
            List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
            for(Square q : threeMovesAway){
                threeMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            Mockito.when(client.chooseTargetSquare(any())).thenReturn(threeMovesAwayCoordinates.get(0));

            //test run around in state adrenaline 1
            playerController.setState(1);
            playerController.getState().runAround();
            assert(player.getPosition().equals(threeMovesAway.get(0)));

            //test run around in state adrenaline 2
            playerController.setState(2);
            threeMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 3);
            threeMovesAwayCoordinates.clear();
            for(Square q : threeMovesAway){

                threeMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            playerController.getState().runAround();
            assert(player.getPosition().equals(threeMovesAway.get(0)));

            //test run around in state normal
            playerController.setState(0);
            threeMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 3);
            threeMovesAwayCoordinates.clear();
            for(Square q : threeMovesAway){

                threeMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            playerController.getState().runAround();
            assert(player.getPosition().equals(threeMovesAway.get(0)));

            //test run around in state frenetic 1
            playerController.setState(3);

            List<Square> fourMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 4);
            assert(!fourMovesAway.isEmpty());
            List<List<Integer>> fourMovesAwayCoordinates = new ArrayList<>();
            for(Square q : fourMovesAway){
                fourMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            assert(!fourMovesAwayCoordinates.isEmpty());
            //Mockito.when(client.chooseTargetSquare(fourMovesAwayCoordinates)).thenReturn(fourMovesAwayCoordinates
            // .get(0));
            playerController.getState().runAround();
            assert(player.getPosition().equals(fourMovesAway.get(0)));
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
    @Test
    public void grabStuffAdrenaline1Test() throws UnknownMapTypeException{
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("playerName", "playerCharacter", gameBoard);
        Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);


        try {
            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            gameBoard.getMap().getMapSquares()[0][0].refill();
            List<Square> twoMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 2);
            List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
            for(Square q : twoMovesAway){
                twoMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            Mockito.when(client.chooseTargetSquare(twoMovesAwayCoordinates)).thenReturn(twoMovesAwayCoordinates.get(0));
            Mockito.when(client.chooseItemToGrab()).thenReturn(0);
            PowerUpCard powerUpCard = new PowerUpCard(new Ammo(1, 0, 0), "NewtonController");
            //Mockito.when(decksReference.drawPowerUp()).thenReturn(powerUpCard);
            playerController.setState(1);
            Grabbable item = twoMovesAway.get(0).getItem().get(0);
            playerController.getState().grabStuff();
            if(item instanceof Weapon){
                Weapon weapon = (Weapon)item;
                assert(player.getInventory().getWeapons().contains(weapon));
            }
            else{
                AmmoTile ammoTile = (AmmoTile)item;
                Ammo ammo = ammoTile.getAmmo();
                if(ammoTile.getPowerUp()){
                    assert(player.getInventory().getPowerUps().size() == 2);
                }
                assert(player.getInventory().getAmmo().getBlue().
                        equals(1 + ammo.getBlue()));
                assert(player.getInventory().getAmmo().getRed().
                        equals(1 + ammo.getRed()));
                assert(player.getInventory().getAmmo().getYellow().
                        equals(1 + ammo.getYellow()));
            }
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }

    @Test
    public void grabStuffAdrenaline2Test() throws UnknownMapTypeException{
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("playerName", "playerCharacter", gameBoard);
        Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);


        try {
            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            gameBoard.getMap().getMapSquares()[0][0].refill();
            List<Square> twoMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 2);
            List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
            for(Square q : twoMovesAway){
                twoMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            Mockito.when(client.chooseTargetSquare(twoMovesAwayCoordinates)).thenReturn(twoMovesAwayCoordinates.get(0));
            Mockito.when(client.chooseItemToGrab()).thenReturn(0);
            PowerUpCard powerUpCard = new PowerUpCard(new Ammo(1, 0, 0), "NewtonController");
            //Mockito.when(decksReference.drawPowerUp()).thenReturn(powerUpCard);
            playerController.setState(2);
            Grabbable item = twoMovesAway.get(0).getItem().get(0);
            playerController.getState().grabStuff();
            if(item instanceof Weapon){
                Weapon weapon = (Weapon)item;
                assert(player.getInventory().getWeapons().contains(weapon));
            }
            else{
                AmmoTile ammoTile = (AmmoTile)item;
                Ammo ammo = ammoTile.getAmmo();
                if(ammoTile.getPowerUp()){
                    assert(player.getInventory().getPowerUps().size() == 2);
                }
                assert(player.getInventory().getAmmo().getBlue().
                        equals(1 + ammo.getBlue()));
                assert(player.getInventory().getAmmo().getRed().
                        equals(1 + ammo.getRed()));
                assert(player.getInventory().getAmmo().getYellow().
                        equals(1 + ammo.getYellow()));
            }
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }

    @Test
    public void grabStuffNormalTest() throws UnknownMapTypeException{
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("playerName", "playerCharacter", gameBoard);
        Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);


        try {
            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            gameBoard.getMap().getMapSquares()[0][0].refill();
            List<Square> oneMoveAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 2);
            List<List<Integer>> oneMoveAwayCoordinates = new ArrayList<>();
            for(Square q : oneMoveAway){
                oneMoveAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }
            //Mockito.when(client.chooseTargetSquare(oneMoveAwayCoordinates)).thenReturn(oneMoveAwayCoordinates.get(0));
            Mockito.when(client.chooseItemToGrab()).thenReturn(0);
            Mockito.when(client.chooseDirection(any())).thenReturn(-1);
            PowerUpCard powerUpCard = new PowerUpCard(new Ammo(1, 0, 0), "NewtonController");
            //Mockito.when(decksReference.drawPowerUp()).thenReturn(powerUpCard);
            playerController.setState(0);
            assert(playerController.getState() != null);
            Grabbable item = oneMoveAway.get(0).getItem().get(0);
            playerController.getState().grabStuff();
            if(item instanceof Weapon){
                Weapon weapon = (Weapon)item;
                assert(player.getInventory().getWeapons().contains(weapon));
            }
            else{
                AmmoTile ammoTile = (AmmoTile)item;
                Ammo ammo = ammoTile.getAmmo();
                if(ammoTile.getPowerUp()){
                    assert(player.getInventory().getPowerUps().size() == 2);
                }
                assert(player.getInventory().getAmmo().getBlue().
                        equals(1 + ammo.getBlue()));
                assert(player.getInventory().getAmmo().getRed().
                        equals(1 + ammo.getRed()));
                assert(player.getInventory().getAmmo().getYellow().
                        equals(1 + ammo.getYellow()));
            }
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
}
