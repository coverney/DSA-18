public class DungeonGame {

    public static int minInitialHealth(int[][] map) {
        int r = map.length;
        int c = map[0].length;

        int[][] DP = new int[r][c];

        // find minInitialHealth for goal (you would need at least that much)
        DP[r-1][c-1] = Math.max((1 - map[r-1][c-1]), 0);

        // finding minInitialHealths for last row
        for (int j = c-2; j >= 0 ; j--) {
            DP[r-1][j] = Math.max((DP[r-1][j+1] - map[r-1][j]), 0);
        }

        // finding minInitialHealths for last column
        for (int i = r-2; i >= 0 ; i--) {
            DP[i][c-1] = Math.max((DP[i+1][c-1] - map[i][c-1]), 0);
        }

        // finding the minInitialHealths for the rest of the elements
        // by finding the min between moving down or to the right
        for (int i = r-2; i >= 0 ; i--) {
            for (int j = c-2; j >= 0 ; j--) {
                int down = Math.max((DP[i+1][j] - map[i][j]), 0);
                int right = Math.max((DP[i][j+1] - map[i][j]), 0);
                DP[i][j] = Math.min(down, right);
            }
        }

        return DP[0][0];
    }

}
