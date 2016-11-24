package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.CellState;
import com.automaton.states.WireElectronState;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 11/21/16.
 */
public class WireWorldTest {

    @Test
    public void twoNextStatesFromCustomOpening() {
        int height=5, width=5;
        Automaton game = new WireWorld(
                new MoorNeighbourhood(width,height,1,false),
                new UniformStateFactory(WireElectronState.VOID),
                height,
                width);

        Map<CellCoordinates, CellState> state0 = new HashMap<>();
        state0.put(new Coords2D(0,2), WireElectronState.WIRE);
        state0.put(new Coords2D(1,2), WireElectronState.ELECTRON_TAIL);
        state0.put(new Coords2D(2,2), WireElectronState.ELECTRON_HEAD);
        state0.put(new Coords2D(3,2), WireElectronState.WIRE);
        state0.put(new Coords2D(4,2), WireElectronState.WIRE);
        state0.put(new Coords2D(3,3), WireElectronState.WIRE);
        state0.put(new Coords2D(3,1), WireElectronState.WIRE);

        Map<CellCoordinates, CellState> state1 = new HashMap<>();
        state1.put(new Coords2D(0,2), WireElectronState.WIRE);
        state1.put(new Coords2D(1,2), WireElectronState.WIRE);
        state1.put(new Coords2D(2,2), WireElectronState.ELECTRON_TAIL);
        state1.put(new Coords2D(3,2), WireElectronState.ELECTRON_HEAD);
        state1.put(new Coords2D(3,1), WireElectronState.ELECTRON_HEAD);
        state1.put(new Coords2D(3,3), WireElectronState.ELECTRON_HEAD);
        state1.put(new Coords2D(4,2), WireElectronState.WIRE);

        Map<CellCoordinates, CellState> state2 = new HashMap<>();
        state2.put(new Coords2D(0,2), WireElectronState.WIRE);
        state2.put(new Coords2D(1,2), WireElectronState.WIRE);
        state2.put(new Coords2D(2,2), WireElectronState.WIRE);
        state2.put(new Coords2D(3,2), WireElectronState.ELECTRON_TAIL);
        state2.put(new Coords2D(3,3), WireElectronState.ELECTRON_TAIL);
        state2.put(new Coords2D(3,1), WireElectronState.ELECTRON_TAIL);
        state2.put(new Coords2D(4,2), WireElectronState.WIRE);

        game.insertStructure(state0);

        Automaton state1Automaton = new WireWorld(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(WireElectronState.VOID),
                height,
                width
        );

        state1Automaton.insertStructure(state1);

        Automaton state2Automaton = new WireWorld(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(WireElectronState.VOID),
                height,
                width
        );

        state2Automaton.insertStructure(state2);

        /*Automaton gameComputedForState1 = game.nextState();
        Automaton gameComputedForState2 = gameComputedForState1.nextState()*/;




        Assert.assertTrue("First nextState() should return return state 1", game.nextState().equals(state1Automaton));
        Assert.assertTrue("Second nextState() should return return state 2", game.nextState().nextState().equals(state2Automaton));
    }
}
