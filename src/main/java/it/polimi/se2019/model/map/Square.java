package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Square is an abstract representation of a tile in the map.
 * Using this abstraction allow us to skip dealing with specific square
 * implementation when generating the map or moving the player.
 * Few funcitons are exposed, which allows for abs operations on squares and
 * are implemented in specific extensions of this class
 *
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public abstract class Square {
  /**
   * reference to the decks from which items can be drawn
   */
  private Decks decks;

  /**
   * Available rooms color
   */
  public enum RoomColor {
    BLUE,
    YELLOW,
    WHITE,
    RED,
    GRAY,
    PURPLE
  }

  /**
   *
   * @return the reference to the decks
   */
  public Decks getDecks(){
    if(decks != null){
      return decks;
    }
    else{
      decks = map.getGameBoard().getDecks();
      return decks;
    }
  }

  /**
   * the map to which the square belongs
   */
  protected Map map;

  /**
   * @return the list of items on the square
   */
  public abstract List<Grabbable> getItem();
  /**
   * Inits a new Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param a The list of adjacents squares
   * @param m the map that this square belongs to
   */
  public Square(Map m, RoomColor roomId, List<Direction> a) {
    map = m;
    decks = map.getGameBoard().getDecks();
    adjacencies = new ArrayList<>();
    if(a == null){
      adjacencies.add(0, new Direction(null, true));
      adjacencies.add(1, new Direction(null, true));
      adjacencies.add(2, new Direction(null, true));
      adjacencies.add(3, new Direction(null, true));
    }
    else{
      adjacencies = a;
    }
    this.idRoom = roomId;
  }

  /**
   * The list of adjacent squares
   */
  private List<Direction> adjacencies;

  /**
   * Take the item from the square
   * @param index int specifying which item to grab
   * @return the item grabbed
   * @param <T> is anything that is grabbable
   */
  public abstract <T extends Grabbable> T grab(int index);

  /**
   * set the adjacencies of a square
   * @param adj the list of adjacencies to set for the square
   */
  public void setAdjacencies(List<Direction> adj){
    adjacencies = new ArrayList<>();
    adjacencies.clear();
    if(adj.get(0) != null){
      adjacencies.add(0, adj.get(0));
      if(adj.get(1) != null){
        adjacencies.add(1, adj.get(1));
        if(adj.get(2) != null){
          adjacencies.add(2, adj.get(2));
          if(adj.get(3) != null){
            adjacencies.add(3, adj.get(3));
          }
        }
      }
    }
  }

  /**
   * For each direction, specify whether there is a wall that way
   * @param north whether the north side is blocked by a wall
   * @param east whether the east side is blocked by a wall
   * @param south whether the south side is blocked by a wall
   * @param west whether the west side is blocked by a wall
   */
  protected void setBlocked(boolean north, boolean east, boolean south, boolean west){
    adjacencies.get(0).setBlocked(north);
    adjacencies.get(1).setBlocked(east);
    adjacencies.get(2).setBlocked(south);
    adjacencies.get(3).setBlocked(west);
  }

  /**
   * The id of the room this Square belongs to
   */
  private RoomColor idRoom;

  /**
   * @return The id of the room this Square belongs to
   */
  public RoomColor getIdRoom() {
    return this.idRoom;
  }

  /**
   * @return The list of adjacencies squares
   */
  public List<Direction> getAdjacencies() {
    return this.adjacencies;
  }

  /**
   * Refill the square
   */
  public abstract void refill();
}
