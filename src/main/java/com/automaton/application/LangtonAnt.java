package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.*;
import com.automaton.states.CellState;

import java.util.*;

/**
 * Created by lick on 11/11/16.
 */

/**
 * Langton Ant simulator.
 */
public class LangtonAnt extends Automaton2Dim{
    private boolean wrapping;

    public LangtonAnt(CellStateFactory stateFactory,
               int height, int width,
               boolean wrapping) {
        super(new VonNeumanNeighbourhood(width, height, 1, wrapping), stateFactory, height, width);
        this.wrapping = wrapping;
    }

    @Override
    protected Automaton newInstance() {
        int height=super.getHeight(), width=super.getWidth();

        return new LangtonAnt(
                super.stateFactory,
                height,
                width,
                wrapping);
    }

    @Override
    protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
        LangtonCell currentLangtonCell = (LangtonCell)currentCell.getState();
        LangtonCell resultLangtonCell = new LangtonCell(currentLangtonCell.getCellState());

        //if there is at least one ant on current cell, flip it's BinaryState (color) immediately
        if(currentLangtonCell.getAntStates().size()>0) {
            if (currentLangtonCell.getCellState() == BinaryState.DEAD)
                resultLangtonCell.setCellState(BinaryState.ALIVE);
            else
                resultLangtonCell.setCellState(BinaryState.DEAD);
        }

        Coords2D currentCoords = (Coords2D) currentCell.getCoords();
        int currentCellXCoord = currentCoords.getX(), currentCellYCoord = currentCoords.getY();
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
                            if(neighbourBoardState==BinaryState.DEAD && isMinY)
                                antStatesSet.add(AntState.NORTH);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMaxY)
                                antStatesSet.add(AntState.SOUTH);
                            break;
                        case SOUTH:
                            if(neighbourBoardState==BinaryState.DEAD && isMaxX)
                                antStatesSet.add(AntState.WEST);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMinX)
                                antStatesSet.add(AntState.EAST);
                            break;
                        case WEST:
                            if(neighbourBoardState==BinaryState.DEAD && isMaxY)
                                antStatesSet.add(AntState.SOUTH);
                            else if(neighbourBoardState==BinaryState.ALIVE && isMinY)
                                antStatesSet.add(AntState.NORTH);
                            break;
                    }

                }
                resultLangtonCell.getAntStates().addAll(antStatesSet);
            }
        }

        return resultLangtonCell;
    }
}
