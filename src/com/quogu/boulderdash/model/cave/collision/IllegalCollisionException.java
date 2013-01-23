package com.quogu.boulderdash.model.cave.collision;

import com.quogu.boulderdash.model.cave.element.CaveElement;

/**
 * A class to represent the exception thrown when an undefined collision occurs.
 * If this happens, it is likely because canCollide() was not called before a
 * collision was attempted.
 * 
 * @author 850226
 * 
 */
public class IllegalCollisionException extends Exception {
    
    private static final long serialVersionUID = -4573533743724284573L;
    
    public IllegalCollisionException(CaveElement e1, CaveElement e2) {
        super(e1.getClass().getName() + "/" + e2.getClass().getName());
    }
    
}
