package com.quogu.boulderdash.view.mouse;

import java.awt.event.MouseEvent;

/**
 * A class for ignoring all mouse events sent. Useful when no other mouse
 * handler is desired.
 * 
 * @author 850226
 * 
 */
public class MouseHandlerNull implements MouseHandler {
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
}
