public class CountingSort {

    /**
     * Use counting sort to sort positive integer array A.
     * Runtime: O(n+k) with n being the number of elements and k being the max element
     *
     * k: maximum element in array A
     */
    static void countingSort(int[] A) {
        //find max elem of A and then make a new array with length = max
        int max = 0;
        for (int val: A) {
            if (val > max){
                max = val;
            }
        }

        int[] counts = new int[max+1];

        // update counts based on the elements in A
        for (int val: A) {
            counts[val] = counts[val] + 1;
        }

        //go through counts and slowly move the information from counts to A
        //remember that the index of counts is the value of A and the value of counts is the amount of a given value in A
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i] > 0){
                counts[i]--;
                A[index] = i;
                index++;
            }
        }

    }

}
