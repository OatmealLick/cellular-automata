package com.automaton.application;

import com.automaton.cell.Cell;
import com.automaton.cell.CellNeighbourhood;
import com.automaton.cell.CellStateFactory;
import com.automaton.cell.MoorNeighbourhood;
import com.automaton.states.BinaryState;
import com.automaton.states.CellState;
import com.automaton.states.WireElectronState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lick on 11/11/16.
 */

/**
 * WireWorld simulator.
 */
public class WireWorld extends Automaton2Dim{

    public WireWorld(CellStateFactory stateFactory,
              int height, int width) {
        super(new MoorNeighbourhood(width, height, 1, false), stateFactory, height, width);
    }

    @Override
    protected Automaton newInstance() {
        int height=super.getHeight(), width=super.getWidth();

        return new WireWorld(
                super.stateFactory,
                height,
                width);
    }

    protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
        CellState currentState = currentCell.getState();
        if(currentState == WireElectronState.ELECTRON_HEAD)
            return WireElectronState.ELECTRON_TAIL;
        else if(currentState == WireElectronState.ELECTRON_TAIL)
            return WireElectronState.WIRE;
        else if(currentState == WireElectronState.WIRE) {
            Set<Integer> becomeElectronCases = new HashSet<>(Arrays.asList(1,2));
            int electronHeadsNearby = 0;
            for(Cell cell : neighboursStates)
                if(cell.getState()==WireElectronState.ELECTRON_HEAD)
                    electronHeadsNearby++;
            if(becomeElectronCases.contains(electronHeadsNearby))
                return WireElectronState.ELECTRON_HEAD;
        }

        return currentState;
    }
}
