package com.quogu.boulderdash.controller;

import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import com.quogu.boulderdash.model.*;

/**
 * A class to ensure the number of diamonds required is valid, and make the box
 * editable only when the game is in Edit mode.
 * 
 * @author 850226
 * 
 */
public class DiamondsCount implements StateManagerListener, KeyListener,
        FocusListener {
    
    protected StateManager stateManager;
    protected JTextField field;
    protected GameState state;
    
    public DiamondsCount(StateManager stateManager, JTextField field) {
        this.stateManager = stateManager;
        this.field = field;
        stateManager.addListener(this);
        field.addKeyListener(this);
        field.addFocusListener(this);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Test if the key is a digit.
        if (!String.valueOf(e.getKeyChar()).matches("\\d")) {
            // Reject the entry if the key does not match.
            e.consume();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // Don't care.
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Test if the string consists only of digits. This deals with blighters
        // who copy-paste illegal things.
        if (!field.getText().matches("\\d*")) {
            // Reset if the string does not match.
            field.setText(String.valueOf(stateManager.getTotalDiamonds()));
        } else {
            if (!field.getText().equals("")) {
                stateManager
                        .setTotalDiamonds(Integer.parseInt(field.getText()));
            }
        }
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        // Don't care.
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        // Test if the string consists of at least one digit. This deals with
        // blighters who leave the field empty.
        if (!field.getText().matches("\\d+")) {
            // Reset if the string does not match.
            field.setText(String.valueOf(stateManager.getTotalDiamonds()));
        }
    }
    
    @Override
    public void gameStateChanged(GameState newState) {
        state = newState;
        field.setEditable(newState == GameState.Edit);
        if (state == GameState.Edit) {
            field.setText(String.valueOf(stateManager.getTotalDiamonds()));
        }
    }
    
    @Override
    public void totalDiamondsChanged(int totalDiamonds) {
        if (state == GameState.Edit) {
            field.setText(String.valueOf(stateManager.getTotalDiamonds()));
        }
    }
    
    public void currentDiamondsChanged(int currentDiamonds) {
        if (stateManager.getGameState() == GameState.Play) {
            field.setText(String.valueOf(stateManager.getCurrentDiamonds()));
            // If we're out of diamonds, we win the game! :D
            if (currentDiamonds == 0) {
                stateManager.setGameState(GameState.Win);
            }
        }
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
