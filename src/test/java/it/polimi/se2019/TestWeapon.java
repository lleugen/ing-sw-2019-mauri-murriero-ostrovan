package it.polimi.se2019;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.controller.weapons.simple.WhisperController;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;


public class TestWeapon {
//    @Test(expected = Weapon.WeaponAlreadyLoadedException.class)
//    public void testReloadLoadedWeapon(){
//        Weapon weapon = new Weapon(null, null, null);
//        weapon.reload(null, null);
//    }
    @Test
    public void testReloadWithPowerupCardsShouldSucceed() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockPlayer","a", gameBoard);
        player.getInventory().discardPowerUp(player.getInventory().getPowerUps().get(0));
        assertTrue(player.getInventory().getPowerUps().isEmpty());
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(1,0,0), null));
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(0,1,0), null));
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(0,0,1), null));
        Weapon weapon = new Weapon("mockWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.setOwner(player);
        weapon.unload();
        weapon.reload(player.getInventory().getPowerUps(), new Ammo(0,0,0));
        assertTrue((weapon.isLoaded()));
        assertTrue(player.getInventory().getPowerUps().isEmpty());
    }
    @Test
    public void testReloadWithPowerUpCardsShouldFail() throws UnknownMapTypeException{
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockPlayer","a", gameBoard);
        player.getInventory().getAmmo().useRed(1);
        player.getInventory().getAmmo().useBlue(1);
        player.getInventory().getAmmo().useYellow(1);
        assertTrue(player.getInventory().getAmmo().getRed() == 0);
        assertTrue(player.getInventory().getAmmo().getBlue() == 0);
        assertTrue(player.getInventory().getAmmo().getYellow() == 0);
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(1,0,0), "mockPowerUp"));
        Weapon weapon = new Weapon("mockWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.setOwner(player);
        weapon.unload();
        assertTrue(!weapon.isLoaded());
        boolean result = weapon.reload(player.getInventory().getPowerUps(), new Ammo(0,0,0));
        assertTrue(!result);
    }
    @Test
    public void testReloadWithAmmoShouldSucceed() throws UnknownMapTypeException{
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockPlayer","mockCharacter", gameBoard);
        assertTrue(player.getInventory().getAmmo().getRed() == 1);
        assertTrue(player.getInventory().getAmmo().getBlue() == 1);
        assertTrue(player.getInventory().getAmmo().getYellow() == 1);
        Weapon weapon = new Weapon("mockWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.setOwner(player);
        weapon.unload();
        assertTrue(!weapon.isLoaded());
        List<PowerUpCard> emptyList = new ArrayList<>();

        weapon.reload(emptyList, player.getInventory().getAmmo());

        assertTrue((weapon.isLoaded()));
        assertTrue(player.getInventory().getAmmo().getRed() == 0);
        assertTrue(player.getInventory().getAmmo().getBlue() == 0);
        assertTrue(player.getInventory().getAmmo().getYellow() == 0);
    }
//    @Test(expected = Weapon.UnableToReloadException.class)
//    public void testReloadWithAmmoShouldFail() throws UnknownMapTypeException{
//        GameBoard gameBoard = new GameBoard(0);
//        Player player = new Player("mockPlayer","a", gameBoard);
//        Weapon weapon = new Weapon(null, null, null);
//        weapon.setOwner(player);
//        weapon.unload();
//        weapon.reload(player.getInventory().getPowerUps(), new Ammo(0,1,1));
//    }

    @Test
    public void testUnload(){
        Weapon weapon = new Weapon("testWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.unload();
        assertTrue(!weapon.isLoaded());
    }

    @Test
    public void testGetters(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            GameBoardController gameBoardController = new GameBoardController(gameBoard);
            WeaponController weaponController = new WhisperController(gameBoardController);
            Weapon whisper = new Weapon("whisper", new Ammo(1,1,1), new Ammo(1,1,1));
            whisper.setController(weaponController);
            assertTrue(weaponController.equals(whisper.getController()));
            assertTrue(whisper.getGrabCost().getRed() == 1);
            assertTrue(whisper.getGrabCost().getBlue() == 1);
            assertTrue(whisper.getGrabCost().getYellow() == 1);
            assertTrue(whisper.getReloadCost().getRed() == 1);
            assertTrue(whisper.getReloadCost().getBlue() == 1);
            assertTrue(whisper.getReloadCost().getYellow() == 1);

            whisper.reload(null, new Ammo(1,1,1));
            assertTrue(whisper.isLoaded());
        }
        catch(UnknownMapTypeException e){
            fail("could not build game board");
        }

    }
}
