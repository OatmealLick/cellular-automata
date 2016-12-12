package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.BinaryState;
import com.automaton.states.CellState;

import java.util.*;

import static com.automaton.states.BinaryState.DEAD;

/**
 * Created by lick on 11/25/16.
 */
public class ElementaryAutomaton extends Automaton1Dim {
    private byte rule;
    private BinaryState[] ruleOutcomes = new BinaryState[8];

    public ElementaryAutomaton(CellStateFactory stateFactory,
                        int width,
                        byte rule) {
        super(new ElementaryNeighbourhood(width), stateFactory, width);
        this.rule=rule;

        for(int i=0; i<ruleOutcomes.length; i++)
            if((rule & (1 << i)) != 0)
                ruleOutcomes[ruleOutcomes.length-i-1] = BinaryState.ALIVE;
            else
                ruleOutcomes[ruleOutcomes.length-i-1] = BinaryState.DEAD;

    }

    @Override
    protected Automaton newInstance() {
        return new ElementaryAutomaton(
                super.stateFactory,
                super.getWidth(),
                rule
        );
    }

    @Override
    protected CellState nextCellState(Cell cell, Set<Cell> neighboursStates) {
        int currentCellX = ((Coords1D)cell.getCoords()).getX();

        if(neighboursStates.size()==2) {
            // left case
            if(currentCellX==0) {
                Set<Cell> helperSet = new LinkedHashSet<>();
                helperSet.add(new Cell(DEAD, new Coords1D(-1)));
                for (Cell neighboursState : neighboursStates) {
                    helperSet.add(neighboursState);
                }
                neighboursStates = helperSet;
            // right case
            } else if(currentCellX==super.getWidth()-1)
                neighboursStates.add(new Cell(DEAD, new Coords1D(currentCellX+1)));

        }

        // now every cell has three neighbours up, and all we need to do is to check their order & states
        // to determine result CellState
        // the moment we know exact order and states of neighbours we can return value using helper array

        List<Boolean> neighboursAliveInOrder = new ArrayList<>(3);
        for(Cell theCell : neighboursStates) {
            neighboursAliveInOrder.add(theCell.getState()==BinaryState.ALIVE);
        }

        // the order from http://mathworld.wolfram.com/ElementaryCellularAutomaton.html
        // starting from oldest bit

        //case 0 - every neighbour is alive
        if(!neighboursAliveInOrder.contains(false))
            return ruleOutcomes[0];
        //case 1 - first two are alive, third one dead
        else if(neighboursAliveInOrder.get(0)
                && neighboursAliveInOrder.get(1))
            return ruleOutcomes[1];
        //case 2 - middle one dead, side cells alive
        else if(neighboursAliveInOrder.get(0)
                && neighboursAliveInOrder.get(2))
            return ruleOutcomes[2];
        //case 3 - left most alive, the other two dead
        else if(neighboursAliveInOrder.get(0))
            return ruleOutcomes[3];
        //case 4 - left most dead, the other two alive
        else if(neighboursAliveInOrder.get(1)
                && neighboursAliveInOrder.get(2))
            return ruleOutcomes[4];
        //case 5 - only middle one alive
        else if(neighboursAliveInOrder.get(1))
            return ruleOutcomes[5];
        //case 6 - only right most one alive
        else if(neighboursAliveInOrder.get(2))
            return ruleOutcomes[6];
        //case 7 - everything cold and dead
        else if(!neighboursAliveInOrder.contains(true))
            return ruleOutcomes[7];

        return null;
    }
}
