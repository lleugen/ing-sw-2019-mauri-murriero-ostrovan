package it.polimi.se2019.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ViewFacadeInterfaceRMIServer {

    /**
     * Send a string message to a client
     */
    void sendGenericMessage(String message) throws UserTimeoutException, RemoteException;
    /**
     * @return the name of the client
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     */
    String getName() throws UserTimeoutException, RemoteException;

    /**
     *@throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return a string specifying the chosen action
     * @param state the player's state which defines the available actions
     *
     */
    String chooseAction(String state) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return index of the power up card to discard
     * @param powerUps the list of power ups available
     */
    int chooseSpawnLocation(List<String> powerUps) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * Choose map type for the match
     * @return an int specifying the map chosen
     */
    int chooseMap() throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * choose how many players will be in the game
     * @return an int specifying the number of players for the game
     */
    int chooseNumberOfPlayers() throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return chosen weapon name
     * @param weapons the list of available weapons
     */
    String chooseWeapon(List<String> weapons) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param possibleTargets is a list of the players who can be targeted(their names)
     * @return a list of chosen targets(names)
     */
    String chooseTargets(List<String> possibleTargets) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param weapons that can be reloaded
     * @return the name of the weapon to reload
     */
    String chooseWeaponToReload(List<String> weapons) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return a list of integers indicating which cards from the player's inventory to use when reloading
     * @param powerUps the list of available power ups
     */
    List<Integer> choosePowerUpCardsForReload(List<String> powerUps) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return the integer specifying the chosen effect
     * @param availableEffects the list of available effects
     */
    Integer chooseIndex(List<String> availableEffects) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return int indicating which item to pick up from those available
     */
    int chooseItemToGrab() throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * choose whether to use a firing mode
     * @return the player's choice
     * @param description the description of the choice
     */
    Boolean chooseFiringMode(String description) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return the player's choice
     * @param description the description of the choice
     */
    Boolean chooseBoolean(String description) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * choose a room from those proposed
     * @return the identifier of the chosen room
     * @param rooms the list of rooms to choose from
     */
    String chooseRoom(List<String> rooms) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param targettableSquareCoordinates the coordinates of all targettable squares
     * @return the coordinates of one chosen square
     */
    List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates) throws UserTimeoutException, RemoteException;

    /**
     * @throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @return 0 for north, 1 for east, 2 for south or 3 for west
     * @param possibleDirections the list of available directions
     */
    Integer chooseDirection(List<Integer> possibleDirections) throws UserTimeoutException, RemoteException;




    /**
     *@throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param mapInfo specifies the content of all map squares
     */
    void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws UserTimeoutException, RemoteException;

    /**
     *@throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param playerInfo contains the damage, marks and number of deaths
     */
    void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws UserTimeoutException, RemoteException;

    /**
     *@throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param killScoreBoardInfo contains information about scored kills and double kills
     */
    void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws UserTimeoutException, RemoteException;

    /**
     *@throws UserTimeoutException if the user takes too long to make a choice or disconnects
     * @throws RemoteException if there is an error with the RMI connection
     * @param characterInfo contains information about player's characters
     */
    void sendCharacterInfo(List<String> characterInfo) throws UserTimeoutException, RemoteException;
}
