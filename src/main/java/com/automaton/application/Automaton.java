package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.CellState;

import java.util.*;

/**
 * Created by lick on 11/12/16.
 */

/**
 * Core engine of application. Used to generate states of board with its {@link com.automaton.application
 * .Automaton#nextState() nextState()} . Every particular kind of automaton game such as {@link GameOfLife Game Of Life}
 * or {@link LangtonAnt Langton Ant} extends Automaton and uses its mechanics. Automaton is a template for every
 * particular Cellular Automaton simulation.
 *
 * <p>
 *     To add new cellular automata one needs to extend this class to ensure proper simulation
 * </p>
 *
 * @see Automaton2Dim
 * @see Automaton1Dim
 * @see GameOfLife
 */
public abstract class Automaton {
    private Map<CellCoordinates, CellState> cells = new HashMap<>();
    CellNeighbourhood neighboursStrategy;
    CellStateFactory stateFactory;

    Automaton(CellNeighbourhood neighboursStrategy,
                     CellStateFactory stateFactory) {
        this.neighboursStrategy = neighboursStrategy;
        this.stateFactory = stateFactory;
    }

    // setting Map field of Automaton
    void setCells(Map<CellCoordinates, CellState> cells) {
        this.cells = cells;
    }

    /**
     * Defines next state of the board using current state. Returns new Automaton Object with correct new state
     *
     * @return automaton
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

    /**
     * Utility method. Adds structure to Automaton's Map
     * @param structure Map with {@link CellCoordinates} as key and {@link CellState} as values
     */
    public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure) {
        cells.putAll(structure);
    }

    /**
     * Returns Iterator used to change states of Cells in Automaton's Map
     *
     * @return {@link CellIterator} Used to iterate through every cell on Automaton's Map
     */
    public CellIterator cellIterator() {
        return new CellIterator();
    }

    /**
     * Used to generate new Automaton with next Step of your simulation.
     *
     * @return new Automaton with correct state of the board computed based on state of the object which method was
     * called on
     */
    protected abstract Automaton newInstance();

    /**
     * Checks if given coordinates are the last on the board. Used by {@link CellIterator}
     *
     * @param coords Coordinates used to check if there are any coordinates on the board to visit after given pair.
     * @return true if there are coordinates after specified, false when there aren't any.
     */
    protected abstract boolean hasNextCoordinates(CellCoordinates coords);

    /**
     * Returns coordinates step BEFORE first coordinates to visit. First coordinates of the board comes right AFTER
     * these returned by initialCoordinates
     *
     * @return coordinates BEFORE first on the board
     */
    protected abstract CellCoordinates initialCoordinates();

    /**
     * Returns coordinates after given one
     * @param coords coordinates to specify the next pair right after them, on the board
     * @return next coordinates
     */
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coords);

    /**
     * Returns CellState computed using given rules of the simulation and parameters
     * @param cell cell which state in next step is required to be computed
     * @param neighboursStates states of the neighbours around the cell, specified by given {@link CellNeighbourhood}
     *
     * @return cell state in a next step of simulation
     *
     * @see CellNeighbourhood
     */
    protected abstract CellState nextCellState(Cell cell, Set<Cell> neighboursStates);

    private Set<Cell> mapCoordinates(Set<CellCoordinates> coords) {
        Set<Cell> resultSet = new LinkedHashSet<>();
        for(CellCoordinates cc : coords) {
            resultSet.add(new Cell(cells.get(cc), cc));
        }
        return resultSet;
    }

    /**
     *  Inner class used to iterate through Automaton's map in order to extract data of cells states or to change
     *  them. Implements {@link Iterator}
     *
     *  @see CellState
     *  @see Automaton
     *  @see Iterator
     */
    public class CellIterator implements Iterator<Cell>{
        private CellCoordinates currentCoordinates;

        CellIterator() {
            currentCoordinates = initialCoordinates();
        }

        /**
         * @return true when there are coordinates to visit in this run, false in other case
         */
        public boolean hasNext() {
            return hasNextCoordinates(currentCoordinates);
        }

        /**
         * @return next Cell to iterate on
         */
        public Cell next() {
            CellCoordinates holderCoords = nextCoordinates(currentCoordinates);
            currentCoordinates = holderCoords;
            return new Cell(cells.get(holderCoords), holderCoords);
        }

        /**
         * Sets the state of cell which iterator is currently on
         * @param state state to be saved in current cell
         */
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
