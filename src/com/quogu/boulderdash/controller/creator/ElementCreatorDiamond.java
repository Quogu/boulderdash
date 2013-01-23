package com.quogu.boulderdash.controller.creator;

import com.quogu.boulderdash.model.cave.*;
import com.quogu.boulderdash.model.cave.element.*;

/**
 * A class to create diamonds within the specified tiles.
 * @author 850226
 *
 */
public class ElementCreatorDiamond extends ElementCreatorBase {
    
    public static final ElementCreatorDiamond Instance =
            new ElementCreatorDiamond();
    
    @Override
    protected void replaceCaveElements(CaveMap caveMap, int x1, int x2, int y1,
            int y2) {
        for (int j = y2; j >= y1; j--) {
            for (int i = x1; i <= x2; i++) {
                caveMap.addElement(new CaveElementDiamond(caveMap, i, j));
            }
        }
        
    }
    
}