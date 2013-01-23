package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.CaveMap;
import com.quogu.boulderdash.model.cave.MissingCaveElementException;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * An base class for elements contained in a CaveMap, implementing the common
 * functionality between CaveElements. CaveElements are immutable. When tick is
 * called, they will be recreated with new property values if necessary. This is
 * to facilitate their storage in a SortedSet - immutability is here used to
 * ensure that concerns are separated so the elements do not have to worry about
 * the sorting of the set containing them.
 * 
 * @author 850226
 */
public abstract class CaveElementBase implements CaveElement {
    
    protected final int x;
    protected final int y;
    protected final CaveMap owner;
    protected final CollisionStrategy collisionStrategy;
    
    public CaveElementBase(CaveMap owner, int x, int y,
            CollisionStrategy collisionStrategy) {
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.collisionStrategy = collisionStrategy;
    }
    
    /**
     * The Cave Elements are sorted in order from greatest Y to smallest Y in
     * order to make ticking work correctly.
     */
    @Override
    public int compareTo(CaveElement e) {
        // Check for less than.
        if (this.y > e.getY() || (this.y == e.getY() && this.x < e.getX())) {
            return -1;
        }
        // Check for equal.
        if (this.y == e.getY() && this.x == e.getX()) {
            return 0;
        }
        // By elimination, it must be greater than.
        return 1;
    }
    
    public CaveMap getCaveMap() {
        return owner;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public void tick() throws IllegalCollisionException,
            MissingCaveElementException {
        // By default, nothing needs to be done.
    }
    
    @Override
    public boolean isSlippery() {
        return false;
    }
    
    @Override
    public boolean canCollide(CaveElement collider) {
        return collisionStrategy.canCollide(this, collider);
    }
    
    @Override
    public void collide(CaveElement collider) throws IllegalCollisionException {
        collisionStrategy.collide(this, collider);
    }
    
    @Override
    public CollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }
    
}
