package it.polimi.se2019.model.server;

import it.polimi.se2019.model.player.Player;

import java.util.List;

/**
 * KillScoreBoard contains all data related to global deaths and scores
 */
public class KillScoreBoard {
    public KillScoreBoard() {
    }

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
     * Add a kill to the scoreboard
     *
     * @param player the player who made the kill
     */
    public void addKills(List<Player> player) {
    }

    /**
     * Add a double kill to the scoreboard
     *
     * @param player the player who made the double kill
     */
    public void addDoubleKill(Player player) {
    }

}