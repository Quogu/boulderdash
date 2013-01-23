package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing a Cave Element capable of falling.
 * 
 * @author 850226
 */
public abstract class CaveElementFalling extends CaveElementBase {
    
    protected final boolean falling;
    
    public CaveElementFalling(CaveMap owner, int x, int y,
            CollisionStrategy collisionStrategy) {
        this(owner, x, y, collisionStrategy, false);
    }
    
    public CaveElementFalling(CaveMap owner, int x, int y,
            CollisionStrategy collisionStrategy, boolean falling) {
        super(owner, x, y, collisionStrategy);
        this.falling = falling;
    }
    
    public boolean isFalling() {
        return falling;
    }
    
    @Override
    public void tick() throws IllegalCollisionException,
            MissingCaveElementException {
        boolean done = false;
        if (y < owner.getNumRows() - 1) {
            CaveElement tileBelow = owner.getCaveElement(x, y + 1);
            if (!falling) {
                // If what's below us is empty, we start falling!
                if (tileBelow.getClass() == CaveElementEmpty.class) {
                    owner.removeElement(this);
                    CaveElement e =
                            CaveElementFactory.getCaveElement(this, x, y, true);
                    owner.addElement(e);
                    // Stops objects from leaving a space from the one on top
                    // when they fall.
                    e.tick();
                }
            }
            // Only needs to move if falling.
            if (falling) {
                // Only needs to move if it has not hit the bottom of the cave.
                if (y < owner.getNumRows() - 1) {
                    // Test if we can collide with the object below.
                    CaveElement tileSide;
                    if (tileBelow.canCollide(this)) {
                        tileBelow.collide(this);
                    }
                    // If we can't collide with it, is it slippery or do we
                    // stop?
                    else if (tileBelow.isSlippery()) {
                        // It's slippery so we look for other pathways to
                        // continue.
                        // First check the bottom right, then the bottom left.
                        if (x < owner.getNumCols() - 1) {
                            tileBelow = owner.getCaveElement(x + 1, y + 1);
                            tileSide = owner.getCaveElement(x + 1, y);
                            if (tileSide.getClass() == CaveElementEmpty.class
                                    && tileBelow.canCollide(this)) {
                                tileBelow.collide(this);
                                done = true;
                            }
                        }
                        // Now check the bottom left if we're not done.
                        if (!done && x > 0) {
                            tileBelow = owner.getCaveElement(x - 1, y + 1);
                            tileSide = owner.getCaveElement(x - 1, y);
                            if (tileSide.getClass() == CaveElementEmpty.class
                                    && tileBelow.canCollide(this)) {
                                tileBelow.collide(this);
                                done = true;
                            }
                        }
                        if (!done) {
                            // We can't seem to fall any further. Stop falling.
                            owner.removeElement(this);
                            owner.addElement(CaveElementFactory.getCaveElement(
                                    this, x, y, false));
                        }
                        
                    } else {
                        // We stop falling.
                        owner.removeElement(this);
                        owner.addElement(CaveElementFactory.getCaveElement(
                                this, x, y, false));
                    }
                }
            }
        } else {
            if (falling) {
                owner.removeElement(this);
                owner.addElement(CaveElementFactory.getCaveElement(this, x, y,
                        false));
            }
        }
    }
}
