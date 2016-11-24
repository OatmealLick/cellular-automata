package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.CellState;

import java.util.*;

/**
 * Created by lick on 11/12/16.
 */
public abstract class Automaton {
    private Map<CellCoordinates, CellState> cells = new HashMap<>();
    private CellNeighbourhood neighboursStrategy;
    private CellStateFactory stateFactory;

    Automaton(CellNeighbourhood neighboursStrategy,
                     CellStateFactory stateFactory) {
        this.neighboursStrategy = neighboursStrategy;
        this.stateFactory = stateFactory;
    }

    //TODO this solution seems not to be so flawless, user can call it and fuck up everything
    protected void setCells(Map<CellCoordinates, CellState> cells) {
        this.cells = cells;
    }

    public Automaton nextState() {
        Automaton automaton = newInstance(stateFactory,neighboursStrategy);
        CellIterator currentStateIterator = cellIterator();
        CellIterator nextStateIterator = automaton.cellIterator();

        while(currentStateIterator.hasNext()) {
            Cell cell = currentStateIterator.next();
            nextStateIterator.next();

            Set<CellCoordinates> neighboursCoords = neighboursStrategy.cellNeighbours(cell.getCoords());
            Set<Cell> neighbours = mapCoordinates(neighboursCoords);

            CellState newCellState = nextCellState(cell.getState(), neighbours);
            nextStateIterator.setState(newCellState);
        }
        return automaton;
    }

    public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure) {
        cells.putAll(structure);
    }

    public CellIterator cellIterator() {
        return new CellIterator();
    }

    protected abstract Automaton newInstance(CellStateFactory factory, CellNeighbourhood neighbourhood);

    protected abstract boolean hasNextCoordinates(CellCoordinates coords);

    protected abstract CellCoordinates initialCoordinates();

    protected abstract CellCoordinates nextCoordinates(CellCoordinates coords);

    protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighboursStates);

    private Set<Cell> mapCoordinates(Set<CellCoordinates> coords) {
        Set<Cell> resultSet = new HashSet<>();
        for(CellCoordinates cc : coords) {
            resultSet.add(new Cell(cells.get(cc), cc));
        }
        return resultSet;
    }

    private class CellIterator implements Iterator<Cell>{
        private CellCoordinates currentCoordinates;

        CellIterator() {
            currentCoordinates = initialCoordinates();
        }

        public boolean hasNext() {
            return hasNextCoordinates(currentCoordinates);
        }

        public Cell next() {
            CellCoordinates holderCoords = nextCoordinates(currentCoordinates);
            currentCoordinates = holderCoords;
            return new Cell(cells.get(holderCoords), holderCoords);
        }

        public void setState(CellState state) {
            cells.put(currentCoordinates, state);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o==null || o.getClass() != getClass())
            return false;

        Automaton automaton = (Automaton)o;

        if(stateFactory.getClass() != automaton.stateFactory.getClass()
                || neighboursStrategy.getClass() != automaton.neighboursStrategy.getClass())
            return false;
        CellIterator iterator = new CellIterator();
        for(int i=0; i<cells.size(); i++) {
            Cell cell = iterator.next();
            if(!cells.get(cell.getCoords()).equals(automaton.cells.get(cell.getCoords())))
                return false;
        }

        return true;
    }
}
