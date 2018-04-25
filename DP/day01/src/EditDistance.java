public class EditDistance {

    public static int minEditDist(String a, String b) {
        int[][] DP = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < DP.length; i++) {
            for (int j = 0; j < DP[0].length; j++) {
                DP[i][j] = -1;
            }
        }
        return minEditDistDP(a, b, a.length(), b.length(), DP);
    }

    private static int minEditDistDP(String str1, String str2, int m, int n, int[][] DP) {
        if (m == 0) {
            // if str1 is empty return the size of str2 (insertions are the only possible operations)
            return n;
        } else if (n == 0) {
            // if str2 is empty return the size of str1 (insertions are the only possible operations)
            return m;
        } else if (DP[m][n] != -1) {
            // check memo
            return DP[m][n];
        } else if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
            // if the end characters being compared are the same, then no operation needs to be down
            // just need to continue with rest of string
            DP[m][n] = minEditDistDP(str1, str2, m - 1, n - 1, DP);
            return DP[m][n];
        } else {
            // add one to the number of operations and explore the different distances based on the guessed operation
            DP[m][n] = 1 + Math.min(Math.min(minEditDistDP(str1, str2, m, n - 1, DP), minEditDistDP(str1, str2, m - 1, n, DP)), minEditDistDP(str1, str2, m - 1, n - 1, DP));
            return DP[m][n];
        }
    }

}
