package it.polimi.se2019;

import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestInventory {
    @BeforeClass
    public void createEmptyInventory(){
        //initialize decks Decks decks = new Decks();
        Inventory inventory = new Inventory(/*decks*/);
        int prova = 1;
    }
    @After
    public void emptyTheInventory(){

    }

    @Test
    public void testAddToInventoryShouldSucceed(){
        Weapon weapon = new Weapon(null,null);
        PowerUpCard powerUp = new PowerUpCard(null, null);
        AmmoTile ammoTile = new AmmoTile(1,1,1,false);
        inventory.addToInventory(weapon);
        inventory.addToInventory(powerUp);
        inventory.addToInventory(ammoTile);
        assert((inventory.getWeapons().contains(weapon)) & (inventory.getPowerUps().contains(powerUp)) & (inventory.getAmmo().getRed() == 1) && (inventory.getAmmo().getBlue() == 1) & (inventory.getAmmo().getYellow() == 1));
    }
    @Test(expected = FullInventoryException.class)
    public void testAddToInventoryFourthWeaponShouldFail(){
        Weapon weapon1 = new Weapon(null,null);
        Weapon weapon2 = new Weapon(null,null);
        Weapon weapon3 = new Weapon(null,null);
        Weapon weapon4 = new Weapon(null,null);
        inventory.add(weapon1);
        inventory.add(weapon2);
        inventory.add(weapon3);
        inventory.add(weapon4);
    }
}
