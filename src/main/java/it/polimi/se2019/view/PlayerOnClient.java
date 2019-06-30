package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerOnClient extends UnicastRemoteObject
        implements ViewFacadeInterfaceRMIClient {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "PlayerOnClient";

  /**
   * Create a new Client
   *
   * @param user User Id this client should bind to on the RMI
   * @param host Hostname the registry is located
   *
   * @throws RemoteException        if an error occurs while initialization
   * @throws MalformedURLException  if we cannot bind to the RMI Registry
   */
  public PlayerOnClient(String user, String host)
          throws RemoteException, MalformedURLException {
    ViewFacadeInterfaceRMIClient test = this;
    Naming.rebind("//" + host + "/players/" + user, test);
  }

  /**
   *
   */
  @Override
  public String getName()  {
    // TODO ricky
    System.out.println("getName");
    return System.console().readLine();
  }

  /**
   *
   */
  @Override
  public String chooseAction(String state)  {
    // TODO ricky
    System.out.println("chooseAction");
    return System.console().readLine();
  }

  /**
   * @return index of the power up card to discard
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps)
  {
    // TODO ricky
    System.out.println("chooseSpawnLocation");
    return Integer.parseInt(System.console().readLine());
  }

  /**
   * Choose map type for the match
   */
  @Override
  public int chooseMap()  {
    // TODO ricky
    System.out.println("chooseMap");
    return Integer.parseInt(System.console().readLine());
  }

  /**
   * choose how many players will be in the game
   */
  @Override
  public int chooseNumberOfPlayers()  {
    // TODO ricky
    System.out.println("chooseNumberOfPlayers");
    return Integer.parseInt(System.console().readLine());
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(List<String> weapons)  {
    // TODO ricky
    System.out.println("chooseWeapon");
    return System.console().readLine();
  }

  /**
   * @param possibleTargets is a list of the players who can be
   *                        targeted(their names)
   * @return a list of chosen targets(names)
   */
  @Override
  public String chooseTargets(List<String> possibleTargets)
  {
    // TODO ricky
    System.out.println("chooseTargets");
    return System.console().readLine();
  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons)
  {
    // TODO ricky
    System.out.println("chooseWeaponToReload");
    return System.console().readLine();
  }

  /**
   * @return a list of integers indicating which cards from the player's
   * inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps)
  {
    // TODO ricky
    System.out.println("choosePowerUpCardsForReload");
    return Arrays.stream(System.console().readLine().split(" "))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  }

  /**
   * @return the integer relative to the availableEffects list
   */
  @Override
  public Integer chooseIndex(List<String> availableEffects)
  {
    // TODO ricky
    System.out.println("chooseIndex");
    return Integer.parseInt(System.console().readLine());
  }

  /**
   * @return int indicating which item to pick up from those available
   */
  @Override
  public int chooseItemToGrab()  {
    // TODO ricky
    System.out.println("chooseItemToGrab");
    return Integer.parseInt(System.console().readLine());
  }

  /**
   * choose whether to use a firing mode
   */
  @Override
  public Boolean chooseFiringMode(String description)
  {
    // TODO ricky
    System.out.println("chooseFiringMode");
    return Boolean.parseBoolean(System.console().readLine());
  }

  /**
   *
   */
  @Override
  public Boolean chooseBoolean(String description)  {
    // TODO ricky
    System.out.println("chooseBoolean");
    return Boolean.parseBoolean(System.console().readLine());
  }

  /**
   * choose a room from those proposed
   */
  @Override
  public String chooseRoom(List<String> rooms)  {
    // TODO ricky
    System.out.println("chooseRoom");
    return System.console().readLine();
  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates)
  {
    // TODO ricky
    System.out.println("chooseTargetSquare");
    return Arrays.stream(System.console().readLine().split(" "))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections)
  {
    // TODO ricky
    System.out.println("chooseDirection");
    return Integer.parseInt(System.console().readLine());
  }

  @Override
  public void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws RemoteException {
    // TODO ricky
    System.out.println("sendMapInfo");
  }

  @Override
  public void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException {
    // TODO ricky
    System.out.println("sendPlayerInfo");
  }

  @Override
  public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException {
    // TODO ricky
    System.out.println("sendKillScoreBoardInfo");
  }
}
