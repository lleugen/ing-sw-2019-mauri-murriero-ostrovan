package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Deck;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Inventory;
import it.polimi.se2019.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
/**
 * @author Eugenio Ostrovan
 */
public class TestInventory {

    static Inventory inventory;

    @Before
    public void createEmptyInventory() throws UnknownMapTypeException {
        //initialize decks Decks decks = new Decks();
        List<Weapon> w = new ArrayList<>();
        w.add(new Weapon("mockWeapon",
                new Ammo(1,1,1),
                new Ammo(1,1,1)));
        Deck wep = new Deck(w);
        List<PowerUpCard> p = new ArrayList<>();
        p.add(new PowerUpCard(new Ammo(1,0,0), "mockPup"));
        Deck pup = new Deck(p);
        List<AmmoTile> a = new ArrayList<>();
        a.add(new AmmoTile(1,1,1, false));
        Deck amo = new Deck(a);
        Decks decks = new Decks(w, p, a);
        GameBoard gameBoard = new GameBoard(0);
        Player player = new Player("player", "character", gameBoard);
        inventory = new Inventory(player, decks);
    }

    @Test
    public void testAddToInventoryShouldSucceed() {
        Weapon weapon = new Weapon("mockWeapon",
                new Ammo(0,0,0),
                new Ammo(1,1,1));
        PowerUpCard powerUp = new PowerUpCard(new Ammo(1,0,0), "mockPup");
        AmmoTile ammoTile = new AmmoTile(1,1,1,false);
        inventory.getWeapons().clear();
        inventory.addWeaponToInventory(weapon);
        inventory.addPowerUpToInventory(powerUp);
        inventory.addAmmoTileToInventory(ammoTile);
        assertTrue(inventory.getPowerUps().contains(powerUp));
        assertTrue(inventory.getAmmo().getRed() == 2);
        assertTrue(inventory.getAmmo().getBlue() == 2);
        assertTrue(inventory.getAmmo().getYellow() == 2);
        //System.err.println(inventory.getWeapons().size());
        assertTrue(inventory.getWeapons().contains(weapon));
    }
    @Test
    public void testAddToInventoryFourthWeaponShouldFail(){
        Weapon weapon1 = new Weapon("a",new Ammo(1,1,1), new Ammo(1,1,1));
        Weapon weapon2 = new Weapon("b",new Ammo(1,1,1), new Ammo(1,1,1));
        Weapon weapon3 = new Weapon("c",new Ammo(1,1,1), new Ammo(1,1,1));
        Weapon weapon4 = new Weapon("d",new Ammo(1,1,1), new Ammo(1,1,1));
        inventory.addWeaponToInventory(weapon1);
        inventory.addWeaponToInventory(weapon2);
        inventory.addWeaponToInventory(weapon3);
        inventory.addWeaponToInventory(weapon4);
        assertTrue (!inventory.getWeapons().contains(weapon4));
    }

    @Test
    public void addToInventoryOverLimit() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player1 = new Player("player1", "character1", gameBoard);
        Inventory inventory = player1.getInventory();
        assertTrue(inventory.getPowerUps().size() == 1);
        inventory.addPowerUpToInventory(gameBoard.getDecks().drawPowerUp());
        inventory.addPowerUpToInventory(gameBoard.getDecks().drawPowerUp());
        inventory.addPowerUpToInventory(gameBoard.getDecks().drawPowerUp());
        assertTrue(inventory.getPowerUps().size() == 3);
        inventory.getAmmo().addRed(4);
        inventory.getAmmo().addBlue(4);
        inventory.getAmmo().addYellow(4);
        assertTrue(inventory.getAmmo().getRed() == 3);
        assertTrue(inventory.getAmmo().getBlue() == 3);
        assertTrue(inventory.getAmmo().getYellow() == 3);
    }


    @Test
    public void testUseAmmo(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Player player = new Player("player", "character", gameBoard);
            player.getInventory().useAmmo(new Ammo(1,1,1));
            assertTrue(player.getInventory().getAmmo().getRed() == 0);
            assertTrue(player.getInventory().getAmmo().getBlue() == 0);
            assertTrue(player.getInventory().getAmmo().getYellow() == 0);
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }

    @Test
    public void testDiscardWeapon(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Player player = new Player("player", "character", gameBoard);
            player.getInventory().addWeaponToInventory(new Weapon("weapon", new Ammo(1,1,1), new Ammo(1,1,1)));
            player.getInventory().discardWeapon(player.getInventory().getWeapons().get(0));
            assertTrue(player.getInventory().getWeapons().isEmpty());
        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }
}
