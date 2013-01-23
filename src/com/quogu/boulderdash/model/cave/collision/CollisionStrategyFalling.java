package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.element.CaveElement;
import com.quogu.boulderdash.model.cave.element.CaveElementFalling;
import com.quogu.boulderdash.model.cave.element.CaveElementPlayer;

/**
 * A class to represent the common part of the collision strategy of falling
 * Cave Elements.
 * 
 * @author 850226
 * 
 */
public abstract class CollisionStrategyFalling implements CollisionStrategy {
    
    @Override
    public boolean canCollide(CaveElement caller, CaveElement collider) {
        if (CaveElementFalling.class.isAssignableFrom(caller.getClass())
                && collider.getClass() == CaveElementPlayer.class) {
            return true;
        }
        return false;
    }
    
    @Override
    public abstract void collide(CaveElement caller, CaveElement collider)
            throws IllegalCollisionException;
    
}
