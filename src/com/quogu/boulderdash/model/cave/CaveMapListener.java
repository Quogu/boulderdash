package com.quogu.boulderdash.model.cave;

/**
 * Interface for classes wishing to be notified of a CaveMap's changes of state
 * to implement.
 * 
 * @author 850226
 * 
 */
public interface CaveMapListener {
    
    /**
     * Event that is fired when the model has performed changes that require a
     * redraw.
     */
    public void redrawRequested();
    
    /**
     * Event that is fired when something bad happens in the Cave Map. Should
     * probably be handled only by views.
     */
    public void showError(String message);
    
}
