package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.element.CaveElement;

public interface CollisionStrategy {
    
    /**
     * Test if the element can be moved into by the given CaveElement.
     * Specifying the caller as well as the collider allows the potential for
     * multiple collision strategies to be deployed that cascade until the first
     * returns true for canCollide.
     * 
     * @param caller
     *            The stationary element being collided with.
     * @param collider
     *            The element moving and causing the collision.
     * @return Is the object penetrable by caveElement?
     */
    public boolean canCollide(CaveElement caller, CaveElement collider);
    
    /**
     * Perform collision logic between this and CaveElement. It should be noted
     * that caveElement is always the active element in collisions - it is the
     * one doing the moving.
     * 
     * @param caller
     *            The stationary element being collided with.
     * @param collider
     *            The element moving and causing the collision.
     * @throws IllegalCollisionException
     *             An error that occurs when collisions are performed without
     *             checking if objects can collide.
     */
    public void collide(CaveElement caller, CaveElement collider)
            throws IllegalCollisionException;
    
}
