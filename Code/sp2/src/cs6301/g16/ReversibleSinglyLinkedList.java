/**
 * <h1>Fall 2017 Short Project 2</h1>
 * <p>
 * P4: Write recursive and nonrecursive functions for the following tasks:
 * <ol>
 *     <li>reverse the order of elements of the SinglyLinkedList class, </li>
 *     <li>print the elements of the SinglyLinkedList class, in reverse order. </li>
 * </ol>
 * Write the code and annotate it with proper loop invariants. Running time: O(n)
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-02
 */
package cs6301.g16;

public class ReversibleSinglyLinkedList<T> extends SinglyLinkedList<T> {

    /**
     * Iteratively reverse current linked list. For each node, assign next with the previous
     * {@code Entry} and continue.
     */
    private void reverseIterative() {
        Entry<T> pre = null;
        Entry<T> cur = header.next;
        Entry<T> tmp;
        // Invariant: cur = head of remained part
        //            pre = head of currently reversed part
        // Each time point the cur.next to pre, and move both pointer forward.
        while(cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        tail = header.next;
        header.next = pre;
    }

    /**
     * Recursively reverse current linked list.
     * <ul>
     *     <li>Base case: If the next {@code Entry} is the last one, assign {@code this.header.next}
     *     to the last {@code Entry} and reverse the link between this {@code Entry} and the next.</li>
     *     <li>Inductive case: Recursively call itself with next {@code Entry}, and treat current
     *     {@code Entry} as the last one, i.e., assign tail with current entry and ending null.</li>
     * </ul>
     * @param remainHeader Remain linked list header.
     */
    private void reverseRecursive(Entry<T> remainHeader) {
        if (remainHeader.next.next == null) {
            this.header.next = remainHeader.next;
            remainHeader.next.next = remainHeader;
        } else {
            this.reverseRecursive(remainHeader.next);
            remainHeader.next.next = remainHeader;
            this.tail = remainHeader;
            this.tail.next = null;
        }
    }

    /**
     * Reversion public entry point.
     * @param recursive Call recursive reversion if {@code True} and iterative reversion otherwise.
     */
    public void reverse(boolean recursive) {
        if (recursive)
            this.reverseRecursive(this.header.next);
        else
            this.reverseIterative();
    }

    /**
     * Recursively reverse print current linked list.
     * @param remain First entry among remaining ones.
     */
    private void reversePrintListRecursive(Entry<T> remain) {
        if (remain != null) {
            this.reversePrintListRecursive(remain.next);
            System.out.print(remain.element + " ");
        }
    }

    /**
     * Iteratively reverse print current linked list.
     */
    private void reversePrintListIterative() {
        StringBuilder stringBuilder = new StringBuilder();
        this.forEach(item -> stringBuilder.insert(0, item + " "));
        System.out.println(stringBuilder.toString());

        /* Concanation way.
        StringBuilder s = new StringBuilder();
        for (T item: this)
            s.insert(0, item + " ");
        System.out.println(s);
        */
    }

    /**
     * Reverse print list public entry point.
     * @param recursive Call recursive reverse print if {@code True} and iterative one otherwise.
     */
    public void reversePrintList(boolean recursive) {
        System.out.print("\n(Reverse order)" + this.size + ": ");
        if (recursive)
            this.reversePrintListRecursive(this.header.next);
        else
            this.reversePrintListIterative();
    }

    public static void main(String[] args) {
        int n = 20;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        ReversibleSinglyLinkedList<Integer> lst = new ReversibleSinglyLinkedList<>();
        for (int i = 1; i <= n; i++) {
            lst.add(i);
        }
        lst.printList();
        lst.reverse(false);
        System.out.println("\nAfter iterative reversion.");
        lst.printList();
        lst.reverse(true);
        System.out.println("\nAfter recursive reversion again.");
        lst.printList();
        lst.reversePrintList(true);
        lst.reversePrintList(false);
    }
}
