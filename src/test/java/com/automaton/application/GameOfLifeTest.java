package com.automaton.application;

import com.automaton.cell.*;
import com.automaton.states.BinaryState;
import com.automaton.cell.Cell;
import com.automaton.states.CellState;
import com.automaton.cell.Coords2D;
import com.automaton.cell.UniformStateFactory;
import com.automaton.states.QuadState;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;
import static org.mockito.Mockito.*;


/**
 * Created by lick on 11/11/16.
 */
public class GameOfLifeTest {
    int height=3;
    int width=3;

    @Test
    public void quadLifeNextStateTest1DifferentColors() {
        int height=3, width=3;
        Map<Coords2D, CellState> state0 = new HashMap<>(), state1 = new HashMap<>();;
        //state0 map
        state0.put(new Coords2D(1,0), QuadState.RED);
        state0.put(new Coords2D(1,1), QuadState.RED);
        state0.put(new Coords2D(2,1), QuadState.GREEN);
        state0.put(new Coords2D(0,2), QuadState.YELLOW);
        state0.put(new Coords2D(1,2), QuadState.BLUE);

        //state1 map
        state1.put(new Coords2D(1,0), QuadState.RED);
        state1.put(new Coords2D(2,0), QuadState.RED);
        state1.put(new Coords2D(2,1), QuadState.GREEN);
        state1.put(new Coords2D(0,2), QuadState.YELLOW);
        state1.put(new Coords2D(1,2), QuadState.BLUE);
        state1.put(new Coords2D(2,2), QuadState.YELLOW);

        Automaton automaton = new GameOfLife(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(QuadState.DEAD),
                height,
                width,
                true,
                "23/3"
        );

        automaton.insertStructure(state0);

        Automaton automatonAfterOneState = new GameOfLife(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(QuadState.DEAD),
                height,
                width,
                true,
                "23/3"
        );




        automatonAfterOneState.insertStructure(state1);

        Automaton result = automaton.nextState();

        Assert.assertTrue("States proper", result.equals(automatonAfterOneState));
    }

    @Test
    public void quadLifeNextCellState3DifferentColors() {
        Automaton gol = new GameOfLife(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(QuadState.DEAD),
                height,
                width,
                true,
                "23/3"
        );

        Set<Cell> neighbours = new HashSet<>(Arrays.asList(
                new Cell(QuadState.DEAD, new Coords2D(0,0)),
                new Cell(QuadState.DEAD, new Coords2D(1,0)),
                new Cell(QuadState.DEAD, new Coords2D(2,0)),
                new Cell(QuadState.DEAD, new Coords2D(0,1)),
                new Cell(QuadState.DEAD, new Coords2D(2,1)),
                new Cell(QuadState.RED, new Coords2D(0,2)),
                new Cell(QuadState.GREEN, new Coords2D(1,3)),
                new Cell(QuadState.BLUE, new Coords2D(2,4))
        ));

        Assert.assertTrue("State of cell should be revived Yellow", gol.nextCellState(
                new Cell(QuadState.DEAD, new Coords2D(2,2)), //these coords doesn't matter, it only checks state
                neighbours
        ).equals(QuadState.YELLOW));
    }

    @Test
    public void quadLifeNextCellState2Red1Blue() {
        Automaton gol = new GameOfLife(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(QuadState.DEAD),
                height,
                width,
                true,
                "23/3"
        );

        Set<Cell> neighbours = new HashSet<>(Arrays.asList(
                new Cell(QuadState.DEAD, new Coords2D(0,0)),
                new Cell(QuadState.DEAD, new Coords2D(1,0)),
                new Cell(QuadState.DEAD, new Coords2D(2,0)),
                new Cell(QuadState.DEAD, new Coords2D(0,1)),
                new Cell(QuadState.DEAD, new Coords2D(2,1)),
                new Cell(QuadState.RED, new Coords2D(0,2)),
                new Cell(QuadState.RED, new Coords2D(1,3)),
                new Cell(QuadState.BLUE, new Coords2D(2,4))
        ));

        Assert.assertTrue("State of cell should be revived Red", gol.nextCellState(
                new Cell(QuadState.DEAD, new Coords2D(2,2)), //these coords doesn't matter, it only checks state,
                neighbours
        ).equals(QuadState.RED));
    }

