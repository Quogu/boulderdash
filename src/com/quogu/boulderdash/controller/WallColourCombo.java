package com.quogu.boulderdash.controller;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

public class WallColourCombo implements StateManagerListener, ActionListener {
    
    protected StateManager stateManager;
    protected CaveMap caveMap;
    protected JComboBox<WallColour> comboBox;
    protected boolean doNotFire;
    
    public WallColourCombo(StateManager stateManager, CaveMap caveMap,
            JComboBox<WallColour> comboBox) {
        this.stateManager = stateManager;
        this.caveMap = caveMap;
        this.comboBox = comboBox;
        stateManager.addListener(this);
        comboBox.addActionListener(this);
        doNotFire = false;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Change the selected walls to the selected colour.
        if (!doNotFire) {
            try {
                for (int j = caveMap.getNumRows() - 1; j >= 0; j--) {
                    for (int i = 0; i < caveMap.getNumCols(); i++) {
                        if (stateManager.pointInSelection(new Point(i, j))) {
                            caveMap.removeElement(caveMap.getCaveElement(i, j));
                            caveMap.addElement(new CaveElementWall(caveMap, i,
                                    j, (WallColour) comboBox.getSelectedItem()));
                        }
                    }
                }
                caveMap.fireRedrawRequested();
            } catch (MissingCaveElementException ex) {
                stateManager.fireShowError(ex.getMessage());
            }
        }
    }
    
    @Override
    public void gameStateChanged(GameState newState) {
        comboBox.setVisible(newState == GameState.Edit);
    }
    
    @Override
    public void totalDiamondsChanged(int totalDiamonds) {
    }
    
    @Override
    public void currentDiamondsChanged(int currentDiamonds) {
    }
    
    @Override
    public void selectionChanged(Point selectionStart, Point selectionEnd) {
        Iterator<CaveElement> i = caveMap.getCaveElements();
        // Test for if all walls are selected, and if they are the same colour.
        // If the selection end is null, then we don't care, we're disabled.
        boolean allWalls = stateManager.getSelectionEnd() != null;
        boolean sameColour = true;
        WallColour wc = null;
        while (i.hasNext() && allWalls) {
            CaveElement e = i.next();
            if (stateManager.pointInSelection(new Point(e.getX(), e.getY()))) {
                if (e.getClass() != CaveElementWall.class) {
                    
                    allWalls = false;
                } else {
                    if (wc == null) {
                        wc = ((CaveElementWall) e).getColour();
                    } else {
                        if (((CaveElementWall) e).getColour() != wc) {
                            sameColour = false;
                        }
                    }
                }
            }
        }
        if (allWalls) {
            comboBox.setEnabled(true);
            doNotFire = true;
            if (sameColour) {
                comboBox.setSelectedItem(wc);
            } else {
                comboBox.setSelectedIndex(-1);
            }
            doNotFire = false;
        } else {
            comboBox.setEnabled(false);
        }
    }
    
    @Override
    public void showError(String message) {
    }
    
}
