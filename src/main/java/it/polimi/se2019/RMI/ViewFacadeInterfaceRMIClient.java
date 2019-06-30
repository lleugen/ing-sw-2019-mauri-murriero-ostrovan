package it.polimi.se2019.RMI;

import java.lang.reflect.Array;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ViewFacadeInterfaceRMIClient extends Remote {
    /**
     *
     */
    String getName() throws RemoteException;

    /**
     *
     */
    String chooseAction(String state) throws RemoteException;

    /**
     * @return index of the power up card to discard
     */
    int chooseSpawnLocation(List<String> powerUps) throws RemoteException;

    /**
     * Choose map type for the match
     */
    int chooseMap() throws RemoteException;

    /**
     * choose how many players will be in the game
     */
    int chooseNumberOfPlayers() throws RemoteException;

    /**
     * @return chosen weapon name
     */
    String chooseWeapon(List<String> weapons) throws RemoteException;

    /**
     * @param possibleTargets is a list of the players who can be targeted(their names)
     * @return a list of chosen targets(names)
     */
    String chooseTargets(List<String> possibleTargets) throws RemoteException;

    /**
     * @param weapons that can be reloaded
     * @return the name of the weapon to reload
     */
    String chooseWeaponToReload(List<String> weapons) throws RemoteException;

    /**
     * @return a list of integers indicating which cards from the player's inventory to use when reloading
     */
    List<Integer> choosePowerUpCardsForReload(List<String> powerUps) throws RemoteException;

    /**
     * @return the integer relative to the availableEffects list
     */
    Integer chooseIndex(List<String> availableEffects) throws RemoteException;

    /**
     * @return int indicating which item to pick up from those available
     */
    int chooseItemToGrab() throws RemoteException;

    /**
     * choose whether to use a firing mode
     */
    Boolean chooseFiringMode(String description) throws RemoteException;

    /**
     *
     */
    Boolean chooseBoolean(String description) throws RemoteException;

    /**
     * choose a room from those proposed
     */
    String chooseRoom(List<String> rooms) throws RemoteException;

    /**
     * @param targettableSquareCoordinates the coordinates of all targettable squares
     * @return the coordinates of one chosen square
     */
    List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates) throws RemoteException;

    /**
     * @return 0 for north, 1 for east, 2 for south or 3 for west
     */
    Integer chooseDirection(List<Integer> possibleDirections) throws RemoteException;


    /**
     *
     * @param mapInfo specifies the content of all map squares
     */
    void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws RemoteException;

    /**
     *
     * @param playerInfo contains the damage, marks and number of deaths
     */
    void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException;

    /**
     *
     * @param killScoreBoardInfo contains information about scored kills and double kills
     */
    void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException;

    /**
     *
     * @param characterInfo contains information about player's characters
     */
    void sendCharacterInfo(List<String> characterInfo) throws RemoteException;

}
