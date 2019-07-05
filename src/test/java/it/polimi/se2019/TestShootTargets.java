package it.polimi.se2019;

import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.controller.weapons.alternative_effects.*;
import it.polimi.se2019.controller.weapons.optional_effects.*;
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
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
/**
 * @author Eugenio Ostrovan
 */
@RunWith(MockitoJUnitRunner.class)
public class TestShootTargets {
    @Mock
    PlayerViewOnServer client;
    @Mock
    PlayerViewOnServer shooterClient;

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
            shooter.getInventory().discardPowerUp(shooter.getInventory().getPowerUps().get(0));
            target.getInventory().discardPowerUp(target.getInventory().getPowerUps().get(0));
            shooter.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
            target.moveToSquare(gameBoard.getMap().getMapSquares()[0][0]);
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
            //Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            //Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
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
            //Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            //Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
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
            //Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            //Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
            thorController.shootTargets(shooter, targets);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
            assertTrue(target.getBoard().getDamageReceived().get(0).equals(shooter));
            assertTrue(target.getBoard().getDamageReceived().get(1).equals(shooter));
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testVortexCannon(){
        VortexCannonController vortexCannonController = new VortexCannonController(gameBoardController);

        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(0);
            coordinates.add(0);
            Mockito.when(shooterClient.chooseTargetSquare(any())).thenReturn(coordinates);
            vortexCannonController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testRocketLauncher(){
        RocketLauncherController rocketLauncherController = new RocketLauncherController(gameBoardController);
        try{
            //Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            //Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
            //Mockito.when(client.chooseFiringMode(anyString())).thenReturn(true);
            Mockito.when(shooterClient.chooseFiringMode(anyString())).thenReturn(false);
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            //Mockito.when(client.chooseIndex()).thenReturn(0);
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            //Mockito.when(shooterClient.chooseDirection(any())).thenReturn(-1);

            rocketLauncherController.fire(shooter, shooterClient);

            //rocketLauncherController.shootTargets(shooter, targets);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testElectroscythe(){
        ElectroscytheController electroscytheController = new ElectroscytheController(gameBoardController);
        try{
            //Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
            //Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            electroscytheController.shootTargets(shooter, targets);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

//    @Test
//    public void testShotgun(){
//        ShotgunController shotgunController= new ShotgunController(gameBoardController);
//
//        try{
//            Mockito.when(client.getName()).thenReturn("target");
//            Mockito.when(shooterClient.getName()).thenReturn("shooter");
//            //Mockito.when(shooterClient.chooseFiringMode(anyString())).thenReturn(false);
//            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
//            shotgunController.fire(shooter, shooterClient);
//        }
//        catch(UserTimeoutException f){
//            fail("exception f");
//        }
//    }

    @Test
    public void testTractorBeam(){
        TractorBeamController tractorBeamController = new TractorBeamController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            //Mockito.when(shooterClient.chooseFiringMode("insert 0 for basic, 1 for powered")).thenReturn(false);
            tractorBeamController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 3);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testPowerGlove(){
        PowerGloveController powerGloveController = new PowerGloveController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            powerGloveController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 1);
            assertTrue(target.getBoard().getMarksAssigned().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testFlameThrower(){
        FlameThrowerController flameThrowerController = new FlameThrowerController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            Mockito.when(shooterClient.chooseDirection(any())).thenReturn(1);
            flameThrowerController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testFurnaceController(){
        FurnaceController furnaceController = new FurnaceController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            //Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            Mockito.when(shooterClient.chooseRoom(any())).thenReturn("BLUE");
            furnaceController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 1);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testRailGun(){
        RailGunController railGunController = new RailGunController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            railGunController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 3);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testShockWave(){
        ShockwaveController shockwaveController = new ShockwaveController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            shockwaveController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 1);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testSledgeHammer(){
        SledgeHammerController sledgeHammerController = new SledgeHammerController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            sledgeHammerController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void ZX2(){
        ZX2Controller zx2Controller = new ZX2Controller(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            zx2Controller.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 1);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testCyberBlade(){
        CyberBladeController cyberBladeController = new CyberBladeController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            Mockito.when(shooterClient.chooseFiringMode("select effect" + 0)).thenReturn(true);
            cyberBladeController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testGrenadeLauncher(){
        GrenadeLauncherController grenadeLauncherController = new GrenadeLauncherController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            grenadeLauncherController.fire(shooter,shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 1);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testHellion(){
        HellionController hellionController = new HellionController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            hellionController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 1);
            assertTrue(target.getBoard().getMarksAssigned().size() == 1);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testLockRifle(){
        LockRifleController lockRifleController = new LockRifleController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            lockRifleController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getMarksAssigned().size() == 1);
            //System.err.println(target.getBoard().getDamageReceived().size());
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testMachineGun(){
        MachineGunController machineGunController = new MachineGunController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            machineGunController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

    @Test
    public void testPlasmaGun(){
        PlasmaGunController plasmaGunController = new PlasmaGunController(gameBoardController);
        try{
            Mockito.when(client.getName()).thenReturn("target");
            Mockito.when(shooterClient.getName()).thenReturn("shooter");
            Mockito.when(shooterClient.chooseTargets(any())).thenReturn("target");
            Mockito.when(shooterClient.chooseFiringMode("select effect" + 0)).thenReturn(true);
            plasmaGunController.fire(shooter, shooterClient);
            assertTrue(target.getBoard().getDamageReceived().size() == 2);
        }
        catch(UserTimeoutException f){
            fail("exception f");
        }
    }

//    @Test
//    public void testElectroscythe(){
//        ElectroscytheController electroscytheController = new ElectroscytheController(gameBoardController);
//        try{
//            //Mockito.when(client.chooseBoolean("Do you want to use a targeting scope?")).thenReturn(false);
//            //Mockito.when(client.chooseBoolean("Do you want to use a tagback grenade?")).thenReturn(false);
//            Mockito.when(client.chooseFiringMode(any())).thenReturn(false);
//            //Mockito.when(electroscytheController.identifyClient(any())).thenReturn(client);
//            electroscytheController.shootTargets(shooter, targets);
//        }
//        catch(UserTimeoutException f){
//            fail("exception f");
//        }
//    }

//    @Test
//    public void testShotgun(){
//        ShotgunController shotgunController= new ShotgunController(gameBoardController);
//
//        try{
//            Mockito.when(client.getName()).thenReturn("target");
//            Mockito.when(shooterClient.getName()).thenReturn("shooter");
//            shotgunController.shootTargets(shooter, targets);
//        }
//        catch(UserTimeoutException f){
//            fail("exception f");
//        }
//    }
}
