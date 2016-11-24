package com.automaton.application;

import com.automaton.cell.MoorNeighbourhood;
import com.automaton.states.BinaryState;
import com.automaton.cell.Coords2D;
import com.automaton.cell.UniformStateFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lick on 11/11/16.
 */
public class Automaton2DimTest {
    int height=99;
    int width=99;

    @Test
    public void correctlyReturningInfoAboutEndOfBoard() {
        Automaton2Dim automaton2Dim = new GameOfLife(new MoorNeighbourhood(
                width, height, 1, false
        ), new UniformStateFactory(BinaryState.ALIVE), height, width, false);
        Assert.assertFalse("Checking hasNextCoordinates when last possible are sent", automaton2Dim.hasNextCoordinates(
                new Coords2D(height-1,width-1)
        ));
    }

    @Test
    public void correctlyReturningInfoAboutFactThatThereAreCoordsToGoThrough() {
        Automaton2Dim automaton2Dim = new GameOfLife(null, new UniformStateFactory(BinaryState.ALIVE), height, width, false);
        Assert.assertTrue("Checking hasNextCoordinates when in somewhat middle", automaton2Dim.hasNextCoordinates(
                new Coords2D(43,54)
        ));
    }

    @Test
    public void nextCoordinatesTake1() {

        Automaton2Dim automaton2Dim = new GameOfLife(null, new UniformStateFactory(BinaryState.ALIVE), height, width, false);

        Assert.assertTrue("Next coords after (56,87) for (100x100) - 0-99 inclusive",
                automaton2Dim.nextCoordinates(new Coords2D(56,87))
                        .equals(new Coords2D(57,87)));
    }

    @Test
    public void nextCoordinatesTake2() {
        Automaton2Dim automaton2Dim = new GameOfLife(new MoorNeighbourhood(
                100, height, 1, false
        ), new UniformStateFactory(BinaryState.ALIVE), height, 100, false);

        Assert.assertTrue("Next coords after (99,67) for (100x100) - 0-99 inclusive",
                automaton2Dim.nextCoordinates(new Coords2D(99,67))
                        .equals(new Coords2D(0,68)));
    }

}
