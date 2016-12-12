package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.CellState;

import java.util.*;

/**
 * Created by lick on 11/12/16.
 */

/**
 * Core of application. Used to generate states of board with its nextState(). Every particular kind of autmaton game
 * such as Game of Life or Langton Ant extends Automaton and uses its mechanics. Automaton is a template for every
 * particular automaton.
 */
public abstract class Automaton {
    private Map<CellCoordinates, CellState> cells = new HashMap<>();
    protected CellNeighbourhood neighboursStrategy;
    protected CellStateFactory stateFactory;

    Automaton(CellNeighbourhood neighboursStrategy,
                     CellStateFactory stateFactory) {
        this.neighboursStrategy = neighboursStrategy;
        this.stateFactory = stateFactory;
    }

    /** setting map of coordinates as soon as we know the properties (e.g. width). Called by Automaton2Dim.
     *
     * @param cells
     */
    void setCells(Map<CellCoordinates, CellState> cells) {
        this.cells = cells;
    }

    /**
     * defines next state of the board using current state. Returns Automaton with correct new state
     * @return
     */
    public Automaton nextState() {
        Automaton automaton = newInstance();
        CellIterator currentStateIterator = cellIterator();
        CellIterator nextStateIterator = automaton.cellIterator();

        while(currentStateIterator.hasNext()) {
            Cell cell = currentStateIterator.next();
            nextStateIterator.next();

            Set<CellCoordinates> neighboursCoords = neighboursStrategy.cellNeighbours(cell.getCoords());
            Set<Cell> neighbours = mapCoordinates(neighboursCoords);

            CellState newCellState = automaton.nextCellState(cell, neighbours);
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

    protected abstract Automaton newInstance();

    protected abstract boolean hasNextCoordinates(CellCoordinates coords);

    protected abstract CellCoordinates initialCoordinates();

    protected abstract CellCoordinates nextCoordinates(CellCoordinates coords);

    protected abstract CellState nextCellState(Cell currentState, Set<Cell> neighboursStates);

    private Set<Cell> mapCoordinates(Set<CellCoordinates> coords) {
        Set<Cell> resultSet = new LinkedHashSet<>();
        for(CellCoordinates cc : coords) {
            resultSet.add(new Cell(cells.get(cc), cc));
        }
        return resultSet;
    }

    public class CellIterator implements Iterator<Cell>{
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
        while(iterator.hasNext()) {
            Cell cell = iterator.next();
            if(!cells.get(cell.getCoords()).equals(automaton.cells.get(cell.getCoords())))
                return false;
        }

        return true;
    }
}
