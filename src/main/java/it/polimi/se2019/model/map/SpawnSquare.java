package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Weapon;

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
  }

  /**
   * Grabs a weapon from the square
   *
   * @return The grabbed weapon
   */
  @Override
  public List<Weapon> grab() {
  }
}