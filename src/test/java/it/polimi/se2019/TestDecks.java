package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.deck.Deck;
import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class TestDecks {
    @Test
    public void testDiscardItems(){
        try{
            GameBoard gameBoard = new GameBoard(0);
            Decks decks = gameBoard.getDecks();
            List<Weapon> weapons = new ArrayList<>();
            List<PowerUpCard> powerUpCards = new ArrayList<>();
            List<AmmoTile> ammoTiles = new ArrayList<>();
            weapons.add(decks.drawWeapon());
            powerUpCards.add(decks.drawPowerUp());
            ammoTiles.add(decks.drawAmmoTile());
            Decks testDecks = new Decks(weapons, powerUpCards, ammoTiles);
            Weapon drawnWeapon = testDecks.drawWeapon();
            PowerUpCard drawnPower = testDecks.drawPowerUp();
            AmmoTile drawAmmo = testDecks.drawAmmoTile();
            testDecks.discardWeapon(drawnWeapon);
            testDecks.discardPowerUp(drawnPower);
            testDecks.discardAmmoTile(drawAmmo);
            assertTrue(drawnWeapon.equals(testDecks.drawWeapon()));
            assertTrue(drawnPower.equals(testDecks.drawPowerUp()));
            assertTrue(drawAmmo.equals(testDecks.drawAmmoTile()));


        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }
}
