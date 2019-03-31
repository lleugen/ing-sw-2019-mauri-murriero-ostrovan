package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Grabbable;

import java.util.List;

/**
 * A Square is an abstract representation of a tile in the map.
 * Using this abstraction allow us to skip dealing with specific square
 * implementation when generating the map or moving the player.
 *
 * Few funcitons are exposed, which allows for base operations on squares and
 * are implemented in specific extensions of this class
 */
public abstract class Square {

    /**
     * Inits a new Square
     *
     * @param roomId The id of the room this Square belongs to
     * @param adjacencies The list of adjacents squares
     */
    public Square(String roomId, List<Direction> adjacencies) {
    }

    /**
     * The list of adjacencies squares
     */
    private List<Direction> adjacencies;

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
    }

    /**
     * Grab items from the square
     *
     * @return The grabbed items
     */
    public List<Grabbable> grab() {
    }

    /**
     * Refill the square
     *
     * @param objects A list of item to refill the square with
     */
    public void refill(List<Grabbable> objects){

    }
}