package com.automaton.cell;

import com.automaton.cell.CellCoordinates;
import com.automaton.cell.Coords2D;
import com.automaton.cell.VonNeumanNeighbourhood;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by lick on 11/10/16.
 */
public class VonNeumanNeighbourhoodTest {
    @Test
    public void randomCellNeighbourhoodRange1() {
        Coords2D exampleCoords = new Coords2D(4,6);
        Set<CellCoordinates> set = new VonNeumanNeighbourhood(10,10,1,false).cellNeighbours(exampleCoords);

        boolean result = true;

        if(!set.contains(new Coords2D(5,6))) result = false;
        if(!set.contains(new Coords2D(3,6))) result = false;
        if(!set.contains(new Coords2D(4,5))) result = false;
        if(!set.contains(new Coords2D(4,7))) result = false;
        if(set.contains(new Coords2D(4,6))) result = false;
        System.out.println(set.toString());
        Assert.assertTrue("Testing for four neigbours within range: 1", result);
    }

    @Test
    public void cellNeighbourhoodRange3() {
        Coords2D randomCoords = new Coords2D(50,23);
        Set<CellCoordinates> set = new VonNeumanNeighbourhood(60,26,2,false).cellNeighbours(randomCoords);

        Set<CellCoordinates> resultSet = new HashSet<>(Arrays.asList(
                // perpendicular
                new Coords2D(48,23),
                new Coords2D(49,23),
                new Coords2D(51,23),
                new Coords2D(52,23),
                new Coords2D(50,24),
                new Coords2D(50,22),
                new Coords2D(50,25),
                new Coords2D(50,21),

                // diagonal
                new Coords2D(49,22),
                new Coords2D(51,22),
                new Coords2D(51,24),
                new Coords2D(49,24)

        ));

        Assert.assertTrue("Comparing two sets of neighbours with range 2", set.equals(resultSet));
    }

    @Test
    public void cellNeighbourhoodRange2WithWrapping() {
        Coords2D exampleCoords = new Coords2D(99,99);
        Set<CellCoordinates> set = new VonNeumanNeighbourhood(100,100,2,true).cellNeighbours(exampleCoords);

        boolean result = true;

        // top bottom left right
        if(!set.contains(new Coords2D(1,99))) result = false;
        if(!set.contains(new Coords2D(97,99))) result = false;
        if(!set.contains(new Coords2D(99,1))) result = false;
        if(!set.contains(new Coords2D(99,97))) result = false;

        // corners
        if(!set.contains(new Coords2D(98,98))) result = false;
        if(!set.contains(new Coords2D(98,0))) result = false;
        if(!set.contains(new Coords2D(0,98))) result = false;
        if(!set.contains(new Coords2D(0,0))) result = false;

        // example coords in set? don't think so
        if(set.contains(new Coords2D(99,99))) result = false;

        // random cell? naah
        //if(set.contains(new Coords2D(97,97))) result = false;

        Assert.assertTrue("Testing neighbourhood with range 2 and wrapping on", result);

    }

    @Test
    public void cellNeighbourhoodRange3WithWrapping() {
        Coords2D exampleCoords = new Coords2D(99,99);
        Set<CellCoordinates> set = new VonNeumanNeighbourhood(100,100,3,true).cellNeighbours(exampleCoords);

        boolean result = true;

        // top bottom left right
        if(!set.contains(new Coords2D(1,99))) result = false;
        if(!set.contains(new Coords2D(97,99))) result = false;
        if(!set.contains(new Coords2D(99,1))) result = false;
        if(!set.contains(new Coords2D(99,97))) result = false;

        // corners
        if(!set.contains(new Coords2D(98,98))) result = false;
        if(!set.contains(new Coords2D(98,0))) result = false;
        if(!set.contains(new Coords2D(0,98))) result = false;
        if(!set.contains(new Coords2D(0,0))) result = false;

        // example coords in set? don't think so
        if(set.contains(new Coords2D(99,99))) result = false;

        // random cell? naah
        if(set.contains(new Coords2D(97,97))) result = false;

        Assert.assertTrue("Testing neighbourhood with range 3 and wrapping on", result);

    }
}
