package com.automaton.application;

import com.automaton.cell.CellCoordinates;
import com.automaton.cell.Coords1D;
import com.automaton.cell.ElementaryNeighbourhood;
import com.automaton.cell.UniformStateFactory;
import com.automaton.states.BinaryState;
import com.automaton.states.CellState;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/25/16.
 */
public class ElementaryAutomatonTest {
    @Test
    public void nextStateAfterWidth5() {
        Automaton automaton = new ElementaryAutomaton(
                new UniformStateFactory(BinaryState.ALIVE),
                5,
                (byte)30
        );

        // killing cells which should be dead in 3rd state of 1Dim automaton with rule 30
        Map<CellCoordinates, CellState> map = new HashMap<>();
        map.put(new Coords1D(2), BinaryState.DEAD);
        map.put(new Coords1D(3), BinaryState.DEAD);

        automaton.insertStructure(map);

        Automaton generatedNextState = automaton.nextState();

        Assert.assertTrue("does width of generated state is ok?",
                ((ElementaryAutomaton)generatedNextState).getWidth()==5);

        Automaton correctAutomatonAfterNextState = new ElementaryAutomaton(
                new UniformStateFactory(BinaryState.ALIVE),
                5,
                (byte)30
        );

        Map<CellCoordinates, CellState> correctMap = new HashMap<>();
        correctMap.put(new Coords1D(1), BinaryState.DEAD);

        correctAutomatonAfterNextState.insertStructure(correctMap);

        Assert.assertEquals("check if generated and computed automaton are the same",
                generatedNextState, correctAutomatonAfterNextState);
    }

    @Test
    public void rule30Width101State5StartingFromOneCellAliveAtPosition50() {
        Automaton automaton = new ElementaryAutomaton(
                new UniformStateFactory(BinaryState.DEAD),
                101,
                (byte)30
        );

        Map<CellCoordinates, CellState> correctMap = new HashMap<>();
        correctMap.put(new Coords1D(50), BinaryState.ALIVE);

        automaton.insertStructure(correctMap);


        Automaton generatedNextState1 = automaton.nextState();

        Automaton correctAutomatonState1 = new ElementaryAutomaton(
                new UniformStateFactory(BinaryState.DEAD),
                101,
                (byte)30
        );

        correctMap = new HashMap<>();
        correctMap.put(new Coords1D(49), BinaryState.ALIVE);
        correctMap.put(new Coords1D(50), BinaryState.ALIVE);
        correctMap.put(new Coords1D(51), BinaryState.ALIVE);
        correctAutomatonState1.insertStructure(correctMap);


        Assert.assertEquals("checking state 1 of automaton with rule 30",
                correctAutomatonState1, generatedNextState1);


        Automaton correctAutomatonState2 = new ElementaryAutomaton(
                new UniformStateFactory(BinaryState.DEAD),
                101,
                (byte)30
        );

        correctMap = new HashMap<>();
        correctMap.put(new Coords1D(48), BinaryState.ALIVE);
        correctMap.put(new Coords1D(49), BinaryState.ALIVE);
        correctMap.put(new Coords1D(52), BinaryState.ALIVE);

        correctAutomatonState2.insertStructure(correctMap);

        Automaton generatedNextState2 = generatedNextState1.nextState();

        Assert.assertEquals("checking state 2 of automaton with rule 30",
                generatedNextState2, correctAutomatonState2);
    }
}
