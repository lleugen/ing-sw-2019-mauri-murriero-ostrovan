package it.polimi.se2019.controller;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.controller.weapons.alternative_effects.*;
import it.polimi.se2019.controller.weapons.optional_effects.*;
import it.polimi.se2019.controller.weapons.ordered_effects.*;
import it.polimi.se2019.controller.weapons.simple.*;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.view.player.PlayerView;

import java.util.List;

/**
 * The game board controller is the game manager, it initializes a game by
 * generating a map and instantiating the players, it then runs the turns until
 * the number of skulls reaches 0, after that it runs the final frenzy round and
 * ends the game.
 */
public class GameBoardController{
  public GameBoardController(List<Player> p, List<PlayerView> v, GameBoard g) {
    players = p;
    clients = v;
    gameBoard = g;
    isReady = false;
    weaponControllers.add(new CyberBladeController());
    weaponControllers.add(new ElectroscytheController());
    weaponControllers.add(new PlasmaGunController());
    weaponControllers.add(new GrenadeLauncherController());
    weaponControllers.add(new RocketLauncherController());
    weaponControllers.add(new HellionController());
    weaponControllers.add(new TractorBeamController());
    weaponControllers.add(new LockRifleController());
    weaponControllers.add(new  VortexCannonController());
    weaponControllers.add(new MachineGunController());
    weaponControllers.add(new ThorController());
    weaponControllers.add(new HeatSeekerController());
    weaponControllers.add(new WhisperController());
    weaponControllers.add(new FurnaceController());
    weaponControllers.add(new RailGunController());
    weaponControllers.add(new ShotgunController());
    weaponControllers.add(new ZX2Controller());
    weaponControllers.add(new FlameThrowerController());
    weaponControllers.add(new PowerGloveController());
    weaponControllers.add(new ShockwaveController());
    weaponControllers.add(new SledgeHammerController());
  }

  private List<Player> players;
  private List<PlayerController> playerControllers;
  private List<PlayerView> clients;
  private GameBoard gameBoard;
  private List<WeaponController> weaponControllers;
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



  /**
   * This method starts the game by generating a map, initializing players
   * and starting the main game loop
   */
  public void startGame() {
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
