package com.automaton.application;

import com.automaton.cell.Cell;
import com.automaton.cell.CellNeighbourhood;
import com.automaton.cell.CellStateFactory;
import com.automaton.cell.Coords2D;
import com.automaton.states.*;

import java.util.*;

/**
 * Created by lick on 11/11/16.
 */
public class LangtonAnt extends Automaton2Dim{
    private boolean wrapping;

    LangtonAnt(CellNeighbourhood neighboursStrategy,
               CellStateFactory stateFactory,
               int height, int width,
               boolean wrapping) {
        super(neighboursStrategy, stateFactory, height, width);
        this.wrapping = wrapping;
    }

    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighbourhood neighbourhood) {
        int height=super.getHeight(), width=super.getWidth();

        return new LangtonAnt(
                neighbourhood,
                factory,
                height,
                width,
                wrapping);
    }

    /**
     * nextCellState determines next cell state judging by states of neighbours and itself.
     *
     *
     * @param currentState
     * @param neighboursStates
     * @return
     */
    protected CellState nextCellState(CellState currentState, Set<Cell> neighboursStates) {
        LangtonCell currentLangtonCell = (LangtonCell)currentState;
        LangtonCell resultLangtonCell = new LangtonCell(currentLangtonCell.getCellState());

        //if there is at least one ant on current cell, flip it's BinaryState (color) immediately
        if(currentLangtonCell.getAntStates().size()>0) {
            if (currentLangtonCell.getCellState() == BinaryState.DEAD)
                resultLangtonCell.setCellState(BinaryState.ALIVE);
            else
                resultLangtonCell.setCellState(BinaryState.DEAD);
        }

        // computing given cell's coords judging by it's neighbours
        Set<Integer> xCoords = new HashSet<>();
        Set<Integer> yCoords = new HashSet<>();
        Coords2D coords;
        int currentCellXCoord = 0, currentCellYCoord = 0;
        for(Cell cell : neighboursStates) {
            coords = (Coords2D)cell.getCoords();
            if(xCoords.contains(coords.getX())) currentCellXCoord = coords.getX();
            else xCoords.add(coords.getX());
            if(yCoords.contains(coords.getY())) currentCellYCoord = coords.getY();
            else yCoords.add(coords.getY());
        }

        // let's compute maximum and minimum x & y to determine position relatively
        // to our given cell
        int maxX=-1, maxY=-1, minX=Integer.MAX_VALUE, minY=Integer.MAX_VALUE;

        if(!wrapping) {
            // setting maximums and minimums when wrapping is off
            for (Cell cell : neighboursStates) {
                Coords2D cellCoords = (Coords2D) cell.getCoords();
                int cellX = cellCoords.getX();
                int cellY = cellCoords.getY();

                if (cellX > maxX) maxX = cellX;
                if (cellX < minX) minX = cellX;
                if (cellY < minY) minY = cellY;
                if (cellY > maxY) maxY = cellY;
            }
        } else {
            // when wrapping is on
            minX = Math.floorMod(currentCellXCoord-1,getWidth());
            maxX = Math.floorMod(currentCellXCoord+1,getWidth());
            minY = Math.floorMod(currentCellYCoord-1,getHeight());
            maxY = Math.floorMod(currentCellYCoord+1,getHeight());
        }

        // for every neighbour cell for our given cell with currentState
        for(Cell cell : neighboursStates) {

            // we cast each Cell to LangtonCell type
            LangtonCell neighbourCell = (LangtonCell)cell.getState();

            // let's save our cell's coords
            Coords2D cellCoords = (Coords2D)cell.getCoords();

            // if there are any ants on this cell
            if(neighbourCell.getAntStates().size()>0) {

                // let's prepare helping variables
                boolean isMaxX = cellCoords.getX()==maxX;
                boolean isMinX = cellCoords.getX()==minX;
                boolean isMaxY = cellCoords.getY()==maxY;
                boolean isMinY = cellCoords.getY()==minY;

                // BinaryState.DEAD stands for WHITE
                // BinaryState.ALIVE stands for BLACK

                BinaryState neighbourBoardState = neighbourCell.getCellState();

                Set<AntState> antStatesSet = new HashSet<>(currentLangtonCell.getAntStates());
                // do the following for every ant standing on our cell
                for (Iterator<AntState> it = neighbourCell.getAntStates().iterator(); it.hasNext();) {
                    AntState state = it.next();

                    // awful necessary if-tree
                    switch (state) {
                        case NORTH:
                            if(neighbourBoardState==BinaryState.DEAD && isMinX)
                                antStatesSet.add(AntState.EAST);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMaxX)
                                antStatesSet.add(AntState.WEST);
                            break;
                        case EAST:
                            if(neighbourBoardState==BinaryState.DEAD && isMaxY)
                                antStatesSet.add(AntState.SOUTH);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMinY)
                                antStatesSet.add(AntState.NORTH);
                            break;
                        case SOUTH:
                            if(neighbourBoardState==BinaryState.DEAD && isMaxX)
                                antStatesSet.add(AntState.WEST);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMinX)
                                antStatesSet.add(AntState.EAST);
                            break;
                        case WEST:
                            if(neighbourBoardState==BinaryState.DEAD && isMinY)
                                antStatesSet.add(AntState.NORTH);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMaxY)
                                antStatesSet.add(AntState.SOUTH);
                            break;
                    }

                }
                resultLangtonCell.getAntStates().addAll(antStatesSet);
                //resultLangtonCell.setAntStates(new ArrayList<>(antStatesSet));
            }
        }

        return resultLangtonCell;
    }
}
