public class SplitCoins {

    public static int splitCoins(int[] coins) {

        // initialize some variables to keep track of position and total value of coins
        int diff = 0;
        int sum = 0;
        int index = 0;

        for (int coin : coins) {
            sum += coin;
        }

        // memo consists of a 2d array with the coin value array representing columns and the two different arrays
        // that can be created by adding a coin to either list
        int[][] DP = new int[sum * 2+1][coins.length];
        for (int i = 0; i < DP.length; i++) {
            for (int j = 0; j < DP[0].length; j++) {
                DP[i][j] = -1;
            }
        }

        //return result of top down approach (it should be the first column and the middle row with minimum difference)
        return splitCoinsDP(diff, index, coins, DP, sum);
    }

    private static int splitCoinsDP(int diff, int index, int[] coins, int[][] DP, int sum) {
        if (index >= coins.length) {
            // if index goes beyond the number of columns in DP, just return the diff because there are no more coins to add
            return Math.abs(diff);
        } else if (DP[diff + sum][index] != -1) {
            // check memo
            return DP[diff + sum][index];
        } else {
            // update memo: value is the minimum of the result when adding the coin at index to either pile
            int new_diff = Math.min(splitCoinsDP(diff + coins[index], index + 1, coins, DP, sum), splitCoinsDP(diff - coins[index], index + 1, coins, DP, sum));
            DP[diff + sum][index] = new_diff;

            return Math.abs(new_diff);
        }
    }
}

