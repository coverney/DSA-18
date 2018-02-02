package your_code;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * An implementation of a priority Queue
 */
public class MyPriorityQueue {

    private MyQueue<Integer> queue;

    public MyPriorityQueue(){
        queue = new MyQueue<Integer>();
    }

    public void enqueue(int item) {
        if (queue.isEmpty()){
            queue.enqueue(item);
        }

        else{
            MyQueue<Integer> temp = new MyQueue<Integer>();
            int placed = 0;
            while(!queue.isEmpty()){
                int obj = queue.dequeue();
                if (item > obj && placed == 0){
                    temp.enqueue(item);
                    placed = 1;
                }
                temp.enqueue(obj);
            }
            queue = temp;
        }

    }

    /**
     * Return and remove the largest item on the queue.
     */
    public int dequeueMax() {
        return (int) queue.dequeue();
    }

}