package com.automaton.gui;

import com.automaton.cell.CellCoordinates;
import com.automaton.cell.Coords2D;
import com.automaton.states.BinaryState;
import com.automaton.states.CellState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lick on 12/13/16.
 */

/**
 * Class defines static method returning Maps with hardcoded structures to be seen in Game of Life
 */
public class Structures {
    static Map<CellCoordinates, CellState> getGlider() {
        Map<CellCoordinates, CellState> glider = new HashMap<>();

        glider.put(new Coords2D(2, 1), BinaryState.ALIVE);
        glider.put(new Coords2D(3, 2), BinaryState.ALIVE);
        glider.put(new Coords2D(3, 3), BinaryState.ALIVE);
        glider.put(new Coords2D(2, 3), BinaryState.ALIVE);
        glider.put(new Coords2D(1, 3), BinaryState.ALIVE);

        return glider;
    }


    static Map<CellCoordinates, CellState>  getGliderGun() {

        Map<CellCoordinates, CellState> gliderGun = new HashMap<>();

        gliderGun.put(new Coords2D(1,5), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(2,5), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(1,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(2,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(11,5), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(11,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(11,7), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(12,4), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(12,8), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(13,3), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(13,9), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(14,3), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(14,9), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(15,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(16,4), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(16,8), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(17,5), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(17,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(17,7), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(18,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(21,3), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(21,4), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(21,5), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(22,3), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(22,4), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(22,5), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(23,2), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(23,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(25,1), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(25,2), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(25,6), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(25,7), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(35,3), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(35,4), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(36,3), BinaryState.ALIVE);
        gliderGun.put(new Coords2D(36,4), BinaryState.ALIVE);

        return gliderGun;
    }



    static Map<CellCoordinates, CellState>  getPulsar() {

        Map<CellCoordinates, CellState> pulsar = new HashMap<>();
        pulsar.put(new Coords2D(12,14), BinaryState.ALIVE);
        pulsar.put(new Coords2D(12,15), BinaryState.ALIVE);
        pulsar.put(new Coords2D(12,16), BinaryState.ALIVE);
        pulsar.put(new Coords2D(12,20), BinaryState.ALIVE);
        pulsar.put(new Coords2D(12,21), BinaryState.ALIVE);
        pulsar.put(new Coords2D(12,22), BinaryState.ALIVE);
        pulsar.put(new Coords2D(14,12), BinaryState.ALIVE);
        pulsar.put(new Coords2D(14,17), BinaryState.ALIVE);
        pulsar.put(new Coords2D(14,19), BinaryState.ALIVE);
        pulsar.put(new Coords2D(14,24), BinaryState.ALIVE);
        pulsar.put(new Coords2D(15,12), BinaryState.ALIVE);
        pulsar.put(new Coords2D(15,17), BinaryState.ALIVE);
        pulsar.put(new Coords2D(15,19), BinaryState.ALIVE);
        pulsar.put(new Coords2D(15,24), BinaryState.ALIVE);
        pulsar.put(new Coords2D(16,12), BinaryState.ALIVE);
        pulsar.put(new Coords2D(16,17), BinaryState.ALIVE);
        pulsar.put(new Coords2D(16,19), BinaryState.ALIVE);
        pulsar.put(new Coords2D(16,24), BinaryState.ALIVE);
        pulsar.put(new Coords2D(17,14), BinaryState.ALIVE);
        pulsar.put(new Coords2D(17,15), BinaryState.ALIVE);
        pulsar.put(new Coords2D(17,16), BinaryState.ALIVE);
        pulsar.put(new Coords2D(17,20), BinaryState.ALIVE);
        pulsar.put(new Coords2D(17,21), BinaryState.ALIVE);
        pulsar.put(new Coords2D(17,22), BinaryState.ALIVE);
        pulsar.put(new Coords2D(19,14), BinaryState.ALIVE);
        pulsar.put(new Coords2D(19,15), BinaryState.ALIVE);
        pulsar.put(new Coords2D(19,16), BinaryState.ALIVE);
        pulsar.put(new Coords2D(19,20), BinaryState.ALIVE);
        pulsar.put(new Coords2D(19,21), BinaryState.ALIVE);
        pulsar.put(new Coords2D(19,22), BinaryState.ALIVE);
        pulsar.put(new Coords2D(20,12), BinaryState.ALIVE);
        pulsar.put(new Coords2D(20,17), BinaryState.ALIVE);
        pulsar.put(new Coords2D(20,19), BinaryState.ALIVE);
        pulsar.put(new Coords2D(20,24), BinaryState.ALIVE);
        pulsar.put(new Coords2D(21,12), BinaryState.ALIVE);
        pulsar.put(new Coords2D(21,17), BinaryState.ALIVE);
        pulsar.put(new Coords2D(21,19), BinaryState.ALIVE);
        pulsar.put(new Coords2D(21,24), BinaryState.ALIVE);
        pulsar.put(new Coords2D(22,12), BinaryState.ALIVE);
        pulsar.put(new Coords2D(22,17), BinaryState.ALIVE);
        pulsar.put(new Coords2D(22,19), BinaryState.ALIVE);
        pulsar.put(new Coords2D(22,24), BinaryState.ALIVE);
        pulsar.put(new Coords2D(24,14), BinaryState.ALIVE);
        pulsar.put(new Coords2D(24,15), BinaryState.ALIVE);
        pulsar.put(new Coords2D(24,16), BinaryState.ALIVE);
        pulsar.put(new Coords2D(24,20), BinaryState.ALIVE);
        pulsar.put(new Coords2D(24,21), BinaryState.ALIVE);
        pulsar.put(new Coords2D(24,22), BinaryState.ALIVE);

        return pulsar;
    }

    static Map<CellCoordinates, CellState>  getSpaceship() {

        Map<CellCoordinates, CellState> spaceship = new HashMap<>();

        spaceship.put(new Coords2D(2,2), BinaryState.ALIVE);
        spaceship.put(new Coords2D(2,4), BinaryState.ALIVE);
        spaceship.put(new Coords2D(3,5), BinaryState.ALIVE);
        spaceship.put(new Coords2D(4,5), BinaryState.ALIVE);
        spaceship.put(new Coords2D(5,5), BinaryState.ALIVE);
        spaceship.put(new Coords2D(6,5), BinaryState.ALIVE);
        spaceship.put(new Coords2D(6,4), BinaryState.ALIVE);
        spaceship.put(new Coords2D(6,3), BinaryState.ALIVE);
        spaceship.put(new Coords2D(5,2), BinaryState.ALIVE);

        return spaceship;
    }
}
