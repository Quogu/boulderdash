package com.quogu.boulderdash.controller.creator;

import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;

/**
 * Interface for controllers that create elements in a CaveMap to implement.
 * 
 * @author 850226
 * 
 */
public interface ElementCreator {
    
    /**
     * Remove the Cave Elements currently in place and replace them with ones of
     * the desired type in the specified Cave Map.
     * 
     * @param stateManager
     *            The application's State Manager.
     * @param caveMap
     *            The Cave Map to replace Elements in.
     * @param x1
     *            The leftmost X coordinate.
     * @param x2
     *            The rightmost X coordinate.
     * @param y1
     *            The leftmost Y coordinate.
     * @param y2
     *            The rightmost Y coordinate.
     */
    public void createCaveElements(StateManager stateManager, CaveMap caveMap,
            int x1, int x2, int y1, int y2);
}
