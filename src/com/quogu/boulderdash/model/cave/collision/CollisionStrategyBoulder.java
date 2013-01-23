package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class representing the collision strategy for Boulder Cave Elements.
 * 
 * @author 850226
 * 
 */
public class CollisionStrategyBoulder extends CollisionStrategyFalling {
    
    @Override
    public void collide(CaveElement caller, CaveElement collider)
            throws IllegalCollisionException {
        if (caller.getClass() == CaveElementBoulder.class
                && collider.getClass() == CaveElementPlayer.class) {
            CaveMap map = caller.getCaveMap();
            // Work out if the boulder can be pushed.
            try {
                if (caller.getY() == collider.getY()) {
                    int side = 0;
                    // Which side is the player on?
                    if (caller.getX() > collider.getX()
                            && caller.getX() < map.getNumCols() - 1) {
                        side = 1;
                    }
                    if (caller.getX() < collider.getX() && caller.getX() > 0) {
                        side = -1;
                    }
                    // If the player's potentially able to push the boulder:
                    if (side != 0
                            && map.getCaveElement(caller.getX() + side,
                                    caller.getY()).getClass() == CaveElementEmpty.class) {
                        // Perform the collision!
                        map.removeElement(caller);
                        map.removeElement(collider);
                        map.removeElement(map.getCaveElement(caller.getX()
                                + side, caller.getY()));
                        map.addElement(CaveElementFactory.getCaveElement(
                                collider, collider.getX() + side,
                                collider.getY()));
                        map.addElement(CaveElementFactory.getCaveElement(
                                caller, caller.getX() + side, caller.getY()));
                        map.addElement(new CaveElementEmpty(map, collider
                                .getX(), collider.getY()));
                    }
                }
            } catch (MissingCaveElementException e) {
                map.fireShowError(e.getMessage());
            }
        }
    }
}
