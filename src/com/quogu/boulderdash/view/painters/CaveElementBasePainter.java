package com.quogu.boulderdash.view.painters;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class to paint Cave Elements that do not have any special cases.
 * 
 * @author 850226
 * 
 */
public class CaveElementBasePainter implements CaveElementPainter {
    
    protected BufferedImage img;
    
    /**
     * Constructor for CaveElementBasePainter. Given a file name, it will assign
     * a graphic to the painter and use this graphic when called.
     * 
     * @param graphicFileName
     */
    public CaveElementBasePainter(String graphicFileName) {
        try {
            this.img = ImageIO.read(new File(graphicFileName));
        } catch (IOException e) {
            e.printStackTrace();
            this.img = new BufferedImage(0, 0, 0);
        }
        
    }
    
    @Override
    public void paint(CaveElement e, Graphics2D g) {
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
