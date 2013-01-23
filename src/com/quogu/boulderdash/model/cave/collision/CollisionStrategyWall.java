package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.element.CaveElement;

public class CollisionStrategyWall implements CollisionStrategy {
    
    @Override
    public boolean canCollide(CaveElement caller, CaveElement collider) {
        return false;
    }
    
    @Override
    public void collide(CaveElement caller, CaveElement collider)
            throws IllegalCollisionException {
        throw new IllegalCollisionException(caller, collider);
    }
    
}
