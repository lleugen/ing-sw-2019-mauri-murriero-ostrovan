package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.rmi.UserTimeoutException;
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Eugenio OStrovan
 * @author Fabio Mauri
 */
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

    public abstract boolean runAround() throws UserTimeoutException;
    public abstract boolean grabStuff() throws UserTimeoutException;
    public abstract boolean shootPeople() throws UserTimeoutException;

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
    public boolean shoot() throws UserTimeoutException {
        boolean result = false;
        List<Weapon> weps = player.getInventory().getWeapons();
        for(Weapon w : weps){
            if(!w.isLoaded()){
                weps.remove(w);
            }
        }
        List<String> weapons = weps.stream().map(Weapon::getName).collect(Collectors.toList());
//      List<String> weapons = player.getInventory().getWeapons().stream()
//              .map((Weapon::getName))
//              .collect(Collectors.toList());


      if(!weapons.isEmpty()){
          String selectedWeapon = client.chooseWeapon(weapons);
          System.out.println("a client chose " + selectedWeapon);
          WeaponController weaponController = null;
          for(WeaponController w : gameBoardController.getWeaponControllers()){
              if(w.getName().equals(selectedWeapon)){
                  weaponController = w;
              }
          }
          if(weaponController != null){
              System.out.println("firing " + weaponController.getName());
              result = weaponController.fire(player, client);
          }

//        List<WeaponController> selectedWeapons;
//        selectedWeapons = gameBoardController.getWeaponControllers().stream()
//                .filter((WeaponController w) ->
//                        w.getName().equals(selectedWeapon)
//                )
//                .collect(Collectors.toList());
//
//        for (WeaponController weaponController: selectedWeapons) {
//
//          weaponController.fire(player, client);
//        }
          if(result){
              for(Weapon w : player.getInventory().getWeapons()){
                  if(w.getName().equals(selectedWeapon)){
                      w.unload();
                      System.out.println("a weapon has been shot");
                  }
              }
          }


      }

        return result;
    }

    /**
     * Spend ammo to reload an unloaded weapon from the player's inventory.
     * Choose which weapon to reload, subtract the reload cost from inventory
     * and set the weapon as loaded.
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public void reload() throws UserTimeoutException {
        Weapon toReload = null;
        boolean result = false;
        //make a list of the player's unloaded weapons
        List<String> playersWeapons = new ArrayList<>();
        for(Weapon w : player.getInventory().getWeapons()){
            if(!w.isLoaded()){
                playersWeapons.add(w.getName());
            }
        }

        if(!playersWeapons.isEmpty()){
            if(client.chooseBoolean("Would you like to reload?")){
                List<Integer> cardsToUseIndexes = new ArrayList<>();
                List<PowerUpCard> cardsToUse = new ArrayList<>();



                //choose which one to reload
                String weaponToReloadName = client.chooseWeaponToReload(playersWeapons);

                //get the weapon given its name
                for (Weapon w : player.getInventory().getWeapons()){
                    if(w.getName().equals(weaponToReloadName)){
                        toReload = w;
                    }
                }
                System.out.println("reloading " + toReload.getName());

                //---------------------------------
                //choose which power up cards to use for reloading
                List<String> powerUps = new ArrayList<>();
                for(PowerUpCard p : player.getInventory().getPowerUps()){
                    powerUps.add(p.getDescription());
                }
                if(!powerUps.isEmpty()){
                    cardsToUseIndexes = client.choosePowerUpCardsForReload(powerUps);
                    //get the chosen cards
                    for(int i : cardsToUseIndexes){
                        cardsToUse.add(player.getInventory().getPowerUps().get(i));
                    }
                }

                if(toReload != null){
                    //reload the weapon
                    result = toReload.reload(cardsToUse, player.getInventory().getAmmo());
                    if(result){
                        playersWeapons.remove(weaponToReloadName);
                        if(toReload.isLoaded()){
                            System.out.println("reloaded " + weaponToReloadName);
                        }
                        if(!playersWeapons.isEmpty()){
                            reload();
                        }
                    }
                    else{
                        try{
                            client.sendGenericMessage("failed to reload");
                        }
                        catch(RemoteException e){
                            System.out.println("failed to send message");
                        }

                    }
                }
                else{
                    System.out.println("this will never happen");
                }

                //---------------------------------


            }
        }
    }

    /**
     * Grab the ammo tile or a weapon from the current square and add
     * the corresponding resources to the inventory.
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public boolean grab() throws UserTimeoutException{
        boolean result = false;
        Square position = player.getPosition();
        int index = 0;

        if(position != null){
            if(position.getItem() != null){
                if(position instanceof SpawnSquare){
                    index = client.chooseItemToGrab();
                    //return here when add to inventory method is finished
                    if(!position.getItem().isEmpty()){
                        player.getInventory().addWeaponToInventory(position.grab(index));
                        result = true;
                    }
                    else{
                        try{
                            client.sendGenericMessage("no more weapons here");
                        }
                        catch(RemoteException r){
                            //
                        }

                    }
                }
                else{
                    player.getInventory().addAmmoTileToInventory(position.grab(0));
                    result = true;
                }
            }
            else{
                try{
                    client.sendGenericMessage("nothing to grab here");
                }
                catch(RemoteException r){
                    //
                }
            }
        }
        else{
            System.out.println("the game is broken lol");
        }
        return result;
    }

    /**
     * Use a power up from the inventory
     * @throws UserTimeoutException if the user takes too long to respond or disconnects
     */
    public boolean usePowerUp() throws UserTimeoutException {
        List<String> powerUpCardsInInventory = new ArrayList<>();
        for(PowerUpCard p : player.getInventory().getPowerUps()){
            if(p.getDescription() == "NewtonRed" || p.getDescription() == "NewtonBlue" || p.getDescription() == "NewtonYellow"){
                powerUpCardsInInventory.add("Newton");
            }
            else if(p.getDescription() == "TeleporterRed" || p.getDescription() == "TeleporterBlue" || p.getDescription() == "TeleporterYellow"){
                powerUpCardsInInventory.add("Teleporter");
            }
            else if(p.getDescription() == "TagbackGrenadeRed" || p.getDescription() == "TagbackGrenadeBlue" || p.getDescription() == "TagbackGrenadeYellow"){
                //powerUpCardsInInventory.add("TagbackGrenade");
            }
            else if(p.getDescription() == "TargetingScopeRed" || p.getDescription() == "TargetingScopeBlue" || p.getDescription() == "TargetingScopeYellow"){
                //powerUpCardsInInventory.add("TargetingScope");
            }
        }
        List<Integer> powerUpCardsToUseIndex;
        powerUpCardsToUseIndex = client.choosePowerUpCardsForReload(powerUpCardsInInventory);
        if(powerUpCardsToUseIndex.size() > 0){
            for(int i = 0; i<powerUpCardsToUseIndex.size(); i++){
                //identify power up controller
                PowerUpController powerUpController = null;
                for(PowerUpController p : gameBoardController.getPowerUpControllers()){
                    if(p.getName().equals(powerUpCardsInInventory.get(i))){
                        powerUpController = p;
                    }
                }
                if(powerUpController != null){
                    return powerUpController.usePowerUp(player);
                }

            }
        }
        return false;
    }

    /**
     * Print current player's inventory
     */
    public String printInventory(){
        //print the player's inventory
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i<player.getInventory().getWeapons().size(); i++){
            buffer.append(player.getInventory().getWeapons().get(i).toString());
            buffer.append(" ");
        }
        buffer.append('\n');
        buffer.append("red ammo: ");
        buffer.append(player.getInventory().getAmmo().getRed());
        buffer.append(" ");
        buffer.append("blue ammo: ");
        buffer.append(player.getInventory().getAmmo().getBlue());
        buffer.append(" ");
        buffer.append("yellow ammo: ");
        buffer.append(player.getInventory().getAmmo().getYellow());
        buffer.append(" ");
        buffer.append('\n');
        for(int i = 0; i<player.getInventory().getPowerUps().size(); i++){
            buffer.append(player.getInventory().getPowerUps().get(i).toString());
            buffer.append(" ");
        }
        buffer.append('\n');
        return buffer.toString();
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
