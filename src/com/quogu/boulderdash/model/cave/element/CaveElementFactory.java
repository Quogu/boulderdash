package com.quogu.boulderdash.model.cave.element;

import java.awt.*;
import com.quogu.boulderdash.model.*;

/**
 * Class for creating new CaveElements based on arguments passed in. Intended to
 * simplify code in collision detection by making fewer cases needed.
 * 
 * @author 850226
 * 
 */
public class CaveElementFactory {
    
    protected static StateManager stateManager;
    
    /**
     * Returns a CaveElement of the same type as e with as many arguments as can
     * be inferred from e, with coordinates x and y.
     * 
     * @param e
     *            The object specifying the type of CaveElement to create and
     *            other parameters.
     * @param x
     *            The new X coordinate.
     * @param y
     *            The new Y coordinate.
     * @return A new CaveElement.
     */
    public static CaveElement getCaveElement(CaveElement e, int x, int y) {
        if (e.getClass() == CaveElementDirt.class) {
            return new CaveElementDirt(e.getCaveMap(), x, y);
        }
        if (e.getClass() == CaveElementEmpty.class) {
            return new CaveElementEmpty(e.getCaveMap(), x, y);
        }
        if (e.getClass() == CaveElementPlayer.class) {
            PlayerOrientation o =
                    CaveElementPlayer
                            .getOrientation(x - e.getX(), y - e.getY());
            StateManager.Instance.setPlayerLocation(new Point(x, y));
            return new CaveElementPlayer(e.getCaveMap(), x, y, o);
        }
        if (e.getClass() == CaveElementWall.class) {
            return new CaveElementWall(e.getCaveMap(), x, y,
                    ((CaveElementWall) e).getColour());
        }
        if (e.getClass() == CaveElementBoulder.class) {
            return new CaveElementBoulder(e.getCaveMap(), x, y,
                    ((CaveElementBoulder) e).isFalling());
        }
        if (e.getClass() == CaveElementDiamond.class) {
            return new CaveElementDiamond(e.getCaveMap(), x, y,
                    ((CaveElementDiamond) e).isFalling());
        }
        
        return null;
    }
    
    /**
     * Returns a CaveElement of the same type as the one passed in. Used to deal
     * with the common code in CaveElementFalling being unable to spawn new
     * Elements of the correct type.
     * 
     * @param e
     *            The Element to base the return value on.
     * @param x
     *            The X coordinate of the new Element.
     * @param y
     *            The Y coordinate of the new Element.
     * @param falling
     *            Whether the new Element will be falling.
     * @return A new CaveElementFalling.
     */
    public static CaveElementFalling getCaveElement(CaveElementFalling e,
            int x, int y, boolean falling) {
        if (e.getClass() == CaveElementBoulder.class) {
            return new CaveElementBoulder(e.getCaveMap(), x, y, falling);
        }
        if (e.getClass() == CaveElementDiamond.class) {
            return new CaveElementDiamond(e.getCaveMap(), x, y, falling);
        }
        
        return null;
    }
    
}
