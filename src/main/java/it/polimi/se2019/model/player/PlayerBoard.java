package it.polimi.se2019.model.player;

import java.util.List;

/**
 * Player board contains all the logic related to damages, marks and scores
 */
public abstract class PlayerBoard {
    public PlayerBoard() {}

    /**
     * 
     */
    private List<Player> MarksAssigned;

    /**
     * 
     */
    private List<Player> DamageReceived;

    /**
     * 
     */
    private Integer deaths;

    /**
     * 
     */
    private List<Integer> DeathValue;

    /**
     * 
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
     * @return
     */
    public List<Integer> getDeathValue() {}

    /**
     * Add 1 damage to the current player
     *
     * @param player The player that did damage
     */
    public void setDamage(Player player) {}

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