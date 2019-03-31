package it.polimi.se2019.model.grabbable;

import java.util.List;
import java.util.Map;

/**
 *  Ammo represent a single ammunition (a color cube on the physical game)
 *  Cause Ammos are often used in groups, a list of helper method have been
 *  exposed for help working with Ammos.
 *
 *  __NOTE__ An Ammo always represent a SINGLE ammunition
 */
public class Ammo extends Grabbable {
    /**
     * Construct a new Ammunition
     *
     * @param colour Color of the new Ammunition
     */
    public Ammo(String colour) {
    }

    /**
     * Color of the Ammunition
     */
    private String colour;

    /**
     * Check if input has at least or more ammo than reference.
     * @param input The set to be compared to reference
     * @param reference  The comparison reference
     *
     * @return  Return true if in base there are at least check's Ammos,
     *          false otherwise
     */
    public static boolean available(List<Ammo> input, List<Ammo> reference) {

    }

    /**
     * @return the color of the current Ammo
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * Map a list of ammos to a Map in which the key represent the color
     * of the Ammo, and the value the number of Ammos of that color found in
     * the list
     *
     * @param set A set of Ammos to map
     *
     * @return A Map generated as stated above
     */
    public static Map map(List<Ammo> set) {

    }

    /**
     *  Clones the current Ammo into a new Ammo
     *
     * @return A new Ammo identical to the Ammo this method was called on
     */
    @Override
    public Ammo clone(){
        return new Ammo(this.getColour());
    }
}