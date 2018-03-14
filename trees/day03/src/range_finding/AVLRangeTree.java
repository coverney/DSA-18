package range_finding;

import java.util.LinkedList;
import java.util.List;

public class AVLRangeTree extends BinarySearchTree<Integer> {

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    RangeNode<Integer> delete(RangeNode<Integer> n, Integer key) {
        n = super.delete(n, key);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            n.num = numChildren(n) + 1;
            return balance(n);
        }
        return null;
    }

    /**
     * Insert a key into the tree rooted at the given node.
     */
    @Override
    RangeNode<Integer> insert(RangeNode<Integer> n, Integer key) {
        n = super.insert(n, key);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            n.num = numChildren(n) + 1;
            return balance(n);
        }
        return null;
    }

    /**
     * Delete the minimum descendant of the given node.
     */
    @Override
    RangeNode<Integer> deleteMin(RangeNode<Integer> n) {
        n = super.deleteMin(n);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            return balance(n);
        }
        return null;
    }

    // Return the height of the given node. Return -1 if null.
    private int height(RangeNode x) {
        if (x == null) return -1;
        return x.height;
    }

    public int height() {
        return Math.max(height(root), 0);
    }

    // Restores the AVL tree property of the subtree.
    RangeNode<Integer> balance(RangeNode<Integer> x) {
        if (balanceFactor(x) > 1) {
            if (balanceFactor(x.rightChild) < 0) {
                x.rightChild = rotateRight(x.rightChild);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) < -1) {
            if (balanceFactor(x.leftChild) > 0) {
                x.leftChild = rotateLeft(x.leftChild);
            }
            x = rotateRight(x);
        }
        return x;
    }

    // Return all keys that are between [lo, hi] (inclusive).
    // L = hi - lo
    // Runtime = O(log(N) + L)

    public List<Integer> rangeIndex(int lo, int hi) {
        List<Integer> l = new LinkedList<>();
        traversal(root, l, lo, hi);
        return l;
    }

    // return the number of keys between [lo, hi], inclusive
    // Runtime = O(logN))
    public int rangeCount(int lo, int hi) {
        int diff = rank(root, hi) - rank(root, lo - 1);
        if (diff < 0) {
            diff = 0;
        }
        return diff;
    }

    private void traversal(RangeNode<Integer> node, List<Integer> list, int lo, int hi){
        if (node != null) {
            if (node.key >= lo && node.key <= hi){
                traversal(node.leftChild, list, lo, hi);
                list.add(node.key);
                traversal(node.rightChild, list, lo, hi);
            }
            else if (node.key <= lo){
                traversal(node.rightChild, list, lo, hi);
            }
            else if (node.key >= hi){
                traversal(node.leftChild, list, lo, hi);
            }

        }
    }

    //returns the number of keys <= k
    private int rank(RangeNode<Integer> node, int k) {
        if (node != null && node.key <= k) {
            if (node.leftChild != null) {
                if (node.rightChild != null) {
                    return 1 + node.leftChild.num + rank(node.rightChild, k);
                } else {
                    return 1 + node.leftChild.num;
                }
            } else if (node.rightChild != null) {
                return 1 + rank(node.rightChild, k);
            } else {
                return 1;
            }

        } else {
            if (node != null && node.leftChild != null) {
                return rank(node.leftChild, k);
            } else {
                return 0;
            }
        }
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     */
    private int balanceFactor(RangeNode x) {
        return height(x.rightChild) - height(x.leftChild);
    }

    /**
     * Perform a right rotation on node `n`. Return the head of the rotated tree.
     */
    private RangeNode<Integer> rotateRight(RangeNode<Integer> x) {
        RangeNode<Integer> y = x.leftChild;
        x.leftChild = y.rightChild;
        y.rightChild = x;

        x.height = 1 + Math.max(height(x.leftChild), height(x.rightChild));
        y.height = 1 + Math.max(height(y.leftChild), height(y.rightChild));

        x.num = numChildren(x) + 1;
        y.num = numChildren(y) + 1;

        return y;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     */
    private RangeNode<Integer> rotateLeft(RangeNode<Integer> x) {
        RangeNode<Integer> y = x.rightChild;
        x.rightChild = y.leftChild;
        y.leftChild = x;
        x.height = 1 + Math.max(height(x.leftChild), height(x.rightChild));
        y.height = 1 + Math.max(height(y.leftChild), height(y.rightChild));

        x.num = numChildren(x) + 1;
        y.num = numChildren(y) + 1;

        return y;
    }

    private int numChildren(RangeNode<Integer> node) {
        int result = 0;
        if (node.leftChild != null) {
            result += node.leftChild.num;
        }
        if (node.rightChild != null) {
            result += node.rightChild.num;
        }
        return result;
    }
}
