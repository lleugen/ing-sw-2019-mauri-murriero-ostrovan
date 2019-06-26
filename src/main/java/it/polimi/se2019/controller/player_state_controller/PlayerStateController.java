package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.powerup.PowerUpController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerStateController {

    protected Player player;
    protected PlayerViewOnServer client;
    protected GameBoardController gameBoardController;
    protected Integer availableActions;
    protected Map map;

    public PlayerStateController(GameBoardController g, Player p, PlayerViewOnServer c){
        gameBoardController = g;
        player = p;
        client = c;
        map = gameBoardController.getGameBoard().getMap();
    }

    public Integer getAvailableActions(){
        return  availableActions;
    }

    public abstract void runAround();
    public abstract void grabStuff();
    public abstract void shootPeople();

    /**
     * Move the player 1 square in one of four directions
     * direction is the direction that the players moves towards
     *                  0 = north
     *                  1 = east
     *                  2 = south
     *                  3 = west
     */
    public void move() {
        int direction = 0;
        try{
            direction = client.chooseDirection(map.getOpenDirections(player.getPosition()));
        }
        catch(UserTimeoutException e){
            //remove player from game
            client.setConnected(false);
        }

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
        player.getInventory().addPowerUpToInventory(player.getInventory().decksReference.drawPowerUp());
        //ask player to choose a power up card to discard, return a colour
        //view will have to be the PlayerView stub
        List<String> powerUps = new ArrayList<>();
        for(PowerUpCard p : player.getInventory().getPowerUps()){
            powerUps.add(p.getDescription());
        }
        int discardedCard;
        try{
            discardedCard = client.chooseSpawnLocation(powerUps);
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
        catch(UserTimeoutException e){
            //remove player from game
            client.setConnected(false);
        }


    }

    /**
     * Make the player choose a weapon from his or her inventory and fire it.
     * Once the weapon is chosen, weapon specific methods will be invoked to
     * choose targets and fire.
     */
    public void shoot() {
        List<String> weapons = new ArrayList<>();
        while(player.getInventory().getWeapons().iterator().hasNext()){
            weapons.add(player.getInventory().getWeapons().iterator().next().getName());
        }
        String weapon = null;
        try{
            weapon = client.chooseWeapon(weapons);
        }
        catch(UserTimeoutException e){
            //remove player from game
            client.setConnected(false);
        }


        WeaponController weaponController = null;
        for(WeaponController w : gameBoardController.getWeaponControllers()){
            if(w.getName().equals(weapon)){
                weaponController = w;
            }
        }

        weaponController.fire(player, client);
    }

    /**
     * Spend ammo to reload an unloaded weapon from the player's inventory.
     * Choose which weapon to reload, subtract the reload cost from inventory
     * and set the weapon as loaded.
     */
    public void reload() {
        List<String> playersWeapons = new ArrayList<>();
        String weaponToReloadName = null;
        List<Integer> cardsToUseIndexes = new ArrayList<>();
        List<PowerUpCard> cardsToUse = new ArrayList<>();

        //make a list of the player's weapons
        for(Weapon w : player.getInventory().getWeapons()){
            playersWeapons.add(w.getName());
        }
        //choose which one to reload
        try{
            weaponToReloadName = client.chooseWeaponToReload(playersWeapons);

            //get the weapon given its name
            for(Weapon w : player.getInventory().getWeapons()){
                if(w.getName().equals(weaponToReloadName)){
                    //choose which power up cards to use for reloading
                    List<String> powerUps = new ArrayList<>();
                    for(PowerUpCard p : player.getInventory().getPowerUps()){
                        powerUps.add(p.getDescription());
                    }
                    cardsToUseIndexes = client.choosePowerUpCardsForReload(powerUps);
                    //get the chosen cards
                    for(int i : cardsToUseIndexes){
                        cardsToUse.add(player.getInventory().getPowerUps().get(i));
                    }
                    //reload the weapon
                    w.reload(cardsToUse, player.getInventory().getAmmo());
                }
            }
        }
        catch(UserTimeoutException e){
            //remove player from game
            client.setConnected(false);
        }

    }

    /**
     * Grab the ammo tile or a weapon from the current square and add
     * the corresponding resources to the inventory.
     */
    public void grab() {
        Square position = player.getPosition();
        int index = 0;
        try {
            index = client.chooseItemToGrab();
        }
        catch(UserTimeoutException e){
            //remove player from game
            client.setConnected(false);
        }

        if(position instanceof SpawnSquare){
            //return here when add to inventory method is finished
            player.getInventory().addWeaponToInventory((Weapon)position.grab(index));
        }
        else{
            player.getInventory().addAmmoTileToInventory((AmmoTile) position.grab(index));
        }
    }

    /**
     * Use a power up from the inventory
     */
    public void usePowerUp(){
        List<String> powerUpCardsInInventory = new ArrayList<>();
        for(PowerUpCard p : player.getInventory().getPowerUps()){
            powerUpCardsInInventory.add(p.getDescription());
        }
        List<Integer> powerUpCardsToUseIndex;
        try{
            powerUpCardsToUseIndex = client.choosePowerUpCardsForReload(powerUpCardsInInventory);
            for(int i = 0; i<powerUpCardsToUseIndex.size(); i++){
                //identify power up controller
                PowerUpController powerUpController = null;
                for(PowerUpController p : gameBoardController.getPowerUpControllers()){
                    if(p.getName().equals(powerUpCardsInInventory.get(i))){
                        powerUpController = p;
                    }
                }
                if(powerUpController != null){
                    powerUpController.usePowerUp(player);
                }

            }
        }
        catch(UserTimeoutException e){
            //remove player from game
            client.setConnected(false);
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
