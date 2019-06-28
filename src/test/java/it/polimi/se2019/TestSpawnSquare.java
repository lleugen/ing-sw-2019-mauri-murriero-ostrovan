package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

@RunWith(MockitoJUnitRunner.class)
public class TestSpawnSquare {
//    @Mock
//    PlayerViewOnServer client;
//    @Test
//    public void testGrabItem(){
//        GameBoard gameBoard = new GameBoard(0);
//        SpawnSquare spawnSquare = (SpawnSquare) gameBoard.getMap().getRedSpawnPoint();
//        List<Weapon> weapons= new ArrayList<>();
//        List<Grabbable> items = new ArrayList<>();
//        items = spawnSquare.getItem();
//        for(int i = 0; i<items.size(); i++){
//            weapons.add((Weapon)items.get(i));
//        }
//        Player player = new Player("player", "character", gameBoard);
//        GameBoardController gameBoardController = new GameBoardController(gameBoard);
//        PlayerController playerController = new PlayerController(gameBoardController, player, client);
//        player.moveToSquare(spawnSquare);
//        spawnSquare.refill();
//        try{
//            Mockito.when(client.chooseItemToGrab()).thenReturn(0);
//            playerController.getState().grab();
//        }
//        catch (UserTimeoutException e){
//            fail();
//        }
//        assert(!player.getInventory().getWeapons().isEmpty());
//    }
}
