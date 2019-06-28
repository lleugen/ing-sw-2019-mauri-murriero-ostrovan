package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jws.soap.SOAPBinding;

import static junit.framework.TestCase.fail;

public class TestAmmoSquare {
    @Test
    public void checkRefill(){
        GameBoard gameBoard = new GameBoard(0);
        Map map = gameBoard.getMap();
        AmmoSquare ammoSquare = (AmmoSquare) map.getMapSquares()[0][0];
        ammoSquare.refill();
        assert(ammoSquare.getItem() != null);
        assert(ammoSquare.getItem().get(0) != null);
    }


    @Test
    public void testRefillAtCreation(){
        GameBoard gameBoard = new GameBoard(0);
        AmmoSquare ammoSquare = (AmmoSquare) gameBoard.getMap().getMapSquares()[0][0];
        assert(ammoSquare.getItem() != null);
        //assert(ammoSquare.getAmmos() != null);
        assert(ammoSquare.getItem().get(0) != null);
    }




    @Mock
    PlayerViewOnServer client;
    @Test
    public void testGrabItem(){
        GameBoard gameBoard = new GameBoard(0);
        AmmoSquare ammoSquare = (AmmoSquare) gameBoard.getMap().getMapSquares()[0][0];
        AmmoTile item = (AmmoTile)ammoSquare.getItem().get(0);
        Player player = new Player("player", "character", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);
        player.moveToSquare(ammoSquare);
        try{
            Mockito.when(client.chooseItemToGrab()).thenReturn(0);
            playerController.getState().grab();
        }
        catch (UserTimeoutException e){
            fail();
        }
        assert(ammoSquare.getItem() == null);
        assert(player.getInventory().getAmmo().getRed() == 1 + item.getAmmo().getRed());
        assert(player.getInventory().getAmmo().getBlue() == 1 + item.getAmmo().getBlue());
        assert(player.getInventory().getAmmo().getYellow() == 1 + item.getAmmo().getYellow());
        if(item.getPowerUp()){
            assert(player.getInventory().getPowerUps().size() == 2);
        }
        else{
            assert(player.getInventory().getPowerUps().size() == 1);
        }

    }

}
