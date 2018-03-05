import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    TreeNode<T> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean contains(T key) {
        return find(root, key) != null;
    }

    /**
     * Add a node to the BST. Internally calls insert to recursively find the new node's place
     */
    public boolean add(T key) {
        if (find(root, key) != null) return false;
        root = insert(root, key);
        size++;
        return true;
    }

    public void addAll(T[] keys) {
        for (T k : keys)
            add(k);
    }

    //O(N) goes through each element once
    //The order of "inorder" is: left child -> parent -> right child
    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<T>();

        if (root != null) {
            helper(result, root);
        }

        return result;
    }

    public void helper(List<T> lst, TreeNode<T> nd) {
        if (nd.leftChild != null)
            helper(lst, nd.leftChild);
        //keep going left until you can't anymore then start adding
        lst.add(nd.key);
        //then start going right
        if (nd.rightChild != null)
            helper(lst, nd.rightChild);
    }

    /**
     * Deletes a node from the BST using the following logic:
     * 1. If the node has a left child, replace it with its predecessor
     * 2. Else if it has a right child, replace it with its successor
     * 3. If it has no children, simply its parent's pointer to it
     */
    public boolean delete(T key) {
        TreeNode<T> toDelete = find(root, key);
        if (toDelete == null) {
            System.out.println("Key does not exist");
            return false;
        }
        TreeNode<T> deleted = delete(toDelete);
        if (toDelete == root) {
            root = deleted;
        }
        size--;
        return true;
    }

    // Worst case: O(N) but average O(h) or O(logN)
    private TreeNode<T> delete(TreeNode<T> n) {
        // Recursive base case
        if (n == null) return null;

        TreeNode<T> replacement;

        if (n.isLeaf())
            // Case 1: no children
            replacement = null;
        else if (n.hasRightChild() != n.hasLeftChild())
            // Case 2: one child
            replacement = (n.hasRightChild()) ? n.rightChild : n.leftChild; // replacement is the non-null child
        else {
            // Case 3: two children
            //pick predecessor or successor as replacement for n and then delete the old predecessor/successor to reorganize
            //the BST. Don't forget ot move the children of n to the children of the new n
//            replacement = findSuccessor(n);
//            delete(replacement);
            replacement = n.leftChild;
            delete(replacement);
            replacement.moveChildrenFrom(n);
        }

        // Put the replacement in its correct place, and set the parent.
        n.replaceWith(replacement);
        return replacement;
    }

    public T findPredecessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> predecessor = findPredecessor(n);
            if (predecessor != null)
                return predecessor.key;
        }
        return null;
    }

    public T findSuccessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> successor = findSuccessor(n);
            if (successor != null)
                return successor.key;
        }
        return null;
    }

    // Worst case O(N) for a one sided and sorted (in descending order) BST
    // Average O(h) or O(logN)
    private TreeNode<T> findPredecessor(TreeNode<T> n) {
        if (n.leftChild != null) {
            //return max of leftChild BST
            TreeNode<T> prede = n.leftChild;
            while (prede.rightChild != null) {
                prede = prede.rightChild;
            }
            return prede;
        }
        // else start from root and find predecessor
        TreeNode<T> prede = null;
        TreeNode<T> temp = root;
        while (temp != null) {
            if (n.key.equals(temp.key)) {
                // found our node value so break since we already know it doesn't have a left kid
                break;
            } else if (n.key.compareTo(temp.key) < 0) {
                //if n is less than key then move to left
                temp = temp.leftChild;
            } else if (n.key.compareTo(temp.key) > 0) {
                //if n is greater than key then set prede and move right
                prede = temp;
                temp = temp.rightChild;
            }
        }
        return prede;
    }

    // Worst case O(N) for a one sided and sorted (in ascending order) BST
    // Average O(h) or O(logN)
    private TreeNode<T> findSuccessor(TreeNode<T> n) {
        if (n.rightChild != null) {
            //return min of rightChild BST
            TreeNode<T> succ = n.rightChild;
            while (succ.leftChild != null) {
                succ = succ.leftChild;
            }
            return succ;
        }
        // else start from root and find successor
        TreeNode<T> succ = null;
        TreeNode<T> temp = root;
        while (temp != null) {
            if (n.key.equals(temp.key)) {
                // found our node value so break since we already know it doesn't have a right kid
                break;
            } else if (n.key.compareTo(temp.key) < 0) {
                //if n is less than key then set succ and move to left to get as close as possible to n
                succ = temp;
                temp = temp.leftChild;
            } else if (n.key.compareTo(temp.key) > 0) {
                //if n is greater than key then move right to get a value bigger
                temp = temp.rightChild;
            }
        }
        return succ;
    }

    /**
     * Returns a node with the given key in the BST, or null if it doesn't exist.
     */
    private TreeNode<T> find(TreeNode<T> currentNode, T key) {
        if (currentNode == null)
            return null;
        int cmp = key.compareTo(currentNode.key);
        if (cmp < 0)
            return find(currentNode.leftChild, key);
        else if (cmp > 0)
            return find(currentNode.rightChild, key);
        return currentNode;
    }

    /**
     * Recursively insert a new node into the BST
     */
    private TreeNode<T> insert(TreeNode<T> node, T key) {
        if (node == null) return new TreeNode<>(key);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = insert(node.leftChild, key);
            node.leftChild.parent = node;
        } else {
            node.rightChild = insert(node.rightChild, key);
            node.rightChild.parent = node;
        }
        return node;
    }
}
