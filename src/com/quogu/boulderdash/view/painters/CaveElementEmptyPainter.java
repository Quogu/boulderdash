package com.quogu.boulderdash.view.painters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.quogu.boulderdash.model.cave.element.CaveElement;

public class CaveElementEmptyPainter implements CaveElementPainter {
    
    public static final CaveElementEmptyPainter Instance =
            new CaveElementEmptyPainter();
    
    @Override
    public void paint(CaveElement e, Graphics2D g) {
        // My favourite kind of method - literally do nothing!
        
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
