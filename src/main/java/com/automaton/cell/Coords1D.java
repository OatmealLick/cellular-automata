package com.automaton.cell;

/**
 * Created by lick on 11/10/16.
 */
public class Coords1D implements CellCoordinates {
    final private int x;

    public Coords1D(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Coords1D coords2D = (Coords1D) o;

        return getX() == coords2D.getX();

    }

    @Override
    public int hashCode() {
        return 31 * getX();
    }
}
