package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.List;

/**
 * An AmmoSquare represent a square where a player can grab Ammos, but not spawn
 */
public class AmmoSquare extends Square {
  /**
   * Inits a new Spawn Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param adjacencies The list of adjacents squares
   */
  public AmmoSquare(String roomId, List<Direction> adjacencies) {
    super(roomId, adjacencies);
  }

  /**
   * The list of Ammos of PowerUps placed on this square
   */
  private List<Grabbable> ammos;

  /**
   * Grab items from the square
   *
   * @return the grabbed items
   */
  @Override
  public List<Grabbable> grab() {
  }
}
