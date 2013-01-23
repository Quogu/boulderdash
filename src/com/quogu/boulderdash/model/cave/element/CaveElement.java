package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.CaveMap;
import com.quogu.boulderdash.model.cave.MissingCaveElementException;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * An interface for elements contained in a CaveMap.
 * 
 * @author 850226
 */
public interface CaveElement extends Comparable<CaveElement> {
    
    /**
     * Get the cave map that owns this element.
     * 
     * @return The cave map that owns this element.
     */
    public CaveMap getCaveMap();
    
    /**
     * Get the X coordinate of this element.
     * 
     * @return The X coordinate of this element.
     */
    public int getX();
    
    /**
     * Get the Y coordinate of this element.
     * 
     * @return The Y coordinate of this element.
     */
    public int getY();
    
    /**
     * Make the element update itself to a new state if required.
     * 
     * @throws IllegalCollisionException
     *             Thrown if an illegal collision is attempted.
     * @throws MissingCaveElementException
     *             Thrown if a cave element is missing when requested.
     */
    public void tick() throws IllegalCollisionException,
            MissingCaveElementException;
    
    /**
     * Whether or not the Cave Element is slippery.
     * 
     * @return
     */
    public boolean isSlippery();
    
    /**
     * Shortcut for calling its CollisionStrategy's canCollide method with it as
     * the first parameter.
     * 
     * @param collider
     *            The tile trying to collide with this.
     * @return Whether the two tiles can collide.
     */
    public boolean canCollide(CaveElement collider);
    
    /**
     * Shortcut for calling its CollisionStrategy's collide method with it as
     * the first parameter.
     * 
     * @param collider
     *            The tile trying to collide with this.
     * @throws IllegalCollisionException
     *             Thrown when the collision attempted is not allowable.
     */
    public void collide(CaveElement collider) throws IllegalCollisionException;
    
    /**
     * Get the strategy the cave element should use for collisions. This should
     * be set in the constructor.
     * 
     * @returns The strategy used for collision handling.
     */
    public CollisionStrategy getCollisionStrategy();
}
