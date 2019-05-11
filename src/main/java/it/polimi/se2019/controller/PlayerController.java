package it.polimi.se2019.controller;

import it.polimi.se2019.controller.player_state.PlayerStateController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.server.GameBoard;
import it.polimi.se2019.view.player.PlayerView;

import java.util.List;

/**
 * This class controls player actions, it contains the player's current state
 * which is determined by the amount of damage received or the state of
 * the game (regular turns or final frenzy round).
 * The class contains the implementation of all basic possible actions
 * (move, grab, shoot, reload, respawn) which are combined into different
 * complex actions that a player can make during a turn
 */
public class PlayerController {

  private GameBoardController gameBoardController;
  private PlayerView client;
  private Player player;

  /**
   *
   */
  public PlayerController(GameBoardController gameBoard, Player p, PlayerView c) {
    gameBoardController = gameBoard;
    client = c;
    player = p;
  }

  /**
   * Identify a player given his name
   */
  public Player identifyPlayer(String playerName){
    Player player = null;
    for(Player p : gameBoardController.getPlayers()){
      if(p.getName().equals(playerName)){
        player = p;
      }
    }
    if(player != null){
      return player;
    }
    else{
      throw new NonExistingPlayerException();
    }
  }

  /**
   * Move the player 1 square in one of four directions
   * @param playerName the name of the player
   * @param direction is the direction that the players moves towards
   *                  0 = north
   *                  1 = east
   *                  2 = south
   *                  3 = west
   */
  public void move(String playerName, int direction) {
    //find the player with playerName
    Player player = identifyPlayer(playerName);
    //move the player
    player.move(player.getPosition().getAdjacencies().get(direction));

  }

  /**
   * Make the player spawn at the start of the game or after being killed.
   * The player has to draw a power up card, then discard one and spawns in
   * the square corresponding to the discarded card's equivalent ammo colour.
   */
  public void spawn(String playerName) {
    Player player = identifyPlayer(playerName);
    player.getInventory().addPowerUpToInventory(player.getMatch().getDecks().drawPowerUp());
    //ask player to choose a power up card to discard, return a colour
    String colour = client.chooseSpawnLocation();
    if(colour.equals("red")){
      //spawn on the red spawnpoint
      player.respawn(gameBoardController.getGameBoard().getMap().getRedSpawnPoint());
    }
    else if(colour.equals("blue")){
      //spawn on the blue spawnpoint
      player.respawn(gameBoardController.getGameBoard().getMap().getBlueSpawnPoint());
    }
    else{
      //spawn on the yellow spawnpoint
      player.respawn(gameBoardController.getGameBoard().getMap().getYellowSpawnPoint());
    }
  }

  /**
   * Make the player choose a weapon from his or her inventory and fire it.
   * Once the weapon is chosen, weapon specific methods will be invoked to
   * choose targets and fire.
   */
  public void shoot(String playerName) {
    String weapon = client.chooseWeapon();
    WeaponController weaponController = null;
    List<String> targetNames = null;
    List<String> chosenTargetNames = null;
    List<Player> chosenTargets = null;

    for(WeaponController w : gameBoardController.getWeaponControllers()){
      if(w.getName().equals(weapon)){
        weaponController = w;
      }
    }

    //weapon will choose the targets, but weapon needs a reference to the client
    /*
    List<Player> targets = weaponController.findTargets(identifyPlayer(playerName));
    for(Player p : targets){
      targetNames.add(p.getName());
    }
    chosenTargetNames = client.chooseTargets(targetNames);
    for(String s : chosenTargetNames){
      chosenTargets.add(identifyPlayer(s));
    }
    */
    weaponController.shootTargets();

    weaponController.
  }

  /**
   * Spend ammo to reload an unloaded weapon from the player's inventory.
   * Choose which weapon to reload, subtract the reload cost from inventory
   * and set the weapon as loaded.
   */
  public void reload(String playerName) {
    Player player = identifyPlayer(playerName);
    List<String> playersWeapons = null;
    String weaponToReloadName = null;
    List<Integer> cardsToUseIndexes = null;
    List<PowerUpCard> cardsToUse = null;

    for(Weapon w : player.getInventory().getWeapons()){
      playersWeapons.add(w.getName());
    }
    weaponToReloadName = client.chooseWeaponToReaload(playersWeapons);

    for(Weapon w : player.getInventory().getWeapons()){
      if(w.getName().equals(weaponToReloadName)){
        cardsToUseIndexes = client.choosePowerUpCardsForReload();
        for(int i : cardsToUseIndexes){
          cardsToUse.add(player.getInventory().getPowerUps().get(i));
        }
        w.reload(cardsToUse, player.getInventory().getAmmo());
      }
    }
  }

  /**
   * Grab the ammo tile or a weapon from the current square and add
   * the corresponding resources to the inventory.
   */
  public void grab(String playerName) {
    Player player = identifyPlayer(playerName);
    Square position = player.getPosition();
    int index = client.chooseItemToGrab();
    if(position instanceof SpawnSquare){
      //return here when add to inventory method is finished
      player.getInventory().addWeaponToInventory(position.grab(index));
    }
    else{
      player.getInventory().addAmmoTileToInventory(position.grab(index));
    }
  }

  public static class InvalidMovementException extends RuntimeException{
    @Override
    public String toString(){
      return"This movement is not possible";
    }
  }
  public static class NonExistingPlayerException extends RuntimeException{
    @Override
    public String toString(){
      return"This player does not exist";
    }
  }
}
