package com.automaton.cell;

/**
 * Created by lick on 11/10/16.
 */
public class Coords2D implements CellCoordinates {
    final private int x,y;

    public Coords2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Coords2D coords2D = (Coords2D) o;

        return getX() == coords2D.getX() && getY() == coords2D.getY();

    }

    @Override
    public int hashCode() {
        return 31 * getX() + getY();
    }
}
