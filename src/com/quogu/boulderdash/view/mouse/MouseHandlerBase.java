package com.quogu.boulderdash.view.mouse;

import java.awt.Point;
import java.awt.event.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.view.*;

public class MouseHandlerBase implements MouseHandler {
    
    protected StateManager stateManager;
    protected GameView gameView;
    protected Point clickStart;
    protected Point clickEnd;
    
    /**
     * Constructor for MouseHandlerCreateElement.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param gameView
     *            The application's Game View.
     * @param controller
     *            The controller for this mouse handler.
     */
    public MouseHandlerBase(StateManager stateManager,
            GameView gameView) {
        this.stateManager = stateManager;
        this.gameView = gameView;
    }
    
    /**
     * Convenience function for converting mouse coordinates to Cave Map
     * coordinates.
     * 
     * @param point
     *            The mouse point to convert.
     * @return The Cave Map coordinates of the given mouse point.
     */
    protected Point mousePointToCaveMapPoint(Point point) {
        Point p = gameView.getCaveMapOffset();
        return new Point((point.x - p.x) / 32, (point.y - p.y) / 32);
    }
    
    /**
     * Given two points making a rectangle, find the top left corner.
     * 
     * @param point1
     *            The first point.
     * @param point2
     *            The second point.
     * @return The top left point in the rectangle.
     */
    protected Point minPoint(Point point1, Point point2) {
        return new Point(Math.min(point1.x, point2.x), Math.min(point1.y,
                point2.y));
    }
    
    /**
     * Given two points making a rectangle, find the bottom right corner.
     * 
     * @param point1
     *            The first point.
     * @param point2
     *            The second point.
     * @return The bottom right point in the rectangle.
     */
    protected Point maxPoint(Point point1, Point point2) {
        return new Point(Math.max(point1.x, point2.x), Math.max(point1.y,
                point2.y));
    }
    
    protected void setSelection(Point point1, Point point2) {
        CaveMap map = gameView.getCaveMap();
        Point p1 =
                minPoint(mousePointToCaveMapPoint(point1),
                        mousePointToCaveMapPoint(point2));
        Point p2 =
                maxPoint(mousePointToCaveMapPoint(point1),
                        mousePointToCaveMapPoint(point2));
        // Ensure that the selection is valid before we pass it on.
        if (p1.x < map.getNumCols() && p1.x >= 0 && p1.y < map.getNumRows()
                && p1.y >= 0 && p2.x < map.getNumCols() && p2.x >= 0
                && p2.y < map.getNumRows() && p2.y >= 0) {
            stateManager.setSelection(p1, p2);
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (clickStart != null) {
            Point p1 = stateManager.getSelectionStart();
            Point p2 = stateManager.getSelectionEnd();
            setSelection(clickStart, e.getPoint());
            // Check if the selection has changed - if yes, redraw.
            if (p1 != stateManager.getSelectionStart()
                    && p2 != stateManager.getSelectionEnd()) {
                gameView.redrawRequested();
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickStart = e.getPoint();
            setSelection(clickStart, clickStart);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickEnd = e.getPoint();
            setSelection(clickStart, clickEnd);
            clickStart = null;
            clickEnd = null;
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
