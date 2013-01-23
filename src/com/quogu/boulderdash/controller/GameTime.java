package com.quogu.boulderdash.controller;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.collision.IllegalCollisionException;

/**
 * A class for implementing timing the ticks of the game when it is playing.
 * 
 * @author 850226
 * 
 */
public class GameTime implements StateManagerListener {
    
    protected StateManager stateManager;
    protected CaveMap caveMap;
    protected Timer timer;
    
    protected class TimerAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                caveMap.tick();
            } catch (IllegalCollisionException | MissingCaveElementException e1) {
                caveMap.fireShowError(e1.getMessage());
            }
        }
    }
    
    public GameTime(StateManager stateManager, CaveMap caveMap) {
        this.stateManager = stateManager;
        stateManager.addListener(this);
        this.caveMap = caveMap;
        timer = new Timer(120, new TimerAction());
        timer.stop();
    }
    
    @Override
    public void gameStateChanged(GameState newState) {
        if (newState == GameState.Play) {
            timer.start();
        } else {
            timer.stop();
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
