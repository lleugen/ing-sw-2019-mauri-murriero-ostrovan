package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.player.Inventory;

import java.util.ArrayList;
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

  @Override
  public List<Grabbable> getItem(){
    List content = new ArrayList();
    content.add(ammos);
    return content;
  }

  /**
   * Grab items from the square
   *
   * @return the grabbed items
   */
  @Override
  public AmmoTile grab(int index) {
    AmmoTile ammoTileCopy = new AmmoTile(ammos.getAmmo().getRed(), ammos.getAmmo().getBlue(),
            ammos.getAmmo().getYellow(), ammos.getPowerUp());
    ammos = null;
    return ammoTileCopy;
  }

  public boolean isSpawnPoint(){
    return false;
  }

  public void refill(){
    //draw an ammo tile from the ammo tile deck
    if(ammos == null){
      ammos = getDecks().drawAmmoTile();
    }
  }
}
