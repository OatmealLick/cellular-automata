package com.automaton.cell;

import com.automaton.cell.CellCoordinates;
import com.automaton.cell.Coords2D;
import com.automaton.cell.MoorNeighbourhood;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by lick on 11/10/16.
 */
public class MoorNeighbourhoodTest {
    @Test
    public void randomCellNeighbourhoodRange1() {
        Set<CellCoordinates> set = new MoorNeighbourhood(2000,2000,1,false).cellNeighbours(new Coords2D(77,1290));
        boolean result = true;

        if(!set.contains(new Coords2D(78, 1291))) result = false;
        if(!set.contains(new Coords2D(77, 1291))) result = false;
        if(!set.contains(new Coords2D(76, 1291))) result = false;
        if(!set.contains(new Coords2D(77, 1289))) result = false;
        if(!set.contains(new Coords2D(78, 1289))) result = false;
        if(!set.contains(new Coords2D(76, 1289))) result = false;
        if(!set.contains(new Coords2D(76, 1290))) result = false;
        if(!set.contains(new Coords2D(78, 1290))) result = false;
        if(set.contains(new Coords2D(77, 1290))) result = false;

        Assert.assertTrue("Testing for eight neighbours within range: 1", result);
    }

    @Test
    public void cellOutOfBoardNeighbourhoodRange1() {
        Set<CellCoordinates> set = new MoorNeighbourhood(50,50,1,false).cellNeighbours(new Coords2D(77,1290));
        Assert.assertTrue("Should result in empty set, as Coords given were not in the board", set.isEmpty());
    }

    @Test
    public void cellOutOfBoardWithWrapping() {
        Set<CellCoordinates> set = new MoorNeighbourhood(100,100,1,true).cellNeighbours(new Coords2D(100,50));

        Assert.assertTrue("Should result in empty set, as Coords given were not in the board", set.isEmpty());
    }

    @Test
    public void borderCellNeighbourhoodRange5WithWrapping() {
        Set<CellCoordinates> set = new MoorNeighbourhood(100,100,5,true).cellNeighbours(new Coords2D(99,50));
        boolean result = true;

        if(!set.contains(new Coords2D(4, 55))) result = false;
        if(!set.contains(new Coords2D(4, 45))) result = false;
        if(!set.contains(new Coords2D(94, 45))) result = false;
        if(!set.contains(new Coords2D(94, 55))) result = false;
        if(set.contains(new Coords2D(99, 50))) result = false;

        Assert.assertTrue("Testing for eight neighbours (wrapping) within range: 5", result);
    }
}
