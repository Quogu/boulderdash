package com.quogu.boulderdash.view.painters;

/**
 * A class to collect the Cave Elements painted using a Base Painter.
 * @author 850226
 *
 */
public class CaveElementPainters extends CaveElementBasePainter {
    
    public static final CaveElementBasePainter BoulderInstance =
            new CaveElementBasePainter("res/CaveElementBoulder.png");
    public static final CaveElementBasePainter DiamondInstance =
            new CaveElementBasePainter("res/CaveElementDiamond.png");
    public static final CaveElementBasePainter DirtInstance =
            new CaveElementBasePainter("res/CaveElementDirt.png");
    
    public CaveElementPainters(String graphicFileName) {
        super(graphicFileName);
    }
    
}
