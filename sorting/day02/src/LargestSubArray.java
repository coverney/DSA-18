import java.util.HashMap;

public class LargestSubArray {
    static int[] largestSubarray(int[] nums) {
        //treat 0 as -1 and then sum up the numbers from the front of the array while saving the sums after each iteration
        //through nums. Two equal sums within the sum array signify a potential subarray. Then store the sums in a HashMap
        //look for same values, keep track of their indexes, and record their length. Return their the pair with largest length
        int[] sums = new int[nums.length];
        sums[0] = nums[0] == 0 ? -1 : 1;
        for (int i = 1; i < nums.length; i++) {
            //append original sum to next element in nums (1 for 1 and -1 for 0)
            sums[i] = sums[i - 1] + (nums[i] == 0 ? -1 : 1);
        }

        HashMap<Integer, Integer> sum_to_index = new HashMap<Integer, Integer>();

        int first = 0;
        int last = 0;
        int max_length = 0;

        for (int i = 1; i < sums.length; i++) {
            if (sums[i] == 0) {
                //special case in that we know all the elements before and up to i add up to 0 so equal 1s and 0s
                int temp_length = i + 1;
                if (temp_length > max_length) {
                    max_length = temp_length;
                    first = 0;
                    last = i;
                }
            } else if (sum_to_index.containsKey(sums[i])) {
                //the sum exists already so we know that the things between the sums negate each other.
                //the trick in this problem is to find out what the cut off points should be
                //the negated elements range from the index at i + 1 to the index with the same sum
                int temp_length = i - sum_to_index.get(sums[i]);
                if (temp_length > max_length) {
                    max_length = temp_length;
                    first = sum_to_index.get(sums[i]);
                    //there may be more solutions depending on whether there is a pair of 1 or 0 on the left side
                    if (first >= 2 && nums[first - 1] + nums[first - 2] == 1) {
                        first = first - 2;
                    }

                    last = i;
                }
            } else {
                sum_to_index.put(sums[i], i + 1);
            }

        }
        return new int[]{first, last};
    }
}
