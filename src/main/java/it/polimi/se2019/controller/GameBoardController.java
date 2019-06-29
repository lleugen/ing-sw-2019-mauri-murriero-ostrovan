package it.polimi.se2019.controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.powerup.*;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.controller.weapons.alternative_effects.*;
import it.polimi.se2019.controller.weapons.optional_effects.*;
import it.polimi.se2019.controller.weapons.ordered_effects.*;
import it.polimi.se2019.controller.weapons.simple.*;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The game board controller is the game manager, it initializes a game by
 * generating a map and instantiating the players, it then runs the turns until
 * the number of skulls reaches 0, after that it runs the final frenzy round and
 * ends the game.
 */
public class GameBoardController{
  /**
   * Namespace this class log to
   */
  private static final String LOG_NAMESPACE = "GameBoardController";

  private List<Player> players;
  private List<PlayerController> playerControllers;
  private List<PlayerViewOnServer> clients;
  private List<String> clientNames;
  private GameBoard gameBoard;
  private List<WeaponController> weaponControllers;
  private List<PowerUpController> powerUpControllers;

  private List<ArrayList<ArrayList<String>>> mapInfo;
  private List<ArrayList<String>> playerInfo;
  private List<ArrayList<String>> killScoreBoardInfo;

  public GameBoardController(GameBoard g) {
    gameBoard = g;
    weaponControllers = new LinkedList<>();
    powerUpControllers = new LinkedList<>();
    players = new LinkedList<>();

    mapInfo = new ArrayList<ArrayList<ArrayList<String>>>();
    for(int i = 0; i<3; i++){
      mapInfo.add(new ArrayList<ArrayList<String>>());
      for(int k = 0; k<4; k++){
          mapInfo.get(i).add(new ArrayList<String>());
      }
    }
    playerInfo = new ArrayList<>();
    killScoreBoardInfo = new ArrayList<>();

    weaponControllers.add(new CyberBladeController(this));
    weaponControllers.add(new ElectroscytheController(this));
    weaponControllers.add(new PlasmaGunController(this));
    weaponControllers.add(new GrenadeLauncherController(this));
    weaponControllers.add(new RocketLauncherController(this));
    weaponControllers.add(new HellionController(this));
    weaponControllers.add(new TractorBeamController(this));
    weaponControllers.add(new LockRifleController(this));
    weaponControllers.add(new VortexCannonController(this));
    weaponControllers.add(new MachineGunController(this));
    weaponControllers.add(new ThorController(this));
    weaponControllers.add(new HeatSeekerController(this));
    weaponControllers.add(new WhisperController(this));
    weaponControllers.add(new FurnaceController(this));
    weaponControllers.add(new RailGunController(this));
    weaponControllers.add(new ShotgunController(this));
    weaponControllers.add(new ZX2Controller(this));
    weaponControllers.add(new FlameThrowerController(this));
    weaponControllers.add(new PowerGloveController(this));
    weaponControllers.add(new ShockwaveController(this));
    weaponControllers.add(new SledgeHammerController(this));

    powerUpControllers.add(new NewtonController());
    powerUpControllers.add(new TagbackGrenadeController());
    powerUpControllers.add(new TargetingScopeController());
    powerUpControllers.add(new TeleporterController());
  }

  /**
   * getter methods for each relevant attribute
   */
  public List<Player> getPlayers(){
    return new LinkedList<>(this.players);
  }

  public List<PlayerViewOnServer> getClients(){
    return new LinkedList<>(this.clients);
  }

  public GameBoard getGameBoard(){
    return this.gameBoard;
  }

  public List<WeaponController> getWeaponControllers(){
    return new LinkedList<>(this.weaponControllers);
  }

  public List<PowerUpController> getPowerUpControllers(){
    return new LinkedList<>(this.powerUpControllers);
  }

  /**
   * add the player controllers to the game and set isReady to true so that
   * the game can start
   */
  public void addPlayerControllers(List<PlayerController> c){
    playerControllers = new LinkedList<>(c);
    players = c.stream()
            .map(PlayerController::getPlayer)
            .collect(Collectors.toList());
    clientNames = c.stream()
            .map(PlayerController::getName)
            .collect(Collectors.toList());
  }

  public Player identifyPlayer(String name){
    Player player = null;
    for(Player p : players){
      if(name.equals(p.getName())){
        player = p;
      }
    }
    return player;
  }

  public List<String> getPlayerNames(List<Player> players){
      return new LinkedList<>(this.clientNames);
  }

  /**
   * This method starts the game by generating a map, initializing players
   * and starting the main game loop
   */
  public void startGame(List<PlayerController> p) {
    addPlayerControllers(p);
    playTurns();
    playFrenzyTurn();

  }

