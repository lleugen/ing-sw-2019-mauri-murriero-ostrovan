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
   * Has one item for each kill and specifies whether that kills was an overkill
   */
  private List<Boolean> overKills;

  /**
   *
   */
  private GameBoard gameBoard;

  /**
   * Init a new KillScoreBoard
   *
   * @param skulls  Number of available skulls
   * @param scores  Array containing the scores of the board
   */
  public KillScoreBoard(GameBoard g, Integer skulls, Integer[] scores) {
    gameBoard = g;
    scoreBoardValue = new ArrayList<>();
    this.remainingSkulls = skulls;
    this.kills = new ArrayList<>();
    this.doubleKills = new ArrayList<>();

    for(int i = 0; i<scores.length; i++){
      scoreBoardValue.add(scores[i]);
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
   * An overkill lets the player add one more mark on the scoreboard, but doesn't consume a skull
   * @param overkill the player who scores the overkill
   */
  public synchronized void addOverKill(Boolean overkill){
    this.overKills.add(overkill);
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

  public List<Player> getKills(){
    return kills;
  }

  public List<Boolean> getOverKills(){
    return overKills;
  }
  public List<Player> getDoubleKills(){
    return doubleKills;
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
    List<Player> scoreboard = Stream.concat(this.kills.stream(), this.doubleKills.stream())
            .collect(Collectors.groupingBy(
                    (Player p) -> p,
                    Collectors.counting()
            ))
            .entrySet().stream()
            .sorted((Map.Entry<Player, Long> a, Map.Entry<Player, Long> b) ->
                    (int) (a.getValue() - b.getValue())
            )
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

    for (int i = 0; i < scoreboard.size(); i++) {
      scoreboard.get(i).addPoints(
              this.scoreBoardValue.get(i)
      );
    }
  }
}
