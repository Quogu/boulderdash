package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.CaveMap;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing the Dirt Cave Element. Dirt can be collided with by the
 * player, which will replace this element with the player, leaving an empty
 * element where the player once was.
 * 
 * @author 850226
 */
public class CaveElementDirt extends CaveElementBase {
    
    public CaveElementDirt(CaveMap owner, int x, int y) {
        super(owner, x, y, new CollisionStrategyDirt());
    }
}
