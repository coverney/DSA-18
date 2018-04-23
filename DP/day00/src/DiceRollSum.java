public class DiceRollSum {

    // Runtime: O(N*D)
    // Space: O(N) not including space it takes for recursive calls
    public static int diceRollSum(int N) {
        int[] DP = new int[N + 1];
        for (int i = 0; i < DP.length; i++) {
            DP[i] = -1; // set a special empty value
        }
        return diceRecurs(N, DP);
    }

    private static int diceRecurs(int i, int[] DP) {
        // base cases
        if (i < 0) return 0;
        if (i == 0) return 1;

        // have we already solved this subproblem
        if (DP[i] != -1) return DP[i];

        // DP[i] = sum for k=1 to 6 of DP[N-k]
        int answer = 0;
        for (int j = 1; j < 7; j++) {
            answer += diceRecurs(i - j, DP);
        }
        // store our answer and return it
        DP[i] = answer;
        return answer;
    }

}
