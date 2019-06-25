//package it.polimi.se2019.controller.player_state_controller;
//
//import it.polimi.se2019.controller.GameBoardController;
//import it.polimi.se2019.model.grabbable.PowerUpCard;
//import it.polimi.se2019.model.grabbable.Weapon;
//import it.polimi.se2019.model.map.Direction;
//import it.polimi.se2019.model.map.SpawnSquare;
//import it.polimi.se2019.model.map.Square;
//import it.polimi.se2019.model.player.Player;
//import it.polimi.se2019.view.player.PlayerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FirstFreneticStateController extends PlayerStateController {
//  public FirstFreneticStateController(GameBoardController g, Player p, PlayerView c) {
//    super(g,p,c);
//    availableActions = 2;
//  }
//
//  @Override
//  public String toString(){
//    return "FirstFreneticState";
//  }
//
//  /**
//   *
//   */
//  public void runAround() {
//    List<Square> fourMovesAway = map.getThreeMovesAwaySquares(player.getPosition());
//    for(Square q : fourMovesAway){
//      for(Direction d : q.getAdjacencies()){
//        if((!d.isBlocked()) & (!fourMovesAway.contains(q))){
//          fourMovesAway.add(q);
//        }
//      }
//    }
//    List<List<Integer>> threeMovesAwayCoordinates = new ArrayList<>();
//    for(Square q : fourMovesAway){
//      threeMovesAwayCoordinates.add(map.getSquareCoordinates(q));
//    }
//    List<Integer> moveToCoordinates = client.chooseTargetSquare(threeMovesAwayCoordinates);
//    player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);
//  }
//
//  /**
//   *
//   */
//  public void grabStuff() {
//    List<Square> twoMovesAway = map.getTwoMovesAwaySquares(player.getPosition());
//    List<List<Integer>> twoMovesAwayCoordinates = new ArrayList<>();
//    for(Square q : twoMovesAway){
//      twoMovesAwayCoordinates.add(map.getSquareCoordinates(q));
//    }
//    List<Integer> moveToCoordinates = client.chooseTargetSquare(twoMovesAwayCoordinates);
//    player.moveToSquare(map.getMapSquares()[moveToCoordinates.get(0)][moveToCoordinates.get(1)]);
//    Square position = player.getPosition();
//    int pickUpIndex = client.chooseItemToGrab();
//    if(position instanceof SpawnSquare){
//      player.getInventory().addWeaponToInventory(position.grab(pickUpIndex));
//    }
//    else{
//      player.getInventory().addAmmoTileToInventory(position.grab(0));
//    }
//  }
//
//  /**
//   *
//   */
//  public void shootPeople() {
//    Integer direction = client.chooseDirection(map.getOpenDirections(player.getPosition()));
//    if(direction != -1){
//      player.move(player.getPosition().getAdjacencies().get(direction));
//    }
//    //reload
//    if(client.chooseBoolean("Do you want to reload a weapon?")){
//      List<String> weaponsToReload = new ArrayList<>();
//      List<Integer> powerUpsForReloadIndex = new ArrayList<>();
//      List<PowerUpCard> powerUpsForReload = new ArrayList<>();
//      List<String> availablePowerUps = new ArrayList<>();
//      for(Weapon w : player.getInventory().getWeapons()){
//        if(!w.isLoaded()){
//          weaponsToReload.add(w.getName());
//        }
//      }
//      String weaponToReload = client.chooseWeaponToReload(weaponsToReload);
//      for(Weapon w : player.getInventory().getWeapons()){
//        if(w.getName().equals(weaponToReload)){
//          for(PowerUpCard p : player.getInventory().getPowerUps()){
//            availablePowerUps.add(p.getDescription());
//          }
//          powerUpsForReloadIndex = client.choosePowerUpCardsForReload(availablePowerUps);
//          for(int i = 0; i<powerUpsForReloadIndex.size(); i++){
//            powerUpsForReload.add(player.getInventory().getPowerUps().get(powerUpsForReloadIndex.get(i)));
//          }
//          w.reload(powerUpsForReload, player.getInventory().getAmmo());
//        }
//      }
//    }
//    shoot();
//  }
//
//}