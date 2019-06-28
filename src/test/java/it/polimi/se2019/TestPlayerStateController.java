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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    public void moveTest(){
        try {
            player.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            Mockito.when(client.chooseDirection(gameBoard.getMap().getOpenDirections(player.getPosition()))).thenReturn(1);
            playerController.getState().move();
            assert(player.getPosition().equals(gameBoard.getMap().getMapSquares()[0][1]));
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
    @Test
    public void spawnTest(){
        try {
            PowerUpCard powerUpCard = new PowerUpCard(new Ammo(1, 0, 0), "NewtonController");
            Mockito.when(decksReference.drawPowerUp()).thenReturn(powerUpCard);
            List<String> powerUps = new ArrayList<>();
            for(PowerUpCard p : player.getInventory().getPowerUps()){
                powerUps.add(p.getDescription());
            }
            Mockito.when(client.chooseSpawnLocation(powerUps)).thenReturn(1);
            assert(player.getPosition().equals(gameBoard.getMap().getRedSpawnPoint()));
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
    @Test
    public void reloadTest(){
        try {
            player.getInventory().addWeaponToInventory(new Weapon("mockWeapon",
                    new Ammo(0, 0, 0),
                    new Ammo(1, 0, 0)));
            player.getInventory().getWeapons().get(0).unload();
            List<String> playersWeapons = new ArrayList<>();
            for (Weapon w : player.getInventory().getWeapons()) {
                playersWeapons.add(w.getName());
            }
            Mockito.when(client.chooseWeaponToReload(playersWeapons)).thenReturn(playersWeapons.get(0));
            List<String> powerUps = new ArrayList<>();
            for (PowerUpCard p : player.getInventory().getPowerUps()) {
                powerUps.add(p.getDescription());
            }
            List powerUpsToUse = new ArrayList();
            powerUpsToUse.add(0);
            Mockito.when(client.choosePowerUpCardsForReload(powerUps)).thenReturn(powerUpsToUse);

            playerController.getState().reload();
            assert (player.getInventory().getWeapons().get(0).isLoaded());
            assert (player.getInventory().getPowerUps().isEmpty());
            assert (player.getInventory().getAmmo().getRed().equals(0));
        }
        catch (UserTimeoutException e){
            fail("Network Timeout Reached");
        }
    }
}