    @Test
    public void nextStateTest1() {

        Map<Coords2D, CellState> map = new HashMap<>(), map2 = new HashMap<>();;
        for(int i=0; i<3; i++) {
            map.put(new Coords2D(i, 1), BinaryState.ALIVE);
            map2.put(new Coords2D(1, i), BinaryState.ALIVE);
        }

        Automaton automaton = new GameOfLife(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(BinaryState.DEAD),
                height,
                width,
                false,
                "23/3"
        );

        automaton.insertStructure(map);

        Automaton automatonAfterOneState = new GameOfLife(
                new MoorNeighbourhood(width, height, 1, false),
                new UniformStateFactory(BinaryState.DEAD),
                height,
                width,
                false,
                "23/3"
        );

        automatonAfterOneState.insertStructure(map2);

        Automaton result = automaton.nextState();

        Assert.assertTrue("States proper", result.equals(automatonAfterOneState));


    }

    @Test
    public void cellStayingAlive() {
        Automaton gol = new GameOfLife(null, new UniformStateFactory(BinaryState.ALIVE), height, width, false, "23/3");
        Set<Cell> neighbours = new HashSet<>(Arrays.asList(
                new Cell(BinaryState.ALIVE, new Coords2D(3,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(4,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(6,5)),
                new Cell(BinaryState.DEAD, new Coords2D(8,5)),
                new Cell(BinaryState.DEAD, new Coords2D(9,5)),
                new Cell(BinaryState.DEAD, new Coords2D(44,5)),
                new Cell(BinaryState.DEAD, new Coords2D(35,5)),
                new Cell(BinaryState.DEAD, new Coords2D(34,5))
        ));

        CellState state = gol.nextCellState(new Cell(BinaryState.ALIVE, new Coords2D(2,2)), //these coords doesn't matter, it only checks state
                neighbours);

        Assert.assertThat("CellState should be ALIVE", state, is(BinaryState.ALIVE));
    }

    @Test
    public void cellDyingOfCrowd() {
        GameOfLife gol = new GameOfLife(null, new UniformStateFactory(BinaryState.ALIVE), height, width, false, "23/3");
        Set<Cell> neighbours = new HashSet<>(Arrays.asList(
                new Cell(BinaryState.ALIVE, new Coords2D(32,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(33,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(34,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(35,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(36,5)),
                new Cell(BinaryState.DEAD, new Coords2D(3,75)),
                new Cell(BinaryState.ALIVE, new Coords2D(38,5)),
                new Cell(BinaryState.DEAD, new Coords2D(3,55))
        ));

        CellState state = gol.nextCellState(new Cell(BinaryState.DEAD, new Coords2D(2,2)), //these coords doesn't matter, it only checks state
                neighbours);

        Assert.assertThat("CellState should be ALIVE", state, is(BinaryState.DEAD));
    }

    @Test
    public void cellDyingOfLoneliness() {
        GameOfLife gol = new GameOfLife(null, new UniformStateFactory(BinaryState.ALIVE), height,width, false, "23/3");
        Set<Cell> neighbours = new HashSet<>(Arrays.asList(
                new Cell(BinaryState.DEAD, new Coords2D(3,35)),
                new Cell(BinaryState.DEAD, new Coords2D(3,53)),
                new Cell(BinaryState.ALIVE, new Coords2D(3,45)),
                new Cell(BinaryState.DEAD, new Coords2D(36,5)),
                new Cell(BinaryState.DEAD, new Coords2D(367,5)),
                new Cell(BinaryState.DEAD, new Coords2D(3,56)),
                new Cell(BinaryState.DEAD, new Coords2D(3,56)),
                new Cell(BinaryState.DEAD, new Coords2D(36,5))
        ));

        CellState state = gol.nextCellState(new Cell(BinaryState.DEAD, new Coords2D(2,2)), //these coords doesn't matter, it only checks state
                neighbours);

        Assert.assertThat("CellState should be ALIVE", state, is(BinaryState.DEAD));
    }

    @Test
    public void cellBeingRevivedDueToMiracle() {
        GameOfLife gol = new GameOfLife(null, new UniformStateFactory(BinaryState.ALIVE), height, width, false, "23/3");
        Set<Cell> neighbours = new HashSet<>(Arrays.asList(
                new Cell(BinaryState.ALIVE, new Coords2D(31,5)),
                new Cell(BinaryState.ALIVE, new Coords2D(3,25)),
                new Cell(BinaryState.ALIVE, new Coords2D(3,35)),
                new Cell(BinaryState.DEAD, new Coords2D(3,55)),
                new Cell(BinaryState.DEAD, new Coords2D(3,65)),
                new Cell(BinaryState.DEAD, new Coords2D(3,75)),
                new Cell(BinaryState.DEAD, new Coords2D(3,85)),
                new Cell(BinaryState.DEAD, new Coords2D(3,95))
        ));

        CellState state = gol.nextCellState(new Cell(BinaryState.ALIVE, new Coords2D(2,2)), //these coords doesn't matter, it only checks state
                neighbours);

        Assert.assertThat("CellState should be ALIVE", state, is(BinaryState.ALIVE));
    }

}
