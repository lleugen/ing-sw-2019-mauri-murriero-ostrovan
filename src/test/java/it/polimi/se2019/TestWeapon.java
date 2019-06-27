package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Player;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class TestWeapon {
//    @Test(expected = Weapon.WeaponAlreadyLoadedException.class)
//    public void testReloadLoadedWeapon(){
//        Weapon weapon = new Weapon(null, null, null);
//        weapon.reload(null, null);
//    }
    @Test
    public void testReloadWithPowerupCardsShouldSucceed(){
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockPlayer","a", gameBoard);
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(1,0,0), null));
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(0,1,0), null));
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(0,0,1), null));
        Weapon weapon = new Weapon("mockWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.setOwner(player);
        weapon.unload();
        weapon.reload(player.getInventory().getPowerUps(), new Ammo(0,0,0));
        assert((weapon.isLoaded()) & (player.getInventory().getPowerUps().isEmpty()));
    }
    @Test
    public void testReloadWithPowerUpCardsShouldFail(){
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockPlayer","a", gameBoard);
        player.getInventory().getAmmo().useRed(1);
        player.getInventory().getAmmo().useBlue(1);
        player.getInventory().getAmmo().useYellow(1);
        assert(player.getInventory().getAmmo().getRed() == 0);
        assert(player.getInventory().getAmmo().getBlue() == 0);
        assert(player.getInventory().getAmmo().getYellow() == 0);
        player.getInventory().addPowerUpToInventory(new PowerUpCard(new Ammo(1,0,0), "mockPowerUp"));
        Weapon weapon = new Weapon("mockWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.setOwner(player);
        weapon.unload();
        assert(!weapon.isLoaded());
        boolean result = weapon.reload(player.getInventory().getPowerUps(), new Ammo(0,0,0));
        assert(!result);
    }
    @Test
    public void testReloadWithAmmoShouldSucceed(){
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("mockPlayer","mockCharacter", gameBoard);
        assert(player.getInventory().getAmmo().getRed() == 1);
        assert(player.getInventory().getAmmo().getBlue() == 1);
        assert(player.getInventory().getAmmo().getYellow() == 1);
        Weapon weapon = new Weapon("mockWeapon", new Ammo(0,0,0), new Ammo(1,1,1));
        weapon.setOwner(player);
        weapon.unload();
        assert(!weapon.isLoaded());
        List<PowerUpCard> emptyList = new ArrayList<>();

        weapon.reload(emptyList, player.getInventory().getAmmo());

        assert((weapon.isLoaded()));
        assert(player.getInventory().getAmmo().getRed() == 0);
        assert(player.getInventory().getAmmo().getBlue() == 0);
        assert(player.getInventory().getAmmo().getYellow() == 0);
    }
//    @Test(expected = Weapon.UnableToReloadException.class)
//    public void testReloadWithAmmoShouldFail(){
//        GameBoard gameBoard = new GameBoard(0);
//        Player player = new Player("mockPlayer","a", gameBoard);
//        Weapon weapon = new Weapon(null, null, null);
//        weapon.setOwner(player);
//        weapon.unload();
//        weapon.reload(player.getInventory().getPowerUps(), new Ammo(0,1,1));
//    }
}
