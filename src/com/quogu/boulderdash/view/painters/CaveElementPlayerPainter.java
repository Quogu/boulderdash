package com.quogu.boulderdash.view.painters;

import java.awt.*;
import java.awt.geom.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class to paint the Player Cave Element.
 * 
 * @author 850226
 * 
 */
public class CaveElementPlayerPainter extends CaveElementBasePainter {
    
    public static final CaveElementPlayerPainter Instance =
            new CaveElementPlayerPainter("res/CaveElementPlayer.png");
    
    public CaveElementPlayerPainter(String graphicFileName) {
        super(graphicFileName);
    }
    
    @Override
    public void paint(CaveElement e, Graphics2D g) {
        CaveElementPlayer player = (CaveElementPlayer) e;
        // Use an affine transform to rotate the player's tile to the correct
        // orientation.
        AffineTransform transform = new AffineTransform();
        double theta;
        switch (player.getOrientation()) {
            case West:
                theta = 0;
                break;
            case North:
                theta = Math.PI / 2;
                break;
            case East:
                theta = Math.PI;
                break;
            case South:
                theta = Math.PI / 2 * 3;
                break;
            default:
                theta = 0;
        }
        transform.rotate(theta, g.getClipBounds().x
                + (g.getClipBounds().width / 2),
                g.getClipBounds().y + (g.getClipBounds().height / 2));
        transform.translate(g.getClipBounds().x, g.getClipBounds().y);
        g.drawImage(img, transform, null);
    }
}
