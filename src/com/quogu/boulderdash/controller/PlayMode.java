package com.quogu.boulderdash.controller;

import java.awt.*;
import java.awt.event.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.collision.IllegalCollisionException;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class to implement the playing of Boulder Dash.
 * 
 * @author 850226
 * 
 */
public class PlayMode implements KeyListener {
    
    protected StateManager stateManager;
    protected CaveMap caveMap;
    
    /**
     * Constructor for the Play Mode controller.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param caveMap
     *            The Cave Map this controller applies to.
     */
    public PlayMode(StateManager stateManager, CaveMap caveMap) {
        this.stateManager = stateManager;
        this.caveMap = caveMap;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (stateManager.getGameState() == GameState.Play) {
            try {
                Point l = stateManager.getPlayerLocation();
                CaveElement c1 = null;
                CaveElement c2 = caveMap.getCaveElement(l.x, l.y);
                // Try and work out which direction to move in.
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        // LOL, Eclipse indentation settings
                        if (l.y > 0) {
                            c1 = caveMap.getCaveElement(l.x, l.y - 1);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (l.y < caveMap.getNumRows() - 1) {
                            c1 = caveMap.getCaveElement(l.x, l.y + 1);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (l.x > 0) {
                            c1 = caveMap.getCaveElement(l.x - 1, l.y);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (l.x < caveMap.getNumCols() - 1) {
                            c1 = caveMap.getCaveElement(l.x + 1, l.y);
                        }
                        break;
                    default:
                }
                // If we're moving, try for a collision.
                if (c1 != null) {
                    if (c1.canCollide(c2)) {
                        c1.collide(c2);
                        caveMap.fireRedrawRequested();
                    }
                }
            } catch (MissingCaveElementException | IllegalCollisionException ex) {
                caveMap.fireShowError(ex.getMessage());
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
