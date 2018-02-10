import java.util.Arrays;

public class MergeSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * This is the recursive step in which you split the array up into
     * a left and a right portion, sort them, and then merge them together.
     *
     *
     * Best-case runtime: O(NlogN) either way, the array will be slit in logN and then merged in N
     * Worst-case runtime: O(NlogN)
     * Average-case runtime: O(NlogN)
     *
     * Space-complexity:O(N) for arrays and O(logN) for Linked Lists
     */
    @Override
    public int[] sort(int[] array) {
        if (array.length <= 1){
            return array;
        }
        int mid = array.length/2;

        int[] left = Arrays.copyOfRange(array, mid, array.length);
        int[] right = Arrays.copyOfRange(array, 0, mid);

        left = sort(left);
        right = sort(right);

        return merge(left, right);
    }

    /**
     * Given two sorted arrays a and b, return a new sorted array containing
     * all elements in a and b. A test for this method is provided in `SortTest.java`
     * Use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     * QUESTION: WHY IS THE INSERTION SORT INCLUSION IN THIS FUNCTION? AND WHY IS IT BETTER THAN A SIMPLE MERGE?
     */
    public int[] merge(int[] a, int[] b) {
        if (a.length + b.length <= INSERTION_THRESHOLD){
            //use insertion sort
            int[] result = new int[a.length+b.length];
            System.arraycopy(a, 0, result, 0, a.length);
            System.arraycopy(b, 0, result, a.length, b.length);

            for (int i = 0; i < result.length; i++) {
                int point = i;
                while (point > 0 && result[point] < result[point-1]){
                    //switch the values at array[point] and array[point-1]
                    int temp = result[point];
                    result[point] = result[point-1];
                    result[point-1] = temp;
                    point--;
                }
            }

            return result;
        }

        else{
            int[] result = new int[a.length+b.length];
            //do merge
            int index1 = 0;
            int index2 = 0;

            for (int i = 0; i < result.length; i++) {
                if (index1 == a.length){
                    result[i] = b[index2];
                    index2++;
                }
                else if (index2 == b.length){
                    result[i] = a[index1];
                    index1++;
                }
                else if (a[index1] >= b[index2]){
                    result[i] = b[index2];
                    index2++;
                }
                else{
                    result[i] = a[index1];
                    index1++;
                }
            }

            return result;
        }
    }

}
