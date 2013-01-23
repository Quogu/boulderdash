package com.quogu.boulderdash.model.cave.element;

import com.quogu.boulderdash.model.cave.CaveMap;
import com.quogu.boulderdash.model.cave.collision.*;

/**
 * A class representing the Player Cave Element. The Player Element is
 * controlled by the application user. It can collide with dirt, diamonds and
 * boulders.
 * 
 * @author 850226
 */
public class CaveElementPlayer extends CaveElementBase {
    
    private PlayerOrientation orientation;
    
    public CaveElementPlayer(CaveMap owner, int x, int y) {
        this(owner, x, y, PlayerOrientation.West);
    }
    
    public CaveElementPlayer(CaveMap owner, int x, int y, PlayerOrientation o) {
        super(owner, x, y, new CollisionStrategyPlayer());
        this.orientation = o;
    }
    
    public PlayerOrientation getOrientation() {
        return orientation;
    }
    
    public static PlayerOrientation getOrientation(int xDiff, int yDiff) {
        switch (xDiff) {
            case -1:
                return PlayerOrientation.West;
            case 1:
                return PlayerOrientation.East;
            default:
        }
        switch (yDiff) {
            case -1:
                return PlayerOrientation.North;
            case 1:
                return PlayerOrientation.South;
            default:
        }
        return PlayerOrientation.East;
    }
    
}
