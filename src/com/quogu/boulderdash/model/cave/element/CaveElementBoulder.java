package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing a Boulder Cave Element.
 * 
 * @author 850226
 * 
 */
public class CaveElementBoulder extends CaveElementFalling {
    
    public CaveElementBoulder(CaveMap owner, int x, int y) {
        this(owner, x, y, false);
    }
    
    public CaveElementBoulder(CaveMap owner, int x, int y, boolean falling) {
        super(owner, x, y, new CollisionStrategyBoulder(), falling);
    }
    
    @Override
    public boolean isSlippery() {
        // Boulders are slippery.
        return true;
    }
    
}
