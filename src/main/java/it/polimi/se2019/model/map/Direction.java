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
     * @param square The next square in this direction
     * @param blocked True if the next square is not accessible, false otherwise
     */
    Direction(Square square, boolean blocked){}

    /**
     * True if the next square is not accessible, false otherwise
     */
    private boolean blocked;

    /**
     * The next square in this direction
     */
    private Square square;

    /**
     * @return The next square in this direction
     */
    public Square getSquare() {}

    /**
     * @return true if the direction is blocked, false otherwise
     */
    public boolean isBlocked() {
        return this.blocked;
    }
}