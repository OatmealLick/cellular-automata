package com.automaton.application;

import com.automaton.states.BinaryState;
import com.automaton.cell.Cell;
import com.automaton.cell.CellNeighbourhood;
import com.automaton.states.CellState;
import com.automaton.cell.CellStateFactory;
import com.automaton.states.QuadState;
import java.lang.String.*;
import java.util.*;

/**
 * Created by lick on 11/11/16.
 */
public class GameOfLife extends Automaton2Dim{
    boolean quadMode;
    String rule;

    public GameOfLife(CellNeighbourhood neighboursStrategy,
                       CellStateFactory stateFactory,
                       int height, int width, boolean quadMode, String rule) {
        super(neighboursStrategy, stateFactory, height, width);
        this.quadMode = quadMode;
        this.rule = rule;
    }

    public boolean isQuadMode() {
        return quadMode;
    }

    @Override
    protected Automaton newInstance() {
        int height=super.getHeight(), width=super.getWidth();


        return new GameOfLife(
                super.neighboursStrategy,
                super.stateFactory,
                height,
                width,
                quadMode,
                rule);

    }

    /**
     * nextCellState determines next cell state judging by states of neighbours and itself.
     *
     * For now method is hardcoded 23/3 beacuse I have no idea whatsoever
     * where should I pass rules to eventually ending up here
     * @param currentCell
     * @param neighboursStates
     * @return
     */
    protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
        List<Set<Integer>> cases = parseRule(rule);
        Set<Integer> surviveCases = cases.get(0);
        Set<Integer> reviveCases = cases.get(1);

        CellState currentState = currentCell.getState();

        if(quadMode) {

            surviveCases = new HashSet<>(Arrays.asList(2,3));
            reviveCases = new HashSet<>(Arrays.asList(3));

            Map<QuadState, Integer> neighboursColors = new HashMap<>();
            int neighboursAlive = 0;
            for(Cell cell: neighboursStates) {
                QuadState state = (QuadState)cell.getState();
                if(state!=QuadState.DEAD) {

                    if (neighboursColors.containsKey(state))
                        neighboursColors.put(state, neighboursColors.get(state) + 1);
                    else
                        neighboursColors.put(state, 1);
                    neighboursAlive++;
                }
            }

            if(currentState != QuadState.DEAD && !surviveCases.contains(neighboursAlive))
                return QuadState.DEAD;
            else if(currentState == QuadState.DEAD && reviveCases.contains(neighboursAlive)) {
                // if every neighbours (of 3) has different colour
                // Case 3 = 1 + 1 + 1
                if(neighboursColors.size()==3) {
                    List<QuadState> stateList = new ArrayList<>(Arrays.asList(
                            QuadState.BLUE,
                            QuadState.YELLOW,
                            QuadState.GREEN,
                            QuadState.RED
                    ));

                    for(Map.Entry<QuadState, Integer> entry : neighboursColors.entrySet())
                        stateList.remove(entry.getKey());

                    return stateList.get(0);

                } else
                    /* There are only 3 neighbours here.
                        Most common color is that, which is beared by 2 or 3 neighbours in map.

                        There can only be one of above cases because 3 = 2 + 1  OR  3 = 3 + 0
                        Therefore when I find a Color used more than once, I know it's maximum
                                                I've been looking for.
                     */
                    for(Map.Entry<QuadState, Integer> entry : neighboursColors.entrySet())
                        if(entry.getValue()>1)
                            return entry.getKey();
            }

        } else {
            int neighboursAlive = 0;
            for (Cell cell : neighboursStates) {
                if (cell.getState() == BinaryState.ALIVE)
                    neighboursAlive++;
            }

            if (currentState == BinaryState.ALIVE && !surviveCases.contains(neighboursAlive))
                return BinaryState.DEAD;
            else if (currentState == BinaryState.DEAD && reviveCases.contains(neighboursAlive))
                return BinaryState.ALIVE;
        }

        return currentState;
    }

    private List<Set<Integer>> parseRule (String rule) {
        String[] parts = rule.split("/");
        String part1 = parts[0];
        String part2 = parts[1];

        Set<Integer> surviveCases = new HashSet<>();
        for(Character c : part1.toCharArray())
            surviveCases.add(Integer.parseInt(c.toString()));

        Set<Integer> reviveCases = new HashSet<>();
        for(Character c : part2.toCharArray())
            reviveCases.add(Integer.parseInt(c.toString()));

        return new ArrayList<>(Arrays.asList(surviveCases, reviveCases));
    }
}
