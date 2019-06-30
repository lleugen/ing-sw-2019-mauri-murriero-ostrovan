package it.polimi.se2019;

import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPowerUpCard {

  @Test
  public void TestGetAmmoEquivalent() {
    Ammo ammo = new Ammo(1, 2, 3);
    Ammo gotAmmo;
    PowerUpCard card = new PowerUpCard(ammo, "descrizione");

    gotAmmo = card.getAmmoEquivalent();

    if (
         gotAmmo.getRed() == 1 &&
         gotAmmo.getBlue() == 2 &&
         gotAmmo.getYellow() == 3
    ){
      // Test Passed
    }
    else {
      fail("A wrong ammo equivalent was returned");
    }
  }

  @Test
  public void getDescription() {
    Ammo ammo = new Ammo(1, 2, 3);
    PowerUpCard card = new PowerUpCard(ammo, "descrizione");

    if (card.getDescription().equals("descrizione")){
      // Test Passed
    }
    else {
      fail("A wrong description was returned");
    }
  }
}