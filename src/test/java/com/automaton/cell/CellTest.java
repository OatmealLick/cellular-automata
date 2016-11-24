package com.automaton.cell;

import com.automaton.states.BinaryState;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lick on 11/10/16.
 */
public class CellTest {
    @Test
    public void EqualityTestWithNumericallyIdenticalCoordsAndStates() {
        Coords2D mockCoords1 = new Coords2D(12321,-32323);
        Coords2D mockCoords2 = new Coords2D(12321,-32323);

        Assert.assertTrue("Comparing two Cells with identical Object states, but different memory addresses",
                new Cell(BinaryState.ALIVE,mockCoords1).equals(new Cell(BinaryState.ALIVE, mockCoords2)));
    }

    @Test
    public void EqualityTestWithDifferentCoords() {
        Coords2D mockCoords1 = new Coords2D(12321,-32323);
        Coords2D mockCoords2 = new Coords2D(4,-32323);

        Assert.assertFalse("Comparing two cells with different coords",
                new Cell(BinaryState.ALIVE,mockCoords1).equals(new Cell(BinaryState.ALIVE, mockCoords2)));
    }

    @Test
    public void EqualityTestWithDifferentStates() {
        Coords2D mockCoords1 = new Coords2D(12321,-32323);
        Coords2D mockCoords2 = new Coords2D(12321,-32323);

        Assert.assertFalse("Comparing two cells with different states",
                new Cell(BinaryState.ALIVE,mockCoords1).equals(new Cell(BinaryState.DEAD, mockCoords2)));
    }


}
