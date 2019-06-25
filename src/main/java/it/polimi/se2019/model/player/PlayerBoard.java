//package it.polimi.se2019.model.player;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Player board contains all the logic related to damages, marks and scores
// */
//public class PlayerBoard {
//  public PlayerBoard() {
//    marksAssigned = new ArrayList<Player>();
//    damageReceived = new ArrayList<Player>();
//    deathValues = new ArrayList<Integer>();
//    deathValues.add(8);
//    deathValues.add(6);
//    deathValues.add(4);
//    deathValues.add(2);
//    deathValues.add(1);
//    isFrenzy = false;
//  }
//
//  /**
//   * The list of marks the player has been assigned. Marks become damage when the players is attacked by the player
//   * who assigned the marks.
//   */
//  private List<Player> marksAssigned;
//
//  /**
//   * The ordered list of players who dealt damage to this player. A player appears on the least once for every point
//   * of damage dealt
//   */
//  private List<Player> damageReceived;
//
//  /**
//   * The amount of point to be assigned for killing this player. The first position contains the number of points to
//   * be assigned to the player who dealt the most damage, in second position is the number of points to be given to
//   * the player who dealt second to most damage etc.
//   */
//  private List<Integer> deathValues;
//
//  /**
//   * The side the player's board is on currently. The player board has two sides : one for normal play and one for
//   * the final frenetic round.
//   */
//  private boolean isFrenzy;
//
//  /**
//   * @return the list of mark assigned to the current player
//   */
//  public List<Player> getMarksAssigned() {
//    return marksAssigned;
//  }
//
//  /**
//   * @return the list of damages assigned to the current player
//   */
//  public List<Player> getDamageReceived() {
//    return damageReceived;
//  }
//
//  /**
//   * @return the amount of points to be assigned to those who kill this player.
//   */
//  public List<Integer> getDeathValue() {
//    return deathValues;
//  }
//
//  /**
//   * Add 1 damage to the current player
//   *
//   * @param player The player that did damage
//   */
//  public void setDamage(Player player) {
//    damageReceived.add(player);
//  }
//
//  /**
//   * Add 1 mark to the current player
//   *
//   * @param player The player that did mark
//   */
//  public void setMark(Player player) {
//    marksAssigned.add(player);
//  }
//
//  /**
//   * Flip the board, changing scores index
//   */
//  public void turnAround() {
//    deathValues.clear();
//    deathValues = new ArrayList<Integer>(Arrays.asList(2, 1, 1, 1, 1, 1));
//    isFrenzy = true;
//  }
//
//  /**
//   * @return if the board is on final frenzy mode state
//   */
//  public boolean getIfIsFrenzy() {
//    return isFrenzy;
//  }
//
//    /**
//     *
//     * @return the player who dealt more damage to this playerboard
//     */
//  public Player getMostWorthyPlayer(){
//      ArrayList<Player> distinctDamagers = new ArrayList<Player>();
//      Integer index = 0, count = 0;
//
//      //create a distinct list of players who dealt damages, in order of first blood first
//      for(int i = 0; i < damageReceived.size(); i++) {
//          if (!distinctDamagers.contains(damageReceived.get(i)))
//              distinctDamagers.add(damageReceived.get(i));
//      }
//
//      //foreach player, get their damage and memorize their index if they are worthy
//      for(int i = 0;  i < distinctDamagers.size(); i++){
//          int counter = 0;
//          for(int j = 0; j < damageReceived.size(); j++){
//              if(damageReceived.get(j) == distinctDamagers.get(i))
//                  counter++;
//          }
//          if(counter > count){
//              count = counter;
//              index = i;
//          }
//      }
//
//      return distinctDamagers.get(index);
//  }
//}
//
