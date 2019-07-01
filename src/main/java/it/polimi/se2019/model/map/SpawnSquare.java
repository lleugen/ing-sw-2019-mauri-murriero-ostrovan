package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.grabbable.Weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A spawn square represent a square where a player can spawn/respawn or
 * grab a weapon
 */
public class SpawnSquare extends Square {

  /**
   * Inits a new Spawn Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param adjacencies The list of adjacents squares
   */
  public SpawnSquare(Map m, RoomColor roomId, List<Direction> adjacencies) {
    super(m, roomId, adjacencies);
    weaponList = new ArrayList<>();
  }

  /**
   * List of Weapons grabbable in this square
   */
  private List<Weapon> weaponList;

  /**
   *
   * @return the list of weapons on this spawn square
   */
  @Override
  public List<Grabbable> getItem(){
    return new LinkedList<>(weaponList);
  }

  /**
   * @return The list of weapons available in this square
   */
  public List<Weapon> getWeaponList() {
    List<Weapon> weaponsCopy = new ArrayList<>();
    Collections.copy(weaponsCopy, weaponList);
    return weaponsCopy;
  }

  /**
   * Grabs a weapon from the square
   *
   * @return The grabbed weapon
   */
  @Override
  public Weapon grab(int index) {
    Weapon weaponCopy = weaponList.get(index);
    weaponList.remove(index);
    return weaponCopy;
  }

  /*
  public boolean isSpawnPoint(){
    return true;
  }
  */

  /**
   * If the square has less than three weapons, draw from the decks to replace those that were picked up.
   */
  public void refill(){
    //draw a weapon from the weapons cards deck
    //add the drawn weapon to weaponsList
    if(map.getGameBoard().getDecks() != null){
      while(weaponList.size() < 3){
        Weapon newWeapon = getDecks().drawWeapon();
        weaponList.add(newWeapon);
      }
    }
    else{
      System.err.println("decks is null");
    }
  }
}
