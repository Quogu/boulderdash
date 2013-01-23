package com.quogu.boulderdash.controller.creator;

import java.util.*;
import java.awt.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class implementing the common parts of an ElementCreator.
 * 
 * @author 850226
 * 
 */
public abstract class ElementCreatorBase implements ElementCreator {
    
    /**
     * Iterate through the Cave Map and remove Cave Elements that are to be
     * replaced with a new type.
     * 
     * @param x1
     *            The leftmost X coordinate.
     * @param x2
     *            The rightmost X coordinate.
     * @param y1
     *            The leftmost Y coordinate.
     * @param y2
     *            The rightmost Y coordinate.
     */
    protected void removeCaveElements(CaveMap caveMap, int x1, int x2, int y1,
            int y2) {
        Iterator<CaveElement> it = caveMap.getCaveElements();
        // Remove all of the old elements. Order N.
        while (it.hasNext()) {
            CaveElement e = it.next();
            // If the Cave Element is within the bounds of the selection:
            if (x1 <= e.getX() && e.getX() <= x2 && y1 <= e.getY()
                    && e.getY() <= y2) {
                it.remove();
            }
        }
    }
    
    /**
     * Replace the removed Cave Elements with ones of the correct type.
     * 
     * @param x1
     *            The leftmost X coordinate.
     * @param x2
     *            The rightmost X coordinate.
     * @param y1
     *            The leftmost Y coordinate.
     * @param y2
     *            The rightmost Y coordinate.
     */
    protected abstract void replaceCaveElements(CaveMap caveMap, int x1,
            int x2, int y1, int y2);
    
    @Override
    public void createCaveElements(StateManager stateManager, CaveMap caveMap,
            int x1, int x2, int y1, int y2) {
        // If we're overwriting the player tile, then obviously there is no more
        // player on the Cave Map.
        //Slight abuse of the selection here.
        stateManager.setSelection(new Point(x1, y1), new Point(x2, y2));
        if (stateManager.getPlayerLocation() != null
                && stateManager.pointInSelection(stateManager
                        .getPlayerLocation())) {
            stateManager.setPlayerLocation(null);
        }
        stateManager.setSelection(null, null);
        // Split into two functions to reduce code duplication.
        removeCaveElements(caveMap, x1, x2, y1, y2);
        replaceCaveElements(caveMap, x1, x2, y1, y2);
        caveMap.fireRedrawRequested();
    }
}
