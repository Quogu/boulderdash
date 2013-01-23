package com.quogu.boulderdash.model;

import java.awt.Point;
import java.util.*;

/**
 * A class to manage the state information about the game. There should be only
 * one instance of StateManager per running instance of the application.
 * 
 * @author 850226
 * 
 */
public class StateManager {
    
    public static StateManager Instance = new StateManager();
    
    protected GameState state;
    protected int editModeDiamonds;
    protected int playModeDiamonds;
    protected Point selectionStart;
    protected Point selectionEnd;
    protected boolean dragMode;
    protected Point dragSelectionStart;
    protected Point dragSelectionEnd;
    protected Point playerLocation;
    protected List<StateManagerListener> listeners;
    
    /**
     * Default constructor for StateManager.
     */
    public StateManager() {
        editModeDiamonds = 0;
        playModeDiamonds = 0;
        playerLocation = null;
        dragMode = false;
        listeners = new ArrayList<StateManagerListener>();
    }
    
    /**
     * Add a listener for state changes.
     * 
     * @param listener
     *            The listener to add.
     */
    public void addListener(StateManagerListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Remove a listener so it will no longer be notified of state changes.
     * 
     * @param listener
     *            The listener to remove.
     */
    public void removeListener(StateManagerListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Get the total diamonds needed to win the game.
     * 
     * @return The total diamonds needed to win the game.
     */
    public int getTotalDiamonds() {
        return editModeDiamonds;
    }
    
    /**
     * Set the total diamonds needed to win the game.
     * 
     * @param totalDiamonds
     *            The total diamonds needed to win the game.
     */
    public void setTotalDiamonds(int totalDiamonds) {
        editModeDiamonds = totalDiamonds;
        playModeDiamonds = totalDiamonds;
        for (StateManagerListener l : listeners) {
            l.totalDiamondsChanged(totalDiamonds);
        }
    }
    
    /**
     * Get the current diamonds still needed to win the game.
     * 
     * @return The current diamonds still needed to win the game.
     */
    public int getCurrentDiamonds() {
        return playModeDiamonds;
    }
    
    /**
     * Set the current diamonds still needed to win the game.
     * 
     * @param currentDiamonds
     *            The current diamonds still needed to win the game.
     */
    public void setCurrentDiamonds(int currentDiamonds) {
        playModeDiamonds = currentDiamonds;
        for (StateManagerListener l : listeners) {
            l.currentDiamondsChanged(currentDiamonds);
        }
    }
    
    /**
     * Get the game's state.
     * 
     * @return The game's current state.
     */
    public GameState getGameState() {
        return state;
    }
    
    /**
     * Set the game's state.
     * 
     * @param state
     *            The state to set the game to.
     */
    public void setGameState(GameState state) {
        // If we're entering a create state, clear the selection.
        if (state != GameState.Play && state != GameState.Edit
                && state != GameState.Win && state != GameState.Lose) {
            selectionStart = null;
            selectionEnd = null;
        }
        // The game cannot start if there is no player.
        if (!(state == GameState.Play && getPlayerLocation() == null)) {
            this.state = state;
            // If we're starting to play, we need to reset the current diamonds.
            if (state == GameState.Play) {
                setCurrentDiamonds(editModeDiamonds);
            }
            for (StateManagerListener l : listeners) {
                l.gameStateChanged(state);
            }
            if (state == GameState.Play && editModeDiamonds == 0) {
                setGameState(GameState.Win);
            }
        } else {
            setGameState(GameState.Edit);
            fireShowError("Game cannot start without a player on the map!");
        }
    }
    
    /**
     * Get the top left corner of the selection in Cave Map coordinates.
     * 
     * @return The top left corner of the selection.
     */
    public Point getSelectionStart() {
        return selectionStart;
    }
    
    /**
     * Get the bottom right corner of the selection in Cave Map coordinates.
     * 
     * @return The bottom right corner of the selection.
     */
    public Point getSelectionEnd() {
        return selectionEnd;
    }
    
    /**
     * Set the currently selected tiles in the Cave Map. Fires the Selection
     * Changed event.
     * 
     * @param start
     *            The starting (top left) position of the selection.
     * @param end
     *            The ending (bottom right) position of the selection.
     */
    public void setSelection(Point start, Point end) {
        selectionStart = start;
        selectionEnd = end;
        for (StateManagerListener l : listeners) {
            l.selectionChanged(selectionStart, selectionEnd);
        }
    }
    
    /**
     * Is the application in Drag Mode?
     * 
     * @return Are we in drag mode?
     */
    public boolean isDragMode() {
        return dragMode;
    }
    
    /**
     * Set if the application is in Drag Mode. Should only ever be true in Edit
     * mode.
     * 
     * @param dragMode
     *            Whether we are in Drag Mode.
     */
    public void setDragMode(boolean dragMode) {
        this.dragMode = dragMode;
    }
    
    /**
     * Get the top left corner of the drag selection.
     * 
     * @return The top left corner of the drag selection.
     */
    public Point getDragSelectionStart() {
        return dragSelectionStart;
    }
    
    /**
     * Get the bottom right corner of the drag selection.
     * 
     * @return The bottom right corner of the drag selection.
     */
    public Point getDragSelectionEnd() {
        return dragSelectionEnd;
    }
    
    /**
     * Set the drag selection area.
     * 
     * @param dragSelectionStart
     *            The top left corner of the drag selection.
     * @param dragSelectionEnd
     *            The bottom right corner of the drag selection.
     */
    public void setDragSelection(Point dragSelectionStart,
            Point dragSelectionEnd) {
        this.dragSelectionStart = dragSelectionStart;
        this.dragSelectionEnd = dragSelectionEnd;
    }
    
    /**
     * Convenience function to determine if a given point in Cave Map
     * coordinates is in the current selection.
     * 
     * @param point
     *            The point to test for selection.
     * @return Whether the point is selected.
     */
    public boolean pointInSelection(Point point) {
        if (selectionStart != null && selectionEnd != null) {
            return (selectionStart.x <= point.x && selectionStart.y <= point.y
                    && point.x <= selectionEnd.x && point.y <= selectionEnd.y);
        } else {
            return false;
        }
        
    }
    
    /**
     * Convenience function to determine if a given point in Cave Map
     * coordinates is in the current drag selection.
     * 
     * @param point
     *            The point to test for drag selection.
     * @return Whether the point is drag selected.
     */
    public boolean pointInDragSelection(Point point) {
        if (dragSelectionStart != null && dragSelectionEnd != null) {
            return (dragSelectionStart.x <= point.x
                    && dragSelectionStart.y <= point.y
                    && point.x <= dragSelectionEnd.x && point.y <= dragSelectionEnd.y);
        } else {
            return false;
        }
        
    }
    
    /**
     * Get the player's location. Null if unset.
     * 
     * @return The player's location or null.
     */
    public Point getPlayerLocation() {
        return playerLocation;
    }
    
    /**
     * Set the player's location. Set to Null if there is no player.
     * 
     * @param location
     *            The player's location.
     */
    public void setPlayerLocation(Point location) {
        playerLocation = location;
    }
    
    /**
     * Fire the Show Error event.
     * 
     * @param message
     *            The error message to show.
     */
    public void fireShowError(String message) {
        for (StateManagerListener l : listeners) {
            l.showError(message);
        }
    }
    
}
