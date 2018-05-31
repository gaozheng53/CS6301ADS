/**
 * <h1>Fall 2017 Short Project 4-1</h1>
 * <p>
 * Write a recursive function to convert a doubly-linked list, in sorted order,
 * into a height-balanced binary search tree, if we interpreted prev as left,
 * and next as right.  Assume that lists are implemented with dummy headers.
 * These are member functions of the list class, and do the work by rearranging
 * references (pointers), without allocating additional space for elements.
 * Write another function for the inverse problem: BST to sorted list.
 * Signatures:
 * <p>
 * // Precondition: list is sorted
 * void sortedListToBST() { ... }
 * <p>
 * // Precondition: data is arranged as a binary search tree
 * //	using prev for left, and next for right
 * void BSTtoSortedList() { ... }
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */
package cs6301.g16;

import java.util.LinkedList;
import java.util.Queue;

public class BSTreeLinkedList<T extends Comparable> {
    /**
     * Class for doubly linked list node and BST node
     *
     * @param <T>
     */
    static class Node<T extends Comparable> {
        T value;
        Node<T> prev = null;
        Node<T> next = null;

        Node(T value) {
            this.value = value;
        }

        /**
         * Set next node
         *
         * @param next Next Node
         */
        public void setNext(Node<T> next) {
            this.next = next;
            if (next != null)
                next.prev = this;
        }

        /**
         * Set previous node
         *
         * @param prev Previous Node
         */
        public void setPrev(Node<T> prev) {
            this.prev = prev;
            if (prev != null)
                prev.next = this;
        }

        /**
         * Detach a node from its prev and next
         * But keep the reference
         */
        public void detach() {
            prev = null;
            next = null;
        }

        @Override
        public String toString() {
            if (value != null)
                return value.toString();
            else
                return null;
        }
    }

    Node<T> head; // dummy header
    Node<T> tail; // dummy tail
    Node<T> root; // tree root
    int size; // number of elements

    /**
     * Constructor to create empty list
     */
    private BSTreeLinkedList() {
        size = 0;
        head = new Node<>(null); // dummy header
        tail = new Node<>(null); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Constructor with sorted array
     *
     * @param values sorted array
     */
    BSTreeLinkedList(T[] values) {
        this();
        if (values != null) {
            size = values.length;
            for (int i = 0; i < values.length; i++) {
                Node node = new Node(values[i]);
                Node lastNode = tail.prev;
                lastNode.setNext(node);
                tail.setPrev(node);
            }
        }
    }

    /**
     * Helper function to print current structure
     */
    void print() {
        if (root == null) {
            // print list
            Node node = head.next;
            System.out.printf("Linked List - [%d]:", size);
            while (node != tail) {
                System.out.print(" " + node);
                node = node.next;
            }
            System.out.println();
        } else {
            // print tree
            Queue<Node<T>> bfsQueue = new LinkedList<>();
            bfsQueue.add(root);
            int numOfLevel = (int) Math.ceil(Math.log(size + 1) / Math.log(2));
            System.out.printf("[%d] BST with %d level(s):\n", size, numOfLevel);
            int nextLevelId = 0;
            int nextLevelNodeCount = 2;

            int fullSize = (int)Math.pow(2, numOfLevel) -1;

            String leadingSpace = ""; // leading space for each level of tree
            for (int i = 0; i < Math.pow(2, numOfLevel - 1); i++)
                leadingSpace += " ";
            //space between nodes in same level
            String space = (leadingSpace + leadingSpace).substring(0, leadingSpace.length() * 2 - 1);

            System.out.print(leadingSpace);
            for (int i = 0; !bfsQueue.isEmpty(); i++) {
                Node node = bfsQueue.remove();
                if (node!= null) {
                    bfsQueue.add(node.prev);
                    bfsQueue.add(node.next);
                }
                //print node
                System.out.print(node==null?" ":node.toString());
                //print inter-node space or new level leading
                if (i == fullSize - 1) break;
                else if (i == nextLevelId) {
                    nextLevelId += nextLevelNodeCount;
                    nextLevelNodeCount *= 2;
                    leadingSpace = leadingSpace.substring(0, leadingSpace.length() / 2);
                    space = (leadingSpace + leadingSpace).substring(0, leadingSpace.length() * 2 - 1);
                    System.out.print("\n" + leadingSpace);
                } else
                    System.out.print(space);
            }
            System.out.println();
        }
    }

    /**
     * Signature required for converting linked list to BST
     * Precondition: list is sorted
     */
    void sortedListToBST() {
        if (size <= 3) {
            // if size<=3 set root node directly
            switch (size) {
                case 1:
                    root = head.next;
                    root.detach();
                    break;
                case 2:
                    root = head.next.next;
                    root.next = null;
                    root.prev.detach();
                    break;
                case 3:
                    root = head.next.next;
                    root.prev.detach();
                    root.next.detach();
                    break;
                default:
                    break;
            }
        } else {
            // init setting of 2 sub-lists
            int leftSize = size / 2;
            int rightSize = size - leftSize - 1;
            BSTreeLinkedList<T> leftList = new BSTreeLinkedList<>();
            BSTreeLinkedList<T> rightList = new BSTreeLinkedList<>();
            leftList.size = leftSize;
            rightList.size = rightSize;

            // find root
            root = head.next;
            for (int i = 0; i < leftSize; i++) {
                root = root.next;
            }

            // construct 2 sub-lists
            leftList.head.setNext(head.next);
            leftList.tail.setPrev(root.prev);

            rightList.head.setNext(root.next);
            rightList.tail.setPrev(tail.prev);

            // covert 2 sub-lists recursively
            leftList.sortedListToBST();
            rightList.sortedListToBST();
            // set children
            root.prev = leftList.root;
            root.next = rightList.root;

            // reset linked list to empty
            head.setNext(tail);
        }
    }

    /**
     * Helper function recursively perform in-order traversal on the tree to reconstruct linked list
     */
    private Node[] dfsVisit(Node node) {
        Node first = node;
        Node last = node;
        if (node.prev != null) {
            Node[] leftRes = dfsVisit(node.prev);
            first = leftRes[0];
            Node leftLast = leftRes[1];
            leftLast.next = node;
            node.prev = leftLast;
        }

        if (node.next != null) {
            Node[] rightRes = dfsVisit(node.next);
            last = rightRes[1];
            Node rightFirst = rightRes[0];
            node.next = rightFirst;
            rightFirst.prev = node;
        }

        Node[] res = {first, last};
        return res;
    }

    /**
     * Signature required for converting BST to linked list
     * Precondition: data is arranged as a binary search tree
     * using prev for left, and next for right
     */
    void BSTtoSortedList() {
        if (root != null) {
            Queue<Node> dfsQueue = new LinkedList<>();
            Node[] res = dfsVisit(root);
            head.setNext(res[0]);
            tail.setPrev(res[1]);
            // reset tree
            root = null;
        }
    }

    /**
     * Main function for testing
     * @param args
     */
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        System.out.println("---- Input List:\n");
        BSTreeLinkedList treeList = new BSTreeLinkedList(array);
        treeList.print();

        System.out.println("\n---- 1. Convert to BST:\n");
        treeList.sortedListToBST();
        treeList.print();

        System.out.println("\n---- 2. Convert to Sorted List:\n");
        treeList.BSTtoSortedList();
        treeList.print();

        System.out.println("\n---- 3. Convert to BST again:\n");
        treeList.sortedListToBST();
        treeList.print();
    }
}
