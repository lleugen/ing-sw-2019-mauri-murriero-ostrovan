package it.polimi.se2019.RMI;

import java.util.List;
import java.rmi.Remote;

public interface ViewFacadeInterfaceRMI extends Remote {

    /**
     * @return index of the power up card to discard
     */
    int chooseSpawnLocation();

    /**
     * Choose map type for the match
     */
    int chooseMap();

    /**
     * choose how many players will be in the game
     */
    int chooseNumberOfPlayers();

    /**
     * @return chosen weapon name
     */
    String chooseWeapon();

    /**
     * @param possibleTargets is a list of the players who can be targeted(their names)
     * @return a list of chosen targets(names)
     */
    List<String> chooseTargets(List<String> possibleTargets);

    /**
     * @param weapons that can be reloaded
     * @return the name of the weapon to reload
     */
    String chooseWeaponToReload(List<String> weapons);

    /**
     * @return a list of integers indicating which cards from the player's inventory to use when reloading
     */
    List<Integer> choosePowerUpCardsForReload();

    /**
     * @return int indicating which item to pick up from those available
     */
    int chooseItemToGrab();

    /**
     * @return a list of flags signaling which firing modes to use
     */
    List<Boolean> chooseFiringMode(List<Boolean> firingModes);

    /**
     * @param targettableSquareCoordinates the coordinates of all targettable squares
     * @return the coordinates of one chosen square
     */
    List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates);

    /**
     * @return 0 for north, 1 for east, 2 for south or 3 for west
     */
    Integer chooseDirection();
}
