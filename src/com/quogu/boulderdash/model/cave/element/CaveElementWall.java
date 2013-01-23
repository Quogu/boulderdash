package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.CaveMap;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing the Wall Cave Element. Walls cannot be collided with and
 * do not need updating.
 * 
 * @author 850226
 */
public class CaveElementWall extends CaveElementBase {
    
    private WallColour colour;
    
    /**
     * Default constructor.
     */
    public CaveElementWall(CaveMap owner, int x, int y) {
        this(owner, x, y, WallColour.Blue);
    }
    
    /**
     * Constructor with specified wall colour.
     * 
     * @param wallColour
     *            The colour of the wall.
     */
    public CaveElementWall(CaveMap owner, int x, int y, WallColour wallColour) {
        super(owner, x, y, new CollisionStrategyWall());
        this.setColour(wallColour);
    }
    
    /**
     * @return The colour of the wall.
     */
    public WallColour getColour() {
        return colour;
    }
    
    /**
     * @param wallColour
     *            The colour of the wall.
     */
    public void setColour(WallColour wallColour) {
        this.colour = wallColour;
    }
    
}
