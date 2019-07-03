package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.powerup.PowerUpController;
import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public abstract void runAround() throws UserTimeoutException;
    public abstract void grabStuff() throws UserTimeoutException;
    public abstract void shootPeople() throws UserTimeoutException;

    /**
     * Move the player 1 square in one of four directions
     * direction is the direction that the players moves towards
     *                  0 = north
     *                  1 = east
     *                  2 = south
     *                  3 = west
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void move() throws UserTimeoutException{
        int direction = 0;
        direction = client.chooseDirection(map.getOpenDirections(player.getPosition()));


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
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void spawn() throws UserTimeoutException {
        player.getInventory().addPowerUpToInventory(player.getInventory().getDecksReference().drawPowerUp());
        //ask player to choose a power up card to discard, return a colour
        //view will have to be the PlayerView stub
        List<String> powerUps = new ArrayList<>();
        for(PowerUpCard p : player.getInventory().getPowerUps()){
            powerUps.add(p.getDescription());
        }
        int discardedCard;
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

    /**
     * Make the player choose a weapon from his or her inventory and fire it.
     * Once the weapon is chosen, weapon specific methods will be invoked to
     * choose targets and fire.
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void shoot() throws UserTimeoutException {
      List<String> weapons = player.getInventory().getWeapons().stream()
              .map((Weapon::getName))
              .collect(Collectors.toList());

        String selectedWeapon = client.chooseWeapon(weapons);
        List<WeaponController> selectedWeapons;
        selectedWeapons = gameBoardController.getWeaponControllers().stream()
                .filter((WeaponController w) ->
                        w.getName().equals(selectedWeapon)
                )
                .collect(Collectors.toList());

        for (WeaponController weaponController: selectedWeapons) {
          weaponController.fire(player, client);
        }

    }

    /**
     * Spend ammo to reload an unloaded weapon from the player's inventory.
     * Choose which weapon to reload, subtract the reload cost from inventory
     * and set the weapon as loaded.
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void reload() throws UserTimeoutException {
        List<Integer> cardsToUseIndexes;
        List<PowerUpCard> cardsToUse = new ArrayList<>();

        //make a list of the player's weapons
        List<String> playersWeapons = player.getInventory().getWeapons().stream()
                .map(Weapon::getName)
                .collect(Collectors.toList());

        //choose which one to reload
        String weaponToReloadName = client.chooseWeaponToReload(playersWeapons);

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

    /**
     * Grab the ammo tile or a weapon from the current square and add
     * the corresponding resources to the inventory.
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void grab() throws UserTimeoutException{
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

    /**
     * Use a power up from the inventory
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void usePowerUp() throws UserTimeoutException {
        List<String> powerUpCardsInInventory = new ArrayList<>();
        for(PowerUpCard p : player.getInventory().getPowerUps()){
            powerUpCardsInInventory.add(p.getDescription());
        }
        List<Integer> powerUpCardsToUseIndex;
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
