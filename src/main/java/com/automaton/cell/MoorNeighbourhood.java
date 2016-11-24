package com.automaton.cell;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lick on 11/10/16.
 */
public class MoorNeighbourhood implements CellNeighbourhood {
    private int width, height, range;
    private boolean wrapping;

    public MoorNeighbourhood(int width, int height, int range, boolean wrapping) {
        this.width = width;
        this.height = height;
        this.range = range;
        this.wrapping = wrapping;
    }


    /**
     * Moor neighbourhood includes every cell touching (even with corner) given one.
     *
     * @param coords
     * @return
     */
    @Override
    public Set<CellCoordinates> cellNeighbours(CellCoordinates coords) {
        Set<CellCoordinates> resultSet = new HashSet<>();
        Coords2D castCoords = (Coords2D) coords;

        int x = castCoords.getX();
        int y = castCoords.getY();

        if(x>=width || y>=height || x<0 || y<0)
            return resultSet;

        for(int j=x-range; j<x+range+1; j++)
            for(int i=y-range; i<y+range+1; i++)
                if(wrapping)
                    resultSet.add(new Coords2D(Math.floorMod(j, width), Math.floorMod(i, height)));
                else if(i>=0 && i<height && j>=0 && j<width)
                    resultSet.add(new Coords2D(j, i));

        // remove itself
        resultSet.remove(castCoords);

        return resultSet;
    }
}
