package it.polimi.se2019.model.player;

import java.util.List;

/**
 * Player board contains all the logic related to damages, marks and scores
 */
public abstract class PlayerBoard {
  public PlayerBoard() {
  }

  /**
   * The list of marks the player has been assigned. Marks become damage when the players is attacked by the player
   * who assigned the marks.
   */
  private List<Player> MarksAssigned;

  /**
   * The ordered list of players who dealt damage to this player. A player appears on the least once for every point
   * of damage dealt
   */
  private List<Player> DamageReceived;

  /**
   * The number of times this player has died
   */
  private Integer deaths;

  /**
   * The amount of point to be assigned for killing this player. The first position contains the number of points to
   * be assigned to the player who dealt the most damage, in second position is the number of points to be given to
   * the player who dealt second to most damage etc.
   */
  private List<Integer> DeathValue;

  /**
   * The side the player's board is on currently. The player board has two sides : one for normal play and one for
   * the final frenetic round.
   */
  private boolean boardSide;

  /**
   * @return the list of mark assigned to the current player
   */
  public List<Player> getMarksAssigned() {
  }

  /**
   * @return the list of damages assigned to the current player
   */
  public List<Player> getDamageReceived() {
  }

  /**
   * @return the number of deaths of this player
   */
  public Integer getDeaths() {
  }

  /**
   * @return the amount of points to be assigned to those who kill this player.
   */
  public List<Integer> getDeathValue() {
  }

  /**
   * Add 1 damage to the current player
   *
   * @param player The player that did damage
   */
  public void setDamage(Player player) {
  }

  /**
   * Add 1 mark to the current player
   *
   * @param player The player that did mark
   */
  public void setMark(Player player) {
  }

  /**
   * Flip the board, changing scores index
   */
  public void turnAround() {
  }

  /**
   * @return The current side of the board
   */
  public boolean getBoardSide() {
  }
}

