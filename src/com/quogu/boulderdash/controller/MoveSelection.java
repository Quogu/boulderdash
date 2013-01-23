package com.quogu.boulderdash.controller;

import java.util.*;
import java.awt.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class for moving the selection after it has been dragged in Edit Mode.
 * 
 * @author 850226
 * 
 */
public class MoveSelection {
    
    protected StateManager stateManager;
    protected CaveMap caveMap;
    
    /**
     * Constructor for MoveSelection.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param caveMap
     *            The application's Cave Map.
     */
    public MoveSelection(StateManager stateManager, CaveMap caveMap) {
        this.stateManager = stateManager;
        this.caveMap = caveMap;
    }
    
    /**
     * Move the selection to the location they were dragged to.
     * 
     * First fill a linked list with all the selected elements, removing them
     * from the cave map as we go, then fill the selection with Empty tiles,
     * then replace the drag area with the selection.
     */
    public void moveSelection() {
        LinkedList<CaveElement> l = new LinkedList<CaveElement>();
        Point ss = stateManager.getSelectionStart();
        Point se = stateManager.getSelectionEnd();
        Point ds = stateManager.getDragSelectionStart();
        Iterator<CaveElement> it = caveMap.getCaveElements();
        // Fill up our linked list.
        while (it.hasNext()) {
            CaveElement e = it.next();
            if (stateManager.pointInSelection(new Point(e.getX(), e.getY()))) {
                l.add(e);
                it.remove();
            }
        }
        // Replace the selection area with Empty Elements.
        for (int j = se.y; j >= ss.y; j--) {
            for (int i = ss.x; i <= se.x; i++) {
                caveMap.addElement(new CaveElementEmpty(caveMap, i, j));
            }
        }
        // Remove all the Elements under the Drag Selection.
        it = caveMap.getCaveElements();
        while (it.hasNext()) {
            CaveElement e = it.next();
            if (stateManager
                    .pointInDragSelection(new Point(e.getX(), e.getY()))) {
                it.remove();
            }
        }
        // Add the Elements in the list to the Drag Selection area.
        for (CaveElement e : l) {
            caveMap.addElement(CaveElementFactory.getCaveElement(e, e.getX()
                    + ds.x - ss.x, e.getY() + ds.y - ss.y));
        }
    }
}
