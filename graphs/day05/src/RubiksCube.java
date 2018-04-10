import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// use this class if you are designing your own Rubik's cube implementation
public class RubiksCube {
    private int[][][] cubes;

    // initialize a solved rubiks cube
    public RubiksCube() {
        this.cubes = new int[2][2][2];
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    this.cubes[i][j][k] = counter;
                    counter++;
                }
            }
        }
    }


    // creates a copy of the rubics cube
    public RubiksCube(RubiksCube r) {
        int counter = 0;
        this.cubes = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    this.cubes[i][j][k] = r.cubes[i][j][k];
                    counter++;
                }
            }
        }
    }

    // return true if this rubik's cube is equal to the other rubik's cube
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RubiksCube))
            return false;
        RubiksCube other = (RubiksCube) obj;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    if (this.cubes[i][j][k] != other.cubes[i][j][k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * return a hashCode for this rubik's cube.
     * <p>
     * Your hashCode must follow this specification:
     * if A.equals(B), then A.hashCode() == B.hashCode()
     * <p>
     * Note that this does NOT mean:
     * if A.hashCode() == B.hashCode(), then A.equals(B)
     */
    @Override
    public int hashCode() {
        // TODO
        return 0;
    }

    public boolean isSolved() {
        RubiksCube goal = new RubiksCube();
        return this.equals(goal);
    }


    // given a list of rotations, return a rubik's cube with the rotations applied
    public RubiksCube rotate(List<Character> c) {
        RubiksCube rub = this;
        for (char r : c) {
            rub = rub.rotate(r);
        }
        return rub;
    }


    // Given a character in ['u', 'U', 'r', 'R', 'f', 'F'], return a new rubik's cube with the rotation applied
    // Do not modify this rubik's cube.
    public RubiksCube rotate(char c) {
        if (c == 'u') {
            this.swap(1, 3, 7, 5);

        } else if (c == 'U') {
            this.swap(1, 5, 7, 3);

        } else if (c == 'r') {
            this.swap(4, 5, 7, 6);

        } else if (c == 'R') {
            this.swap(4, 6, 7, 5);

        } else if (c == 'f') {
            this.swap(0, 1, 5, 4);

        } else {
            this.swap(0, 4, 5, 1);
        }
        return this;
    }

    private void swap(int x1, int x2, int x3, int x4) {
        int temp = this.cubes[x1/4][(x1/2)%2][x1%2];
        this.cubes[x1/4][(x1/2)%2][x1%2] = this.cubes[x2/4][(x2/2)%2][x2%2];
        this.cubes[x2/4][(x2/2)%2][x2%2] = this.cubes[x3/4][(x3/2)%2][x3%2];
        this.cubes[x3/4][(x3/2)%2][x3%2] = this.cubes[x4/4][(x4/2)%2][x4%2];
        this.cubes[x4/4][(x4/2)%2][x4%2] = temp;
    }

    // returns a random scrambled rubik's cube by applying random rotations
    public static RubiksCube scrambledCube(int numTurns) {
        RubiksCube r = new RubiksCube();
        char[] listTurns = getScramble(numTurns);
        for (int i = 0; i < numTurns; i++) {
            r = r.rotate(listTurns[i]);
        }
        return r;
    }

    public static char[] getScramble(int size) {
        char[] listTurns = new char[size];
        for (int i = 0; i < size; i++) {
            switch (ThreadLocalRandom.current().nextInt(0, 6)) {
                case 0:
                    listTurns[i] = 'u';
                    break;
                case 1:
                    listTurns[i] = 'U';
                    break;
                case 2:
                    listTurns[i] = 'r';
                    break;
                case 3:
                    listTurns[i] = 'R';
                    break;
                case 4:
                    listTurns[i] = 'f';
                    break;
                case 5:
                    listTurns[i] = 'F';
                    break;
            }
        }
        return listTurns;
    }


    // return the list of rotations needed to solve a rubik's cube
    public List<Character> solve() {
        // TODO
        return new ArrayList<>();
    }

}



