package it.polimi.se2019.model.map;

import java.it.polimi.se2019.model.grabbable.Grabbable;
import java.it.polimi.se2019.model.grabbable.Weapon;

import java.util.ArrayList;
import java.util.Collections;
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
  public SpawnSquare(String roomId, List<Direction> adjacencies) {
    super(roomId, adjacencies);
  }

  /**
   * List of Weapons grabbable in this square
   */
  private List<Weapon> weaponList;

  /**
   * @return The list of weapons available in this square
   */
  public List<Weapon> getWeaponList() {
    List<Weapon> weaponsCopy = new ArrayList<Weapon>();
    Collections.copy(weaponsCopy, weaponList);
    return weaponsCopy;
  }

  /**
   * Grabs a weapon from the square
   *
   * @return The grabbed weapon
   */
  public Grabbable grab() {
    //ask player which weapon he or she wants to pick up and obtain index
    Weapon weaponCopy = weaponList.get(index);
    weaponList.remove(index);
    return weaponCopy;
  }

  public void refill(){
    //draw a weapon from the weapons cards deck
    //add the drawn weapon to weaponsList
  }
}
