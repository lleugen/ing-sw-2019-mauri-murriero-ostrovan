package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayerStateController {
    @Mock
    PlayerViewOnServer client;
    @Mock
    PlayerViewOnServer shooterClient;
    @Mock
    Decks decksReference;
    GameBoard gameBoard = new GameBoard(0);
    Player player = new Player("playerName", "playerCharacter", gameBoard);
    Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
    GameBoardController gameBoardController = new GameBoardController(gameBoard);
    PlayerController playerController = new PlayerController(gameBoardController, player, client);
    PlayerController shooterController = new PlayerController(gameBoardController, shooter, shooterClient);

  public TestPlayerStateController() throws UnknownMapTypeException {}

  @Test
    public void moveTest() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("playerName", "playerCharacter", gameBoard);
        Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);
        PlayerController shooterController = new PlayerController(gameBoardController, shooter, shooterClient);

        try {
            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            Mockito.when(client.chooseDirection(gameBoard.getMap().getOpenDirections(player.getPosition()))).thenReturn(1);
            playerController.getState().move();
            assertTrue(player.getPosition().equals(gameBoard.getMap().getMapSquares()[0][1]));
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
    @Test
    public void spawnTest() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("playerName", "playerCharacter", gameBoard);
        Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);
        PlayerController shooterController = new PlayerController(gameBoardController, shooter, shooterClient);
        Integer colour;
        if(player.getInventory().getPowerUps().get(0).getAmmoEquivalent().getRed() == 1){
            colour = 0;
        }
        else if(player.getInventory().getPowerUps().get(0).getAmmoEquivalent().getBlue() == 1){
            colour = 1;
        }
        else{
            colour = 2;
        }

        try {
            List<String> powerUps = new ArrayList<>();
            for(PowerUpCard p : player.getInventory().getPowerUps()){
                powerUps.add(p.getDescription());
            }
//            Mockito.when(client.chooseSpawnLocation(powerUps)).thenReturn(0);
            playerController.getState().spawn();
            if(colour == 0){
                assertTrue(player.getPosition().equals(gameBoard.getMap().getRedSpawnPoint()));
            }
            else if(colour == 1){
                assertTrue(player.getPosition().equals(gameBoard.getMap().getBlueSpawnPoint()));
            }
            else{
                assertTrue(player.getPosition().equals(gameBoard.getMap().getYellowSpawnPoint()));
            }

        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
    @Test
    public void reloadTest() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("playerName", "playerCharacter", gameBoard);
        Player shooter = new Player("shooterName", "shooterCharacter", gameBoard);
        GameBoardController gameBoardController = new GameBoardController(gameBoard);
        PlayerController playerController = new PlayerController(gameBoardController, player, client);
        PlayerController shooterController = new PlayerController(gameBoardController, shooter, shooterClient);

        try {
            player.getInventory().addWeaponToInventory(new Weapon("mockWeapon",
                    new Ammo(0, 0, 0),
                    new Ammo(2, 1, 0)));
            player.getInventory().getWeapons().get(0).unload();
            assertTrue(player.getInventory().getWeapons().get(0).getOwner().equals(player));
            player.getInventory().discardPowerUp(player.getInventory().getPowerUps().get(0));
            player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(1,0,0), "mockPowerUp"));
            List<String> playersWeapons = new ArrayList<>();
            for (Weapon w : player.getInventory().getWeapons()) {
                playersWeapons.add(w.getName());
            }
            Mockito.when(client.chooseWeaponToReload(playersWeapons)).thenReturn(playersWeapons.get(0));
            List<String> powerUps = new ArrayList<>();
            for (PowerUpCard p : player.getInventory().getPowerUps()) {
                powerUps.add(p.getDescription());
            }
            List<Integer> powerUpsToUse = new ArrayList();
            powerUpsToUse.add(0);
            Mockito.when(client.choosePowerUpCardsForReload(powerUps)).thenReturn(powerUpsToUse);

            playerController.getState().reload();
            assertTrue (player.getInventory().getWeapons().get(0).isLoaded());
            assertTrue (player.getInventory().getPowerUps().isEmpty());
            assertTrue (player.getInventory().getAmmo().getRed().equals(0));
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
}
