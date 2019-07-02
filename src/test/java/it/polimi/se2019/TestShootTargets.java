package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.controller.weapons.alternative_effects.ElectroscytheController;
import it.polimi.se2019.controller.weapons.optional_effects.RocketLauncherController;
import it.polimi.se2019.controller.weapons.optional_effects.VortexCannonController;
import it.polimi.se2019.controller.weapons.ordered_effects.ThorController;
import it.polimi.se2019.controller.weapons.simple.HeatSeekerController;
import it.polimi.se2019.controller.weapons.simple.WhisperController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class TestShootTargets {
    @Mock
    PlayerViewOnServer client;
    @Mock
    PlayerViewOnServer shooterClient;

//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }

    GameBoard gameBoard;
    GameBoardController gameBoardController;
    Player shooter;
    Player target;
    List<Player> targets;
    PlayerController clientController;
    PlayerController shooterController;
    List<PlayerController> playerControllers;
    List<Player> players;
    @Before
    public void initializeContext(){
        try{
            gameBoard = new GameBoard(0);
            gameBoardController = new GameBoardController(gameBoard);
            shooter = new Player("shooter", "character", gameBoard);
            target = new Player("target", "character", gameBoard);
            players = new ArrayList<>();
            players.add(shooter);
            players.add(target);
            gameBoard.addPlayers(players);
            targets = new ArrayList<>();
            targets.add(target);
            clientController = new PlayerController(gameBoardController, target, client);
            shooterController = new PlayerController(gameBoardController, shooter, shooterClient);
            playerControllers = new ArrayList<>();
            playerControllers.add(clientController);
            playerControllers.add(shooterController);
            gameBoardController.addPlayerControllers(playerControllers);
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }

    }
    @Test
    public void testHeatSeeker(){
        HeatSeekerController heatSeekerController = new HeatSeekerController(gameBoardController);

        try{
            heatSeekerController.shootTargets(shooter, targets);
            Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
            assertTrue(target.getBoard().getDamageReceived().size() == 3);
            assertTrue(target.getBoard().getDamageReceived().get(0).equals(shooter));
            assertTrue(target.getBoard().getDamageReceived().get(1).equals(shooter));
            assertTrue(target.getBoard().getDamageReceived().get(2).equals(shooter));
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testWhisper(){
        WhisperController whisperController = new WhisperController(gameBoardController);

        try{
            Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
            whisperController.shootTargets(shooter, targets);
            assertTrue(target.getBoard().getDamageReceived().size() == 3);
            assertTrue(target.getBoard().getDamageReceived().get(0).equals(shooter));
            assertTrue(target.getBoard().getDamageReceived().get(1).equals(shooter));
            assertTrue(target.getBoard().getDamageReceived().get(2).equals(shooter));
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testThor(){
        ThorController thorController = new ThorController(gameBoardController);

        try{
            Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
            thorController.shootTargets(shooter, targets);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
            assertTrue(target.getBoard().getDamageReceived().get(0).equals(shooter));
            assertTrue(target.getBoard().getDamageReceived().get(1).equals(shooter));
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

//    @Test
//    public void testVortexCannon(){
//        VortexCannonController vortexCannonController = new VortexCannonController(gameBoardController);
//
//        try{
//            Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
//            Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
//            Mockito.when(client.getName()).thenReturn("target");
//            Mockito.when(shooterClient.getName()).thenReturn("shooter");
//            vortexCannonController.findTargets(shooter);
//        }
//        catch(UserTimeoutException f){
//            fail("exception f");
//        }
//    }

//    @Test
//    public void testRocketLauncher(){
//        RocketLauncherController rocketLauncherController = new RocketLauncherController(gameBoardController);
//        try{
//            Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
//            Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
//            Mockito.when(client.chooseFiringMode(anyString())).thenReturn(true);
//            Mockito.when(shooterClient.chooseFiringMode(anyString())).thenReturn(true);
//            rocketLauncherController.shootTargets(shooter, targets);
//        }
//        catch(UserTimeoutException f){
//            fail("exception f");
//        }
//    }

//    @Test
//    public void testElectroscythe(){
//        ElectroscytheController electroscytheController = new ElectroscytheController(gameBoardController);
//        try{
//            Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
//            Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
//            electroscytheController.shootTargets(shooter, targets);
//        }
//        catch(UserTimeoutException f){
//            fail("exception f");
//        }
//    }
}
