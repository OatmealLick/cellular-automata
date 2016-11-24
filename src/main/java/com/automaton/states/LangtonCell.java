package com.automaton.states;

import java.util.*;

/**
 * Created by User on 19.11.2016.
 */
public class LangtonCell implements CellState {

    private BinaryState cellState;
    private List<AntState> antStates = new ArrayList<>();

    public LangtonCell(BinaryState cellState) {
        this.cellState = cellState;
        antStates = new ArrayList<>();
    }

    public List<AntState> getAntStates() {
        return antStates;
    }

    /*public void setAntStates(List<AntState> antStates) {
        this.antStates = antStates;
    }*/

    public BinaryState getCellState() {
        return cellState;
    }

    public void setCellState(BinaryState cellState) {
        this.cellState = cellState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangtonCell that = (LangtonCell) o;

        if (cellState != that.cellState) return false;
        return antStates.equals(that.antStates);

    }

    @Override
    public int hashCode() {
        int result = cellState.hashCode();
        result = 31 * result + antStates.hashCode();
        return result;
    }
}
