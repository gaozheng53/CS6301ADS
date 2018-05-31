/**
 * <h1>Fall 2017 Short Project 2</h1>
 * <p>
 * P1: Given two linked lists implementing sorted sets, write functions for union, intersection, and
 * set difference of the sets.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-02
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SortedSetList {

    /**
     * Return elements common to l1 and l2, in sorted order.
     *
     * @param l1      First sorted set list.
     * @param l2      Second sorted set list
     * @param outList an empty list created by the calling
     * @param <T>     List type.
     */
    public static <T extends Comparable<? super T>> void intersect(List<T> l1, List<T> l2, List<T> outList) {
        Iterator<T> iter1 = l1.iterator();
        Iterator<T> iter2 = l2.iterator();
        if (!iter1.hasNext() || !iter2.hasNext())
            return;

        T item1 = iter1.next();
        T item2 = iter2.next();
        while (iter1.hasNext() && iter2.hasNext()) {
            if (item1.compareTo(item2) == 0) {
                outList.add(item1);
                item1 = iter1.next();
                item2 = iter2.next();
            } else if (item1.compareTo(item2) < 0)
                item1 = iter1.next();
            else
                item2 = iter2.next();
        }
    }

    /**
     * Return elements from both l1 and l2, in sorted order. This implementation do not modify
     * the original two linked list, in stead, it add each element to the new list.
     *
     * @param l1      First sorted set list.
     * @param l2      Second sorted set list
     * @param outList an empty list created by the calling
     * @param <T>     List type.
     */
    public static <T extends Comparable<? super T>> void union(List<T> l1, List<T> l2, List<T> outList) {
        Iterator<T> iter1 = l1.iterator();
        Iterator<T> iter2 = l2.iterator();
        if (!iter1.hasNext() || !iter2.hasNext())
            return;

        T item1 = null, item2 = null;
        // flag is used to determine which iterator should proceed.
        // 1: iterator 1 proceed; 2: iterator 2 proceed; Otherwise, both proceed.
        int flag = 0;
        while (true) {
            switch (flag) {
                case 1:
                    item1 = iter1.next();
                    break;
                case 2:
                    item2 = iter2.next();
                    break;
                default:
                    item1 = iter1.next();
                    item2 = iter2.next();
                    break;
            }

            if (item1.compareTo(item2) == 0) {
                outList.add(item1);
                flag = 0;
            } else if (item1.compareTo(item2) < 0) {
                outList.add(item1);
                flag = 1;
            } else {
                outList.add(item2);
                flag = 2;
            }

            // After operation on current element, if one of the iterator ends, copy the rest of
            // the other iterator to outList and return.
            if (!iter1.hasNext()) {
                while (iter2.hasNext()) {
                    item2 = iter2.next();
                    outList.add(item2);
                }
                return;
            } else if (!iter2.hasNext()) {
                while (iter1.hasNext()) {
                    item1 = iter1.next();
                    outList.add(item1);
                }
                return;
            }
        }
    }

    /**
     * Return elements in l1 but not in l2, in sorted order.
     *
     * @param l1      First sorted set list.
     * @param l2      Second sorted set list
     * @param outList an empty list created by the calling
     * @param <T>     List type.
     */
    public static <T extends Comparable<? super T>> void difference(List<T> l1, List<T> l2, List<T> outList) {
        Iterator<T> iter1 = l1.iterator();
        Iterator<T> iter2 = l2.iterator();
        if (!iter1.hasNext() || !iter2.hasNext())
            return;

        T item1 = iter1.next();
        T item2 = iter2.next();
        while (iter1.hasNext() && iter2.hasNext()) {
            if (item1.compareTo(item2) == 0) {
                // If exist in both list, ignore and proceed in both lists.
                item1 = iter1.next();
                item2 = iter2.next();
            } else if (item1.compareTo(item2) < 0) {
                // If item in List 1 is smaller than item in List 2. Add item and proceed in List 1.
                outList.add(item1);
                item1 = iter1.next();
            } else {
                // Otherwise, proceed in List 2 and check with the same item in List 1 again.
                item2 = iter2.next();
            }
        }
        while (iter1.hasNext()) {
            item1 = iter1.next();
            outList.add(item1);
        }
    }

    public static void main(String[] args) {
        int n = 60;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        List<Integer> lst1 = new ArrayList<>();
        for (int i = 0; i <= n; i += 2) {
            lst1.add(i);
        }
        System.out.println("List 1: " + lst1);
        List<Integer> lst2 = new LinkedList<>();
        for (int i = 0; i <= n * 2; i += 3) {
            lst2.add(i);
        }
        System.out.println("List 2: " + lst2);

        List<Integer> intersectList = new ArrayList<>();
        intersect(lst1, lst2, intersectList);
        System.out.println("\nIntersection(List1, List2): " + intersectList);
        intersectList = new LinkedList<>();
        intersect(lst2, lst1, intersectList);
        System.out.println("Intersection(List2, List1): " + intersectList);

        List<Integer> unionList = new ArrayList<>();
        union(lst1, lst2, unionList);
        System.out.println("\nUnion(List1, List2):: " + unionList);
        unionList = new LinkedList<>();
        union(lst2, lst1, unionList);
        System.out.println("Union(List2, List1):: " + unionList);

        List<Integer> differenceList = new ArrayList<>();
        difference(lst1, lst2, differenceList);
        System.out.println("\nDifference(List1, List2): " + differenceList);
        differenceList = new LinkedList<>();
        difference(lst2, lst1, differenceList);
        System.out.println("Difference(List2, List1): " + differenceList);
    }
}
