
public class InsertionSort extends SortAlgorithm {
    /**
     * Use the insertion sort algorithm to sort the array
     *
     *
     * Best-case runtime: O(N) run through array once and it is already sorted
     * Worst-case runtime: O(N^2)
     * Average-case runtime: O(N^2) might not have to run through everything but still rounds to N^2
     *
     * Space-complexity: O(1) arranging the elements in place
     */
    @Override
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int point = i;
            while (point > 0 && array[point] < array[point-1]){
                //switch the values at array[point] and array[point-1]
                int temp = array[point];
                array[point] = array[point-1];
                array[point-1] = temp;
                point--;
            }
        }
        return array;
    }
}
