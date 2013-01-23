package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing a Diamond Cave Element.
 * 
 * @author 850226
 * 
 */
public class CaveElementDiamond extends CaveElementFalling {
    
    public CaveElementDiamond(CaveMap owner, int x, int y) {
        this(owner, x, y, false);
    }
    
    public CaveElementDiamond(CaveMap owner, int x, int y, boolean falling) {
        super(owner, x, y, new CollisionStrategyDiamond(), falling);
    }
    
    @Override
    public boolean isSlippery() {
        // Diamonds are slippery.
        return true;
    }
    
}
