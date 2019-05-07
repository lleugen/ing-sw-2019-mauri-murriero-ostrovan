package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.*;

import it.polimi.se2019.model.grabbable.Grabbable;
import java.util.List;

/**
 * A Square is an abstract representation of a tile in the map.
 * Using this abstraction allow us to skip dealing with specific square
 * implementation when generating the map or moving the player.
 * Few funcitons are exposed, which allows for base operations on squares and
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
    return decks;
  }



  /**
   *
   */
  public Grabbable getItem(){
    return item;
  }
  /**
   * Inits a new Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param adjacencies The list of adjacents squares
   */
  public Square(String roomId, List<Direction> adjacencies) {
    this.adjacencies = adjacencies;
    this.idRoom = roomId;
  }

  /**
   * The list of adjacencies squares
   */
  private List<Direction> adjacencies;

  /**
   *
   */
  protected void setAdjacencies(List<Direction> adj){
    adjacencies = adj;
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
