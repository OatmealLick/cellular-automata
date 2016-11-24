package com.automaton.cell;

import com.automaton.cell.Coords2D;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lick on 11/10/16.
 */
public class Coords2DTest {
    @Test
    public void EqualityCoords2DTest() {
        Coords2D one = new Coords2D(2,3);
        Coords2D two = new Coords2D(2,3);
        Assert.assertTrue(one.equals(two));
    }
}
