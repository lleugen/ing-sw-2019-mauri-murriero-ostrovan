package it.polimi.se2019.model.map;

/**
 * A Direction is an attribute of square containing information about adjacent
 * elements in one of four cardinal direction.
 * It contains a reference to the next square in that direction
 */
public class Direction {
  /**
   * Generate a new direction
   *
   * @param s  The next square in this direction
   * @param b True if the next square is not accessible, false otherwise
   */
  public Direction(Square s, boolean b) {
    if (s == null){
      this.square = null;
      blocked = true;
    }
    else {
      this.square = s;
      this.blocked = b;
    }
  }

  /**
   * True if the next square is not accessible, false otherwise
   */
  private boolean blocked;

  /**
   * The next square in this direction
   */
  private Square square;

  /**
   * @param isBlocked specifies whether there is a wall in this direction
   */
  protected void setBlocked(boolean isBlocked){
    blocked = isBlocked;
  }

  /**
   * @return The next square in this direction
   */
  public Square getSquare() {
    return square;
  }

  /**
   * @return true if the direction is blocked, false otherwise
   */
  public boolean isBlocked() {
    return this.blocked;
  }

  /**
   * @return true if the direction is accessible, false if it is blocked
   */
  public boolean isAccessible() {
    return !this.blocked;
  }
}