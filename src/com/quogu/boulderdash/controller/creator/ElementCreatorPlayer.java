package com.quogu.boulderdash.controller.creator;

import java.awt.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class to create a player at the specified tile.
 * 
 * @author 850226
 * 
 */
public class ElementCreatorPlayer implements ElementCreator {
    
    public static final ElementCreatorPlayer Instance =
            new ElementCreatorPlayer();
    
    @Override
    public void createCaveElements(StateManager stateManager, CaveMap caveMap,
            int x1, int x2, int y1, int y2) {
        // Check if more than one player is trying to be created.
        try {
            if (x1 != x2 || y1 != y2) {
                throw new TooManyPlayerElementsException(x1, x2, y1, y2);
            }
            // We're OK, only one player's being created.
            try {
                Point p = stateManager.getPlayerLocation();
                // We may need to remove the old player location.
                if (p != null && (p.x != x1 || p.y != y1)) {
                    caveMap.removeElement(caveMap.getCaveElement(p.x, p.y));
                    caveMap.addElement(new CaveElementEmpty(caveMap, p.x, p.y));
                }
                caveMap.removeElement(caveMap.getCaveElement(x1, y1));
            } catch (MissingCaveElementException e) {
                caveMap.fireShowError(e.getMessage());
            }
            caveMap.addElement(new CaveElementPlayer(caveMap, x1, y1));
            stateManager.setPlayerLocation(new Point(x1, y1));
            caveMap.fireRedrawRequested();
        } catch (TooManyPlayerElementsException e) {
            caveMap.fireShowError(e.getMessage());
        }
    }
}
