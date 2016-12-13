package com.automaton.cell;

import com.automaton.states.BinaryState;
import com.automaton.states.CellState;

/**
 * Created by lick on 11/10/16.
 */

/**
 * Atomic structure of board. Consists of {@link CellState} and {@link CellCoordinates}
 *
 * @see CellCoordinates
 * @see CellState
 */
public class Cell {
    final private CellState state;
    final private CellCoordinates coords;

    public Cell(CellState state, CellCoordinates coords) {
        this.state = state;
        this.coords = coords;
    }

    public CellState getState() {
        return state;
    }

    public CellCoordinates getCoords() {
        return coords;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null || getClass() != o.getClass()) return false;

        Cell cell = (Cell)o;

        return cell.coords.equals(coords) && cell.state==state;
    }

    @Override
    public int hashCode() {
        int offset = 0;
        if(getState()==BinaryState.ALIVE) {
            offset++;
        } else {
            offset+=2;
        }

        return offset + getCoords().hashCode();
    }

}
