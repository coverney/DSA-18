import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// use this class if you are designing your own Rubik's cube implementation
public class RubiksCube {

    private class Cubie {

        int id;
        int[] orien;

        private Cubie(int id, int[] orien) {
            this.id = id;
            this.orien = orien;
        }

        private void cross_product(int[] a) {
            if (Math.abs(this.orien[0]) != Math.abs(a[0]) || Math.abs(this.orien[1]) != Math.abs(a[1]) || Math.abs(this.orien[2]) != Math.abs(a[2])) {
                int[] result = new int[3];
                result[0] = a[1] * this.orien[2] - a[2] * this.orien[1];
                result[1] = a[2] * this.orien[0] - a[0] * this.orien[2];
                result[2] = a[0] * this.orien[1] - a[1] * this.orien[0];
                this.orien = result;
            }
        }

        private boolean equals(Cubie other) {
            if (this.id == other.id) {
                for (int i = 0; i < this.orien.length; i++) {
                    if (this.orien[i] != other.orien[i]) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        public String toString() {
            return "" + this.id + this.orien[0] + this.orien[1] + this.orien[2];
        }
    }

    private Cubie[][][] cubes;
    private ArrayList<Character> rotations;

    // initialize a solved rubiks cube
    public RubiksCube() {
        this.cubes = new Cubie[2][2][2];
        this.rotations = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    this.cubes[i][j][k] = new Cubie(counter, new int[]{0, 0, 1});
                    counter++;
                }
            }
        }
    }

    // initialize a solved rubiks cube
    public RubiksCube(int[] orien) {
        this.cubes = new Cubie[2][2][2];
        this.rotations = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    this.cubes[i][j][k] = new Cubie(counter, orien);
                    counter++;
                }
            }
        }
    }


    // creates a copy of the rubiks cube
    public RubiksCube(RubiksCube r) {
        this.rotations = new ArrayList<>(r.rotations);
        this.cubes = new Cubie[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    this.cubes[i][j][k] = new Cubie(r.cubes[i][j][k].id, r.cubes[i][j][k].orien);
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
                    if (!this.cubes[i][j][k].equals(other.cubes[i][j][k])) {
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
        StringBuilder representation = new StringBuilder();
        for (Cubie[][] cube : this.cubes) {
            for (Cubie[] aCube : cube) {
                for (Cubie anACube : aCube) {
                    representation.append(anACube.toString());
                }
            }
        }
        return representation.toString().hashCode();
    }

    public boolean isSolved() {
        RubiksCube goal = new RubiksCube(new int[]{0, 0, 1});
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
        RubiksCube newRubiksCube = new RubiksCube(this);
        if (c == 'u') {
            newRubiksCube.swap(1, 3, 7, 5, new int[]{0, 0, 1});

        } else if (c == 'U') {
            newRubiksCube.swap(1, 5, 7, 3, new int[]{0, 0, -1});

        } else if (c == 'r') {
            newRubiksCube.swap(4, 5, 7, 6, new int[]{0, 1, 0});

        } else if (c == 'R') {
            newRubiksCube.swap(4, 6, 7, 5, new int[]{0, -1, 0});

        } else if (c == 'f') {
            newRubiksCube.swap(7, 3, 2, 6, new int[]{-1, 0, 0});

        } else {
            newRubiksCube.swap(7, 6, 2, 3, new int[]{1, 0, 0});
        }
        return newRubiksCube;
    }

    public RubiksCube rotateSolve(char c) {
        RubiksCube newRubiksCube = rotate(c);
        newRubiksCube.rotations.add(c);
        return newRubiksCube;
    }

    private void swap(int x1, int x2, int x3, int x4, int[] transformation) {
        Cubie temp = this.cubes[x1 / 4][(x1 / 2) % 2][x1 % 2];
        this.cubes[x1 / 4][(x1 / 2) % 2][x1 % 2] = this.cubes[x2 / 4][(x2 / 2) % 2][x2 % 2];
        this.cubes[x2 / 4][(x2 / 2) % 2][x2 % 2] = this.cubes[x3 / 4][(x3 / 2) % 2][x3 % 2];
        this.cubes[x3 / 4][(x3 / 2) % 2][x3 % 2] = this.cubes[x4 / 4][(x4 / 2) % 2][x4 % 2];
        this.cubes[x4 / 4][(x4 / 2) % 2][x4 % 2] = temp;

        this.cubes[x1 / 4][(x1 / 2) % 2][x1 % 2].cross_product(transformation);
        this.cubes[x2 / 4][(x2 / 2) % 2][x2 % 2].cross_product(transformation);
        this.cubes[x3 / 4][(x3 / 2) % 2][x3 % 2].cross_product(transformation);
        this.cubes[x4 / 4][(x4 / 2) % 2][x4 % 2].cross_product(transformation);


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

    private List<RubiksCube> neighbors() {
        List<RubiksCube> neighbors = new ArrayList<>();
        char[] reference = new char[]{'u', 'U', 'r', 'R', 'f', 'F'};
        for (char instruction : reference) {
            neighbors.add(rotateSolve(instruction));
        }
        return neighbors;
    }


    // return the list of rotations needed to solve a rubik's cube
    public List<Character> solve() {
        Queue<RubiksCube> open = new LinkedList<>();
        HashSet<RubiksCube> closed = new HashSet<>();
        closed.add(this);
        open.add(this);
        while (!open.isEmpty()) {
            RubiksCube temp = open.remove();
            if (temp.isSolved()) {
                return temp.rotations;
            }
            for (RubiksCube neigh : temp.neighbors()) {
                if (!closed.contains(neigh)) {
                    closed.add(neigh);
                    open.add(neigh);
                }
            }
        }
        return new ArrayList<>();
    }

}



