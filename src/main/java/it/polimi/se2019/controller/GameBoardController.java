package it.polimi.se2019.controller;

import it.polimi.se2019.rmi.UserTimeoutException;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The game board controller is the game manager, it initializes a game by
 * generating a map and instantiating the players, it then runs the turns until
 * the number of skulls reaches 0, after that it runs the final frenzy round and
 * ends the game.
 *
 * @author Eugenio OStrovan
 * @author Fabio Mauri
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
  private AtomicBoolean gameEnded = new AtomicBoolean(false);

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

    this.powerUpControllers.add(new NewtonController(this));
    this.powerUpControllers.add(new TagbackGrenadeController(this));
    this.powerUpControllers.add(new TargetingScopeController(this));
    this.powerUpControllers.add(new TeleporterController(this));
  }

  /**
   * getter methods for each relevant attribute
   * @return the list of players in this game
   */
  public List<Player> getPlayers(){
    return new LinkedList<>(this.players);
  }

  /**
   *
   * @return the list of player controllers in this game
   */
  public List<PlayerController> getPlayerControllers(){
    return new LinkedList<>(this.playerControllers);
  }

  /**
   *
   * @return the list of clients in this game
   */
  public List<PlayerViewOnServer> getClients(){
    return new LinkedList<>(this.clients);
  }

  /**
   *
   * @return the reference to the game's board
   */
  public GameBoard getGameBoard(){
    return this.gameBoard;
  }

  /**
   *
   * @return the list of weapon controllers in the game
   */
  public List<WeaponController> getWeaponControllers(){
    return new LinkedList<>(this.weaponControllers);
  }

  /**
   *
   * @return the list of all power up controllers
   */
  public List<PowerUpController> getPowerUpControllers(){
    return new LinkedList<>(this.powerUpControllers);
  }

  /**
   * add the player controllers to the game and set isReady to true so that
   * the game can start
   * @param c the list of player controllers to add to the game
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

  /**
   * get the player who has argument name
   * @param name the name of the player to identify
   * @return the player with argument name
   */
  public Player identifyPlayer(String name){
    Player player = null;
    for(Player p : this.players){
      if(name.equals(p.getName())){
        player = p;
      }
    }
    return player;
  }

  /**
   *
   * @param players the list of players who's names will be returned
   * @return the list of names of the given players
   */
  public static List<String> getPlayerNames(List<Player> players){
      return players.stream().map(Player::getName).collect(Collectors.toList());
  }

  /**
   * This method starts the game by generating a map, initializing players
   * and starting the main game loop
   * @param p the list of player controllers who will be playing the game
   */
  public void startGame(List<PlayerController> p) {
    addPlayerControllers(p);
    playTurns();
    playFrenzyTurn();
    this.gameEnded.set(true);
  }

  /**
   * @return true if the game handled by this board ended, false if it is running
   */
  public boolean isGameEnded(){
    return this.gameEnded.get();
  }
  /**
   * Id of the player currently playing on playerControllers
   */
  private int currentPlayer = 0;

  /**
   * This method is the main game loop, it makes players do actions on their
   * turn, replaces resources that have been picked up during a turn and
   * resolves player deaths.
   */
  public void playTurns() {
    this.currentPlayer = 0;
    int currentPlayerAvailableActions;
    Integer numberOfTurns = 0;
    //spawn all players
    for(int i = 0; i<players.size(); i++){
      try{
        sendInfo();
        try{
          clients.get(currentPlayer).sendGenericMessage("It is your turn to spawn");
        }
        catch(RemoteException f){
          //whatever
        }
        playerControllers.get(i).getState().spawn();
      }
      catch (UserTimeoutException e){
        handleDisconnection(e);
      }
    }

    int activePlayers = this.playerControllers.size();
    boolean actionResult;
    while(this.gameBoard.getKillScoreBoard().gameRunning()){
      try {
        //notify all players of who's turn it is
        for(PlayerViewOnServer c : clients){
          try{
            if(c != clients.get(currentPlayer)){
              c.sendGenericMessage("It is " + players.get(currentPlayer).getName() + "'s turn");
            }
            else{
              c.sendGenericMessage("It is your turn");
              c.sendGenericMessage(playerControllers.get(currentPlayer).getState().printInventory());
            }
          }
          catch(RemoteException f){
            //whatever
          }
        }

        //ask the current player to make his actions
        currentPlayerAvailableActions = playerControllers.get(currentPlayer).getState().getAvailableActions();
        while(currentPlayerAvailableActions > 0){
          sendInfo();
          try{
            clients.get(currentPlayer).sendGenericMessage("make an action");
          }
          catch(RemoteException f){
            //whatever
          }
          actionResult = playerControllers.get(currentPlayer).playTurn();
          if(actionResult){
            currentPlayerAvailableActions --;
          }
        }
        PlayerController.reloadWeapon(
                clients.get(currentPlayer),
                players.get(currentPlayer)
        );
        refillSquares();
        endOfTurnDeathResolution();
      }
      catch (UserTimeoutException e){
        handleDisconnection(e);
      }
      currentPlayer++;
      numberOfTurns++;
      if(numberOfTurns > 500 || activePlayers < 3){
        break;
      }
      if(currentPlayer >= players.size()){
        activePlayers = this.playerControllers.size();
        currentPlayer = 0;
      }
    }
  }

  /**
   * Refill all squares
   */
  private void refillSquares(){
    for(int i = 0; i<3; i++){
      for(int k = 0; k<4; k++){
        if(gameBoard.getMap().getMapSquares()[i][k] != null){
          gameBoard.getMap().getMapSquares()[i][k].refill();
        }
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
    int currentPlayerAvailableActions;
    boolean actionResult;
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
        currentPlayerAvailableActions = playerControllers.get(currentPlayer).getState().getAvailableActions();
        while(currentPlayerAvailableActions > 0){
          actionResult = playerControllers.get(currentPlayer).playTurn();
          if(actionResult){
            currentPlayerAvailableActions --;
          }
        }
        endOfTurnDeathResolution();
      }
      catch (UserTimeoutException e){
        handleDisconnection(e);
      }
      currentPlayer++;
      if(currentPlayer == players.size()){
        currentPlayer = 0;
      }
    }
    gameBoard.getKillScoreBoard().resolveScoreboard();
  }


  /**
   * Send to the client the current state of the gameboard
   *
   * @throws UserTimeoutException if the user is disconnected
   */
  private void sendInfo() throws UserTimeoutException {
    try {
      //PlayerViewOnServer client = playerControllers.get(currentPlayer).getClient();

      for(PlayerViewOnServer c : clients){
        c.sendPlayerInfo(this.genPlayerInfo(players.get(currentPlayer)));

        c.sendMapInfo(this.genMapInfo());

        c.sendKillScoreBoardInfo(this.genKillScoreboardInfo());
      }
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
      if (currentAmmoTile != null) {
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
      }
      else{
        toReturn.add("no ammo tiles");
      }
    }
    else {
        toReturn.add(Integer.toString(square.getItem().size()));
        for(int i = 0; i<square.getItem().size(); i++){
            if(square.getItem().get(i) != null){
                toReturn.add(square.getItem().get(i).toString());
            }
            else{
              toReturn.add("no weapon");
            }
        }
    }
    currentSquare.clear();
    currentSquare.add(square);
    toReturn.addAll(
            this.gameBoard.getMap().getPlayersOnSquares(currentSquare).stream()
                    .map(Player::getName)
                    .collect(Collectors.toList())
    );

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
    // adding board state
    ArrayList<String> state = new ArrayList<>();
    state.add(0, curPlayer.getName());
    state.add(1, curPlayer.getBoard().getIfIsFrenzy() ? "frenzy" : "normal");
    toReturn.add(3, state);

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
    ArrayList<String> tmp = new ArrayList<>();
    for (int i = 0; i < curKillBoard.getKills().size(); i++) {
      tmp.add(curKillBoard.getKills().get(i).getName());
      tmp.add(curKillBoard.getOverKills().get(i) ? "true" : "false");
    }

    toReturn.add(
            0,
            tmp
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
    Integer numberOfDeaths = 0;
    for(Player p : players){
      if(p.getBoard().getDamageReceived().size() > 11){
        if(numberOfDeaths > 0){
          gameBoard.getKillScoreBoard().addDoubleKill(p.getBoard().getDamageReceived()
                  .get(p.getBoard().getDamageReceived().size() -1));
        }
        p.resolveDeath();
        numberOfDeaths ++;
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

  /**
   * Helper function for handling a player disconnection exception
   *
   * @param e Disconnection Exception
   */
  private static void handleDisconnection(UserTimeoutException e){
    Logger.getLogger(LOG_NAMESPACE).log(
            Level.INFO,
            "User Disconnected",
            e
    );
  }
}
