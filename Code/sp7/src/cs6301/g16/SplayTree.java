/**
 * <h1>Fall 2017 Short Project 7 - 3</h1>
 * <p>
 * Splay Tree implementation extends BST.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-15
 */

package cs6301.g16;

import java.util.Comparator;

public class SplayTree<T extends Comparable<? super T>> extends BST<T> {
    SplayTree() {
        super();
    }

    void splay() {
        if (stack==null || stack.size() == 0)
            return;

        Entry<T> t = stack.pop();

        if (t != root) {
            // get parent entry
            Entry<T> p = stack.pop();
            if (p == root) {
                if (t == root.right()) {
                    // Root right child
                    rotateLeft(p,Entry.NIL);
                }
                else {
                    // Root left child
                    rotateRight(p,Entry.NIL);
                }
            }
            else {
                // get grandparent entry
                Entry<T> g_p = stack.pop();
                // grandparent's parent entry if has
                Entry<T> g_p_p = stack.pop();
                if(g_p_p == null)
                    g_p_p = Entry.NIL;

                if(p == g_p.left()){
                    //L
                    if(t == p.left()){
                        //LL
                        rotateRR(g_p,g_p_p);
                    }
                    else {
                        //LR
                        rotateLR(g_p,g_p_p);
                    }
                }
                else {
                    //R
                    if(t == p.left()){
                        //RL
                        rotateRL(g_p,g_p_p);
                    }
                    else {
                        //RR
                        rotateLL(g_p,g_p_p);
                    }
                }
            }
        }
    }

    // Trigger splay in following public method
    // Stack is maintained in BST
    @Override
    public T get(T x) {
        T result = super.get(x);
        splay();
        return result;
    }

    @Override
    public boolean add(T x) {
        boolean result = super.add(x);
        splay();
        return result;
    }

    @Override
    public T remove(T x) {
        T result = super.remove(x);
        splay();
        return result;
    }

    public T max() {
        T result = super.max();
        splay();
        return result;
    }

    @Override
    public T min() {
        T result = super.min();
        splay();
        return result;
    }

    @Override
    public boolean contains(T x) {
        boolean result = super.contains(x);
        splay();
        return result;
    }

    public static void main(String[] args) {
        SplayTree<Integer> t = new SplayTree<>();
        t.add(1); System.out.println("t.add(1)"); t.printTree();
        t.add(2); System.out.println("t.add(2)"); t.printTree();
        t.get(1); System.out.println("t.get(1)"); t.printTree();
        t.add(5); System.out.println("t.add(5)"); t.printTree();
        t.get(2); System.out.println("t.get(2)"); t.printTree();
        t.add(3); System.out.println("t.add(3)"); t.printTree();
        t.add(-1); System.out.println("t.add(-1)"); t.printTree();
        System.out.println("t.min() = "+t.min()); t.printTree();
        System.out.println("t.contains(1) = "+t.contains(1)); t.printTree();
        System.out.println("t.remove(1) = "+t.remove(1)); t.printTree();
        System.out.println("t.remove(3) = "+t.remove(1)); t.printTree();
    }

    @Override
    void printTree(Entry<T> node) {
        // Override to mark root node
        if (!node.isNil()) {
            printTree(node.left());
            if(node == root)
                System.out.print(" (" + node.element+")");
            else
                System.out.print(" " + node.element);
            printTree(node.right());
        }
    }
}
