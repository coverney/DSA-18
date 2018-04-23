import java.util.Arrays;

public class LongestIncreasingSubsequence {
    public static int max;
    // Runtime: O(N^2) must do sub problem around N times, for each sub problem need to iterate through more problems to find max
    // Space: O(N) not including space it takes for recursive calls
    public static int LIS(int[] A) {
        LongestIncreasingSubsequence.max = 0;

        int[] DP = new int[A.length];
        for (int i = 0; i < DP.length; i++) {
            DP[i] = -1; // set a special empty value
        }

        subseqRecurs(A, DP, A.length);
        return LongestIncreasingSubsequence.max;
    }

    private static int subseqRecurs(int[] input, int[] DP, int index) {
        // base cases
        if (index == 0){
            return 0;
        }
        if (index == 1){
            return 1;
        }

        // check memo
        if (DP[index-1] != -1){
            return DP[index-1];
        }

        // find the max LIS for a sub sequence from 0 to index
        int res, max_LIS_here = 1;

        for (int i = 1; i < index; i++) {
            // max LIS for sub problem is just 1 + the max LIS of an earlier sequence that it would fit in
            res = subseqRecurs(input, DP, i);
            if (input[i-1] < input[index-1] && res + 1 > max_LIS_here){
                max_LIS_here = res + 1;
            }
        }

        // update the global max
        if (LongestIncreasingSubsequence.max < max_LIS_here){
            LongestIncreasingSubsequence.max = max_LIS_here;
        }

        // update the memo and return
        DP[index-1] = max_LIS_here;
        return max_LIS_here;
    }
}