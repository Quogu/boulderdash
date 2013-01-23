package com.quogu.boulderdash.model;

import java.awt.*;

/**
 * Interface for classes wishing to be notified of a StateManager's changes of
 * state to implement.
 * 
 * @author 850226
 * 
 */
public interface StateManagerListener {
    
    /**
     * An event to notify that the current state the game is in has changed.
     * 
     * @param newState
     *            The game's new state.
     */
    public void gameStateChanged(GameState newState);
    
    /**
     * An event to notify that the total diamonds needed to win the game has
     * changed.
     * 
     * @param totalDiamonds
     *            The new value of required diamonds.
     */
    public void totalDiamondsChanged(int totalDiamonds);
    
    /**
     * An event to notify that the number of diamonds remaining to collect
     * before the game is won has changed. Should only be fired in Play mode.
     * 
     * @param currentDiamonds
     *            The number of diamonds left to win the game.
     */
    public void currentDiamondsChanged(int currentDiamonds);
    
    /**
     * An event to notify that the current selection has been changed.
     * 
     * @param selectionStart
     *            The top left corner of the selection.
     * @param selectionEnd
     *            The bottom right corner of the selection.
     */
    public void selectionChanged(Point selectionStart, Point selectionEnd);
    
    /**
     * Event that is fired when something bad happens in the Cave Map. Should
     * probably be handled only by views.
     */
    public void showError(String message);
}
