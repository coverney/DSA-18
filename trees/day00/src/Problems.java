import java.util.*;

public class Problems {

    public static BinarySearchTree<Integer> minimalHeight(List<Integer> values) {
        // sort the values and then put them into a BST
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Collections.sort(values);
        add(bst, values, 0, values.size()-1);
        return bst;
    }

    private static void add(BinarySearchTree<Integer> bst, List<Integer>lst, int lo, int hi){
        if(lo > hi){
            return;
        }
        if (lo == hi){
            bst.add(lst.get(lo));
            return;
        }
        //the middle of the list should be the root of the BST
        int mid = (lo + hi)/2;
        bst.add(lst.get(mid));
        add(bst, lst, 0, mid);
        add(bst, lst, mid+1, hi);

    }

    public static boolean isIsomorphic(TreeNode n1, TreeNode n2) {
        // TODO
        return false;
    }
}
