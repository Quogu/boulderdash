package com.quogu.boulderdash.model.cave;

import java.awt.Point;
import java.util.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.collision.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class representing the cave tile map.
 * 
 * @author 850226
 */
public class CaveMap implements StateManagerListener {
    
    protected final StateManager stateManager;
    protected final int rows;
    protected final int cols;
    
    protected List<CaveElement> elementsBackup;
    protected Point playerPositionBackup;
    protected List<CaveElement> elements;
    protected List<CaveMapListener> listeners;
    
    /**
     * Fill the CaveMap with a grid of Empty Cave Elements of the specified
     * size.
     */
    public CaveMap(StateManager stateManager, int cols, int rows) {
        this.stateManager = stateManager;
        stateManager.addListener(this);
        this.rows = rows;
        this.cols = cols;
        elements = new ArrayList<CaveElement>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                elements.add(new CaveElementEmpty(this, j, i));
            }
        }
        elementsBackup = null;
        listeners = new ArrayList<CaveMapListener>();
    }
    
    /**
     * Default constructor. Pick an arbitrary sized grid and instantiate it.
     */
    public CaveMap(StateManager stateManager) {
        this(stateManager, 30, 20);
    }
    
    /**
     * Add a Cave Element to the CaveMap.
     * 
     * @param element
     *            The Cave Element to add.
     */
    public void addElement(CaveElement element) {
        elements.add(element);
    }
    
    /**
     * Remove a Cave Element from the CaveMap.
     * 
     * @param element
     *            The Cave Element to remove.
     */
    public void removeElement(CaveElement element) {
        assert (element.getCaveMap() == this);
        elements.remove(element);
    }
    
    /**
     * Attach a listener for events to the CaveMap.
     * 
     * @param listener
     *            The listener to attach.
     */
    public void addListener(CaveMapListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Remove a listener that was previously attached to the CaveMap.
     * 
     * @param listener
     */
    public void removeListener(CaveMapListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Get the number of rows in the CaveMap.
     * 
     * @return The number of rows in the CaveMap.
     */
    public int getNumRows() {
        return rows;
    }
    
    /**
     * Get the number of columns in the CaveMap.
     * 
     * @return The number of columns in the CaveMap.
     */
    public int getNumCols() {
        return cols;
    }
    
    /**
     * Get the Cave Element with specified coordinates.
     * 
     * @param x
     *            The X coordinate of the Cave Element to get.
     * @param y
     *            The Y coordinate of the Cave Element to get.
     * @return The specified Cave Element.
     * @throws MissingCaveElementException
     *             Thrown when an element that should be in our CaveMap isn't.
     */
    public CaveElement getCaveElement(int x, int y)
            throws MissingCaveElementException {
        // Now we are infinitely sad that our data structure doesn't have
        // indexed access.
        Iterator<CaveElement> i = elements.iterator();
        while (i.hasNext()) {
            CaveElement e = i.next();
            if (e.getX() == x && e.getY() == y) {
                return e;
            }
        }
        // If we haven't returned, there is a missing Cave Element. This is bad.
        throw new MissingCaveElementException(x, y);
    }
    
    /**
     * Get an iterator for the Cave Map.
     * 
     * @return An iterator for the Cave Map.
     */
    public Iterator<CaveElement> getCaveElements() {
        return elements.iterator();
        
    }
    
    /**
     * Function for making a backup of the current CaveMap, for when Play mode
     * starts.
     */
    public void backupElements() {
        playerPositionBackup = stateManager.getPlayerLocation();
        elementsBackup = new ArrayList<CaveElement>();
        elementsBackup.addAll(elements);
    }
    
    /**
     * Function for restoring the CaveMap from a backup, for when Play mode
     * ends.
     */
    public void restoreElements() {
        elements.clear();
        elements.addAll(elementsBackup);
        stateManager.setPlayerLocation(playerPositionBackup);
        elementsBackup = null;
        playerPositionBackup = null;
    }
    
    /**
     * Perform timed updates on the whole CaveMap by iterating through the
     * Elements, updating each one.
     * 
     * @throws IllegalCollisionException
     *             Thrown when an illegal collision is attempted.
     * @throws MissingCaveElementException
     *             Thrown when a cave element is requested but is not present in
     *             the model.
     */
    public void tick() throws IllegalCollisionException,
            MissingCaveElementException {
        for (int j = rows - 1; j >= 0; j--) {
            for (int i = 0; i < cols; i++) {
                getCaveElement(i, j).tick();
            }
        }
        fireRedrawRequested();
    }
    
    /**
     * Fire the Redraw Requested event.
     */
    public void fireRedrawRequested() {
        for (CaveMapListener l : listeners) {
            l.redrawRequested();
        }
    }
    
    /**
     * Fire the Show Error event.
     * 
     * @param message
     *            The error message to show.
     */
    public void fireShowError(String message) {
        for (CaveMapListener l : listeners) {
            l.showError(message);
        }
    }
    
    @Override
    public void gameStateChanged(GameState newState) {
        if (newState != GameState.Win && newState != GameState.Lose
                && elementsBackup != null) {
            restoreElements();
        }
        if (newState == GameState.Play && elementsBackup == null) {
            backupElements();
        }
    }
    
    @Override
    public void totalDiamondsChanged(int totalDiamonds) {
        // Don't care.
    }
    
    @Override
    public void currentDiamondsChanged(int currentDiamonds) {
        // Don't care.
    }
    
    @Override
    public void selectionChanged(Point selectionStart, Point selectionEnd) {
        // Don't care.
    }
    
    @Override
    public void showError(String message) {
        // Don't care.
    }
    
}
