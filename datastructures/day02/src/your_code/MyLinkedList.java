package your_code;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Chicken val;
        Node prev;
        Node next;

        private Node(Chicken d, Node prev, Node next) {
            this.val = d;
            this.prev = prev;
            this.next = next;
        }
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(Chicken c) {
        addLast(c);
    }

    public Chicken pop() {
        return removeLast();
    }

    public void addLast(Chicken c) {
        Node newN = new Node(c, null, null);
        if (size == 0){
            head = newN;
            tail = newN;

        }
        else{
            Node prev = tail;
            tail = newN;
            tail.prev = prev;
            prev.next = tail;

        }
        size++;
    }

    public void addFirst(Chicken c) {
        Node newN = new Node(c, null, null);
        if (size == 0){
            head = newN;
            tail = newN;
        }
        else{
            Node next = head;
            head.prev = newN;
            head = newN;
            head.next = next;

        }
        size++;
    }

    public Chicken get(int index) {
        int i = 0;
        if (size == 0 || index > size-1){
            return null;
        }
        Node temp = head;
        while (i < index ){
            if (temp == null || temp.next == null){
                return null;
            }
            temp = temp.next;
            i++;
        }
        return temp.val;
    }

    public Chicken remove(int index) {
        int i = 0;
        if (size == 0 || index > size-1){
            return null;
        }
        Node temp = head;
        while (i < index){
            if (temp == null){
                return null;
            }
            temp = temp.next;
            i++;
        }
        if (temp.next == null && temp.prev == null){
            //only element in list
            head = null;
            tail = null;
        }
        else if (temp.next == null){
            //end of list
            Node prev = temp.prev;
            tail = prev;
            prev.next = null;
            temp.prev = null;
        }
        else if (temp.prev == null){
            //beg. of list
            Node next = temp.next;
            next.prev = null;
            head = next;
            temp.next = null;
        }
        else{

            Node prev = temp.prev;
            Node next = temp.next;
            prev.next = next;
            next.prev = prev;
            temp.next = null;
            temp.prev = null;
        }
        size--;
        return temp.val;
    }

    public Chicken removeFirst() {
        if (size == 0){
            return null;
        }

        else if (head.next == null){
            Node oldH = head;
            head = null;
            tail = null;
            size --;
            return oldH.val;
        }
        else{
            Node oldH = head;
            head = head.next;
            head.prev = null;
            size --;
            return oldH.val;
        }

    }

    public Chicken removeLast() {
        if (size == 0){
            return null;
        }
        else if (tail.prev == null){
            // only element in list
            Node oldT = tail;
            head = null;
            tail = null;
            size --;
            return oldT.val;
        }
        else{
            Node oldT = tail;
            tail = tail.prev;
            tail.next = null;
            size --;
            return oldT.val;
        }
    }
}