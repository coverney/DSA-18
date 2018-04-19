import java.util.Arrays;
import java.util.PriorityQueue;

public class BarnRepair {
    public static class Empty implements Comparable{
        private int length;

        public Empty(int len){
            this.length = len;
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Empty))
                return -1;
            return this.length - ((Empty) o).length;
        }
    }

    public static int solve(int M, int[] occupied) {
        // greedy choice: smallest gap (empty stalls that it would cover)

        // first sort the occupied array
        Arrays.sort(occupied);

        //maintain a PQ so you know which boards to get (compare the length of the empty stall section)
        PriorityQueue<Empty> queue = new PriorityQueue<Empty>();

        int num_blocked = occupied.length;
        int num_boards = 1; //cover the first cow

        // go through the list of occupied stalls and create Empty objects to represent empty stalls
        // add Empty objects to PQ
        for (int i = 0; i < occupied.length-1; i++) {
            if (occupied[i+1] > occupied[i] + 1){
                // this means there is a gap in cows
                queue.offer(new Empty(occupied[i+1]-occupied[i]-1));
                //there are gaps so introduce new board to minimize blocked empty stalls
                num_boards++;
            }
        }

        //if num_boards is greater than allowed, then must use PQ to add some blocked empty stalls
        //to get to M number of boards
        while (num_boards > M){
            Empty extra = queue.remove();
            num_boards--;
            num_blocked += extra.length;
        }
        return num_blocked;
    }
}