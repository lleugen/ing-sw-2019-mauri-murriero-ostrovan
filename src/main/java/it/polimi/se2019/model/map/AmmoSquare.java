package java.it.polimi.se2019.model.map;

import java.it.polimi.se2019.model.grabbable.Ammo;
import java.it.polimi.se2019.model.grabbable.AmmoTile;
import java.it.polimi.se2019.model.grabbable.Grabbable;
import java.it.polimi.se2019.model.player.Inventory;

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
    AmmoTile ammoTileCopy = new AmmoTile(this.ammos.getAmmo.getRed(), this.ammos.getAmmo.getBlue(),
            this.ammos.getAmmo.getYellow(), this.ammos.getPowerUp);
    ammos = null;
    return ammoTileCopy;
  }

  public void refill(){
    //draw an ammo tile from the ammo tile deck
    //ammos = drawn ammo tile
  }
}
