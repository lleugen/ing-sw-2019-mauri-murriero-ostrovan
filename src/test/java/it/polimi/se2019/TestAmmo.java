package it.polimi.se2019;

import it.polimi.se2019.model.grabbable.Ammo;
import org.junit.Test;

import static it.polimi.se2019.model.grabbable.Ammo.compare;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAmmo {
    @Test
    public void testCompareShouldReturn1(){
        Ammo ammoSet = new Ammo(1, 1, 1);
        Ammo ammoReference = new Ammo(1, 1, 1);
        int result = compare(ammoSet, ammoReference);
        assertEquals(1, result);
    }
    @Test
    public void testCompareShouldReturn0RedMissing(){
        Ammo ammoSet = new Ammo(0,1,1);
        Ammo ammoReference = new Ammo(1, 1, 1);
        int result = compare(ammoSet, ammoReference);
        assertEquals(0, result);
    }
    @Test
    public void testCompareShouldReturn0BlueMissing(){
        Ammo ammoSet = new Ammo(1,0,1);
        Ammo ammoReference = new Ammo(1,1,1);
        int result = compare(ammoSet, ammoReference);
        assertEquals(0, result);
    }
    @Test
    public void testCompareShouldReturn0YellowMissing(){
        Ammo ammoSet = new Ammo(1,1,0);
        Ammo ammoReference = new Ammo(1,1,1);
        int result = compare(ammoSet, ammoReference);
        assertEquals(0, result);
    }
    @Test
    public void testAddRedNoWaste(){
        Ammo ammo = new Ammo(0,0,0);
        ammo.addRed(1);
        int result = ammo.getRed();
        assertEquals(1, result);
    }
    @Test
    public void testAddRedWithWaste(){
        Ammo ammo = new Ammo(0,0,0);
        ammo.addRed(4);
        int result = ammo.getRed();
        assertEquals(3, result);
    }
    @Test
    public void testAddBlueNoWaste(){
        Ammo ammo = new Ammo(0,0,0);
        ammo.addBlue(1);
        int result = ammo.getBlue();
        assertEquals(1, result);
    }
    @Test
    public void testAddBlueWithWaste(){
        Ammo ammo = new Ammo(0,0,0);
        ammo.addBlue(4);
        int result = ammo.getBlue();
        assertEquals(3, result);
    }
    @Test
    public void testAddYellowNoWaste(){
        Ammo ammo = new Ammo(0,0,0);
        ammo.addYellow(1);
        int result = ammo.getYellow();
        assertEquals(1, result);
    }
    @Test
    public void testAddYellowWithWaste(){
        Ammo ammo = new Ammo(0,0,0);
        ammo.addYellow(4);
        int result = ammo.getYellow();
        assertEquals(3, result);
    }
    @Test
    public void testUseRedShouldSucceed(){
        Ammo ammo = new Ammo(1,1,1);
        ammo.useRed(1);
        int result = ammo.getRed();
        assertEquals(0, result);
    }
    @Test
    public void testUseRedShouldFail(){
        Ammo ammo = new Ammo(1,1,1);
        ammo.useRed(2);
        int result = ammo.getRed();
        assertEquals(1, result);
    }
    @Test
    public void testUseBlueShouldSucceed(){
        Ammo ammo = new Ammo(1,1,1);
        ammo.useBlue(1);
        int result = ammo.getBlue();
        assertEquals(0, result);
    }
    @Test
    public void testUseBlueShouldFail(){
        Ammo ammo = new Ammo(1,1,1);
        ammo.useBlue(2);
        int result = ammo.getBlue();
        assertEquals(1, result);
    }
    @Test
    public void testUseYellowShouldSucceed(){
        Ammo ammo = new Ammo(1,1,1);
        ammo.useYellow(1);
        int result = ammo.getYellow();
        assertEquals(0, result);
    }
    @Test
    public void testUseYellowShouldFail(){
        Ammo ammo = new Ammo(1,1,1);
        ammo.useYellow(2);
        int result = ammo.getYellow();
        assertEquals(1, result);
    }

    @Test
    public void testCreation(){
        Ammo ammo = new Ammo(1,2,3);
        assertTrue(ammo != null);
        assertTrue(ammo.getRed() == 1);
        assertTrue(ammo.getBlue() == 2);
        assertTrue(ammo.getYellow() == 3);
    }


}
