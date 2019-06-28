package it.polimi.se2019.model;

import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    int i;
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
    return this.remainingSkulls > 0;
  }

  /**
   * Resolves the scoreboard, summing up each kill and assigning each player
   * his score
   */
  public synchronized void resolveScoreboard(){
    Stream.concat(this.kills.stream(), this.doubleKills.stream())
            .collect(Collectors.groupingBy(
                    (Player p) -> p,
                    Collectors.counting()
            ))
            .entrySet().stream()
            .sorted((Map.Entry<Player, Long> a, Map.Entry<Player, Long> b) ->
                    (int) (a.getValue() - b.getValue())
            )
            .forEach((Map.Entry<Player, Long> p) ->
                    p.getKey().addPoints(p.getValue())
            );

  }
}
