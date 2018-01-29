import static java.lang.Math.abs;

public class MyArrayList {
    private Cow[] elems;
    private int size;

    // Runtime: O(1)
    public MyArrayList() {
        this(10);
    }

    // Runtime: O(n) or 0(1)?
    public MyArrayList(int capacity) {
        this.elems = new Cow[capacity];
        this.size = 0;
    }

    // Runtime: O(1)*
    public void add(Cow c) {
        if (size >= 0.25 * elems.length) {
            Cow[] elems2 = new Cow[elems.length * 2];
            System.arraycopy(elems, 0, elems2, 0, elems.length);
            elems = elems2;
        }

        this.elems[size] = c;
        size++;
    }

    // Runtime: O(1)
    public int size() {
        return this.size;
    }

    // Runtime: O(1)
    public Cow get(int index) {
        if (index < 0 || index >= this.size) {

            throw new IndexOutOfBoundsException("index is out of array bounds");
        }

        Cow cow = this.elems[index];
        return cow;

    }

    // Runtime: O(n)*
    public Cow remove(int index) {
        if (index < 0 || index >= this.size) {

            throw new IndexOutOfBoundsException("index is out of array bounds");
        }
        Cow cow = this.elems[index];
        // shift the array to the left
        for (int i = index; i < size - 1; i++) {

            elems[i] = elems[i + 1];
        }
        this.size--;

        if (size < 0.25 * elems.length) {
            Cow[] elems2 = new Cow[elems.length / 2];
            System.arraycopy(elems, 0, elems2, 0, elems.length / 2);
            elems = elems2;
        }
        return cow;
    }

    // Runtime: O(n)
    public void add(int index, Cow c) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("index is out of array bounds");
        }
        this.add(c);
        size--;

        for (int i = size; i > index; i--) {
            elems[i] = elems[i - 1];
        }
        elems[index] = c;
        size++;
    }

    // Additional Practice

    //Given an array with an odd number of elements, find the median without sorting.
    //Runtime: O(n2)
    public int find_median(int[] array) throws Exception {
        for (int i = 0; i < array.length - 1; i++) {
            int num_bigger = 0;
            int num_smaller = 0;
            int num_equal = 0;
            for (int j = 0; j < array.length; j++) {

                if (array[i] > array[j]) {
                    num_bigger++;
                } else if (array[i] < array[j]) {
                    num_smaller++;
                } else {
                    num_equal++;
                }
            }

            if (abs(num_bigger - num_smaller) < num_equal) {
                return array[i];
            }

        }
        throw new Exception("not odd");
    }

    public int circularly_sorted(int[] array) {
        if (array.length < 1) {
            return -1;
        }

        int num_descending = 0;
        int index = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]){
                num_descending = num_descending + 1;
                index = i;
            }

            if((num_descending > 1)|| (num_descending > 0 && array[index-1] < array[array.length-1])){
                return -1;
            }
        }

        return index;
    }

    //Find the longest sorted substring of an input array. Return an array of size 2: the starting and ending indices of said substring.
    public int[] longest_sorted(int[] array){
        int[] descending = new int[array.length];
        int[] ascending = new int[array.length];
        for (int i = 1; i < array.length; i++) {

            if(array[i] > array[i-1]){
                ascending[i] = 1;

            }
            else if(array[i] < array[i-1]){
                descending[i] = 1;

            }
            else{
                ascending[i] = 1;
                descending[i] = 1;
            }
        }

        int max_inrow1 = 0;
        int max_index1 = 0;
        int temp_inrow1 = 0;
        for (int i = 0; i < array.length; i++) {
            if(ascending[i] == 1){
                temp_inrow1++;
            }
            if(ascending[i] == 0){
                if (temp_inrow1 > max_inrow1){
                    max_inrow1 = temp_inrow1;
                    max_index1 = i-1;
                    temp_inrow1 = 0;
                }
            }
        }

        if (temp_inrow1 > max_inrow1){
            max_inrow1 = temp_inrow1;
            max_index1 = array.length-1;
        }

        int max_inrow2 = 0;
        int max_index2 = 0;
        int temp_inrow2 = 0;
        for (int i = 0; i < array.length; i++) {
            if(descending[i] == 1){
                temp_inrow2++;
            }
            if(descending[i] == 0){
                if (temp_inrow2 > max_inrow2){
                    max_inrow2 = temp_inrow2;
                    max_index2 = i-1;
                    temp_inrow2 = 0;
                }
            }
        }
        if (temp_inrow2 > max_inrow2){
            max_inrow2 = temp_inrow2;
            max_index2 = array.length-1;
        }

        if (max_inrow1 >= max_inrow2){
            return new int[]{array[max_index1 - (max_inrow1)], array[max_index1]};
        }
        else{
            return new int[]{array[max_index2 - (max_inrow2)], array[max_index2]};
        }
    }

    public int[] longest_sorted2(int[] array){
        int start = 0;
        int end = 0;

        int curr_start = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] >= array[i-1]){
                if (i - curr_start > end - start){
                    start = curr_start;
                    end = i;
                }
            }
            else{
                curr_start = i;
            }
        }
        return new int[]{array[start], array[end]};
    }
}
