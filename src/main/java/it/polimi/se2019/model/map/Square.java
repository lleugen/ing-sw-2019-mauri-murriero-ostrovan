package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.*;

import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.ArrayList;
import java.util.List;

/**
 * A Square is an abstract representation of a tile in the map.
 * Using this abstraction allow us to skip dealing with specific square
 * implementation when generating the map or moving the player.
 * Few funcitons are exposed, which allows for abs operations on squares and
 * are implemented in specific extensions of this class
 */
public abstract class Square {
  /**
   *
   */
  private Grabbable item;

  /**
   *
   */
  private Decks decks;

  public Decks getDecks(){
    if(decks != null){
      return decks;
    }
    else{
      decks = map.getGameBoard().getDecks();
      return decks;
    }
  }

  protected Map map;

  /**
   *
   */
  public abstract List<Grabbable> getItem();
  /**
   * Inits a new Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param a The list of adjacents squares
   */
  public Square(Map m, String roomId, List<Direction> a) {
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
   * The list of adjacencies squares
   */
  private List<Direction> adjacencies;

  /**
   *
   */
  public abstract <T extends Grabbable> T grab(int index);

  /**
   *
   */
  public abstract boolean isSpawnPoint();

  /**
   * set the adjacencies of a square
   */
  protected void setAdjacencies(List<Direction> adj){
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

  protected void setBlocked(boolean north, boolean east, boolean south, boolean west){
    adjacencies.get(0).setBlocked(north);
    adjacencies.get(1).setBlocked(east);
    adjacencies.get(2).setBlocked(south);
    adjacencies.get(3).setBlocked(west);
  }

  /**
   * The id of the room this Square belongs to
   */
  private String idRoom;

  /**
   * @return The id of the room this Square belongs to
   */
  public String getIdRoom() {
    return this.idRoom;
  }

  /**
   * @return The list of adjacencies squares
   */
  public List<Direction> getAdjacencies() {
    return this.adjacencies;
  }

  /**
   * Grab items from the square
   *
   * @return The grabbed items
   */
  //public abstract Grabbable grab();

  /**
   * Refill the square
   */
  public abstract void refill();
}
