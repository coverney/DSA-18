import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class FrequencyPrint {

    static String frequencyPrint(String s) {
        // INCREASING order of frequencies
        // go through each index of the string and add the character into a HashMap with the key as the character and the value as the counts
        // of a certain character.
        // sort the hashmap by value and then concatenate the results into a string

        //O(N) solution: make another hashmap that flips the key and value and then iterate through the new hashmap using range of nubmer of words and checking
        //if the hasmap contains that number and then adding the value (word) to the result

        HashMap<String, Integer> map = new HashMap<String, Integer>();


        String[] words1 = s.split("\\s+");
        for (int i = 0; i < words1.length; i++){
            String word = words1[i];
            if (!map.containsKey(word)){
                map.put(word, 1);
            }
            else{
                map.put(word, map.get(word) + 1);
            }

        }

        //sort through map by value
        //QUESTION: WHAT IS THE BIG O OF THAT? O(NlogN)
        Map<String, Integer> sortedMap =
        map.entrySet().stream()
                        .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));


        //put everything from the sorted map and add it to result

        String result = "";
        for (String key: sortedMap.keySet()) {
            result = String.join("", Collections.nCopies(map.get(key), key+" "))+result;
        }

        return result;
    }

}
