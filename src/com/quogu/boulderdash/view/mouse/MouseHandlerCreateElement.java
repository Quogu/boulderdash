package com.quogu.boulderdash.view.mouse;

import java.awt.*;
import java.awt.event.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.view.*;
import com.quogu.boulderdash.controller.creator.*;

/**
 * A class to handle mouse events in the various Create Element modes.
 * 
 * @author 850226
 * 
 */
public class MouseHandlerCreateElement extends MouseHandlerBase {
    
    protected ElementCreator controller;
    
    /**
     * Constructor for MouseHandlerCreateElement.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param gameView
     *            The application's Game View.
     */
    public MouseHandlerCreateElement(StateManager stateManager,
            GameView gameView, ElementCreator controller) {
        super(stateManager, gameView);
        this.controller = controller;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickEnd = e.getPoint();
            setSelection(clickStart, clickEnd);
            Point p1 = stateManager.getSelectionStart();
            Point p2 = stateManager.getSelectionEnd();
            clickStart = null;
            clickEnd = null;
            stateManager.setSelection(null, null);
            controller.createCaveElements(stateManager, gameView.getCaveMap(),
                    p1.x, p2.x, p1.y, p2.y);
        }
    }
    
}
