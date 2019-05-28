package it.polimi.se2019;

import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Inventory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestInventory {

    static Inventory inventory;

    @BeforeClass
    public static void createEmptyInventory(){
        //initialize decks Decks decks = new Decks();
        inventory = new Inventory(null);
        int prova = 1;
    }
    @After
    public void emptyTheInventory(){

    }

    @Test
    public void testAddToInventoryShouldSucceed(){
        Weapon weapon = new Weapon(null,null, null);
        PowerUpCard powerUp = new PowerUpCard(null, null);
        AmmoTile ammoTile = new AmmoTile(1,1,1,false);
        inventory.addWeaponToInventory(weapon);
        inventory.addPowerUpToInventory(powerUp);
        inventory.addAmmoTileToInventory(ammoTile);
        assert((inventory.getWeapons().contains(weapon)) & (inventory.getPowerUps().contains(powerUp)) & (inventory.getAmmo().getRed() == 1) && (inventory.getAmmo().getBlue() == 1) & (inventory.getAmmo().getYellow() == 1));
    }
    @Test(expected = Inventory.InventoryFullException.class)
    public void testAddToInventoryFourthWeaponShouldFail(){
        Weapon weapon1 = new Weapon(null,null, null);
        Weapon weapon2 = new Weapon(null,null, null);
        Weapon weapon3 = new Weapon(null,null, null);
        Weapon weapon4 = new Weapon(null,null, null);
        inventory.addWeaponToInventory(weapon1);
        inventory.addWeaponToInventory(weapon2);
        inventory.addWeaponToInventory(weapon3);
        inventory.addWeaponToInventory(weapon4);
    }
}
