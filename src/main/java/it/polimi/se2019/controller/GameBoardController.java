package it.polimi.se2019.controller;

import it.polimi.se2019.controller.powerup.*;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.controller.weapons.alternative_effects.*;
import it.polimi.se2019.controller.weapons.optional_effects.*;
import it.polimi.se2019.controller.weapons.ordered_effects.*;
import it.polimi.se2019.controller.weapons.simple.*;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * The game board controller is the game manager, it initializes a game by
 * generating a map and instantiating the players, it then runs the turns until
 * the number of skulls reaches 0, after that it runs the final frenzy round and
 * ends the game.
 */
public class GameBoardController{
  public GameBoardController(GameBoard g) {
    gameBoard = g;
    isReady = false;
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

  private List<Player> players;
  private List<PlayerController> playerControllers;
  private List<PlayerView> clients;
  private GameBoard gameBoard;
  private List<WeaponController> weaponControllers;
  private List<PowerUpController> powerUpControllers;
  private boolean isReady;

  /**
   * getter methods for each relevant attribute
   */
  public List<Player> getPlayers(){
    return players;
  }

  public List<PlayerView> getClients(){
    return clients;
  }

  public GameBoard getGameBoard(){
    return gameBoard;
  }

  public List<WeaponController> getWeaponControllers(){
    return weaponControllers;
  }

  public List<PowerUpController> getPowerUpControllers(){
    return powerUpControllers;
  }

  /**
   * add the player controllers to the game and set isReady to true so that the game can start
   */
  public void addPlayerControllers(List<PlayerController> c){
    playerControllers = c;
    isReady = true;
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
      List<String> names = new ArrayList<>();
      for(Player p : players){
          names.add(p.getName());
      }
      return names;
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
  public void playTurns() {
    int currentPlayer = 0;
    while(gameBoard.getKillScoreBoard().gameRunning()){
      playerControllers.get(currentPlayer).
              playTurn(playerControllers.get(currentPlayer).getState().getAvailableActions());
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
  }
}
