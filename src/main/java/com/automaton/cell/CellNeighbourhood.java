package com.automaton.cell;

import java.util.Set;

/**
 * Created by lick on 11/10/16.
 */
/**
 * Cell Neighbourhood specifies neighbours of the given cell. Range is a value determining
 * distance between neighbours and center cell. The bigger range is the more neighbours cell has. Wrapping
 * defines whether a cell on the edge of the board has the neighbour which is located on the opposite edge of the
 * board.
 */
public interface CellNeighbourhood {
    /**
     * Returns neighbours of given cell
     *
     * @param coords coordinates of cell user want to get neighbours of
     * @return set full of neighbours
     */
    Set<CellCoordinates> cellNeighbours(CellCoordinates coords);
}
