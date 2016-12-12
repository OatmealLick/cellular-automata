package com.automaton.application;

import com.automaton.cell.CellCoordinates;
import com.automaton.cell.Coords2D;
import com.automaton.cell.UniformStateFactory;
import com.automaton.cell.VonNeumanNeighbourhood;
import com.automaton.states.AntState;
import com.automaton.states.BinaryState;
import com.automaton.states.CellState;
import com.automaton.states.LangtonCell;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/21/16.
 */
public class LangtonAntTest {
    @Test
    public void langtonNextStatesTestGodHaveMercyUponOurSouls() {
        int height = 3, width = 3;
        Automaton twoAntsBothInTheMiddle = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                false
        );

        LangtonCell cell = new LangtonCell(BinaryState.DEAD);
        cell.getAntStates().add(AntState.SOUTH);
        cell.getAntStates().add(AntState.NORTH);

        Map<CellCoordinates, CellState> twoAnts = new HashMap<>();
        twoAnts.put(new Coords2D(1,1), cell);
        twoAntsBothInTheMiddle.insertStructure(twoAnts);

        Automaton state1 = twoAntsBothInTheMiddle.nextState();

        Automaton computedState = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                false
        );

        LangtonCell one = new LangtonCell(BinaryState.DEAD);
        one.getAntStates().add(AntState.WEST);

        LangtonCell two = new LangtonCell(BinaryState.DEAD);
        two.getAntStates().add(AntState.EAST);

        LangtonCell three = new LangtonCell(BinaryState.ALIVE);

        Map<CellCoordinates, CellState> map = new HashMap<>();
        map.put(new Coords2D(0,1), one);
        map.put(new Coords2D(2,1), two);
        map.put(new Coords2D(1,1), three);

        computedState.insertStructure(map);

        Automaton state2 = state1.nextState();
        LangtonCell one2 = new LangtonCell(BinaryState.DEAD);
        one2.getAntStates().add(AntState.SOUTH);

        LangtonCell two2 = new LangtonCell(BinaryState.DEAD);
        two2.getAntStates().add(AntState.NORTH);

        LangtonCell three2 = new LangtonCell(BinaryState.ALIVE);
        LangtonCell four2 = new LangtonCell(BinaryState.ALIVE);
        LangtonCell five2 = new LangtonCell(BinaryState.ALIVE);

        Map<CellCoordinates, CellState> map2 = new HashMap<>();
        map2.put(new Coords2D(0,0), one2);
        map2.put(new Coords2D(2,2), two2);
        map2.put(new Coords2D(0,1), three2);
        map2.put(new Coords2D(1,1), four2);
        map2.put(new Coords2D(2,1), five2);

        Automaton computedState2 = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                false
        );

        computedState2.insertStructure(map2);


        Assert.assertTrue("correct state 1", state1.equals(computedState));
        Assert.assertTrue("correct state 2", state2.equals(computedState2));

    }

    @Test
    public void oneAndTeleportingIMeanWrappinglyAppearingOnTheOtherSide() {
        int height = 5, width = 5;
        Automaton oneLonelyAnt = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                true
        );

        LangtonCell cellWithLonelyAntInLowerRightCorner = new LangtonCell(BinaryState.DEAD);
        cellWithLonelyAntInLowerRightCorner.getAntStates().add(AntState.NORTH);

        Map<CellCoordinates, CellState> oneAnt = new HashMap<>();
        oneAnt.put(new Coords2D(4,0), cellWithLonelyAntInLowerRightCorner);
        oneLonelyAnt.insertStructure(oneAnt);

        Automaton state1 = oneLonelyAnt.nextState();

        Automaton computedState = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                true
        );

        LangtonCell one = new LangtonCell(BinaryState.DEAD);;
        one.getAntStates().add(AntState.EAST);

        LangtonCell three = new LangtonCell(BinaryState.ALIVE);

        Map<CellCoordinates, CellState> map = new HashMap<>();
        map.put(new Coords2D(0,0), one);
        map.put(new Coords2D(4,0), three);

        computedState.insertStructure(map);

        Assert.assertEquals("wrapping ants", computedState, state1);
    }

    @Test
    public void langtonWrappingOn5For5() {
        int height = 5, width = 5;
        Automaton antsInTheUpperLeftCornerTeleportingToDifferentCorners = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                true
        );

        LangtonCell cell = new LangtonCell(BinaryState.ALIVE);
        cell.getAntStates().add(AntState.EAST);
        cell.getAntStates().add(AntState.NORTH);

        Map<CellCoordinates, CellState> threeAnts = new HashMap<>();
        threeAnts.put(new Coords2D(0,4), cell);
        antsInTheUpperLeftCornerTeleportingToDifferentCorners.insertStructure(threeAnts);

        Automaton state1 = antsInTheUpperLeftCornerTeleportingToDifferentCorners.nextState();

        Automaton computedState = new LangtonAnt(
                new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),
                height,
                width,
                true
        );

        LangtonCell one = new LangtonCell(BinaryState.DEAD);
        one.getAntStates().add(AntState.SOUTH);

        LangtonCell two = new LangtonCell(BinaryState.DEAD);
        two.getAntStates().add(AntState.WEST);

        Map<CellCoordinates, CellState> map = new HashMap<>();
        map.put(new Coords2D(0,3), one);
        map.put(new Coords2D(4,4), two);

        computedState.insertStructure(map);

        Assert.assertEquals("wrapping ants", computedState, state1);
    }
}
