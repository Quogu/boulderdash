package com.quogu.boulderdash.model.cave;

/**
 * A class to represent the exception thrown when a Cave Element with given
 * coordinates is requested and not found. As Empty Cave Elements have their own
 * class, this means somebody has been rather remiss with adding and removing
 * tiles.
 * 
 * @author 850226
 * 
 */
public class MissingCaveElementException extends Exception {
    
    private static final long serialVersionUID = 1597772084773909147L;
    
    public MissingCaveElementException(int x, int y) {
        super("Missing Cave Element at " + x + ", " + y + ".");
    }
    
}
