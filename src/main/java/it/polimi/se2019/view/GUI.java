package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.view.GUIcontrollers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main GUI class, it contains every method called from the server
 *
 * @author Riccardo Murriero
 */
public class GUI extends UnicastRemoteObject
        implements ViewFacadeInterfaceRMIClient{
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "GUI";

  private GUIPlayersBoard playersBoardWindow;
  private GUIGameBoard gameBoardWindow;

  private String nickname;
  private String character;
  private String lastWeaponSelected;
  private String lastMapSelected;
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
   * @return the player nickname
   */
  @Override
  public String getName()  {
    return nickname;
  }

    /**
     *
     * @return character name
     */
  @Override
  public String getCharacter(){
    return character;
  }

  /**
   * @param state    which player state are you
   *
   * @return    one of the available actions of the given state
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public String chooseAction(String state)
  {
    ActionSetView actionSetWindow = new ActionSetView(state, playersInfo.findFolder(getName()));

    Object result = loadChooser(actionSetWindow);
    if(result != null)
      return (String)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseAction non è andato a buon fine.");
  }

  /**
   * @param powerUps: list of available powerUps names
   *
   * @return index of the power up card to discard
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public int chooseSpawnLocation(List<String> powerUps)
  {
    GUISpawnLocation spawnLocationWindow = new GUISpawnLocation(powerUps);

    Object result = loadChooser(spawnLocationWindow);
    if(result != null)
      return (int)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseSpawnLocation non è andato a buon fine.");
  }

  /**
   * @return witch map type for the match from 0 to 3
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public int chooseMap() {
    GUIMapChooser mapChooserWindow = new GUIMapChooser();

    Object result = loadChooser(mapChooserWindow);
    if (result != null) {
      lastMapSelected = "map" + result.toString();
      return (int) result;
    } else {
      throw new InvalidClosedGUIException("GUIError: chooseMap non è andato a buon fine.");
    }
  }

  /**
   * @return how many players will be in the game
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public int chooseNumberOfPlayers()  {
    GUIPlayersNumber playersNumberWindow = new GUIPlayersNumber();

    Object result = loadPopUpWindow("playernumberchooser", new MyStage(), playersNumberWindow);
    if(result != null)
      return (int)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseNumberOfPlayers non è andato a buon fine.");
  }

  /**
   * @param weapons: list of available weapons
   *
   * @return chosen weapon name
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public String chooseWeapon(List<String> weapons)  {
    GUIWeaponChooser weaponChooserWindow = new GUIWeaponChooser(weapons);

    Object result = loadChooser(weaponChooserWindow);
    lastWeaponSelected = (String)result;
    if(result != null)
      return (String)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseWeapon non è andato a buon fine.");
  }

  /**
   * @param possibleTargets is a list of the players who can be
   *                        targeted(their names)
   * @return a list of chosen targets(names)
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public String chooseTargets(List<String> possibleTargets)
  {
    List<String> targetsFolders = new ArrayList<>();
    for (String possibleTarget : possibleTargets) {
      targetsFolders.add(playersInfo.findFolder(possibleTarget));
    }

    GUITargetChoose targetChooseWindow = new GUITargetChoose(possibleTargets, targetsFolders);

    Object result = loadChooser(targetChooseWindow);
    if(result != null)
      return (String)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseTargets non è andato a buon fine.");
  }

  /**
   * @param weapons that can be reloaded
   * @return the name of the weapon to reload
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public String chooseWeaponToReload(List<String> weapons)
  {
    GUIWeaponLoader weaponLoaderWindow = new GUIWeaponLoader(weapons);

    Object result = loadChooser(weaponLoaderWindow);
    if(result != null)
      return (String)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseWeaponToReload non è andato a buon fine.");
  }

  /**
   * @return a list of integers indicating which cards from the player's
   * inventory to use when reloading
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public List<Integer> choosePowerUpCardsForReload(List<String> powerUps)
  {
    List<Integer> chosenInt = new ArrayList<>();
    int lastAnswer;
    boolean keepAsking = true;

    while ((keepAsking) && (chosenInt.size() < powerUps.size())){
      GUIPowerUpsChooser powerUpsChooserWindow = new GUIPowerUpsChooser(powerUps, chosenInt);
      Object result = loadChooser(powerUpsChooserWindow);

      if(result == null)
        throw new InvalidClosedGUIException("GUIError: choosePowerUpCardsForReload non è andato a buon fine.");

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
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public Integer chooseIndex(List<String> availableEffects)
  {
    GUIEffectChooser effectChooserWindow = new GUIEffectChooser(lastWeaponSelected, availableEffects);

    Object result = loadPopUpWindow("effectchooser", new MyStage(), effectChooserWindow);
    if(result != null)
      return (int)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseIndex non è andato a buon fine.");
  }

  /**
   * @return int indicating which item to pick up from those available
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public int chooseItemToGrab()  {
    GUIItemToGrabChooser itemToGrabChooserWindow = new GUIItemToGrabChooser();

    Object result = loadChooser(itemToGrabChooserWindow);
    if(result != null)
      return (int)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseItemToGrab non è andato a buon fine.");
  }

  /**
   * choose whether to use a firing mode
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public Boolean chooseFiringMode(String description)
  {
    GUIBooleanQuestion booleanQuestionWindow = new GUIBooleanQuestion("Vuoi attivare l'effetto <" + description + "> ora?", "Sì, Attiva", "No, non ora");

    Object result = loadPopUpWindow("booleanquestion", new MyStage(), booleanQuestionWindow);
    if(result != null)
      return (Boolean) result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseFiring non è andato a buon fine.");
  }

  /**
   *  choose to answer from a yes/no question from the server
   *
   * @param description   question asked
   * @return true/false choice
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public Boolean chooseBoolean(String description)  {
    GUIBooleanQuestion booleanQuestionWindow = new GUIBooleanQuestion("DOMANDA: <" + description + ">?", "Va bene", "Non va bene");

    Object result = loadPopUpWindow("booleanquestion", new MyStage(), booleanQuestionWindow);
    if(result != null)
      return (Boolean) result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseBoolean non è andato a buon fine.");
  }

  /**
   * choose a room from those proposed
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public String chooseRoom(List<String> rooms)  {
    GUIRoomChooser roomChooserWindow = new GUIRoomChooser(lastMapSelected, rooms);

    Object result = loadChooser(roomChooserWindow);
    if(result != null)
      return (String)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseRoom non è andato a buon fine.");
  }

  /**
   * @param targettableSquareCoordinates the coordinates of all targettable squares
   * @return the coordinates of one chosen square
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates)
  {
    GUISquareChooser squareChooserWindow = new GUISquareChooser(lastMapSelected, targettableSquareCoordinates);

    Object result = loadPopUpWindow("squarechooser", new MyStage(), squareChooserWindow);

    if(result != null){
      String coordStr = (String)result;
      String[] coordVet = coordStr.split("_");
      List<Integer> coords = new ArrayList<>();
      coords.add(Integer.getInteger(coordVet[0]));
      coords.add(Integer.getInteger(coordVet[1]));

      return coords;
    }else
      throw new InvalidClosedGUIException("GUIError: chooseTargetSquare non è andato a buon fine.");
  }

  /**
   * @return 0 for north, 1 for east, 2 for south or 3 for west
   * @throws InvalidClosedGUIException if the GUI closes without a valid result
   */
  @Override
  public Integer chooseDirection(List<Integer> possibleDirections)
  {
    GUIDirectionChooser directionChooserWindow = new GUIDirectionChooser(possibleDirections);

    Object result = loadPopUpWindow("movement", new MyStage(), directionChooserWindow);
    if(result != null)
      return (int)result;
    else
      throw new InvalidClosedGUIException("GUIError: chooseDirection non è andato a buon fine.");
  }

  @Override
  public void sendMapInfo(List<ArrayList<ArrayList<String>>> mapInfo) throws RemoteException {
    if(gameBoardWindow == null){
      gameBoardWindow = new GUIGameBoard(playersInfo);
      launchMainBoard(lastMapSelected, false, gameBoardWindow);
    }
    gameBoardWindow.setMapInfo(mapInfo);
  }

  @Override
  public void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException {
    if(playersBoardWindow == null){
      playersBoardWindow = new GUIPlayersBoard(getName(), playersInfo);
      launchMainBoard("groupsheets", true, playersBoardWindow);
    }
    playersBoardWindow.setPlayerInfo(playerInfo);
  }

  @Override
  public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException {
    if(gameBoardWindow == null){
      gameBoardWindow = new GUIGameBoard(playersInfo);
      launchMainBoard(lastMapSelected, false, gameBoardWindow);
    }
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
  }

  private void launchMainBoard(String fxmlName, Boolean isPlayerStage, GUIGenericWindow controller){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + fxmlName + ".fxml"));
    loader.setController(controller);

    try {
      Parent root = loader.load();
      if(isPlayerStage){
        MyStage playersStage = new MyStage();
        playersStage.setScene(new Scene(root));
        playersStage.initStyle(StageStyle.UNDECORATED);

        playersStage.show();
      }
      else{
        MyStage boardStage = new MyStage();
        boardStage.setScene(new Scene(root));
        boardStage.initStyle(StageStyle.UNDECORATED);

        boardStage.show();
      }

    }
    catch(IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Initialization failed",
              e
      );
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
    }
    catch(IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Initialization Error",
              e
      );
    }
    return null;
  }

  /**
   * Helper function. Load a new Chooser PopUp Windows
   *
   * @param controller Controller for the popup
   */
  private Object loadChooser(GUIGenericWindow controller){
    return this.loadPopUpWindow("chooser", new MyStage(), controller);
  }

  public static class InvalidClosedGUIException extends RuntimeException {
    public InvalidClosedGUIException(String message){
      super(message);
    }
  }
}
