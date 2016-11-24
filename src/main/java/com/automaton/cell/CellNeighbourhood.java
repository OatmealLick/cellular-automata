package com.automaton.cell;

import java.util.Set;

/**
 * Created by lick on 11/10/16.
 */
public interface CellNeighbourhood {
    public Set<CellCoordinates> cellNeighbours(CellCoordinates coords);
}
