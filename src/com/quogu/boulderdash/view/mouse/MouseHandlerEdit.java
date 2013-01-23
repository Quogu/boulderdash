package com.quogu.boulderdash.view.mouse;

import java.awt.*;
import java.awt.event.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.view.*;
import com.quogu.boulderdash.controller.*;

/**
 * A class to handle mouse input during Edit mode.
 * 
 * @author 850226
 * 
 */
public class MouseHandlerEdit extends MouseHandlerBase {
    
    protected Point dragStartSquare;
    protected MoveSelection controller;
    
    /**
     * Constructor for MouseHandlerCreateElement.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param gameView
     *            The application's Game View.
     */
    public MouseHandlerEdit(StateManager stateManager, GameView gameView) {
        super(stateManager, gameView);
        controller = new MoveSelection(stateManager, gameView.getCaveMap());
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickStart = e.getPoint();
            Point p1 = mousePointToCaveMapPoint(clickStart);
            stateManager.setDragMode(stateManager.pointInSelection(p1));
            if (!stateManager.isDragMode()) {
                setSelection(clickStart, clickStart);
            } else {
                // Record the squre we started dragging. It will be useful.
                dragStartSquare = mousePointToCaveMapPoint(clickStart);
                stateManager.setDragSelection(stateManager.getSelectionStart(),
                        stateManager.getSelectionEnd());
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (clickStart != null) {
            if (!stateManager.isDragMode()) {
                Point p1 = stateManager.getSelectionStart();
                Point p2 = stateManager.getSelectionEnd();
                setSelection(clickStart, e.getPoint());
                // Check if the selection has changed - if yes, redraw.
                if (p1 != stateManager.getSelectionStart()
                        && p2 != stateManager.getSelectionEnd()) {
                    gameView.redrawRequested();
                }
            } else {
                Point p = mousePointToCaveMapPoint(e.getPoint());
                Point ss = stateManager.getSelectionStart();
                Point se = stateManager.getSelectionEnd();
                Point dragStart =
                        new Point(ss.x + (p.x - dragStartSquare.x), ss.y
                                + (p.y - dragStartSquare.y));
                Point dragEnd =
                        new Point(se.x + (p.x - dragStartSquare.x), se.y
                                + (p.y - dragStartSquare.y));
                stateManager.setDragSelection(dragStart, dragEnd);
                gameView.redrawRequested();
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickEnd = e.getPoint();
            
            // If less than a square is dragged, deselect.
            if (stateManager.isDragMode()) {
                if (mousePointToCaveMapPoint(clickStart).equals(
                        mousePointToCaveMapPoint(clickEnd))) {
                    stateManager.setSelection(null, null);
                    gameView.redrawRequested();
                } else {
                    // Work out where the drag ends.
                    Point p = mousePointToCaveMapPoint(e.getPoint());
                    Point ss = stateManager.getSelectionStart();
                    Point se = stateManager.getSelectionEnd();
                    Point dragStart =
                            new Point(ss.x + (p.x - dragStartSquare.x), ss.y
                                    + (p.y - dragStartSquare.y));
                    Point dragEnd =
                            new Point(se.x + (p.x - dragStartSquare.x), se.y
                                    + (p.y - dragStartSquare.y));
                    // If we've dragged outside the cave, ignore it.
                    if (dragStart.x >= 0
                            && dragStart.y >= 0
                            && dragEnd.x <= gameView.getCaveMap().getNumCols() - 1
                            && dragEnd.y <= gameView.getCaveMap().getNumRows() - 1) {
                        stateManager.setDragSelection(dragStart, dragEnd);
                        controller.moveSelection();
                        stateManager.setSelection(dragStart, dragEnd);
                    } else {
                        stateManager.setDragSelection(null, null);
                    }
                    gameView.redrawRequested();
                    
                }
            } else {
                setSelection(clickStart, clickEnd);
            }
            stateManager.setDragMode(false);
            clickStart = null;
            clickEnd = null;
        }
    }
    
}
