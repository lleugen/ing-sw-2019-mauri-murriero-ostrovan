package it.polimi.se2019.controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.powerup.*;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.controller.weapons.alternative_effects.*;
import it.polimi.se2019.controller.weapons.optional_effects.*;
import it.polimi.se2019.controller.weapons.ordered_effects.*;
import it.polimi.se2019.controller.weapons.simple.*;
import it.polimi.se2019.model.KillScoreBoard;
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
  private GameBoard gameBoard;
  private List<WeaponController> weaponControllers;
  private List<PowerUpController> powerUpControllers;


  public GameBoardController(GameBoard g) {
    this.gameBoard = g;
    this.weaponControllers = new LinkedList<>();
    this.powerUpControllers = new LinkedList<>();
    this.players = g.getPlayers();

    this.weaponControllers.add(new CyberBladeController(this));
    this.weaponControllers.add(new ElectroscytheController(this));
    this.weaponControllers.add(new PlasmaGunController(this));
    this.weaponControllers.add(new GrenadeLauncherController(this));
    this.weaponControllers.add(new RocketLauncherController(this));
    this.weaponControllers.add(new HellionController(this));
    this.weaponControllers.add(new TractorBeamController(this));
    this.weaponControllers.add(new LockRifleController(this));
    this.weaponControllers.add(new VortexCannonController(this));
    this.weaponControllers.add(new MachineGunController(this));
    this.weaponControllers.add(new ThorController(this));
    this.weaponControllers.add(new HeatSeekerController(this));
    this.weaponControllers.add(new WhisperController(this));
    this.weaponControllers.add(new FurnaceController(this));
    this.weaponControllers.add(new RailGunController(this));
    this.weaponControllers.add(new ShotgunController(this));
    this.weaponControllers.add(new ZX2Controller(this));
    this.weaponControllers.add(new FlameThrowerController(this));
    this.weaponControllers.add(new PowerGloveController(this));
    this.weaponControllers.add(new ShockwaveController(this));
    this.weaponControllers.add(new SledgeHammerController(this));

    this.powerUpControllers.add(new NewtonController());
    this.powerUpControllers.add(new TagbackGrenadeController());
    this.powerUpControllers.add(new TargetingScopeController());
    this.powerUpControllers.add(new TeleporterController());
  }

  /**
   * getter methods for each relevant attribute
   */
  public List<Player> getPlayers(){
    return new LinkedList<>(this.players);
  }

  public List<PlayerController> getPlayerControllers(){
    return new LinkedList<>(this.playerControllers);
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
    this.playerControllers = new LinkedList<>(c);
    this.players = c.stream()
            .map(PlayerController::getPlayer)
            .collect(Collectors.toList());
    this.clients = c.stream()
            .map(PlayerController::getClient)
            .collect(Collectors.toList());
  }

  public Player identifyPlayer(String name){
    Player player = null;
    for(Player p : this.players){
      if(name.equals(p.getName())){
        player = p;
      }
    }
    return player;
  }

  public static List<String> getPlayerNames(List<Player> players){
      return players.stream().map(Player::getName).collect(Collectors.toList());
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
    this.currentPlayer = 0;

    while(this.gameBoard.getKillScoreBoard().gameRunning()){
      try {
        sendInfo();
        this.playerControllers.get(this.currentPlayer).playTurn(
                this.playerControllers.get(
                        this.currentPlayer
                ).getState().getAvailableActions()
        );
        endOfTurnDeathResolution();
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
      PlayerViewOnServer client = playerControllers.get(currentPlayer).getClient();

      client.sendMapInfo(this.genMapInfo());
      client.sendPlayerInfo(
              this.genPlayerInfo(
                      playerControllers.get(currentPlayer).getPlayer()
              )
      );
      client.sendKillScoreBoardInfo(this.genKillScoreboardInfo());
    }
    catch (RemoteException e){
      throw new UserTimeoutException(e);
    }

  }

  /**
   * Gen mapInfo to send down to the client
   *
   * @return Generated MapInfo
   */
  private List<ArrayList<ArrayList<String>>> genMapInfo(){
    List<ArrayList<ArrayList<String>>> toReturn = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      toReturn.add(i, new ArrayList<>());
      for (int k = 0; k < 4; k++) {
        Square square = this.gameBoard.getMap().getMapSquares()[i][k];
        toReturn.get(i).add(k, new ArrayList<>());
        if (square != null) {
          toReturn.get(i).get(k).addAll(this.getMapSquareInfo(square));
        }
        else {
          toReturn.get(i).get(k).add("NR");
        }
      }
    }

    return toReturn;
  }

  /**
   * Get the info for a single square of the map
   *
   * @param square Square to generate info about
   */
  private List<String> getMapSquareInfo(Square square){
    List<String> toReturn = new ArrayList<>();
    AmmoTile currentAmmoTile;
    List<Square> currentSquare = new ArrayList<>();

    if (square instanceof AmmoSquare) {
      currentAmmoTile = (AmmoTile) square.getItem().get(0);
      for (int l = 0; l < currentAmmoTile.getAmmo().getRed(); l++) {
        toReturn.add("red");
      }
      for (int l = 0; l < currentAmmoTile.getAmmo().getBlue(); l++) {
        toReturn.add("blue");
      }
      for (int l = 0; l < currentAmmoTile.getAmmo().getYellow(); l++) {
        toReturn.add("yellow");
      }
      if (currentAmmoTile.getPowerUp()) {
        toReturn.add("power up");
      }
      toReturn.addAll(
              this.gameBoard.getMap().getPlayersOnSquares(currentSquare).stream()
                      .map(Player::getName)
                      .collect(Collectors.toList())
      );
    }
    else {
      toReturn.add("Spawn");
    }

    return toReturn;
  }

  /**
   * Gen PlayerInfo to send down to the client
   *
   * @param curPlayer Player to gen info about
   *
   * @return The generated PlayerInfo
   */
  private List<ArrayList<String>> genPlayerInfo(Player curPlayer){
    List<ArrayList<String>> toReturn = new ArrayList<>();

    // adding damages
    toReturn.add(
            0,
            curPlayer.getBoard().getDamageReceived().stream()
                    .map(Player::getName)
                    .collect(Collectors.toCollection(ArrayList::new))
    );
    // adding marks
    toReturn.add(
            1,
            curPlayer.getBoard().getMarksAssigned().stream()
                    .map(Player::getName)
                    .collect(Collectors.toCollection(ArrayList::new))
    );
    // adding deaths
    toReturn.add(
            2,
            curPlayer.getBoard().getDeathValue().stream()
                    .limit(1)
                    .map(String::valueOf)
                    .collect(Collectors.toCollection(ArrayList::new))
    );

    return toReturn;
  }

  /**
   * Gen KillScoreboardInfo to send down to the player
   *
   * @return The generated KillScoreboardInfo
   */
  private List<ArrayList<String>> genKillScoreboardInfo(){
    List<ArrayList<String>> toReturn = new ArrayList<>();

    KillScoreBoard curKillBoard = this.gameBoard.getKillScoreBoard();
    // Adding kills
    toReturn.add(
            0,
            curKillBoard.getKills().stream()
                    .map(Player::getName)
                    .collect(Collectors.toCollection(ArrayList::new))
    );
    // Adding double kills
    toReturn.add(
            1,
            curKillBoard.getDoubleKills().stream()
                    .map(Player::getName)
                    .collect(Collectors.toCollection(ArrayList::new))
    );

    return toReturn;
  }
  /**
   * deal with player deaths at the end of each turn
   */
  private void endOfTurnDeathResolution() throws UserTimeoutException{
    PlayerController currentPlayerController;
    for(Player p : players){
      if(p.getBoard().getDamageReceived().size() > 11){
        p.resolveDeath();
        for(PlayerController pc : playerControllers){
          if(pc.getName().equals(p.getName())){
            currentPlayerController = pc;
            try{
              currentPlayerController.getState().spawn();
            }
            catch (UserTimeoutException e){
              throw new UserTimeoutException(e);
            }
          }
        }
      }
    }
  }
}
