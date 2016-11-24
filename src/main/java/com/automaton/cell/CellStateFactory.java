package com.automaton.cell;

import com.automaton.states.CellState;

/**
 * Created by lick on 11/12/16.
 */
public interface CellStateFactory {
    public CellState initialState(CellCoordinates coords);
}
