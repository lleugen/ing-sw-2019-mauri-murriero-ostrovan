package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An AmmoSquare represent a square where a player can grab Ammos, but not spawn
 */
public class AmmoSquare extends Square {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "AmmoSquare";

  /**
   * Inits a new Spawn Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param adjacencies The list of adjacents squares
   */
  public AmmoSquare(Map m, RoomColor roomId, List<Direction> adjacencies) {
    super(m, roomId, adjacencies);
    ammos = null;
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

  public AmmoTile getAmmos(){
    return ammos;
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

  /**
   * If the square doesn't contain the necessary items, draw from the decks to refill them
   */
  public void refill(){
    //draw an ammo tile from the ammo tile deck
    if(ammos == null){
      if(map.getGameBoard().getDecks() != null){
        ammos = map.getGameBoard().getDecks().drawAmmoTile();
      }
      else{
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Deck is null"
        );
      }
    }
  }
}