  /**
   * This method is the main game loop, it makes players do actions on their
   * turn, replaces resources that have been picked up during a turn and
   * resolves player deaths.
   */
  int currentPlayer = 0;
  public void playTurns() {
    currentPlayer = 0;
    while(gameBoard.getKillScoreBoard().gameRunning()){
      try {
        sendInfo();
        playerControllers.get(currentPlayer).
                playTurn(playerControllers.get(currentPlayer).getState().getAvailableActions());
      }
      catch (UserTimeoutException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.INFO,
                "User Disconnected",
                e
        );
      }
      currentPlayer++;
      if(currentPlayer == players.size()){
        currentPlayer = 0;
      }
    }
  }

  /**
   * This method manages the final frenzy round which is played when the
   * number of skulls on the scoreboard reaches 0 after this final round the
   * scoreboard will be resolved and the game will end
   */
  public void playFrenzyTurn() {
    //player the last turn and end the game
    gameBoard.setFrenzy();
    for(Player p : players){
      if(p.getBoard().getDamageReceived().isEmpty()){
        p.getBoard().turnAround();
      }
    }
    for(int i = currentPlayer; i<players.size(); i++){
      playerControllers.get(i).setState(3);
    }
    for(int i = 0; i<currentPlayer; i++){
      playerControllers.get(i).setState(4);
    }
    for(int i = 0; i<players.size(); i++){
      try {
        sendInfo();
        playerControllers.get(currentPlayer).playTurn
              (playerControllers.get(currentPlayer).getState().getAvailableActions());
      }
      catch (UserTimeoutException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.INFO,
                "User Disconnected",
                e
        );
      }
      currentPlayer++;
      if(currentPlayer == players.size()){
        currentPlayer = 0;
      }
    }
  }


  /**
   * Send to the client the current state of the gameboard
   *
   * @throws UserTimeoutException if the user is disconnected
   */
  private void sendInfo() throws UserTimeoutException {
    try {
      AmmoTile currentAmmoTile = null;
      List<Player> playersOnSquare = new ArrayList<>();
      List<Square> currentSquare = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
        for (int k = 0; k < 4; k++) {

          if (gameBoard.getMap().getMapSquares()[i][k] != null) {
            if (gameBoard.getMap().getMapSquares()[i][k] instanceof AmmoSquare) {
              currentAmmoTile = (AmmoTile) gameBoard.getMap().getMapSquares()[i][k].getItem().get(0);
              for (int l = 0; l < currentAmmoTile.getAmmo().getRed(); l++) {
                mapInfo.get(i).get(k).add("red");
              }
              for (int l = 0; l < currentAmmoTile.getAmmo().getBlue(); l++) {
                mapInfo.get(i).get(k).add("blue");
              }
              for (int l = 0; l < currentAmmoTile.getAmmo().getYellow(); l++) {
                mapInfo.get(i).get(k).add("yellow");
              }
              if (currentAmmoTile.getPowerUp()) {
                mapInfo.get(i).get(k).add("power up");
              }
              currentSquare.clear();
              currentSquare.add(gameBoard.getMap().getMapSquares()[i][k]);
              playersOnSquare = gameBoard.getMap().getPlayersOnSquares(currentSquare);
              for (Player p : playersOnSquare) {
                mapInfo.get(i).get(k).add(p.getName());
              }
            }
          } else {
            mapInfo.get(i).get(k).add("NR");
          }
        }
      }
      playerControllers.get(currentPlayer).getClient().sendMapInfo(mapInfo);

      for (int i = 0; i < 3; i++) {
        playerInfo.get(i).clear();
      }
      for (int i = 0; i < gameBoard.getPlayers().get(currentPlayer).getBoard().getDamageReceived().size(); i++) {
        playerInfo.get(0)
                .add(gameBoard.getPlayers().get(currentPlayer).getBoard().getDamageReceived().get(i).getName());
      }
      for (int i = 0; i < gameBoard.getPlayers().get(currentPlayer).getBoard().getMarksAssigned().size(); i++) {
        playerInfo.get(1)
                .add(gameBoard.getPlayers().get(currentPlayer).getBoard().getMarksAssigned().get(i).getName());
      }
      playerInfo.get(3).add(gameBoard.getPlayers().get(currentPlayer).getBoard().getDeathValue().get(0).toString());
      playerControllers.get(currentPlayer).getClient().sendPlayerInfo(playerInfo);


      for (int i = 0; i < 2; i++) {
        killScoreBoardInfo.get(i).clear();
      }
      for (int i = 0; i < gameBoard.getKillScoreBoard().getKills().size(); i++) {
        killScoreBoardInfo.get(0).add(gameBoard.getKillScoreBoard().getKills().get(i).getName());
      }
      for (int i = 0; i < gameBoard.getKillScoreBoard().getDoubleKills().size(); i++) {
        killScoreBoardInfo.get(0).add(gameBoard.getKillScoreBoard().getDoubleKills().get(i).getName());
      }
      playerControllers.get(currentPlayer).getClient().sendKillScoreBoardInfo(killScoreBoardInfo);
    }
    catch (RemoteException e){
      throw new UserTimeoutException(e);
    }

  }
}
