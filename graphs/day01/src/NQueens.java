import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {


    /**
     * Checks the 45° and 135° diagonals for an existing queen. For example, if the board is a 5x5
     * and you call checkDiagonal(board, 3, 1), The positions checked for an existing queen are
     * marked below with an `x`. The location (3, 1) is marked with an `o`.
     * <p>
     * ....x
     * ...x.
     * x.x..
     * .o...
     * .....
     * <p>
     * Returns true if a Queen is found.
     * <p>
     * Do not modify this function (the tests use it)
     */
    public static boolean checkDiagonal(char[][] board, int r, int c) {
        int y = r - 1;
        int x = c - 1;
        while (y >= 0 && x >= 0) {
            if (board[y][x] == 'Q') return true;
            x--;
            y--;
        }
        y = r - 1;
        x = c + 1;
        while (y >= 0 && x < board[0].length) {
            if (board[y][x] == 'Q') return true;
            x++;
            y--;
        }
        return false;
    }

    private static boolean checkRow(char[][] board, int r, int c, int counter) {
        int tempCol = c;
        while (tempCol >= 0) {
            if (board[r][tempCol] == 'Q') return false;
            tempCol--;
        }

        tempCol = c;
        while (tempCol < counter) {
            if (board[r][tempCol] == 'Q') return false;
            tempCol++;
        }

        return true;
    }

    private static boolean checkCol(char[][] board, int r, int c, int counter) {
        int tempRow = r;
        while (tempRow >= 0) {
            if (board[tempRow][c] == 'Q') return false;
            tempRow--;
        }

        tempRow = r;
        while (tempRow < counter) {
            if (board[tempRow][c] == 'Q') return false;
            tempRow++;
        }
        return true;
    }


    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[][] copyOf(char[][] A) {
        char[][] B = new char[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    //Time: O(N) for validation, O(N^N) for traversal because height of tree is n and each node could have n branches
    //Space: several n*n 2D arrays
    public static List<char[][]> nQueensSolutions(int n) {
        // makes an array of board solutions
        List<char[][]> solutions = new ArrayList<>();
        // creates a starting board and sets every position blank
        char[][] current = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                current[i][j] = '.';
            }
        }
        // calls the recursive solve function
        solve(solutions, current, n, 0, 0, n);
        return solutions;
    }

    private static void solve(List<char[][]> solutions, char[][] current, int numQueen, int row, int col, int counter) {
        // of the number of leftover queens is 0, then you filled up a board, so add it to solution array
        if (numQueen == 0) {
            solutions.add(copyOf(current));
        }

        else {
            for (int i = 0; i < counter; i++) {
                // check if it is valid to place a queen at a certain position
                if (isvalid(current, i, col, counter)) {
                    // if valid set its position to 'Q' and do recursive call to fill out the rest of the queens
                    current[i][col] = 'Q';
                    //the next queen can't be at the same col, so I increment col by 1
                    solve(solutions, current, numQueen - 1, i, col + 1, counter);
                    //I have to make sure to reset the position or backtrack
                    current[i][col] = '.';
                }

            }
        }


    }

    private static boolean isvalid(char[][] board, int row, int col, int n) {
        if (!checkCol(board, row, col, n) || !checkRow(board, row, col, n)) {
            return false;
        }

        // for some reason the checkDiagonal function does not work, so I wrote my own
        //4 cases for four different diagonals (+45, -45, +135, -135)

        int temprow = row;
        int tempcol = col;
        while ((temprow < n) && (tempcol < n)) {
            if (board[temprow][tempcol] == 'Q') {
                return false;
            }
            temprow++;
            tempcol++;

        }

        temprow = row;
        tempcol = col;
        while ((temprow < n) && (tempcol > -1)) {
            if (board[temprow][tempcol] == 'Q') {
                return false;
            }
            temprow++;
            tempcol--;

        }
        temprow = row;
        tempcol = col;
        while ((temprow > -1) && (tempcol < n)) {
            if (board[temprow][tempcol] == 'Q') {
                return false;
            }
            temprow--;
            tempcol++;
        }


        temprow = row;
        tempcol = col;
        while ((temprow > -1) && (tempcol > -1)) {
            if (board[temprow][tempcol] == 'Q') {
                return false;
            }

            temprow--;
            tempcol--;
        }

        return true;
    }

}
