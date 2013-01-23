package com.quogu.boulderdash;

import com.quogu.boulderdash.view.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;

/**
 * The entry point for the application.
 * 
 * @author 850226
 */
public class Application {
    
    /**
     * Entry method for the application. Set up the model and default view.
     * 
     * @param args
     *            The command-line arguments passed into the application.
     */
    public static void main(String[] args) {
        
        SwingViewHelper.setNativeLookAndFeel();
        CaveMap caveMap = new CaveMap(StateManager.Instance);
        MainWindow mainWindow = new MainWindow(caveMap, StateManager.Instance);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        
    }
    
}
