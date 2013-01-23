package com.quogu.boulderdash.view.painters;

import java.awt.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * Interface for classes that paint cave elements to implement.
 * 
 * @author 850226
 * 
 */
public interface CaveElementPainter {
    
    /**
     * Paint the specified Cave Element.
     * 
     * @param e
     *            The Element to be painted.
     * @param g
     *            The graphics object onto which the element should be painted.
     */
    void paint(CaveElement e, Graphics2D g);
    
    /**
     * Paint a selection border of sorts around the specified Cave Element.
     * 
     * @param e
     *            The Element to paint a selection border on.
     * @param g
     *            The graphics object onto which the selection border should be
     *            painted.
     */
    void paintSelection(CaveElement e, Graphics2D g);
    
}
