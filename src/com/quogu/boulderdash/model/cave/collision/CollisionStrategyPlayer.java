package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.element.*;

public class CollisionStrategyPlayer implements CollisionStrategy {
    
    @Override
    public boolean canCollide(CaveElement caller, CaveElement collider) {
        // Test if caveElement is a subclass of CaveElementFalling:
        if (caller.getClass() == CaveElementPlayer.class
                && CaveElementFalling.class.isAssignableFrom(collider
                        .getClass())) {
            return true;
        }
        return false;
    }
    
    @Override
    public void collide(CaveElement caller, CaveElement collider)
            throws IllegalCollisionException {
        assert (caller.getCaveMap() == collider.getCaveMap());
        if (CaveElementFalling.class.isAssignableFrom(collider.getClass())) {
            StateManager.Instance.setGameState(GameState.Lose);
        } else {
            throw new IllegalCollisionException(caller, collider);
        }
    }
    
}
