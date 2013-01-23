package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class representing the collision strategy for Diamond Cave Elements.
 * 
 * @author 850226
 * 
 */
public class CollisionStrategyDiamond extends CollisionStrategyFalling {
    
    @Override
    public void collide(CaveElement caller, CaveElement collider) {
        assert (caller.getCaveMap() == collider.getCaveMap());
        if (caller.getClass() == CaveElementDiamond.class
                && collider.getClass() == CaveElementPlayer.class) {
            // Remove the diamond, replace it with the player, replace the
            // player with empty.
            CaveMap map = caller.getCaveMap();
            map.removeElement(caller);
            map.removeElement(collider);
            map.addElement(CaveElementFactory.getCaveElement(collider,
                    caller.getX(), caller.getY()));
            map.addElement(new CaveElementEmpty(map, collider.getX(), collider
                    .getY()));
            StateManager.Instance.setCurrentDiamonds(StateManager.Instance
                    .getCurrentDiamonds() - 1);
        }
    }
}
