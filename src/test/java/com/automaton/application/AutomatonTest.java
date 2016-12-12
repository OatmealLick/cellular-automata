package com.automaton.application;

import com.automaton.states.BinaryState;
import com.automaton.states.CellState;
import com.automaton.cell.Coords2D;
import com.automaton.cell.MoorNeighbourhood;
import com.automaton.cell.UniformStateFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/18/16.
 */
public class AutomatonTest {
    int height=15, width=15;

    @Test
    public void equalityTest() {
        Automaton[] automatons = new Automaton[3];

        Map<Coords2D, CellState> map = new HashMap<>();
        for(int i=0; i<3; i++)
            map.put(new Coords2D(10+i,10), BinaryState.ALIVE);

        for(int i=0; i<automatons.length; i++) {
            automatons[i] = new GameOfLife(
                    new MoorNeighbourhood(width, height, 1, false),
                    new UniformStateFactory(BinaryState.DEAD),
                    height,
                    width,
                    false,
                    "23/3"

            );
            automatons[i].insertStructure(map);
        }

        Map<Coords2D, CellState> map2 = new HashMap<>();
        map2.put(new Coords2D(1,2), BinaryState.ALIVE);

        automatons[2].insertStructure(map2);

        Assert.assertTrue("Two identical automatons", automatons[0].equals(automatons[1]));
        Assert.assertFalse("Different automatons", automatons[0].equals(automatons[2]));
    }
}
