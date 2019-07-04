package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.view.GUIcontrollers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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
  private GUIItemToGrabChooser itemToGrabChooserWindow;

  private GUIPlayersBoard playersBoardWindow;
  private GUIGameBoard gameBoardWindow;
  private MyStage playersStage, boardStage;

  private String nickname, character;
  private String lastWeaponSelected, lastMapSelected;
  private PlayersNamesKeeper playersInfo;

  /**
   * Initialize a new GUI Player Interface
   *
   * @param user      Username of the player
   * @param character Character of the player
   *
   * @throws RemoteException if something goes wrong with RMI
   */
  public GUI(String user, String character) throws RemoteException {
    this.nickname = user;
    this.character = character;
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
  public String chooseAction(String state)
  {
    actionSetWindow = new ActionSetView(state, playersInfo.findFolder(getName()));

    Object result = loadPopUpWindow("chooser", new MyStage(), actionSetWindow);
    if(result != null)
      return (String)result;
    return "run";
  }

  /**
   * @return index of the power up card to discard
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps)
  {
    spawnLocationWindow = new GUISpawnLocation(powerUps);

    Object result = loadPopUpWindow("chooser", new MyStage(), spawnLocationWindow);
    //gameBoardWindow.setLocalPlayerCoords(powerUps.get((int)result));
    if(result != null)
      return (int)result;
    return 0;
  }

  /**
   * Choose map type for the match
   */
  @Override
  public int chooseMap()  {
    mapChooserWindow = new GUIMapChooser();

    Object result = loadPopUpWindow("chooser", new MyStage(), mapChooserWindow);
    lastMapSelected = "map" + result.toString();
    if(result != null)
      return (int)result;
    return 0;
  }

  /**
   * choose how many players will be in the game
   */
  @Override
  public int chooseNumberOfPlayers()  {
    playersNumberWindow = new GUIPlayersNumber();

    Object result = loadPopUpWindow("playernumberchooser", new MyStage(), playersNumberWindow);
    if(result != null)
      return (int)result;
    return 1;
  }

  /**
   * @return chosen weapon name
   */
  @Override
  public String chooseWeapon(List<String> weapons)  {
    weaponChooserWindow = new GUIWeaponChooser(weapons);

    Object result = loadPopUpWindow("chooser", new MyStage(), weaponChooserWindow);
    lastWeaponSelected = (String)result;
    if(result != null)
      return (String)result;
    return weapons.get(0);
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
      targetsFolders.add(playersInfo.findFolder(possibleTargets.get(i)));

    targetChooseWindow = new GUITargetChoose(possibleTargets, targetsFolders);

    Object result = loadPopUpWindow("chooser", new MyStage(), targetChooseWindow);
    if(result != null)
      return (String)result;
    return possibleTargets.get(0);
  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons)
  {
    weaponLoaderWindow = new GUIWeaponLoader(weapons);

    Object result = loadPopUpWindow("chooser", new MyStage(), weaponLoaderWindow);
    if(result != null)
      return (String)result;
    return weapons.get(0);
  }

  /**
   * @return a list of integers indicating which cards from the player's
   * inventory to use when reloading
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps)
  {
    List<Integer> chosenInt = new ArrayList<>();
    int lastAnswer;
    boolean keepAsking = true;

    while ((keepAsking) && (chosenInt.size() < powerUps.size())){
      powerUpsChooserWindow = new GUIPowerUpsChooser(powerUps, chosenInt);
      Object result = loadPopUpWindow("chooser", new MyStage(), powerUpsChooserWindow);

      if(result == null)
        result = -1;

      lastAnswer = (int)result;
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
    effectChooserWindow = new GUIEffectChooser(lastWeaponSelected, availableEffects);

    Object result = loadPopUpWindow("effectchooser", new MyStage(), effectChooserWindow);
    if(result != null)
      return (int)result;
    return 0;
  }

  /**
   * @return int indicating which item to pick up from those available
   */
  @Override
  public int chooseItemToGrab()  {
    itemToGrabChooserWindow = new GUIItemToGrabChooser();

    Object result = loadPopUpWindow("chooser", new MyStage(), itemToGrabChooserWindow);
    if(result != null)
      return (int)result;
    return 0;
  }

  /**
   * choose whether to use a firing mode
   */
  @Override
  public Boolean chooseFiringMode(String description)
  {
    booleanQuestionWindow = new GUIBooleanQuestion("Vuoi attivare l'effetto <" + description + "> ora?", "Sì, Attiva", "No, non ora");

    Object result = loadPopUpWindow("booleanquestion", new MyStage(), booleanQuestionWindow);
    if(result != null)
      return (Boolean) result;
    return false;
  }

  /**
   *
   */
  @Override
  public Boolean chooseBoolean(String description)  {
    booleanQuestionWindow = new GUIBooleanQuestion("DOMANDA: <" + description + ">?", "Va bene", "Non va bene");

    Object result = loadPopUpWindow("booleanquestion", new MyStage(), booleanQuestionWindow);
    if(result != null)
      return (Boolean) result;
    return false;
  }

  /**
   * choose a room from those proposed
   */
  @Override
  public String chooseRoom(List<String> rooms)  {
    roomChooserWindow = new GUIRoomChooser(lastMapSelected, rooms);

    Object result = loadPopUpWindow("chooser", new MyStage(), roomChooserWindow);
    if(result != null)
      return (String)result;
    return rooms.get(0);
  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates)
  {
    squareChooserWindow = new GUISquareChooser(lastMapSelected, targettableSquareCoordinates);

    Object result = loadPopUpWindow("squarechooser", new MyStage(), squareChooserWindow);

    if(result != null){
      String coordStr = (String)result;
      String[] coordVet = coordStr.split("_");
      List<Integer> coords = new ArrayList<>();
      coords.add(Integer.getInteger(coordVet[0]));
      coords.add(Integer.getInteger(coordVet[1]));

      return coords;
    }else
      return null;
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections)
  {
    directionChooserWindow = new GUIDirectionChooser(possibleDirections);

    Object result = loadPopUpWindow("movement", new MyStage(), directionChooserWindow);
    if(result != null)
      return (int)result;
    return 0;
  }

  @Override
  public void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws RemoteException {
    if(gameBoardWindow == null){
      gameBoardWindow = new GUIGameBoard(playersInfo);
      launchMainBoard(lastMapSelected, false, gameBoardWindow);
    }
     // launchGameBoard();
    gameBoardWindow.setMapInfo(mapInfo);
  }

  @Override
  public void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException {
    if(playersBoardWindow == null){
      playersBoardWindow = new GUIPlayersBoard(getName(), playersInfo);
      launchMainBoard("groupsheets", true, playersBoardWindow);
    }
     // launchPlayersBoard();
    playersBoardWindow.setPlayerInfo(playerInfo);
  }

  @Override
  public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException {
    if(gameBoardWindow == null){
      gameBoardWindow = new GUIGameBoard(playersInfo);
      launchMainBoard(lastMapSelected, false, gameBoardWindow);
    }
    //  launchGameBoard();
    gameBoardWindow.setKillScoreBoardInfo(killScoreBoardInfo);
  }

  @Override
  public void sendCharacterInfo(List<String> characterInfo) throws RemoteException {
    if(playersInfo == null){
      playersInfo = new PlayersNamesKeeper();
      for(int i = 0; i < characterInfo.size(); i+=2)
        playersInfo.addPlayer(characterInfo.get(i), characterInfo.get(i+1));
    }
    if(playersBoardWindow == null){
      playersBoardWindow = new GUIPlayersBoard(getName(), playersInfo);
      launchMainBoard("groupsheets", true, playersBoardWindow);
    }
    //  launchPlayersBoard();
  }

  private void launchMainBoard(String fxmlName, Boolean isPlayerStage, GUIGenericWindow controller){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + fxmlName + ".fxml"));
    loader.setController(controller);

    try {
      Parent root = loader.load();
      if(isPlayerStage){
        playersStage = new MyStage();
        playersStage.setScene(new Scene(root));
        playersStage.initStyle(StageStyle.UNDECORATED);

        playersStage.show();
      }else{
        boardStage = new MyStage();
        boardStage.setScene(new Scene(root));
        boardStage.initStyle(StageStyle.UNDECORATED);

        boardStage.show();
      }

    }catch(IOException e){
      e.printStackTrace();
    }
  }

  private Object loadPopUpWindow(String fxmlName, MyStage toLoad, GUIGenericWindow controller){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + fxmlName + ".fxml"));
    loader.setController(controller);

    try {
      Parent root = loader.load();
      toLoad.setScene(new Scene(root));
      toLoad.initStyle(StageStyle.UNDECORATED);

      return toLoad.showAndGetResult(controller);
    }catch(IOException e){
      e.printStackTrace();
    }
    return null;
  }
}
