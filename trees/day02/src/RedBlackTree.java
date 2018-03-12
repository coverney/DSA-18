import java.util.NoSuchElementException;


public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private boolean isRed(TreeNode x) {
        return x != null && x.color == RED;
    }

    private boolean isBlack(TreeNode x) {
        return x != null && x.color == BLACK;
    }

    // ====================================
    //            Insertion Code
    // ====================================


    public boolean add(T key) {
        super.add(key);
        root.color = BLACK;
        return true;
    }


    // make a left-leaning link lean to the right
    TreeNode<T> rotateRight(TreeNode<T> h) {
        TreeNode<T> x = h.leftChild;
        TreeNode<T> b = x.rightChild;
        x.rightChild = h;
        h.leftChild = b;
        x.color = x.rightChild.color;
        x.rightChild.color = true;
        return x;
    }

    // make a right-leaning link lean to the left
    TreeNode<T> rotateLeft(TreeNode<T> h) {
        TreeNode<T> x = h.rightChild;
        TreeNode<T> b = x.leftChild;
        x.leftChild = h;
        h.rightChild = b;
        x.color = x.leftChild.color;
        x.leftChild.color = true;
        return x;
    }

    // flip the colors of a TreeNode and its two children
    TreeNode<T> flipColors(TreeNode<T> h) {
        h.color = !h.color;
        h.leftChild.color = !h.leftChild.color;
        h.rightChild.color = !h.rightChild.color;
        return h;
    }


    /**
     * fix three cases:
     * 1. h.right is red
     * 2. h.left is red, and h.left.left is red
     * 2. h.left and h.right are red
     * return balanced node
     */
    private TreeNode<T> balance(TreeNode<T> h) {
        if (h.rightChild != null && h.rightChild.color) {
            if (h.leftChild == null || !h.leftChild.color) {
                h = rotateLeft(h);
            }
        }

        if (h.leftChild != null && h.leftChild.color && h.leftChild.leftChild != null && h.leftChild.leftChild.color) {
            h = rotateRight(h);
        }
        if (h.leftChild != null && h.leftChild.color && h.rightChild != null && h.rightChild.color) {
            h = flipColors(h);
        }
        return h;
    }


    /**
     * Recursively insert a new node into the BST
     * Runtime: O(logN), O(logN) to find insertion point and maximum two rotations
     */
    @Override
    TreeNode<T> insert(TreeNode<T> h, T key) {
        if (h == null) {
            return new TreeNode<T>(key, true);
        }
        if (h.key.compareTo(key) <= 0) {

            h.rightChild = insert(h.rightChild, key);
        } else if (h.key.compareTo(key) > 0) {
            h.leftChild = insert(h.leftChild, key);
        }

        h = balance(h);
        return h;
    }


    // ====================================
    //            Deletion Code
    // ====================================


    /**
     * Removes the specified key from the tree
     * (if the key is in this tree).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean delete(T key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return false;

        // if both children of root are black, set root to red
        if (!isRed(root.leftChild) && !isRed(root.rightChild))
            root.color = RED;

        root = delete(root, key);
        size--;
        if (!isEmpty()) root.color = BLACK;
        return true;
    }


    // the smallest key in subtree rooted at x; null if no such key
    private TreeNode<T> min(TreeNode<T> x) {
        if (x.leftChild == null) return x;
        else return min(x.leftChild);
    }

    // delete the key-value pair with the minimum key rooted at h
    TreeNode<T> deleteMin(TreeNode<T> h) {
        // OPTIONAL TODO: write this function and use it in delete(h, key)
        return h;
    }

    // delete the key-value pair with the given key rooted at h
    TreeNode<T> delete(TreeNode<T> h, T key) {
        // OPTIONAL TODO
        return h;
    }

    // ====================================
    //          LLRB Verification
    // ====================================

    public boolean is23() {
        return is23(root);
    }

    // return true if this LLRB is a valid 2-3 tree
    private boolean is23(TreeNode<T> n) {
        // checks for the cases in the LLRB tree that are checked for to maintain it
        if (n == null) return true;
        if (isRed(n.rightChild)) return false;
        if (isRed(n.leftChild) && isRed(n.leftChild.leftChild)) return false;
        // checks the cases and then recursively runs down the tree
        return is23(n.rightChild) && is23(n.leftChild);
    }

    public boolean isBalanced() {
        return isBalanced(root) != -1;
    }

    // return -1 if the tree is not balanced. Otherwise, return the black-height of the tree
    // QUESTION: THIS METHOD JUST CHECKS IF THE TREE WORKS AS A LLRB TREE RIGHT? YES
    private int isBalanced(TreeNode<T> n) {
        if (n == null) return 0;
        //recursive call on down the tree since determining height starts from bottom up
        int lBalanced = isBalanced(n.leftChild);
        int rBalanced = isBalanced(n.rightChild);
        // if left or right child is unbalanced, then the whole tree is unbalanced
        if (lBalanced == -1 || rBalanced == -1) return -1;
        // count the number of left and right black children
        if (isBlack(n.leftChild)) lBalanced++;
        if (isBlack(n.rightChild)) rBalanced++;
        // if the two sides don't have the same black height then it is unbalanced
        if (lBalanced != rBalanced) return -1;
        return lBalanced;
    }

}