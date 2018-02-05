import java.util.*;

public class Problems {

    public static class Node {
        int val;
        Node next;

        Node(int d) {
            this.val = d;
            next = null;
        }
    }

    public static List<Integer> removeKDigits(int[] A, int k) {
        List<Integer> l = new LinkedList<>();

        int index = 0;
        l.add(A[0]);
        while (k > 0 && index < A.length-1){

            if (l.size() > 0 && l.get(l.size() - 1) > A[index + 1]){
                l.remove(l.size()-1);
               // System.out.println(l);
                k--;
            }

            else{

                l.add(A[index+1]);
                index++;
            }

        }

        if (l.size() < A.length - k){
            for (int i = index+1; i < A.length; i++) {
                l.add(A[i]);
            }
        }

        while (l.size() > A.length - k){
            l.remove(l.size()-1);
        }

        //System.out.println(l);
        return l;
    }

    public static boolean isPalindrome(Node n) {
        // find middle of linked list
        Node fast = n;
        Node slow = n;
        Node second_start = null;
        if (fast == null || fast.next == null){
            //either none or one element in the linked list
            return true;
        }
        else if (fast.next.next == null){
            //only two elements in the linked list
            if (fast.val == fast.next.val){
                return true;
            }
            else{
                return false;
            }
        }
        // more than two elements in the linked list
        while (true){
            fast = fast.next.next;
            if (fast == null){
                //even number of elements in the linked list
                second_start = slow.next;
                break;
            }
            else if (fast.next == null){
                //odd number of elements in the linked list
                second_start = slow.next.next;
                break;
            }

            slow = slow.next;
        }
        // second_start is the beginning node of the second half of the linked list
        // now, reverse the second half of the linked list
        Node prev = null;
        Node current = second_start;
        Node next;
        while(current !=null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;

        }
        // if two linked lists are equal than return true, else return false
        while (prev != null){
            if (prev.val != n.val){
                return false;
            }
            prev = prev.next;
            n = n.next;
        }
        return true;
    }


    public static String infixToPostfix(String s) {
        Stack l = new Stack();
        String output = "";
        int numP = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/'){
                if (!l.isEmpty() && numP == 0){
                    output += l.pop();
                }
                l.push(c);
            }
            else if(c == ')'){
                numP -= 0;
                output+=l.pop();
                output+= ' ';
            }
            else if (c == '('){
                numP += 1;
            }
            else{

                if (c != ' '){
                    output+= c;
                    output+= ' ';
                }

            }

        }

        if(!l.isEmpty()){
            output += l.pop();
            output += ' ';
        }

        return output.substring(0, output.length() - 1);
    }

}
