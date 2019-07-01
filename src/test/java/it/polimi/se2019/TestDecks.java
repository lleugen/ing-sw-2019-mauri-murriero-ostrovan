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
            for(int i = 0; i<3; i++){
                weapons.add(decks.drawWeapon());
                powerUpCards.add(decks.drawPowerUp());
                ammoTiles.add(decks.drawAmmoTile());
            }
            Deck weaponDeck = new Deck(weapons);
            Deck powerDeck = new Deck(powerUpCards);
            Deck ammoDeck = new Deck(ammoTiles);


        }
        catch(UnknownMapTypeException e){
            fail("could not create game board");
        }
    }
}
