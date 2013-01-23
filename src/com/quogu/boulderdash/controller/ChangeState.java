package com.quogu.boulderdash.controller;

import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import com.quogu.boulderdash.model.*;

/**
 * A class for handling changing the state of the game.
 * 
 * @author 850226
 * 
 */
public class ChangeState implements StateManagerListener, ActionListener {
    
    protected StateManager stateManager;
    protected AbstractButton button;
    protected GameState state;
    
    /**
     * Constructor for the ChangeState controller, for buttons.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param button
     *            The button the controller is associated with.
     * @param state
     *            The state to change into when the controller is activated.
     */
    public ChangeState(StateManager stateManager, AbstractButton button,
            GameState state) {
        this.stateManager = stateManager;
        this.button = button;
        this.state = state;
        button.addActionListener(this);
        stateManager.addListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        stateManager.setGameState(state);
    }
    
    @Override
    public void gameStateChanged(GameState newState) {
        button.setSelected(state == newState);
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
