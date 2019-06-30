package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.*;
import it.polimi.se2019.model.map.Square;
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
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TestFreneticStates {
    @Mock
    PlayerViewOnServer client;



    @Test
    public void grabStuffFirstFreneticTest(){
        try {
            GameBoard gameBoard = new GameBoard(0);
            Decks decksReference = gameBoard.getDecks();
            Player player = new Player("playerName", "playerCharacter", gameBoard);
            Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            PlayerController playerController = new PlayerController(gameBoardController, player, client);



            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);

            List<Square> twoMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 2);
            List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
            for(Square q : twoMovesAway){
                twoMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }

            Mockito.when(client.chooseTargetSquare(twoMovesAwayCoordinates)).thenReturn(twoMovesAwayCoordinates.get(0));
            Mockito.when(client.chooseItemToGrab()).thenReturn(0);

            playerController.setState(3);
            twoMovesAway.get(0).refill();
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
                    assert(!player.getInventory().getPowerUps().isEmpty());
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
        catch(UnknownMapTypeException f){
            fail("could not create map");
        }
    }
    @Test
    public void grabStuffSecondFreneticTest(){
        try {
            GameBoard gameBoard = new GameBoard(0);
            Decks decksReference = gameBoard.getDecks();
            Player player = new Player("playerName", "playerCharacter", gameBoard);
            Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            PlayerController playerController = new PlayerController(gameBoardController, player, client);




            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);

            List<Square> threeMovesAway = gameBoard.getMap().getReachableSquares(player.getPosition(), 3);
            List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
            for(Square q : threeMovesAway){
                threeMovesAwayCoordinates.add(gameBoard.getMap().getSquareCoordinates(q));
            }

            Mockito.when(client.chooseTargetSquare(threeMovesAwayCoordinates)).thenReturn(threeMovesAwayCoordinates.get(0));
            Mockito.when(client.chooseItemToGrab()).thenReturn(0);
            PowerUpCard powerUpCard = new PowerUpCard(new Ammo(1, 0, 0), "NewtonController");
            //Mockito.when(decksReference.drawPowerUp()).thenReturn(powerUpCard);

            playerController.setState(4);
            Grabbable item = threeMovesAway.get(0).getItem().get(0);
            playerController.getState().grabStuff();
            if(item instanceof Weapon){
                Weapon weapon = (Weapon)item;
                assert(player.getInventory().getWeapons().contains(weapon));
            }
            else{
                AmmoTile ammoTile = (AmmoTile)item;
                Ammo ammo = ammoTile.getAmmo();
                if(ammoTile.getPowerUp()){
                    assert(player.getInventory().getPowerUps().contains(powerUpCard));
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
        catch(UnknownMapTypeException f){
            fail("could not create map");
        }
    }

    @Test
    public void shootPeopleTest(){

    }
}
