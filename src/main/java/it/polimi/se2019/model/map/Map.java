package it.polimi.se2019.model.map;

/**
 * A map is a virtual collection of squares, linked together.
 * In reality, only one square is inserted in this class, cause all square are
 * linked together, and therefore the list of squares can easily be retrived
 */
public class Map {
  /**
   *
   */
  private Square root;

  /**
   *
   */
  private Square[][] mapSquares;

  /**
   * Init a new map
   *
   * @param mapType The type of the map to generate
   *
   * @throws UnknownMapTypeException if the type is not valid
   */
  public Map(String mapType) throws UnknownMapTypeException{
  }

  /**
   * Gets the root square
   */
  public Square getRoot() {
    return this.root;
  }
}
