package com.automaton.application;

import com.automaton.states.BinaryState;
import com.automaton.cell.Cell;
import com.automaton.cell.CellNeighbourhood;
import com.automaton.states.CellState;
import com.automaton.cell.CellStateFactory;
import com.automaton.states.QuadState;
import java.util.*;

/**
 * Created by lick on 11/11/16.
 */

/**
 * Conway's Game of Life with ability to modify basic rules. Class is in receipt of QuadMode.
 *
 * @see Automaton
 * @see Automaton1Dim
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

    @Override
    protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {

        // set rules as Sets of cases defining when cell lives and revives
        // rest of cases are when cell dies
        List<Set<Integer>> cases = parseRule(rule);
        Set<Integer> surviveCases = cases.get(0);
        Set<Integer> reviveCases = cases.get(1);

        CellState currentState = currentCell.getState();

        // if quadmode we have to have particularly Conway's rules to properly set colors
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
                        Most common color is that, which is being worn by 2 or 3 neighbours in map.

                        There can only be one of cases mentioned above because 3 = 2 + 1  OR  3 = 3 + 0
                        Therefore when I find a Color used more than once, I know it's max-used color
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

            // return computed state
            if (currentState == BinaryState.ALIVE && !surviveCases.contains(neighboursAlive))
                return BinaryState.DEAD;
            else if (currentState == BinaryState.DEAD && reviveCases.contains(neighboursAlive))
                return BinaryState.ALIVE;
        }

        return currentState;
    }

    // convert rule given by User as String to List of Sets to pass to our nextState()
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
