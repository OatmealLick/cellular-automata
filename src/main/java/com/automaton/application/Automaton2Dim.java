package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.CellState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/11/16.
 */

/**
 * Automaton extension providing proper handling of two dimensional cellular automata
 *
 * @see Automaton
 * @see GameOfLife
 */
public abstract class Automaton2Dim extends Automaton {
    private int height, width;

    Automaton2Dim(CellNeighbourhood neighboursStrategy,
              CellStateFactory stateFactory,
              int height, int width) {
        super(neighboursStrategy, stateFactory);
        Map<CellCoordinates, CellState> map = new HashMap<>();
        Coords2D initialCoords = (Coords2D)initialCoordinates();
        for(int i=initialCoords.getY(); i<height; i++)
            for(int j=initialCoords.getX()+1; j<width; j++) {
                Coords2D coords2D = new Coords2D(j,i);
                map.put(coords2D, stateFactory.initialState(coords2D));
            }
        super.setCells(map);
        this.height=height;
        this.width=width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected boolean hasNextCoordinates(CellCoordinates coords) {
        return !(new Coords2D(width-1, height-1).equals(coords));
    }

    protected CellCoordinates initialCoordinates() {
        return new Coords2D(-1,0);
    }

    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
        if(!hasNextCoordinates(coords))
            return null;
        Coords2D coords2D = (Coords2D)coords;
        if(coords2D.getX()==width-1)
            return new Coords2D(0, coords2D.getY()+1);
        else
            return new Coords2D(coords2D.getX()+1, coords2D.getY());
    }


}
