public class LocksAndKeys {

    private static void swap(char[] A, int i, int j) {
        char t = A[i];
        A[i] = A[j];
        A[j] = t;
    }
    static char[][] locksAndKeys(char[] locks, char[] keys) {
        // Return a 2d char array, where result[0] is the sorted locks, and result[1] is the sorted keys
        char[][] result = new char[2][];
        quickSort(keys, locks, 0, keys.length-1);
        result[0] = locks;
        result[1] = keys;
        return result;
    }


    private static void quickSort(char[] k, char[]l,  int lo, int hi) {
        if (lo < hi) {

            //choose last character of key array for lock partition
            int p = partition(l, lo, hi, k[hi]);

            //use the pivot to partition the key array
            partition(k, lo ,hi, l[p]);

            // Recur for [low...pivot-1] & [pivot+1...high] for nuts and
            // bolts array.
            quickSort(k, l, lo, p-1);
            quickSort(k, l, p+1, hi);
        }
    }

    // Similar to standard partition method, but pivot is an argument
    private static int partition(char[] arr, int low, int high, char pivot)
    {
        int index = low;
        for (int i = low; i < high; i++) {
            if (arr[i] < pivot){
                //if the character is less than pivot, move it to the left of pivot
                swap(arr, index, i);
                index++;
            }
            else if (arr[i] == pivot){
                //if the character us equal to pivot, move it to the end of the list and run through the loop
                // again with the new value at i
                swap(arr, i, high);
                i--;
            }

        }
        //at the very end, swap the pivot with where it should be , determined by index, and return its new position
        swap(arr, index, high);
        return index;
    }

}




