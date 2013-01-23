package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.CaveElement;
import com.quogu.boulderdash.model.cave.element.CaveElementDirt;
import com.quogu.boulderdash.model.cave.element.CaveElementEmpty;
import com.quogu.boulderdash.model.cave.element.CaveElementFactory;
import com.quogu.boulderdash.model.cave.element.CaveElementPlayer;

public class CollisionStrategyDirt implements CollisionStrategy {
    
    @Override
    public boolean canCollide(CaveElement caller, CaveElement collider) {
        if (caller.getClass() == CaveElementDirt.class
                && collider.getClass() == CaveElementPlayer.class) {
            return true;
        }
        return false;
    }
    
    @Override
    public void collide(CaveElement caller, CaveElement collider)
            throws IllegalCollisionException {
        assert (caller.getCaveMap() == collider.getCaveMap());
        if (caller.getClass() == CaveElementDirt.class
                && collider.getClass() == CaveElementPlayer.class) {
            collidePlayer((CaveElementDirt) caller, (CaveElementPlayer) collider);
        } else {
            throw new IllegalCollisionException(caller, collider);
        }
    }
    
    /**
     * Handle collision with a player.
     * 
     * @param player
     *            The player that has collided with this.
     */
    public void collidePlayer(CaveElementDirt dirt, CaveElementPlayer player) {
        CaveMap map = dirt.getCaveMap();
        map.removeElement(dirt);
        map.removeElement(player);
        // The player has moved here.
        map.addElement(CaveElementFactory.getCaveElement(player, dirt.getX(),
                dirt.getY()));
        // An empty space has been created where the player once was.
        map.addElement(new CaveElementEmpty(map, player.getX(),
                player.getY()));
    }
    
}
