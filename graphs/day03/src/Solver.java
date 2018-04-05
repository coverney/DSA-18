/**
 * Solver definition for the 8 Puzzle challenge
 * Construct a tree of board states using A* to find a path to the goal
 */

import java.util.*;

public class Solver {

    public int minMoves = -1;
    private State solutionState;
    private boolean solved = false;

    /**
     * State class to make the cost calculations simple
     * This class holds a board state and all of its attributes
     */
    private class State implements Comparable<State>{
        // Each state needs to keep track of its cost and the previous state
        private Board board;
        private int moves; // equal to g-cost in A*
        public int cost; // equal to f-cost in A*
        private State prev;

        public State(Board board, int moves, State prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.cost = this.moves + board.manhattan();
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).board.equals(this.board);
        }

        public int compareTo(State s){
            return s.cost - this.cost;
        }
    }

    /*
     * Return the root state of a given state
     */
    private State root(State state) {
        while (state.prev != null){
            state = state.prev;
        }
        return state;
    }

    /*
     * A* Solver
     * Find a solution to the initial board using A* to generate the state tree
     * and a identify the shortest path to the the goal state
     */
    public Solver(Board initial) {
        this.solutionState = new State(initial, 0, null);
//        solution();
    }

    /*
     * Is the input board a solvable state
     * Research how to check this without exploring all states
     */
    public boolean isSolvable() {
        return this.solutionState.board.solvable();
    }

    /*
     * Return the sequence of boards in a shortest solution, null if unsolvable
     */
    public Iterable<Board> solution() {
        PriorityQueue<State> open = new PriorityQueue<>();
        List<State> closed = new LinkedList<>();
        List<Board> solution = new LinkedList<>();
        open.add(this.solutionState);
        while (!open.isEmpty()){
            State q = open.poll();
            if (!q.board.solvable()){
                continue;
            }
            Iterable<Board> neighbors = q.board.neighbors();
            for (Board neighbor:neighbors) {
//                neighbor.printBoard();
                State state = new State(neighbor, q.moves+1, q);
                this.solutionState = state;
                if (!isSolvable()){
                    continue;
                }
                if (neighbor.isGoal()){
                    //stop search
                    System.out.println(minMoves);
                    minMoves = state.moves;
                    return null;
                }
                boolean ignored = false;
                for (State n:open) {
                    if (n.equals(state) && n.cost < state.cost){
                        ignored = true;
                        break;
                    }
                }
                for (State n :closed) {
                    if (n.equals(state) && n.cost < state.cost){
                        ignored = true;
                        break;
                    }
                }
                if (!ignored){
                    open.add(state);
                }
            }
            closed.add(q);
        }

        return null;
    }

    public State find(Iterable<State> iter, Board b) {
        for (State s : iter) {
            if (s.board.equals(b)) {
                return s;
            }
        }
        return null;
    }

    /*
     * Debugging space
     */
    public static void main(String[] args) {
        int[][] initState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board initial = new Board(initState);

        Solver solver = new Solver(initial);
    }


}
