package com.quogu.boulderdash.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JToolBar.Separator;

import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.controller.*;

import com.quogu.boulderdash.model.cave.element.WallColour;

/**
 * A class for the main window of the application's view.
 * 
 * @author 850226
 * 
 */
public class MainWindow extends JFrame {
    
    private static final long serialVersionUID = 788353927344873250L;
    
    protected final StateManager stateManager;
    protected CaveMap caveMap;
    
    /**
     * Constructor for the Main Window.
     * 
     * @param caveMap
     *            The Cave Map to create within the window's Game View.
     * @param stateManager
     *            The application's State Manager.
     */
    public MainWindow(CaveMap caveMap, StateManager stateManager) {
        
        super("Boulder Dash");
        
        this.caveMap = caveMap;
        this.stateManager = stateManager;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1280, 720));
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        mainPanel.add(BorderLayout.NORTH, getMainToolbar());
        mainPanel.add(BorderLayout.CENTER, getGameView());
        
        this.setContentPane(mainPanel);
        this.pack();
        stateManager.setGameState(GameState.Edit);
    }
    
    /**
     * Helper function to assemble the game view.
     * 
     * @return
     */
    protected JScrollPane getGameView() {
        GameView gameView = new GameView(stateManager, caveMap);
        // We need to be able to receive focus.
        gameView.setFocusable(true);
        gameView.addKeyListener(new PlayMode(stateManager, caveMap));
        JScrollPane gameViewScrollPane =
                new JScrollPane(gameView,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return gameViewScrollPane;
    }
    
    /**
     * Helper function to assemble the main toolbar.
     * 
     * @return The main toolbar.
     */
    protected JToolBar getMainToolbar() {
        
        JToggleButton editButton = new JToggleButton("Edit");
        new ChangeState(stateManager, editButton, GameState.Edit);
        JToggleButton newWallButton = new JToggleButton("Create Wall");
        new ChangeState(stateManager, newWallButton, GameState.CreateWall);
        JToggleButton newBoulderButton = new JToggleButton("Create Boulder");
        new ChangeState(stateManager, newBoulderButton, GameState.CreateBoulder);
        JToggleButton newDiamondButton = new JToggleButton("Create Diamond");
        new ChangeState(stateManager, newDiamondButton, GameState.CreateDiamond);
        JToggleButton newDirtButton = new JToggleButton("Create Dirt");
        new ChangeState(stateManager, newDirtButton, GameState.CreateDirt);
        JToggleButton newPlayerButton = new JToggleButton("Create Player");
        new ChangeState(stateManager, newPlayerButton, GameState.CreatePlayer);
        JToggleButton playButton = new JToggleButton("Play");
        new ChangeState(stateManager, playButton, GameState.Play);
        
        // Use a button group to enforce only one being selected at once.
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(editButton);
        buttonGroup.add(newWallButton);
        buttonGroup.add(newBoulderButton);
        buttonGroup.add(newDiamondButton);
        buttonGroup.add(newDirtButton);
        buttonGroup.add(newPlayerButton);
        buttonGroup.add(playButton);
        
        // If we don't do this, the toolbar looks weird outside of Edit mode.
        Separator sep = new Separator();
        new ChangeStateComponentVisible(stateManager, sep);
        
        JButton deleteButton = new JButton("Delete");
        new ChangeStateComponentVisible(stateManager, deleteButton);
        
        JComboBox<WallColour> wallColourCombo = new JComboBox<WallColour>();
        // Populate the combo box with all available colours.
        for (WallColour c : WallColour.values()) {
            wallColourCombo.addItem(c);
        }
        wallColourCombo.setSelectedIndex(-1);
        wallColourCombo.setEnabled(false);
        new WallColourCombo(stateManager, caveMap, wallColourCombo);
        
        JTextField diamondText = new JTextField();
        diamondText.setText(String.valueOf(stateManager.getTotalDiamonds()));
        new DiamondsCount(stateManager, diamondText);
        
        JToolBar mainToolbar = new JToolBar("Main");
        mainToolbar.add(editButton);
        mainToolbar.add(newWallButton);
        mainToolbar.add(newBoulderButton);
        mainToolbar.add(newDiamondButton);
        mainToolbar.add(newDirtButton);
        mainToolbar.add(newPlayerButton);
        mainToolbar.add(playButton);
        mainToolbar.add(sep);
        mainToolbar.add(deleteButton);
        mainToolbar.add(wallColourCombo);
        mainToolbar.addSeparator();
        mainToolbar.add(diamondText);
        
        return mainToolbar;
        
    }
}
