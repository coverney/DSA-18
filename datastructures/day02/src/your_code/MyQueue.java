package your_code;

import ADTs.QueueADT;

import java.util.LinkedList;

/**
 * An implementation of the Queue interface.
 */
public class MyQueue<T> implements QueueADT<T> {

    private LinkedList<Integer> ll;

    public MyQueue() {
        ll = new LinkedList<>();
    }

    @Override
    public void enqueue(int item) {
        ll.add(item);
    }

    @Override
    public T dequeue() {
        return (T) ll.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public T front() {
        return (T) ll.getFirst();
    }
}