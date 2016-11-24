package com.automaton.cell;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lick on 11/10/16.
 */
public class VonNeumanNeighbourhood implements CellNeighbourhood{
    private int width, height, range;
    private boolean wrapping;

    public VonNeumanNeighbourhood(int width, int height, int range, boolean wrapping) {
        this.width = width;
        this.height = height;
        this.range = range;
        this.wrapping = wrapping;
    }

    /**
     * Von Neuman's neighbourhood means four adjacent cells following world direction pattern (N E S W).
     * For now only implemented range = 1
     *
     * TODO any given range
     *
     * @param coords
     * @return
     */
    @Override
    public Set<CellCoordinates> cellNeighbours(CellCoordinates coords) {
        Set<CellCoordinates> resultSet = new HashSet<>();
        Coords2D castCoords = (Coords2D)coords;
        int x = castCoords.getX(), y = castCoords.getY();

        if(x>=width || y>=height || x<0 || y<0)
            return resultSet;

        for(int i=0; i<=range; i++)
            for(int j=x-range+i; j<=x+range-i; j++) {
                if(wrapping) {
                    resultSet.add(new Coords2D(Math.floorMod(j, width), Math.floorMod(y - i, height)));
                    resultSet.add(new Coords2D(Math.floorMod(j, width), Math.floorMod(y + i, height)));
                }
                else {
                    if (y + i >= 0 && y + i < height && j >= 0 && j < width)
                        resultSet.add(new Coords2D(j, y + i));
                    if (y - i >= 0 && y - i < height && j >= 0 && j < width)
                        resultSet.add(new Coords2D(j, y - i));
                }
            }



        /*for(int j=x-range; j<x+range+1; j++)
            for(int i=y-range; i<y+range+1; i++)
                if(Math.hypot(x-j,y-i)<=range)
                    if(wrapping)
                        resultSet.add(new Coords2D(j % width, i % height));
                    else if(i>=0 && i<height && j>=0 && j<width)
                        resultSet.add(new Coords2D(j, i));*/

        /*for(int i=x-range; i<x+range+1; i++)
            if(wrapping)
                resultSet.add(new Coords2D( i % width, y));
            else if(i>=0 && i<width)
                resultSet.add(new Coords2D(i,y));

        for(int i=y-range; i<y+range+1; i++)
            if(wrapping)
                resultSet.add(new Coords2D( x, i % height));
            else if(i>=0 && i<height)
                resultSet.add(new Coords2D(x,i));*/

        // remove itself
        resultSet.remove(castCoords);

        return resultSet;
    }
}
