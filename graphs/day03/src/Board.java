import java.util.LinkedList;
import java.util.List;

/**
 * Board definition for the 8 Puzzle challenge
 */
public class Board {

    private int n;
    public int[][] tiles;

    // Create a 2D array representing the solved board state
    private int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    /*
     * Set the global board size and tile state
     */
    public Board(int[][] b) {
        this.tiles = b;
        this.n = b.length; // should be 3
    }

    /*
     * Prints out the board state nicely for debugging purposes
     */
    public void printBoard() {
        for (int[] tiles : this.tiles) {
            for (int aTile : tiles) System.out.print(aTile + " ");
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Size of the board 
     (equal to 3 for 8 puzzle, 4 for 15 puzzle, 5 for 24 puzzle, etc)
     */
    private int size() {
        return this.n;
    }

    /*
     * Sum of the manhattan distances between the tiles and the goal
     */
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                int value = this.tiles[i][j];
                if (value == 0) {
                    continue;
                }
                value -= 1;
                int hori = Math.abs(j - (value % 3));
                int verti = Math.abs(i - value / 3);
                manhattan += hori + verti;
            }
        }
        return manhattan;
    }

    /*
     * Compare the current state to the goal state
     */
    public boolean isGoal() {
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (this.tiles[i][j] != this.goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Returns true if the board is solvable
     * Research how to check this without exploring all states
     */
    public boolean solvable() {
        boolean[] reference = new boolean[n * n - 1];
        int num_inv = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                int value = this.tiles[i][j];
                if (value != 0) {
                    reference[value - 1] = true;
                    for (int k = 0; k < value - 1; k++) {
                        if (!reference[k]) {
                            num_inv++;

                        }
                    }
                }
            }
        }
        return num_inv % 2 != 1;
    }

    /*
     * Return all neighboring boards in the state tree
     */
    public Iterable<Board> neighbors() {
        // all the edge cases when trying to move pieces into the blank spaces

        // find blank tile
        int i0 = 0;
        int j0 = 0;
        outerloop:
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (this.tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    break outerloop;
                }
            }
        }

        List<Board> boards = new LinkedList<>();

        Board board = new Board(clone(this.tiles));
        if (board.swap(board.tiles, i0, j0, i0 + 1, j0)) {
            boards.add(board);
        }


        board = new Board(clone(this.tiles));
        if (board.swap(board.tiles, i0, j0, i0 - 1, j0)) {
            boards.add(board);
        }

        board = new Board(clone(this.tiles));
        if (board.swap(board.tiles, i0, j0, i0, j0 + 1)) {
            boards.add(board);
        }

        board = new Board(clone(this.tiles));
        if (board.swap(board.tiles, i0, j0, i0, j0 - 1)) {
            boards.add(board);
        }
        return boards;
    }

    private boolean swap(int[][] tiles, int i1, int j1, int i2, int j2) {
        if (i2 < 0 || i2 >= this.n || j2 < 0 || j2 >= this.n) {
            return false;
        }

        int temp = tiles[i1][j1];
        tiles[i1][j1] = tiles[i2][j2];
        tiles[i2][j2] = temp;
        return true;
    }

    private int[][] clone(int[][] tiles){
        int[][] new_tiles = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            new_tiles[i] = tiles[i].clone();
        }
        return new_tiles;
    }
    /*
     * Check if this board equals a given board state
     */
    @Override
    public boolean equals(Object x) {
        // Check if the board equals an input Board object
        if (x == this) return true;
        if (x == null) return false;
        if (!(x instanceof Board)) return false;
        // Check if the same size
        Board y = (Board) x;
        if (y.tiles.length != n || y.tiles[0].length != n) {
            return false;
        }
        // Check if the same tile configuration
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != y.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // DEBUG - Your solution can include whatever output you find useful
        int[][] initState = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(initState);

        System.out.println("Size: " + board.size());
        System.out.println("Solvable: " + board.solvable());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        Iterable<Board> it = board.neighbors();
    }
}
