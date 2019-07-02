package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.view.GUIcontrollers.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GUI extends UnicastRemoteObject
        implements ViewFacadeInterfaceRMIClient{

  private ActionSetView actionSetWindow;
  private GUIWeaponLoader weaponLoaderWindow;
  private GUIMapChooser mapChooserWindow;
  private GUIPlayersNumber playersNumberWindow;
  private GUISpawnLocation spawnLocationWindow;
  private GUIWeaponChooser weaponChooserWindow;
  private GUITargetChoose targetChooseWindow;
  private GUIPowerUpsChooser powerUpsChooserWindow;
  private GUIEffectChooser effectChooserWindow;
  private GUIDirectionChooser directionChooserWindow;
  private GUIBooleanQuestion booleanQuestionWindow;
  private GUISquareChooser squareChooserWindow;
  private GUIRoomChooser roomChooserWindow;

  private GUIPlayersBoard playersBoardWindow;
  private MyStage playersStage;

  private String nickname, character, characterFolder;
  private String lastWeaponSelected, lastMapSelected;

  public GUI(String nickname, String character) throws RemoteException {
    this.nickname = nickname;
    this.character = character;
    for(int i = 0; i < GUILogin.charactersNames.length; i++)
      if(GUILogin.charactersNames[i].equals(character))
        characterFolder = "char" + i;
  }

  /**
   *
   */
  @Override
  public String getName()  {
    return nickname;
  }

  @Override
  public String getCharacter(){
    return character;
  }

  /**
   *
   */
  @Override
  public String chooseAction(String state)  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    actionSetWindow = new ActionSetView(state, characterFolder);
    loader.setController(actionSetWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (String)secondaryStage.showAndGetResult(actionSetWindow);
    }catch(IOException e){
      e.printStackTrace();
      return "Trovato errore nella scelta dell'azione...";
    }
  }

  /**
   * @return index of the power up card to discard
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps)
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    spawnLocationWindow = new GUISpawnLocation(powerUps);
    loader.setController(spawnLocationWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (int)secondaryStage.showAndGetResult(spawnLocationWindow);
    }catch(IOException e){
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * Choose map type for the match
   */
  @Override
  public int chooseMap()  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    mapChooserWindow = new GUIMapChooser();
    loader.setController(mapChooserWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      int res = (int)secondaryStage.showAndGetResult(mapChooserWindow);
      lastMapSelected = "map" + res;
      return res;
    }catch(IOException e){
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * choose how many players will be in the game
   */
  @Override
  public int chooseNumberOfPlayers()  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/playernumberchooser.fxml"));

    playersNumberWindow = new GUIPlayersNumber();
    loader.setController(playersNumberWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (int)secondaryStage.showAndGetResult(playersNumberWindow);
    }catch(IOException e){
      e.printStackTrace();
      return 1;
    }
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(List<String> weapons)  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    weaponChooserWindow = new GUIWeaponChooser(weapons);
    loader.setController(weaponChooserWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      lastWeaponSelected = (String)secondaryStage.showAndGetResult(weaponChooserWindow);
      return lastWeaponSelected;
    }catch(IOException e){
      e.printStackTrace();
      return weapons.get(0);
    }
  }

  /**
   * @param possibleTargets is a list of the players who can be
   *                        targeted(their names)
   * @return a list of chosen targets(names)
   */
  @Override
  public String chooseTargets(List<String> possibleTargets)
  {
    List<String> targetsFolders = new ArrayList<>();
    for(int i = 0; i < possibleTargets.size(); i++)
      targetsFolders.add(playersBoardWindow.getCharacterFolder(possibleTargets.get(i)));

    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    targetChooseWindow = new GUITargetChoose(possibleTargets, targetsFolders);
    loader.setController(targetChooseWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (String)secondaryStage.showAndGetResult(targetChooseWindow);
    }catch(IOException e){
      e.printStackTrace();
      return possibleTargets.get(0);
    }
  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons)
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    weaponLoaderWindow = new GUIWeaponLoader(weapons);
    loader.setController(weaponLoaderWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (String)secondaryStage.showAndGetResult(weaponLoaderWindow);
    }catch(IOException e){
      e.printStackTrace();
      return "";
    }
  }

  /**
   * @return a list of integers indicating which cards from the player's
   * inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps)
  {
    List<Integer> chosenInt = new ArrayList<>();
    int lastAnswer = -1;
    boolean keepAsking = true;

    while ((keepAsking) && (chosenInt.size() < powerUps.size())){
      FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

      powerUpsChooserWindow = new GUIPowerUpsChooser(powerUps, chosenInt);
      loader.setController(powerUpsChooserWindow);

      try {
        Parent root = loader.load();
        MyStage secondaryStage = new MyStage();
        secondaryStage.setScene(new Scene(root));

        lastAnswer = (int) secondaryStage.showAndGetResult(powerUpsChooserWindow);
      } catch (IOException e) {
        e.printStackTrace();
        lastAnswer = -1;
      }
      if(lastAnswer != -1){
        chosenInt.add(lastAnswer);
      }else
        keepAsking = false;
    }
    return chosenInt;

  }

  /**
   * @return the integer relative to the availableEffects list
   */
  @Override
  public Integer chooseIndex(List<String> availableEffects)
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/effectchooser.fxml"));

    effectChooserWindow = new GUIEffectChooser(lastWeaponSelected, availableEffects);
    loader.setController(effectChooserWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (int)secondaryStage.showAndGetResult(effectChooserWindow);
    }catch(IOException e){
      e.printStackTrace();
      return -1;
    }
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/booleanquestion.fxml"));

    booleanQuestionWindow = new GUIBooleanQuestion("Vuoi attivare l'effetto <" + description + "> ora?", "Sì, Attiva", "No, non ora");
    loader.setController(booleanQuestionWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (Boolean)secondaryStage.showAndGetResult(booleanQuestionWindow);
    }catch(IOException e){
      e.printStackTrace();
      return false;
    }
  }

  /**
   *
   */
  @Override
  public Boolean chooseBoolean(String description)  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/booleanquestion.fxml"));

    booleanQuestionWindow = new GUIBooleanQuestion("DOMANDA: <" + description + ">?", "Va bene", "Non va bene");
    loader.setController(booleanQuestionWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (Boolean)secondaryStage.showAndGetResult(booleanQuestionWindow);
    }catch(IOException e){
      e.printStackTrace();
      return false;
    }
  }

  /**
   * choose a room from those proposed
   */
  @Override
  public String chooseRoom(List<String> rooms)  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/chooser.fxml"));

    roomChooserWindow = new GUIRoomChooser(lastMapSelected, rooms);
    loader.setController(roomChooserWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      return (String)secondaryStage.showAndGetResult(roomChooserWindow);
    }catch(IOException e){
      e.printStackTrace();
      return rooms.get(0);
    }
  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates)
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/squarechooser.fxml"));

    squareChooserWindow = new GUISquareChooser(lastMapSelected, targettableSquareCoordinates);
    loader.setController(squareChooserWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      String coordStr = (String)secondaryStage.showAndGetResult(squareChooserWindow);
      String[] coordVet = coordStr.split("_");
      List<Integer> coords = new ArrayList<>();
      coords.add(Integer.getInteger(coordVet[0]));
      coords.add(Integer.getInteger(coordVet[1]));

      return coords;
    }catch(IOException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections)
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/movement.fxml"));

    directionChooserWindow = new GUIDirectionChooser(possibleDirections);
    loader.setController(directionChooserWindow);

    try {
      Parent root = loader.load();
      MyStage secondaryStage = new MyStage();
      secondaryStage.setScene(new Scene(root));

      int res = (int)secondaryStage.showAndGetResult(directionChooserWindow);
      return res;
    }catch(IOException e){
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws RemoteException {
    // TODO ricky
    System.out.println("sendMapInfo");
  }

  @Override
  public void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException {
    if(playersBoardWindow == null)
      launchPlayersBoard();
    playersBoardWindow.setPlayerInfo(playerInfo);
  }

  @Override
  public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException {
    // TODO ricky
    System.out.println("sendKillScoreBoardInfo");
  }

  @Override
  public void sendCharacterInfo(List<String> characterInfo) throws RemoteException {
    if(playersBoardWindow == null)
      launchPlayersBoard();
    playersBoardWindow.setCharacterInfo(characterInfo);
  }

  private void launchPlayersBoard(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/groupsheets.fxml"));

    playersBoardWindow = new GUIPlayersBoard(getName());
    loader.setController(playersBoardWindow);

    try {
      Parent root = loader.load();
      playersStage = new MyStage();
      playersStage.setScene(new Scene(root));

      playersStage.show();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
