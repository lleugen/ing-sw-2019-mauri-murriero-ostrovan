package it.polimi.se2019.model;

import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * KillScoreBoard contains all data related to global deaths and scores
 */
public class KillScoreBoard {
  /**
   * Number of skulls already available
   */
  private Integer remainingSkulls;

  /**
   * List of players that made doubleKills (chronological ordered)
   */
  private List<Player> doubleKills;

  /**
   * List of players that made kills (chronological ordered)
   */
  private List<Player> kills;

  /**
   * List of scores assignable to a player (in order)
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

    Integer i;
    Integer element;

    for (i = 0; i < scores.length; i++){
      element = scores[i];
      this.scoreBoardValue.add(element);
    }
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
    if (this.remainingSkulls > 0){
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Resolves the scoreboard, summing up each kill
   *
   * @return a map of Player:score
   */
  public synchronized Map<Player, Integer> resolveScoreboard(){
    // Points for each player
    Map<Player, Integer> points = new HashMap<>();

    // Temp scoreboard used while processing normal kills
    Map<Player, Integer> scoreboard = new HashMap<>();

    // Summing Up Doubles kills
    Integer i;
    Player p;
    Integer score;

    for (i = 0; i < this.doubleKills.size(); i++){
      p = this.doubleKills.get(i);

      // Checking if player has alredy been initialized
      score = points.get(p);

      if (score == null){
        // Player has not been initialized yet
        points.put(p, 1);
      }
      else {
        // Player has already been initialized
        points.put(p, score + 1);
      }
    }

    // Summing up standards kills
    for (i = 0; i < this.kills.size(); i++){
      p = this.kills.get(i);

      // Checking if player has alredy been initialized
      score = scoreboard.get(p);

      if (score == null){
        // Player has not been initialized yet
        scoreboard.put(p, 1);
      }
      else {
        // Player has already been initialized
        scoreboard.put(p, score + 1);
      }
    }

    // Sorting Temp scoreboard
    Set<Map.Entry<Player, Integer>> settedMap;
    ArrayList<Map.Entry<Player, Integer>> listedMap;
    Boolean sorted = false;
    Map.Entry<Player, Integer> a;
    Map.Entry<Player, Integer> b;
    Map.Entry<Player, Integer> tmp;

    settedMap = scoreboard.entrySet();
    listedMap = new ArrayList<>(settedMap);

    while (sorted == false){
      for (i = 0; i < (listedMap.size() - 1); i++){

        a = listedMap.get(i);
        b = listedMap.get(i + 1);

        if (a.getValue() < b.getValue()){
          // Swapping
          listedMap.set(i, b);
          listedMap.set((i + 1), a);
        }
      }

      // Checking if sorted
      for (i = 0; i < (listedMap.size() - 1); i++){

        a = listedMap.get(i);
        b = listedMap.get(i + 1);

        if (a.getValue() < b.getValue()){
          sorted = false;
        }
      }
    }

    for (i = 0; i < (listedMap.size() - 1); i++){
      Integer scoredPoints;
      Integer currentPoints;

      if (i >= this.scoreBoardValue.size()){
        scoredPoints = this.scoreBoardValue.get(
          this.scoreBoardValue.size() - 1   // Getting last score
        );
      }
      else {
        scoredPoints = this.scoreBoardValue.get(i);
      }

      // Checking if player has alredy been initialized
      currentPoints = points.get(listedMap.get(i).getKey());

      if (currentPoints == null){
        // Player has not been initialized yet
        scoreboard.put(p, scoredPoints);
      }
      else {
        // Player has already been initialized
        scoreboard.put(p, scoredPoints + currentPoints);
      }
    }

    return points;
  }
}
