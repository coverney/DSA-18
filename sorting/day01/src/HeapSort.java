public class HeapSort extends SortAlgorithm {
    int size;
    int[] heap;

    private int parent(int i) {
        return (i-1) / 2;
    }

    private int leftChild(int i) {
        return 2*i + 1;
    }

    private int rightChild(int i) {
        return 2 * (i + 1);
    }

    // Check children, and swap with larger child if necessary.
    // Corrects the position of element indexed i by sinking it.
    // Use either recursion or a loop to then sink the child O(logN)
    //QUESTION: IS THERE A MORE CONDENSE WAY TO WRITE THIS? integrate the if statements by keeping track of a max
    public void sink(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        if (left < size && right < size){
            // there are two children
            if (heap[i] < heap[left] || heap[i] < heap[right]){
                int childidx = 0;
                if (heap[left] >= heap[right]){
                    childidx = left;
                }
                else{
                    childidx = right;
                }
                super.swap(heap, i, childidx);
                sink(childidx);
            }

        }
        else if (left < size){
            // has only left child
            if (heap[i] < heap[left]){
                super.swap(heap, i, left);
                sink(left);
            }
        }

        else if (right < size){
            // has only right child
            if (heap[i] < heap[right]){
                super.swap(heap, i, right);
                sink(right);
            }
        }

    }

    // Given the array, build a heap by correcting every non-leaf's position, starting from the bottom, then
    // progressing upward O(N)
    public void heapify(int[] array) {
        this.heap = array;
        this.size = array.length;

        for (int i=this.size / 2 - 1; i>=0; i--) {
            sink(i);
        }
    }

    /**
     * Best-case runtime: O(NlogN) Either way, heapify takes O(N) and removing each element takes O(NlogN)
     * Worst-case runtime: O(NlogN)
     * Average-case runtime: O(NlogN) O(N) + O(NlogN) = O(NlogN)
     *
     * Space-complexity: 0(1) doing everything in place
     */
    @Override
    public int[] sort(int[] array) {
        heapify(array);

        for (int i=size-1; i>0; i--) {
            //replace heap[0] with the last element
            super.swap(heap, 0, i);
            size--; //need to decrement size because you don't want to re-introduce the already sorted elements into the heap
            sink(0);
        }

        return heap;
    }
}
