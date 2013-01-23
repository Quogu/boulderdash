package com.quogu.boulderdash.view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.quogu.boulderdash.model.*;
import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;
import com.quogu.boulderdash.view.mouse.*;
import com.quogu.boulderdash.view.painters.*;
import com.quogu.boulderdash.controller.GameTime;
import com.quogu.boulderdash.controller.creator.*;

/**
 * A class for displaying the Cave Map to the player using the Swing toolkit.
 * 
 * @author 850226
 */
public class GameView extends JPanel implements CaveMapListener,
        StateManagerListener {
    
    private static final long serialVersionUID = -2472114306066870947L;
    
    protected final StateManager stateManager;
    protected CaveMap caveMap;
    public final int tileSize;
    protected final MouseHandler mouseHandlerNull;
    protected final MouseHandler mouseHandlerEdit;
    protected final MouseHandler mouseHandlerCreateBoulder;
    protected final MouseHandler mouseHandlerCreateDiamond;
    protected final MouseHandler mouseHandlerCreateDirt;
    protected final MouseHandler mouseHandlerCreatePlayer;
    protected final MouseHandler mouseHandlerCreateWall;
    protected MouseHandler currentMouseHandler;
    protected static final Map<Class<? extends CaveElement>, CaveElementPainter> painters =
            new HashMap<Class<? extends CaveElement>, CaveElementPainter>();
    static {
        painters.put(CaveElementBoulder.class,
                CaveElementPainters.BoulderInstance);
        painters.put(CaveElementDiamond.class,
                CaveElementPainters.DiamondInstance);
        painters.put(CaveElementDirt.class, CaveElementPainters.DirtInstance);
        painters.put(CaveElementEmpty.class, CaveElementEmptyPainter.Instance);
        painters.put(CaveElementPlayer.class, CaveElementPlayerPainter.Instance);
        painters.put(CaveElementWall.class, CaveElementWallPainter.Instance);
    }
    
    /**
     * A class for dispatching mouse events to the current Mouse Handler.
     * 
     * @author 850226
     * 
     */
    protected class MouseEventDispatcher implements MouseHandler {
        
        public void mouseEntered(MouseEvent e) {
            currentMouseHandler.mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e) {
            currentMouseHandler.mouseExited(e);
        }
        
        public void mouseClicked(MouseEvent e) {
            currentMouseHandler.mouseClicked(e);
        }
        
        public void mousePressed(MouseEvent e) {
            currentMouseHandler.mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e) {
            currentMouseHandler.mouseReleased(e);
        }
        
        public void mouseDragged(MouseEvent e) {
            currentMouseHandler.mouseDragged(e);
        }
        
        public void mouseMoved(MouseEvent e) {
            currentMouseHandler.mouseMoved(e);
        }
    }
    
    /**
     * Constructor for GameView. Takes a CaveMap and displays it.
     * 
     * @param caveMap
     *            The CaveMap to display.
     */
    public GameView(StateManager stateManager, CaveMap caveMap) {
        this(stateManager, caveMap, 32);
    }
    
    /**
     * Constructor for GameView. Takes a CaveMap and displays it. Allows
     * specifying of the size of the graphics tiles to use.
     * 
     * @param caveMap
     *            The CaveMap to display.
     * @param tileSize
     *            The size of the graphics tiles to use.
     */
    public GameView(StateManager stateManager, CaveMap caveMap, int tileSize) {
        super();
        this.stateManager = stateManager;
        this.caveMap = caveMap;
        this.tileSize = 32;
        this.setSize(caveMap.getNumCols() * tileSize, caveMap.getNumRows()
                * tileSize);
        stateManager.addListener(this);
        caveMap.addListener(this);
        new GameTime(stateManager, caveMap);
        mouseHandlerEdit = new MouseHandlerEdit(stateManager, this);
        mouseHandlerCreateBoulder =
                new MouseHandlerCreateElement(stateManager, this,
                        new ElementCreatorBoulder());
        mouseHandlerCreateDiamond =
                new MouseHandlerCreateElement(stateManager, this,
                        new ElementCreatorDiamond());
        mouseHandlerCreateDirt =
                new MouseHandlerCreateElement(stateManager, this,
                        new ElementCreatorDirt());
        mouseHandlerCreatePlayer =
                new MouseHandlerCreateElement(stateManager, this,
                        new ElementCreatorPlayer());
        mouseHandlerCreateWall =
                new MouseHandlerCreateElement(stateManager, this,
                        new ElementCreatorWall());
        mouseHandlerNull = new MouseHandlerNull();
        
        currentMouseHandler = mouseHandlerNull;
        MouseEventDispatcher dispatcher = new MouseEventDispatcher();
        this.addMouseListener(dispatcher);
        this.addMouseMotionListener(dispatcher);
    }
    
    /**
     * Get the Cave Map currently associated with this GameView.
     * 
     * @return The Game View's Cave Map.
     */
    public CaveMap getCaveMap() {
        return caveMap;
    }
    
    /**
     * Get the offset at which the Cave Map starts being drawn. Useful for mouse
     * handling.
     * 
     * @return The offset at which the Cave Map starts being drawn.
     */
    public Point getCaveMapOffset() {
        return new Point((getWidth() - (caveMap.getNumCols() * tileSize)) / 2,
                (getHeight() - (caveMap.getNumRows() * tileSize)) / 2);
    }
    
    @Override
    public void redrawRequested() {
        repaint();
    }
    
    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "An Error Has Occurred",
                JOptionPane.ERROR_MESSAGE);
    }
    
    public void gameStateChanged(GameState state) {
        switch (state) {
            case Edit:
                currentMouseHandler = mouseHandlerEdit;
                break;
            case CreateBoulder:
                currentMouseHandler = mouseHandlerCreateBoulder;
                break;
            case CreateDiamond:
                currentMouseHandler = mouseHandlerCreateDiamond;
                break;
            case CreateDirt:
                currentMouseHandler = mouseHandlerCreateDirt;
                break;
            case CreatePlayer:
                currentMouseHandler = mouseHandlerCreatePlayer;
                break;
            case CreateWall:
                currentMouseHandler = mouseHandlerCreateWall;
                break;
            case Play:
                // Yes, this is meant to fall through to Default.
                this.requestFocusInWindow();
            default:
                currentMouseHandler = mouseHandlerNull;
        }
        repaint();
    }
    
    @Override
    public void totalDiamondsChanged(int totalDiamonds) {
        // Don't care.
    }
    
    @Override
    public void currentDiamondsChanged(int currentDiamonds) {
        // Don't care.
    }
    
    @Override
    public void selectionChanged(Point selectionStart, Point selectionEnd) {
        // Don't care.
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        // Create a copy of the passed in Graphics so we don't have to worry
        // about restoring changes once we are done.
        Graphics2D g = (Graphics2D) (graphics.create());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        Point p = getCaveMapOffset();
        // Set the clip rectangle to the game area. Useful for small levels.
        g.setClip(p.x, p.y, caveMap.getNumCols() * tileSize,
                caveMap.getNumRows() * tileSize);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        Iterator<CaveElement> i = caveMap.getCaveElements();
        while (i.hasNext()) {
            CaveElement e = i.next();
            g.setClip(p.x + (e.getX() * 32), p.y + (e.getY() * 32), 32, 32);
            painters.get(e.getClass()).paint(e, g);
            if (stateManager.getGameState() != GameState.Play
                    && stateManager.getGameState() != GameState.Win
                    && stateManager.getGameState() != GameState.Lose) {
                // Paint the selection.
                if (stateManager
                        .pointInSelection(new Point(e.getX(), e.getY()))) {
                    painters.get(e.getClass()).paintSelection(e, g);
                }
                // Paint the drag selection if necessary.
                if (stateManager.isDragMode()
                        && stateManager.pointInDragSelection(new Point(
                                e.getX(), e.getY()))) {
                    painters.get(e.getClass()).paintSelection(e, g);
                }
            }
            
        }
        String message = "";
        GameState state = stateManager.getGameState();
        if (state == GameState.Win) {
            // If you're wondering why the message is "You're Winner!":
            // http://en.wikipedia.org/wiki/File:YOU%27RE_WINNER_trophy.jpg
            message = "You're Winner!";
        }
        if (state == GameState.Lose) {
            // MOOOOOORTAL KOOOOOOOOOMBAAAAAAAAAAT
            message = "Fatality!";
        }
        // Draw a special message if the game is won or lost..
        if (stateManager.getGameState() == GameState.Win
                || stateManager.getGameState() == GameState.Lose) {
            g.setClip(p.x, p.y, caveMap.getNumCols() * tileSize,
                    caveMap.getNumRows() * tileSize);
            g.setColor(Color.GRAY);
            // Draw the box with lowered alpha to match Figure 6.
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    0.4f));
            Rectangle r = g.getClipBounds();
            g.fillRoundRect(r.x + 50, r.y + 50, r.width - 100, r.height - 100,
                    20, 20);
            g.setColor(Color.WHITE);
            g.setComposite(AlphaComposite.Src);
            // Set the font larger.
            g.setFont(g.getFont().deriveFont(60f));
            // Find the area of the string so it can be centred.
            FontMetrics fm = g.getFontMetrics();
            Rectangle s = fm.getStringBounds(message, g).getBounds();
            g.drawString(message, r.x + ((r.width - s.width) / 2), r.y
                    + (r.height / 2));
        }
        g.dispose();
    }
}
