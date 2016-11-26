package com.automaton.cell;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by lick on 11/25/16.
 */
public class ElementaryNeighbourhood implements CellNeighbourhood {
    int width;

    public ElementaryNeighbourhood(int width) {
        this.width = width;
    }

    @Override
    public Set<CellCoordinates> cellNeighbours(CellCoordinates coords) {
        Set<CellCoordinates> resultSet = new LinkedHashSet<>();
        for (int i = -1; i < 2; i++) {
            int x = ((Coords1D) coords).getX() + i;
            if (x>=0 && x<width)
                resultSet.add(new Coords1D(x));
        }
        return resultSet;
    }
}
