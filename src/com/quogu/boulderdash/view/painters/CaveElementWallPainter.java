package com.quogu.boulderdash.view.painters;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

import javax.imageio.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class to paint the Wall Cave Elements.
 * 
 * @author 850226
 * 
 */
public class CaveElementWallPainter implements CaveElementPainter {
    
    public static final CaveElementWallPainter Instance =
            new CaveElementWallPainter("res/CaveElementWall-", ".png");
    
    // Needed to store the wall images.
    protected Map<WallColour, BufferedImage> wallImages =
            new HashMap<WallColour, BufferedImage>();
    
    /**
     * Constructor for the Wall Painter. Will look through the list of existing
     * wall colours and find a graphics tile for each using the defined prefix
     * and suffix.
     * 
     * @param graphicFileNamePrefix
     *            The prefix to use when looking for graphics.
     * @param graphicFileNameSuffix
     *            The suffix to use when looking for graphics.
     */
    public CaveElementWallPainter(String graphicFileNamePrefix,
            String graphicFileNameSuffix) {
        for (WallColour wc : WallColour.values()) {
            String s = wc.toString();
            BufferedImage img;
            try {
                img =
                        ImageIO.read(new File(graphicFileNamePrefix + s
                                + graphicFileNameSuffix));
            } catch (IOException e) {
                e.printStackTrace();
                img = new BufferedImage(0, 0, 0);
            }
            wallImages.put(wc, img);
        }
    }
    
    @Override
    public void paint(CaveElement e, Graphics2D g) {
        CaveElementWall wall = (CaveElementWall)e;
        BufferedImage img = wallImages.get(wall.getColour());
        g.drawImage(img, g.getClipBounds().x, g.getClipBounds().y, null);
    }

    @Override
    public void paintSelection(CaveElement e, Graphics2D g) {
        g = (Graphics2D) g.create();
        g.setColor(Color.GRAY);
        g.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 0.4f));
        Rectangle r = g.getClipBounds();
        g.fillRoundRect(r.x, r.y, r.width, r.height, 15,
                15);
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.Src);
        g.drawRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 15, 15);
        g.dispose();
    }
}
