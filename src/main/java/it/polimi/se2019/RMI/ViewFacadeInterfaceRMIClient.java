package it.polimi.se2019.RMI;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ViewFacadeInterfaceRMIClient extends Remote, Serializable {
    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return the name of the client
     */
    String getName() throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return the chosen action
     * @param state the player's state which determines the available actions
     */
    String getCharacter() throws RemoteException;

    /**
     *
     */
    String chooseAction(String state) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return index of the power up card to discard
     * @param powerUps the list of available power ups
     */
    int chooseSpawnLocation(List<String> powerUps) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * Choose map type for the match
     * @return an int specifying the chosen map
     */
    int chooseMap() throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * choose how many players will be in the game
     * @return an int specifying how many players the user would like to be in the game
     */
    int chooseNumberOfPlayers() throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return chosen weapon name
     * @param weapons  the list of available weapons
     */
    String chooseWeapon(List<String> weapons) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @param possibleTargets is a list of the players who can be targeted(their names)
     * @return a list of chosen targets(names)
     */
    String chooseTargets(List<String> possibleTargets) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @param weapons that can be reloaded
     * @return the name of the weapon to reload
     */
    String chooseWeaponToReload(List<String> weapons) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return a list of integers indicating which cards from the player's inventory to use when reloading
     * @param powerUps the list of available power ups
     */
    List<Integer> choosePowerUpCardsForReload(List<String> powerUps) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return the integer relative to the availableEffects list
     * @param availableEffects the list of available effects to choose from
     *
     */
    Integer chooseIndex(List<String> availableEffects) throws RemoteException;

    /**
     * @return int indicating which item to pick up from those available
     * @throws RemoteException if there is an error with the RMI connection
     */
    int chooseItemToGrab() throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * choose whether to use a firing mode
     * @return the user's choice
     * @param description the description of the effect
     */
    Boolean chooseFiringMode(String description) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return the user's choice
     * @param description the description of the choice to be made
     */
    Boolean chooseBoolean(String description) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * choose a room from those proposed
     * @return a string identifying the chosen room
     * @param rooms the list of available rooms
     */
    String chooseRoom(List<String> rooms) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @param targettableSquareCoordinates the coordinates of all targettable squares
     * @return the coordinates of one chosen square
     */
    List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @return 0 for north, 1 for east, 2 for south or 3 for west
     * @param possibleDirections the list of available directions
     */
    Integer chooseDirection(List<Integer> possibleDirections) throws RemoteException;


    /**
     *@throws RemoteException if there is an error with the RMI connection
     * @param mapInfo specifies the content of all map squares
     */
    void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws RemoteException;

    /**
     *@throws RemoteException if there is an error with the RMI connection
     * @param playerInfo contains the damage, marks and number of deaths
     */
    void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException;

    /**
     *@throws RemoteException if there is an error with the RMI connection
     * @param killScoreBoardInfo contains information about scored kills and double kills
     */
    void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException;

    /**
     * @throws RemoteException if there is an error with the RMI connection
     * @param characterInfo contains information about player's characters
     */
    void sendCharacterInfo(List<String> characterInfo) throws RemoteException;

}
