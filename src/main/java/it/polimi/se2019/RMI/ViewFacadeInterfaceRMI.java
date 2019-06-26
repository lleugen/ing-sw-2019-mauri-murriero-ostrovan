package it.polimi.se2019.RMI;

import java.util.List;
import java.rmi.Remote;

public interface ViewFacadeInterfaceRMI extends Remote {
    /**
     *
     */
    String getName() throws UserTimeoutException;

    /**
     *
     */
    String chooseAction(String state) throws UserTimeoutException;

    /**
     *
     */
    String getName();

    /**
     *
     */
    String chooseAction(String state);

    /**
     * @return index of the power up card to discard
     */
    int chooseSpawnLocation(List<String> powerUps) throws UserTimeoutException;

    /**
     * Choose map type for the match
     */
    int chooseMap() throws UserTimeoutException;

    /**
     * choose how many players will be in the game
     */
    int chooseNumberOfPlayers() throws UserTimeoutException;

    /**
     * @return chosen weapon name
     */
    String chooseWeapon(List<String> weapons) throws UserTimeoutException;

    /**
     * @param possibleTargets is a list of the players who can be targeted(their names)
     * @return a list of chosen targets(names)
     */
    String chooseTargets(List<String> possibleTargets) throws UserTimeoutException;

    /**
     * @param weapons that can be reloaded
     * @return the name of the weapon to reload
     */
    String chooseWeaponToReload(List<String> weapons) throws UserTimeoutException;

    /**
     * @return a list of integers indicating which cards from the player's inventory to use when reloading
     */
    List<Integer> choosePowerUpCardsForReload(List<String> powerUps) throws UserTimeoutException;

    /**
     * @return the integer relative to the availableEffects list
     */
    Integer chooseIndex(String weaponName, List<String> availableEffects) throws UserTimeoutException;

    /**
     * @return int indicating which item to pick up from those available
     */
    int chooseItemToGrab() throws UserTimeoutException;

    /**
     * choose whether to use a firing mode
     */
    Boolean chooseFiringMode(String description) throws UserTimeoutException;

    /**
     *
     */
    Boolean chooseBoolean(String description) throws UserTimeoutException;

    /**
     * choose a room from those proposed
     */
    String chooseRoom(List<String> rooms) throws UserTimeoutException;

    /**
     * @param targettableSquareCoordinates the coordinates of all targettable squares
     * @return the coordinates of one chosen square
     */
    List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates) throws UserTimeoutException;

    /**
     * @return 0 for north, 1 for east, 2 for south or 3 for west
     */
    Integer chooseDirection(List<Integer> possibleDirections) throws UserTimeoutException;
}
