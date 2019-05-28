package it.polimi.se2019.view.player;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMI;

import it.polimi.se2019.view.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerView implements ViewFacadeInterfaceRMI {

  public PlayerView(){
    weaponLoaderWindow = new GUIWeaponLoader();
    actionSetWindow = new ActionSetView();
    mapChooserWindow = new GUIMapChooser();
    playersNumberWindow = new GUIPlayersNumber();
    spawnLocationWindow = new GUISpawnLocation();
    weaponChooserWindow = new GUIWeaponChooser();
    targetChooseWindow = new GUITargetChoose();
    powerUpsChooserWindow = new GUIPowerUpsChooser();
  }

  /**
   *
   */
  public void generateLoginInfo(Client clientReference){
    JTextField playerNameField = new JTextField("min 6 chars", 20);
    JComboBox<String> characterCombo = new JComboBox<>(new String[] {"Banshee", ":D-STRUTT-OR3", "Dozer", "Sprog", "Violetta"});
    JButton confirmButton = new JButton("Log in");
    confirmButton.addActionListener(e -> {
      if(playerNameField.getText().length() >= 6){
        this.name = playerNameField.getText();
        this.character = (String) characterCombo.getSelectedItem();
        loginFrame.setVisible(false);
        clientReference.findLobby();
      }
    });

    loginFrame = new JFrame("Adrenalina - Log in");
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    loginFrame.setResizable(false);
    loginFrame.setLayout(new FlowLayout());
    loginFrame.add(new JTextArea("player name:"));
    loginFrame.add(playerNameField);
    loginFrame.add(new JTextArea("character:"));
    loginFrame.add(characterCombo);
    loginFrame.add(confirmButton);
    loginFrame.setVisible(true);
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
  private ActionSetView actionSetWindow;

  /**
   *
   */
  private JFrame loginFrame;

  /**
   *
   */
  private GUIWeaponLoader weaponLoaderWindow;

  /**
   *
   */
  private GUIMapChooser mapChooserWindow;

  /**
   *
   */
  private GUIPlayersNumber playersNumberWindow;

  /**
  *
  */
  private GUISpawnLocation spawnLocationWindow;

    /**
     *
     */
    private GUIWeaponChooser weaponChooserWindow;

    /**
     *
     */
    private GUITargetChoose targetChooseWindow;

    /**
     *
     */
    private GUIPowerUpsChooser powerUpsChooserWindow;

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

  private String chooseAction(String actionSetName){
    List<String> temp = new ArrayList<String>();
    temp.add(actionSetName);
    return actionSetWindow.askAndRequest(temp).get(0);
  }

  /**
   * @return int indicating the map type chosen
   */
  @Override
  public int chooseMap(){
    List<String> temp = new ArrayList<>(); temp.add("0"); temp.add("1"); temp.add("2"); temp.add("3");
    return Integer.getInteger(mapChooserWindow.askAndRequest(temp).get(0));
  }

  @Override
  public int chooseNumberOfPlayers(){
    List<String> args = new ArrayList<>(); args.add("1"); args.add("2"); args.add("3"); args.add("4"); args.add("5");
    return Integer.getInteger(playersNumberWindow.askAndRequest(args).get(0));
  }

  /**
   * @return red, blue or yellow
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps){
    return Integer.getInteger(spawnLocationWindow.askAndRequest(powerUps).get(0));
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(List<String> weapons){
    return weaponChooserWindow.askAndRequest(weapons).get(0);
  }

  /**
   * @param possibleTargets is a list of the players who can be targeted(their names)
   * @return a list of chosen targets(names)
   */
  @Override
  public String chooseTargets(List<String> possibleTargets){
    return targetChooseWindow.askAndRequest(possibleTargets).get(0);
  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons){
      return weaponLoaderWindow.askAndRequest(weapons).get(0);
  }

  /**
   * @return a list of integers indicating which cards from the player's inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps){
    List<String> temp = powerUpsChooserWindow.askAndRequest(powerUps);
    List<Integer> tempInt = new ArrayList<>();
    for(String s: temp)
        tempInt.add(Integer.getInteger(s));
    return tempInt;
  }

  /**
   * @return the integer relative to the availableEffects list
   */
  @Override
  public Integer chooseIndex(String weaponName, List<String> availableEffects){
    //TO DO: FORM
    return 0;
  }

  /**
   * @return int indicating which item to pick up from those available
   */
  @Override
  public int chooseItemToGrab(){
    //TO DO: FORM
    return 0;
  }

  @Override
  public String chooseRoom(List<String> rooms){
    //TO DO: FORM
    return rooms.get(0);
  }

  /**
   * @return which firing mode to use
   *
   * @param description:
   */
  @Override
  public Boolean chooseFiringMode(String description){
    //TO DO: FORM
    return false;
  }

  @Override
  public Boolean chooseBoolean(String description){
    //TO DO: JDIALOG HERE
    return false;
  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates){
    //TO DO: FORM
    return null;
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections){
    //TO DO: FORM
    return 0;
  }
}