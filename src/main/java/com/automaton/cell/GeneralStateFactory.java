package com.automaton.cell;

import com.automaton.states.CellState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/12/16.
 */

/**
 * GeneralStateFactory is used do initialise map with custom state of the board
 */
public class GeneralStateFactory implements CellStateFactory {
    private Map<CellCoordinates, CellState> map = new HashMap<>();

    GeneralStateFactory(Map<CellCoordinates, CellState> map) {
        this.map = map;
    }

    @Override
    public CellState initialState(CellCoordinates coords) {
        return map.get(coords);
    }
}
