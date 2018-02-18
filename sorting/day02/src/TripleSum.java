
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class TripleSum {

    static int tripleSum(int arr[], int sum) {
        //O(N^2)
        //sort the array in ascending order
        Arrays.sort(arr);
        int result = 0;

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        //iterate through sorted array from the end.
        for (int i = arr.length-1; i >= 0 ; i--) {
            int value = arr[i];
            //run through the rest of the elements of the list looking for pair sums that add up to sum-k
            //clear hashmap
            int target = sum - value;
            for (int j = i -1; j >= 0; j--) {
                int value2 = arr[j];
                if (map.containsKey(value2)){
                    result++;
                }
                else{
                    int delta = target - arr[j];
                    map.put(delta, arr[j]);
                }
            }
            map.clear();

        }

        return result;
    }
}
