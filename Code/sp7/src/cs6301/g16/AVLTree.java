/**
 * <h1>Fall 2017 Short Project 7 - 2</h1>
 * <p>
 * Extend BST to AVL trees (AVLTree).
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-20
 */
package cs6301.g16;

import java.util.Iterator;
import java.util.Scanner;


public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        static Entry NIL = new Entry();
        int height;

        Entry() {
            super();
            height = 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Entry<T> getNIL() {
            return (Entry<T>) NIL;
        }

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    private boolean isBalance = true;

    AVLTree() {
        super();
    }

    @Override
    public BST.Entry<T> newEntry(T x) {
        Entry<T> t = new Entry<T>();
        if (x == null)
            return t.getNIL();
        return new Entry<>(x, t.getNIL(), t.getNIL());
    }

    /**
     * Override BST.add() to add a element and keep balanced.
     *
     * @param x Element to be added.
     * @return Ture if add success, false if the tree already has this element.
     */
    @Override
    public boolean add(T x) {
        if (root.element == null) {  //First element
            root.nil=true;
            return super.add(x);
        }
        boolean flag = super.add(x);
        if (!flag) {//x is already in the tree
            return false;
        } else {
            reBalance();   // If add successfully,rebalance all node through the path from new node to root.
        }
        return true;
    }

    /**
     * Override BST.remove() and keep the whole tree balanced.
     *
     * @param x Element to be deleted.
     * @return removed node's element.
     */
    @Override
    public T remove(T x) {
        T t = super.remove(x);
        if (t == null)
            return null;  //No such node
        Entry<T> removeElement = (Entry<T>) stack.pop();
        updateHeightDownEntry(removeElement);
        reBalance();
        return t;

    }

    /**
     * Calculate the node's height.
     *
     * @param node The node to be calculated.
     * @return the node's height.
     */
    private int updateHeight(Entry<T> node) {
        if (node.left().isNil() && node.right().isNil())
            node.height = 0;
        else if (node.left().isNil())
            node.height = 1 + ((Entry<T>) (node.right())).height;
        else if (node.right().isNil())
            node.height = 1 + ((Entry<T>) (node.left())).height;
        else
            node.height = 1 + Math.max(((Entry<T>) (node.right())).height, ((Entry<T>) (node.left())).height);
        return node.height;
    }

    /**
     * Calculate the node left and right subtree's difference.
     *
     * @param node The node to be calculated.
     * @return The height difference of the subtree of the node.
     */
    private int balanceFactor(Entry<T> node) {
        if (node.right().isNil())
            return -node.height;
        else if (node.left().isNil())
            return node.height;
        else
            return ((Entry<T>) (node.right())).height - ((Entry<T>) (node.left())).height;
    }

    /**
     * Keep balance along the trip from current node to root
     */
    private void reBalance() {
        while (!stack.isEmpty()) {
            Entry<T> A = (Entry<T>) stack.pop();   //Current element
            Entry<T> AParent = (Entry<T>) stack.peek();
            if (A.height == updateHeight(A))  //No update, do not need to continue update higher nodes.
                return;
            else
                updateHeight(A);
            switch (balanceFactor(A)) {
                case -2:  //Left is heavier
                    if (balanceFactor((Entry<T>) A.left()) <= 0) {
                        balanceLL(A, AParent);
                    } else {
                        balanceLR(A, AParent);
                    }
                    break;
                case 2:   //Right is heavier
                    if (balanceFactor((Entry<T>) A.right()) >= 0) {
                        balanceRR(A, AParent);
                    } else {
                        balanceRL(A, AParent);
                    }
            }
        }
    }

    /**
     * LL case
     *
     * @param P      Violated balance node.
     * @param Parent the parent of P.
     */
    private void balanceLL(BST.Entry<T> P, BST.Entry<T> Parent) {
        super.rotateRight(P, Parent);
        BST.Entry<T> newRoot;
        newRoot = P.right();
        updateHeightDownEntry((Entry<T>) newRoot);
    }

    /**
     * LR case
     *
     * @param P      Violated balance node.
     * @param Parent the parent of P.
     */
    private void balanceLR(BST.Entry<T> P, BST.Entry<T> Parent) {
        super.rotateLR(P, Parent);
        BST.Entry<T> newRoot = P.left().right();
        updateHeightDownEntry((Entry<T>) newRoot);
    }

    /**
     * RL case
     *
     * @param P      Violated balance node.
     * @param Parent the parent of P.
     */
    private void balanceRL(BST.Entry<T> P, BST.Entry<T> Parent) {
        super.rotateRL(P, Parent);
        BST.Entry<T> newRoot = P.right().left();
        updateHeightDownEntry((Entry<T>) newRoot);
    }

    /**
     * RR case
     *
     * @param P      Violated balance node.
     * @param Parent the parent of P.
     */
    private void balanceRR(BST.Entry<T> P, BST.Entry<T> Parent) {
        super.rotateLeft(P, Parent);
        BST.Entry<T> newRoot = P.right();
        updateHeightDownEntry((Entry<T>) newRoot);
    }

    /**
     * In-order traverse the whole tree to check balance.
     *
     * @param node The start root node to traverse.
     */
    private void inOrder(Entry<T> node) {
        if (node.element != null) {
            inOrder((Entry<T>) node.left());
            if (Math.abs(balanceFactor(node)) > 1)
                isBalance = false;
            inOrder((Entry<T>) node.right());
        }
    }

    /**
     * Call in-order traverse from root and output balance condition.
     */
    public void inOrderEntry() {
        inOrder((Entry<T>) root);
        System.out.println();
        System.out.println("Is a balance tree? " + isBalance);
    }


    /**
     * Call traversalDown() start from newRoot.
     * @param newRoot The start node to traverse down.
     */
    public void updateHeightDownEntry(Entry<T> newRoot) {
        newRoot.height = traversalDown(newRoot);
    }

    /**
     * Traverse from node and set all children's height of node.
     * @param node The start node to traverse down.
     * @return
     */
    private int traversalDown(BST.Entry<T> node) {
        if (!node.isNil()) {
            int l = traversalDown(node.left());
            Entry<T> nodeLeft = (Entry<T>) node.left();
            nodeLeft.height = l;
            int r = traversalDown(node.right());
            Entry<T> nodeRight = (Entry<T>) node.right();
            nodeRight.height = r;
            return 1 + Math.max(l, r);
        } else {
            return 0;
        }
    }

    /**
     * Main test function.
     */
    public static void main(String[] args) {
        AVLTree<Integer> t = new AVLTree<>();
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
        Iterator<Integer> itr = t.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        t.inOrderEntry();
    }
}


