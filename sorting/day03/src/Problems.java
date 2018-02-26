import java.util.Arrays;
import java.util.LinkedList;

public class Problems {

    static void sortNumsBetween100s(int[] A) {
        // Big O: O(w(n+b))
        //add 100 to all the elms of A and then radix sort it
        for (int i = 0; i < A.length; i++) {
            A[i] = A[i] + 100;
        }

        RadixSort.radixSort(A, 10);

        for (int i = 0; i < A.length; i++) {
            A[i] = A[i] - 100;
        }
    }

    /**
     * @param n the character number, 0 is the rightmost character
     * @return
     */
    private static int getNthCharacter(String s, int n) {
        return s.charAt(s.length() - 1 - n) - 'a';
    }


    /**
     * Use counting sort to sort the String array according to a character
     *
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByCharacter(String[] A, int n) {
        LinkedList<String>[] L = new LinkedList[26];
        for (int i = 0; i < 26; i++)
            //make a linked list for each element in L
            L[i] = new LinkedList<>();

        for (String i : A) {
            // Extract the relevant digit from i, and add i to the corresponding Linked List.
            int index = getNthCharacter(i, n);
            L[index].addLast(i);
        }

        int j = 0; // index in A to place numbers
        for (LinkedList<String> list : L) {
            // Put all numbers in the linked lists into A
            while(!list.isEmpty()){
                A[j] = list.removeFirst();
                j++;
            }
        }
    }

    /**
     * @param stringLength The length of each of the strings in S
     */
    static void sortStrings(String[] S, int stringLength) {
        int w = S[0].length();
        for (int i = 0; i < w; i++) {
            countingSortByCharacter(S, i);
        }
    }

    /**
     * @param A The array to count swaps in
     * very specialized merge sort, looking for inversions
     * as you are merging you keep track of 3 pointers: left subarray, right subarray,
     * have two merge functions, one returns the count and one returns the array for all the recursive calls
     */

    public static int countSwaps(int[] A) {
        int count = 0;

        return count;
    }

}
