package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.AmmoTile;
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
  private AmmoTile ammos;

  /**
   * Grab items from the square
   *
   * @return the grabbed items
   */
  public Grabbable grab() {
    AmmoTile ammoTileCopy = new AmmoTile(this.ammos.ammo.getRed(), this.ammos.ammo.getBlue(), this.ammos.ammo.getYellow(), this.ammos.powerUp);
    ammos = null;
    return ammoTileCopy;
  }

  public void refill(){
    //draw an ammo tile from the ammo tile deck
    //ammos = drawn ammo tile
  }
}
