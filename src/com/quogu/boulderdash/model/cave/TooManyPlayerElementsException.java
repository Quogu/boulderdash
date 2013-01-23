package com.quogu.boulderdash.model.cave;

/**
 * A class to represent the exception thrown when more than one player tile is
 * created by the user. It's a shame that we can't just implement adding a
 * player tile by clicking, but the exam spec is what it is. :)
 * 
 * @author 850226
 * 
 */
public class TooManyPlayerElementsException extends Exception {
    private static final long serialVersionUID = -384639214597255819L;
    
    public TooManyPlayerElementsException(int x1, int x2, int y1, int y2) {
        super("Player tiles created from (" + x1 + "," + y1 + ") to (" + x2
                + "," + y2 + ").");
    }
    
}
