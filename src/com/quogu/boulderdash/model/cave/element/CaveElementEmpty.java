package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.CaveMap;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing the Empty Cave Element. All objects can collide with the
 * Empty Element, swapping their places.
 * 
 * @author 850226
 * 
 */
public class CaveElementEmpty extends CaveElementBase {
    
    public CaveElementEmpty(CaveMap owner, int x, int y) {
        super(owner, x, y, new CollisionStrategyEmpty());
    }
    
}
