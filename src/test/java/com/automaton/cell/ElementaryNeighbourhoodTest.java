package com.automaton.cell;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by lick on 11/25/16.
 */
public class ElementaryNeighbourhoodTest {
    @Test
    public void neighboursNumberWithNeighbourhoodWidth3() {
        CellNeighbourhood neighbourhood = new ElementaryNeighbourhood(3);
        Set<CellCoordinates> generatedNeighboursOfCell2 = neighbourhood.cellNeighbours(new Coords1D(2));
        Set<CellCoordinates> generatedNeighboursOfCell0 = neighbourhood.cellNeighbours(new Coords1D(0));
        Set<CellCoordinates> generatedNeighboursOfCell1 = neighbourhood.cellNeighbours(new Coords1D(1));

        Set<CellCoordinates> correctSetOfNeighboursForCell2 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(1),
                new Coords1D(2)
        ));

        Set<CellCoordinates> correctSetOfNeighboursForCell0 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(0),
                new Coords1D(1)
        ));

        Set<CellCoordinates> correctSetOfNeighboursForCell1 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(0),
                new Coords1D(1),
                new Coords1D(2)
        ));

        Assert.assertEquals("neighbours width: 3, checking number and order of neighbours generated for cell 0",
                generatedNeighboursOfCell0, correctSetOfNeighboursForCell0);

        Assert.assertEquals("neighbours width: 3, checking number and order of neighbours generated for cell 1",
                generatedNeighboursOfCell1, correctSetOfNeighboursForCell1);

        Assert.assertEquals("neighbours width: 3, checking number and order of neighbours generated for cell 2",
                generatedNeighboursOfCell2, correctSetOfNeighboursForCell2);
    }

    @Test
    public void neighboursNumberWithNeighbourhoodWidth5() {
        CellNeighbourhood neighbourhood = new ElementaryNeighbourhood(5);
        Set<CellCoordinates> generatedNeighboursOfCell3 = neighbourhood.cellNeighbours(new Coords1D(3));
        Set<CellCoordinates> generatedNeighboursOfCell0 = neighbourhood.cellNeighbours(new Coords1D(0));
        Set<CellCoordinates> generatedNeighboursOfCell4 = neighbourhood.cellNeighbours(new Coords1D(4));
        Set<CellCoordinates> generatedNeighboursOfCell1 = neighbourhood.cellNeighbours(new Coords1D(1));

        Set<CellCoordinates> correctSetOfNeighboursForCell3 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(2),
                new Coords1D(3),
                new Coords1D(4)
        ));

        Set<CellCoordinates> correctSetOfNeighboursForCell0 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(0),
                new Coords1D(1)
        ));

        Set<CellCoordinates> correctSetOfNeighboursForCell4 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(3),
                new Coords1D(4)
        ));

        Set<CellCoordinates> correctSetOfNeighboursForCell1 = new LinkedHashSet<>(Arrays.asList(
                new Coords1D(0),
                new Coords1D(1),
                new Coords1D(2)
        ));

        Assert.assertEquals("neighbours width: 5, checking number and order of neighbours generated for cell 3",
                generatedNeighboursOfCell3, correctSetOfNeighboursForCell3);

        Assert.assertEquals("neighbours width: 5, checking number and order of neighbours generated for cell 4",
                generatedNeighboursOfCell4, correctSetOfNeighboursForCell4);

        Assert.assertEquals("neighbours width: 5, checking number and order of neighbours generated for cell 0",
                generatedNeighboursOfCell0, correctSetOfNeighboursForCell0);

        Assert.assertEquals("neighbours width: 5, checking number and order of neighbours generated for cell 1",
                generatedNeighboursOfCell1, correctSetOfNeighboursForCell1);

    }
}
