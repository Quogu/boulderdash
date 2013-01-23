package com.quogu.boulderdash.view;

import javax.swing.*;

public class SwingViewHelper {
    
    /**
     * Set the look and feel of the Swing toolkit to emulate the platform it is
     * currently running on. I implemented this only because I utterly detest
     * the Metal L&F that is unfortunately default.
     */
    public static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // If this fails, we begrudgingly allow Swing to use its Metal L&F.
        }
    }
    
}
