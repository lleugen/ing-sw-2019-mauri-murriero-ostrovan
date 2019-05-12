package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.List;

public class PlayerStateController {

    private Player player;
    private PlayerView client;
    private GameBoardController gameBoardController;

    /**
     * Move the player 1 square in one of four directions
     * direction is the direction that the players moves towards
     *                  0 = north
     *                  1 = east
     *                  2 = south
     *                  3 = west
     */
    public void move() {
        int direction = client.chooseMoveDirection();
        //move the player
        if(!player.getPosition().getAdjacencies().get(direction).isBlocked()){
            player.move(player.getPosition().getAdjacencies().get(direction));
        }
        else{
            throw new DirectionBlockedException();
        }
    }

    /**
     * Make the player spawn at the start of the game or after being killed.
     * The player has to draw a power up card, then discard one and spawns in
     * the square corresponding to the discarded card's equivalent ammo colour.
     */
    public void spawn() {
        player.getInventory().addPowerUpToInventory(gameBoardController.getGameBoard().getDecks().drawPowerUp());
        //ask player to choose a power up card to discard, return a colour
        //client will have to be the PlayerView stub
        int discardedCard = client.chooseSpawnLocation();
        if(player.getInventory().getPowerUps().get(discardedCard).getAmmoEquivalent().getRed() == 1){
            //spawn on the red spawnpoint
            player.respawn(gameBoardController.getGameBoard().getMap().getRedSpawnPoint());
        }
        else if(player.getInventory().getPowerUps().get(discardedCard).getAmmoEquivalent().getBlue() == 1){
            //spawn on the blue spawnpoint
            player.respawn(gameBoardController.getGameBoard().getMap().getBlueSpawnPoint());
        }
        else{
            //spawn on the yellow spawnpoint
            player.respawn(gameBoardController.getGameBoard().getMap().getYellowSpawnPoint());
        }
        player.getInventory().discardPowerUp(player.getInventory().getPowerUps().get(discardedCard));
    }

    /**
     * Make the player choose a weapon from his or her inventory and fire it.
     * Once the weapon is chosen, weapon specific methods will be invoked to
     * choose targets and fire.
     */
    public void shoot() {
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

    }

    /**
     * Spend ammo to reload an unloaded weapon from the player's inventory.
     * Choose which weapon to reload, subtract the reload cost from inventory
     * and set the weapon as loaded.
     */
    public void reload() {
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
    public void grab() {
        Square position = player.getPosition();
        int index = client.chooseItemToGrab();
        if(position instanceof SpawnSquare){
            //return here when add to inventory method is finished
            player.getInventory().addWeaponToInventory((Weapon)position.grab(index));
        }
        else{
            player.getInventory().addAmmoTileToInventory((AmmoTile) position.grab(index));
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
    public static class DirectionBlockedException extends RuntimeException{
        @Override
        public String toString(){
            return"There is a wall in that direction.";
        }
    }
}
