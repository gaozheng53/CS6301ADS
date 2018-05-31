/**
 * <h1>Fall 2017 Short Project 7 - 1</h1>
 * <p>
 * Implement binary search trees (BST), with the following public methods: contains, add, remove,
 * iterator.  Concentrate on elegance of code (especially, iterator), and reusability of code in
 * extended BST classes (AVL, Red Black, and, Splay Trees).
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-15
 */

package cs6301.g16;

import java.util.*;


public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {

        /**
         * Static {@code Entry} instance of {@code NIL}.
         */
        static Entry NIL = new Entry();

        T element;
        private Entry<T> left, right;
        boolean nil;

        /**
         * Internal constructor only used to create NIL.
         */
        protected Entry() {
            this.element = null;
            this.left = null;
            this.right = null;
            this.nil = true;
        }

        /**
         * Public constructor to create an {@code Entry}. Normally, when a new node is created,
         * should call: {@code new Entry(x, null, null)}, and {@code NIL} node will automatically
         * substitute the {@code null} pointer.
         *
         * @param x     Element to be stored in current entry.
         * @param left  Left child of current entry.
         * @param right Right child of current entry.
         */
        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left == null ? getNIL() : left;
            this.right = right == null ? getNIL() : right;
            this.nil = false;
        }

        public Entry<T> left() {
            return left;
        }

        public void setLeft(Entry<T> left) {
            this.left = left;
        }

        public Entry<T> right() {
            return right;
        }

        public void setRight(Entry<T> right) {
            this.right = right;
        }


        /**
         * Return {@code NIL} entry. Need to be override by subclass to get subclass {@code NIL}
         * instance.
         *
         * @return {@code NIL} entry.
         */
        @SuppressWarnings("unchecked")
        protected Entry<T> getNIL() {
            return (Entry<T>) NIL;
        }

        /**
         * Return whether current node is {@code NIL}.
         *
         * @return {@code True} if current node is {@code NIL}, and {@code False} otherwise.
         */
        public boolean isNil() {
            return nil;
        }

        @Override
        public String toString() {
            if(isNil())
                return "Nil";
            return element.toString();
        }
    }

    protected Entry<T> root;
    protected int size;
    protected Deque<Entry<T>> stack;

    public BST() {   //Initial BST
        root = newEntry(null);
        size = 0;
    }

    /**
     * Find {@code x} in tree.
     *
     * @param x The element to find.
     *
     * @return Node where search ends.
     */
    private Entry<T> find(T x) {
        stack = new ArrayDeque<>();
        stack.push(newEntry(null));
        return find(root, x);
    }

    /**
     * Helper function used by {@code Entry<T> find(T x)} with additional parameter specifying the
     * subtree to find.
     *
     * @param t Root the the subtree to find {@code x}.
     * @param x The element to find.
     *
     * @return Node where search ends.
     */
    private Entry<T> find(Entry<T> t, T x) {

        if (t.isNil() || x.compareTo(t.element) == 0) {
            stack.push(t);
            return t;
        }
        while (true) {
            stack.push(t);

            if (x.compareTo(t.element) < 0) {
                if (t.left.isNil())
                    break;
                else
                    t = t.left;
            } else if (x.compareTo(t.element) == 0) {
                break;
            } else {
                if (t.right.isNil())
                    break;
                else
                    t = t.right;
            }
        }

        return t;
    }

    /**
     * Add x to tree. If tree contains a node with same key, replace element by x. Returns true if x
     * is a new element added to tree.
     *
     * @param x Element to be added.
     *
     * @return {@code True} if the element was successfully added to BST, {@code False} otherwise.
     */
    public boolean add(T x) {
        if (root.isNil()) {
            root = newEntry(x);
            size = 1;
            return true;
        }
        Entry<T> t = find(x);
        if (x.compareTo(t.element)==0) {
            t.element = x; // If duplicate,replace to the new one.
            return false;
        } else {
            Entry<T> newEntry = newEntry(x);
            if (x.compareTo(t.element) < 0)
                t.left = newEntry;
            else
                t.right = newEntry;
            stack.push(newEntry);
        }
        size++;
        return true;
    }

    /**
     * Remove x from tree. Return x if found, otherwise return null
     *
     * @param x Element to be deleted.
     *
     * @return The element being deleted.
     */
    public T remove(T x) {
        if (root.isNil())
            return null;
        Entry<T> t = find(x);
        if (t.element.compareTo(x)!=0)
            return null;
        T result = t.element;
        if (t.left.isNil() || t.right.isNil())   //t has 0 or 1 child
            bypass(t);
        else {   //t has 2 children
            int stackSize = stack.size();
            Entry<T> minRight = find(t.right, t.element);
            int afterStackSize = stack.size();
            t.element = minRight.element;
            bypass(minRight);
            for(int i=0;i<afterStackSize-stackSize-1;i++)
                stack.pop();
        }
        size--;
        return result;
    }

    /**
     * Whether there are element x in the tree.
     *
     * @param x The element we find.
     *
     * @return If find out,return true. Otherwise,return false.
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        return !t.isNil() && t.element.compareTo(x)==0;
    }

    /**
     * TO DO: Is there an element that is equal to x in the tree? Element in tree that is equal to x
     * is returned, null otherwise.
     */
    public T get(T x) {
        Entry<T> t = find(x);
        if (!t.isNil() && t.element.compareTo(x)==0)
            return t.element;
        return null;
    }

    /**
     * Get the maximum element of BST.
     *
     * @return The maximum element of BST.
     */
    public T max() {
        if (root.isNil())
            return null;
        Entry<T> t = root;
        stack.push(t);
        while (!t.right.isNil()) {
            t = t.right;
            stack.push(t);
        }
        return t.element;
    }

    /**
     * Get the minimum element of BST.
     *
     * @return The minimum element of BST.
     */
    public T min() {
        if (root.isNil())
            return null;
        Entry<T> t = root;
        stack.push(t);
        while (!t.left.isNil()) {
            t = t.left;
            stack.push(t);
        }
        return t.element;
    }

    //Call when t has at most one child.
    protected void bypass(Entry<T> t) {
        stack.pop();
        Entry<T> pt = stack.peek(); //Get ancestor of t.

        Entry<T> c = t.left.isNil() ? t.right : t.left;


        if (pt.isNil()) {   //pt is root.
            root = c;
        }
        else if(t.left.isNil()&&t.right.isNil()) {
            if(!pt.isNil()){
                if(pt.left == t)
                    pt.left = newEntry(null);
                else if(pt.right == t)
                    pt.right = newEntry(null);
            }
        }
        else if (pt.left == t) {
            pt.left = c;
        } else {
            pt.right = c;
        }
    }

    /**
     * Generate new {@code Entry} with element {@code x}. Need to be override by subclass to create
     * new sub-{@code Entry}.
     *
     * @param x Element in the new entry.
     *
     * @return New {@code Entry} instance.
     */
    protected Entry<T> newEntry(T x) {
        if (x == null)
            return new Entry<T>().getNIL();
        return new Entry<>(x, null, null);
    }

    /**
     * Single left rotate based on {@code Entry P}, which is the top most element of the rotation.
     * The rotation is {@code protected} so that subclass can access the function.
     *
     * @param Pivot  The top most entry of the rotation.
     * @param Parent Parent node of entry.
     */
    protected void rotateLeft(Entry<T> Pivot, Entry<T> Parent) {

        Entry<T> Q = Pivot.right;
        Pivot.right = Q.left;
        Q.left = Pivot;

        if (Parent.isNil())
            root = Q;
        else {
            if (Parent.left() == Pivot)
                Parent.setLeft(Q);
            else // if (p_t == peek().right)
                Parent.setRight(Q);
        }
    }

    protected void rotateRight(Entry<T> Pivot, Entry<T> Parent) {

        Entry<T> Q = Pivot.left;
        Pivot.left = Q.right;
        Q.right = Pivot;

        if (Parent.isNil())
            root = Q;
        else {
            if (Parent.left() == Pivot)
                Parent.setLeft(Q);
            else // if (p_t == peek().right)
                Parent.setRight(Q);
        }
    }

    protected void rotateLR(Entry<T> Pivot, Entry<T> Parent) {
        rotateLeft(Pivot.left(), Pivot);
        rotateRight(Pivot, Parent);
    }

    protected void rotateLL(Entry<T> Pivot, Entry<T> Parent) {
        Entry<T> rightTmp = Pivot.right;
        rotateLeft(Pivot, Parent);
        rotateLeft(rightTmp, Parent);
    }

    protected void rotateRL(Entry<T> Pivot, Entry<T> Parent) {
        rotateRight(Pivot.right(), Pivot);
        rotateLeft(Pivot, Parent);
    }

    protected void rotateRR(Entry<T> Pivot, Entry<T> Parent) {
        Entry<T> leftTmp = Pivot.left;
        rotateRight(Pivot, Parent);
        rotateRight(leftTmp, Parent);
    }

    /**
     * Generate iterator of the BST.
     *
     * @return {@code Iterator} of the BST.
     */
    public Iterator<T> iterator() {
        return new InOrderItr();
    }


    private class InOrderItr implements Iterator<T> {
        private final Deque<Entry<T>> stackTwo = new ArrayDeque<>();
        Entry<T> current = null;

        public InOrderItr() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stackTwo.isEmpty() || !current.isNil();
        }

        @Override
        public T next() {
            while (!current.isNil()) {
                stackTwo.push(current);
                current = current.left;
            }
            Entry<T> t = stackTwo.pop();
            current = t.right;
            return t.element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation for in-order iterator.");
        }

    }

    public static void main(String[] args) {
        BST<Integer> t = new BST<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Test 1.Test add(),remove(),toArray()");
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                break;
            }
        }
        System.out.println("Test 2.Test Iterator()");
        Iterator<Integer> itr = null;
        itr = t.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }

        System.out.println();
        System.out.println(t.isValid() ? "t is valid BST." : "t is not a valid BST.");
    }

    public Comparable[] toArray() {
    /* write code to place elements in array here */
        Comparable[] arr = new Comparable[size];
        if (!root.isNil())
            toArrayHelper(arr, 0, root);
        return arr;
    }

    //The helper function of toArray()
    private int toArrayHelper(Comparable[] arr, int i, Entry<T> node) {
        if (!node.left.isNil()) {
            i = toArrayHelper(arr, i, node.left);
        }
        arr[i++] = node.element;
        if (!node.right.isNil()) {
            i = toArrayHelper(arr, i, node.right);
        }
        return i;
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (!node.isNil()) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

    boolean isValid() {
        return isValid(root);
    }

    private boolean isValid(Entry<T> x) {
        if (x.isNil())
            return true;
        if (!x.left().isNil() && x.element.compareTo(x.left().element) < 0)
            return false;
        if (!x.right().isNil() && x.element.compareTo(x.right().element) > 0)
            return false;
        return isValid(x.left()) && isValid(x.right());
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
