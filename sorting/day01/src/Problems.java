import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Problems {

    private static PriorityQueue<Integer> minPQ() {
        return new PriorityQueue<>(11);
    }

    private static PriorityQueue<Integer> maxPQ() {
        return new PriorityQueue<>(11, Collections.reverseOrder());
    }

    private static double getMedian(List<Integer> A) {
        double median = (double) A.get(A.size() / 2);
        if (A.size() % 2 == 0)
            median = (median + A.get(A.size() / 2 - 1)) / 2.0;
        return median;
    }

    // Runtime of this algorithm is O(N^2). Sad! We provide it here for testing purposes
    // O(N^2) because of the sorting involved. seen is a sorted array with each element of out being
    // the median from seen with one added input
    public static double[] runningMedianReallySlow(int[] A) {
        double[] out = new double[A.length];
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            int j = 0;
            while (j < seen.size() && seen.get(j) < A[i])
                j++;
            seen.add(j, A[i]);
            out[i] = getMedian(seen);
        }
        return out;
    }


    /**
     *
     * @param inputStream an input stream of integers
     * @return the median of the stream, after each element has been added
     * QUESTION: what is the run-time? O(NlogN)
     */
    public static double[] runningMedian(int[] inputStream) {
        double[] runningMedian = new double[inputStream.length];
        //keep two PQs (one max and one min) with the median at the top of both. when a new value is added depending on whether the size is odd or even
        //calculate the median and add it to the runningMedian array
        PriorityQueue<Integer> min = minPQ();
        PriorityQueue<Integer> max = maxPQ();

        for (int i = 0; i < inputStream.length; i++) {
            if (i == 0 || inputStream[i] < max.peek()){
                max.offer(inputStream[i]);
            }
            else{
                min.offer(inputStream[i]);
            }

            //balance the two PQs
            while (max.size() > min.size()+1){
                min.offer(max.poll());
            }

            while (min.size() > max.size()+1){
                max.offer(min.poll());
            }

            //calculate the median

            if (min.size() == max.size()){
                runningMedian[i] = (min.peek() + max.peek())*0.5;
            }
            else if (min.size() > max.size()){
                runningMedian[i] = min.peek();
            }
            else if (max.size() > min.size()){
                runningMedian[i] = max.peek();
            }
        }

        return runningMedian;
    }

}