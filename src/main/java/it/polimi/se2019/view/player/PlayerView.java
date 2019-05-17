package it.polimi.se2019.view.player;

import it.polimi.se2019.RMI.ControllerFacadeInterfaceRMI;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMI;
import it.polimi.se2019.controller.ControllerFacadeImplementation;

import java.util.List;
import java.util.Scanner;

public class PlayerView implements ViewFacadeInterfaceRMI {
  public PlayerView(){
  }

  /**
   * reference to the controller
   */
  ControllerFacadeImplementation controller;

  /**
   * Take turn
   */
  public void playTurn(Integer availableActions){
    for(int i = 0; i<availableActions; i++){
      String chosenAction = chooseAction();
      if(chosenAction.equals("run")){
        controller
      }
      else if()
    }
  }

  /**
   *
   */
  private PlayerBoardView board;

  /**
   *
   */
  private PlayerInventoryView inventory;

  /**
   *
   */
  private ActionSetView actionSet;

  /**
   *
   */
  private String name;

  private String character;

  public String getName(){
    return name;
  }

  public String getCharacter(){
    return character;
  }

  private String chooseAction(){

  }

  /**
   * @return int indicating the map type chosen
   */
  @Override
  public int chooseMap(){
    //ask
    //return
  }

  @Override
  public int chooseNumberOfPlayers(){
    //ask
    //return
  }

  /**
   * @return red, blue or yellow
   */
  @Override
  public int chooseSpawnLocation(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("Choose a power up card to discard to respawn");
    return scanner.nextInt();
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(){

  }

  /**
   * @param possibleTargets is a list of the players who can be targeted(their names)
   * @return a list of chosen targets(names)
   */
  @Override
  public List<String> chooseTargets(List<String> possibleTargets){

  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons){

  }

  /**
   * @return a list of integers indicating which cards from the player's inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(){

  }

  /**
   * @return int indicating which item to pick up from those available
   */
  @Override
  public int chooseItemToGrab(){

  }

  /**
   * @return which firing mode to use : 0 = basic, 1 = powered up
   */
  //!revisit this one
  @Override
  public boolean chooseFiringMode(){

  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates){

  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseMoveDirection(){

  }

}