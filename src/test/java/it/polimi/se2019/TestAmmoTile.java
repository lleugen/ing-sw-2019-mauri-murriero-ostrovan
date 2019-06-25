//package it.polimi.se2019;
//
//import it.polimi.se2019.model.grabbable.Ammo;
//import it.polimi.se2019.model.grabbable.AmmoTile;
//import org.junit.Test;
//
//import static org.junit.Assert.fail;
//
//public class TestAmmoTile {
//
//  @Test
//  public void TestGetAmmo() {
//    AmmoTile testTile = new AmmoTile(1,2, 3,false);
//    Ammo returnedAmmo;
//
//    returnedAmmo = testTile.getAmmo();
//
//    if (
//            (returnedAmmo.getRed() == 1) &&
//                    (returnedAmmo.getBlue() == 2) &&
//                    (returnedAmmo.getYellow() == 3)
//    ){
//      // Test Passed
//    }
//    else {
//      fail("Wrong Ammos values were returned");
//    }
//  }
//
//  @Test
//  public void TestGetPowerUpFalse() {
//    AmmoTile testTile = new AmmoTile(1,2, 3,false);
//
//    if (testTile.getPowerUp() == false){
//      // Test Passed
//    }
//    else {
//      fail("Power Up Was Not Setted");
//    }
//  }
//
//  @Test
//  public void TestGetPowerUpTrue() {
//    AmmoTile testTile = new AmmoTile(1,2, 3,true);
//
//    if (testTile.getPowerUp() == true){
//      // Test Passed
//    }
//    else {
//      fail("Power Up Was Setted");
//    }
//  }
//}