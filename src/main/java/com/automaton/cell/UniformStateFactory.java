package com.automaton.cell;

import com.automaton.states.CellState;

/**
 * Created by lick on 11/12/16.
 */

/**
 * UniformStateFactory specifies one state for every cell to be initialised
 */
public class UniformStateFactory implements CellStateFactory {
    private CellState state;

    public UniformStateFactory(CellState state) {
        this.state = state;
    }

    @Override
    public CellState initialState(CellCoordinates coords) {
        return state;
    }
}
