package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.CaveElement;
import com.quogu.boulderdash.model.cave.element.CaveElementEmpty;
import com.quogu.boulderdash.model.cave.element.CaveElementFactory;

public class CollisionStrategyEmpty implements CollisionStrategy {
    
    @Override
    public boolean canCollide(CaveElement caller, CaveElement collider) {
        if (caller.getClass() == CaveElementEmpty.class) {
            return true;
        }
        return false;
    }
    
    @Override
    public void collide(CaveElement caller, CaveElement collider) {
        assert (caller.getCaveMap() == collider.getCaveMap());
        CaveMap map = caller.getCaveMap();
        map.removeElement(caller);
        map.removeElement(collider);
        // The positions of the elements are switched.
        map.addElement(CaveElementFactory.getCaveElement(collider,
                caller.getX(), caller.getY()));
        map.addElement(CaveElementFactory.getCaveElement(caller,
                collider.getX(), collider.getY()));
        
    }
    
}
