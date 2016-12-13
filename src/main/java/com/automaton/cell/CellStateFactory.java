package com.automaton.cell;

import com.automaton.states.CellState;

/**
 * Created by lick on 11/12/16.
 */

/**
 * Defining proper initialisation of board.
 */
public interface CellStateFactory {
    /**
     * Method to be called for every single cell while initialising map for {@link com.automaton.application.Automaton}
     * @param coords Coordinates for which method returns CellState
     * @return state of cell
     */
    CellState initialState(CellCoordinates coords);
}
