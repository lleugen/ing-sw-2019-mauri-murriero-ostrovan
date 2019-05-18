package it.polimi.se2019.model.map;

import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A spawn square represent a square where a player can spawn/respawn or
 * grab a weapon
 */
public class SpawnSquare extends Square {

  /**
   * Inits a new Spawn Square
   *
   * @param roomId      The id of the room this Square belongs to
   * @param adjacencies The list of adjacents squares
   */
  public SpawnSquare(String roomId, List<Direction> adjacencies) {
    super(roomId, adjacencies);
  }

  /**
   * List of Weapons grabbable in this square
   */
  private List<Weapon> weaponList;

  /**
   * @return The list of weapons available in this square
   */
  public List<Weapon> getWeaponList() {
    List<Weapon> weaponsCopy = new ArrayList<Weapon>();
    Collections.copy(weaponsCopy, weaponList);
    return weaponsCopy;
  }

  /**
   * Grabs a weapon from the square
   *
   * @return The grabbed weapon
   */
  @Override
  public Weapon grab(int index) {
    Weapon weaponCopy = weaponList.get(index);
    weaponList.remove(index);
    return weaponCopy;
  }

  public boolean isSpawnPoint(){
    return true;
  }

  public void refill(){
    //draw a weapon from the weapons cards deck
    //add the drawn weapon to weaponsList
    while(weaponList.size() < 3){
      Weapon newWeapon = getDecks().drawWeapon();//decks deve restituire un'arma, non una lista
      weaponList.add(newWeapon);
    }
  }
}
