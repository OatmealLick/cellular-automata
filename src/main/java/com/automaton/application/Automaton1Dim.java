package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.CellState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/11/16.
 */

/**
 * Automaton extension providing proper handling of one dimensional cellular automata
 *
 * @see Automaton
 * @see ElementaryAutomaton
 */
public abstract class Automaton1Dim extends Automaton {
    private int width;

    Automaton1Dim(CellNeighbourhood neighboursStrategy,
                  CellStateFactory stateFactory,
                  int width) {
        super(neighboursStrategy, stateFactory);
        Map<CellCoordinates, CellState> map = new HashMap<>();

        for(int j=0; j<width; j++) {
            Coords1D coords1D = new Coords1D(j);
            map.put(coords1D, stateFactory.initialState(coords1D));
        }
        super.setCells(map);
        this.width=width;
    }

    public int getWidth() {
        return width;
    }

    protected boolean hasNextCoordinates(CellCoordinates coords) {
        return !(new Coords1D(width-1).equals(coords));
    }

    protected CellCoordinates initialCoordinates() {
        return new Coords1D(-1);
    }

    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
        if(!hasNextCoordinates(coords))
            return null;
        return new Coords1D(((Coords1D) coords).getX()+1);
    }
}