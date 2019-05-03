package java.it.polimi.se2019.model.server;

import java.it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * KillScoreBoard contains all data related to global deaths and scores
 */
public class KillScoreBoard {
  /**
   *
   */
  private Integer remainingSkulls;

  /**
   *
   */
  private List<Player> doubleKills;

  /**
   *
   */
  private List<Player> kills;

  /**
   *
   */
  private List<Integer> scoreBoardValue;

  /**
   * Init a new KillScoreBoard
   *
   * @param skulls  Number of available skulls
   * @param scores  Array containing the scores of the board
   */
  public KillScoreBoard(Integer skulls, Integer[] scores) {
    this.remainingSkulls = skulls;
    this.kills = new ArrayList<>();
    this.doubleKills = new ArrayList<>();
    this.scoreBoardValue.addAll(Arrays.asList(value));
  }

  /**
   * Add a kill to the scoreboard
   *
   * @param player the player who made the kill
   */
  public synchronized void addKill(Player player) {
    this.kills.add(player);
    this.remainingSkulls--;
  }

  /**
   * Add a double kill to the scoreboard
   *
   * @param player the player who made the double kill
   */
  public void addDoubleKill(Player player) {
    this.addKill(player);
    this.doubleKills.add(player);
  }

  /**
   * @return  true when there are available skulls on the scoreboard,
   *          false otherwise
   */
  public boolean gameRunning(){
    return this.remainingSkulls > 0;
  }

  public Map<String, Integer> resolveScoreboard(){
    
  }
}

