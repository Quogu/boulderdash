package com.quogu.boulderdash.controller;

import java.awt.Point;

import javax.swing.*;
import com.quogu.boulderdash.model.*;

/**
 * A class to show and hide UI components when changing states, so they are only
 * visible in Edit Mode.
 * 
 * @author 850226
 * 
 */
public class ChangeStateComponentVisible implements StateManagerListener {
    
    protected StateManager stateManager;
    protected JComponent component;
    
    public ChangeStateComponentVisible(StateManager stateManager,
            JComponent component) {
        this.stateManager = stateManager;
        this.component = component;
        stateManager.addListener(this);
    }
    
    @Override
    public void gameStateChanged(GameState newState) {
        component.setVisible(newState == GameState.Edit);
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
