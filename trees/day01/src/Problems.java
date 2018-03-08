import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problems {

    //O(N)!!!
    //counting sort without the need to turn lists into number because actively create sum while iterating through the output array of counting sort
    public static int leastSum(int[] A) {
        // make a HashMap and count how many of each char from 0 to 9 there is in the list
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < A.length; i++) {
            int val = A[i];
            if (map.containsKey(val)) {
                map.put(val, map.get(val) + 1);
            } else {
                map.put(val, 1);
            }
        }
        //go through the chars 0 to 9 and find their values in the Hashmap and add them to the two lists respectively.
        //the pointer variable makes sure that items (in ascending order) get added to lists in an alternating way
        int[] reference = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> lst1 = new ArrayList<Integer>();
        List<Integer> lst2 = new ArrayList<Integer>();
        int pointer = 1;
        for (int i = 0; i < reference.length; i++) {
            if (map.containsKey(reference[i])) {
                //that particular number is in the map
                if (pointer == 1) {
                    for (int j = 0; j < map.get(reference[i]); j++) {
                        if (j % 2 == 0) {
                            lst1.add(reference[i]);
                        } else {
                            lst2.add(reference[i]);
                        }
                    }
                    pointer = 2;
                } else {
                    for (int j = 0; j < map.get(reference[i]); j++) {
                        if (j % 2 == 0) {
                            lst2.add(reference[i]);
                        } else {
                            lst1.add(reference[i]);
                        }
                    }
                    pointer = 1;
                }
            }
        }
        //convert the two lists into numbers and return their sum
        int num1 = 0;
        for (Integer i : lst1) { // assuming list is of type List<Integer>
            num1 = 10 * num1 + i;
        }
        int num2 = 0;
        for (Integer i : lst2) { // assuming list is of type List<Integer>
            num2 = 10 * num2 + i;
        }
        return num1 + num2;
    }
}
