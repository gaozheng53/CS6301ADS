/*
 * <h1>Fall 2017 Short Project 6 - 1</h1>
 * <p>
 * Sort an array using k-way merge (normally used for external sorting): Split the array A into k
 * fragments, sort them recursively, and merge them using a priority queue (of size k). [In external
 * sorting applications, intermediate sorted sub-arrays are written to disk. The algorithm sorts A
 * by using buffers of size O(k).]
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-02
 */

package cs6301.g16;

import cs6301.g00.Timer;

import java.util.*;
import java.util.stream.Collectors;

public class KMergeSort<T extends Comparable<? super T>> {
    private static final int threshold = 100;
    private int k;

    public KMergeSort(int k) {
        this.k = k;
    }

    public List<T> sort(List<T> list) {

        if (list.size() < 10 || list.size() <= k * 10) {
            Collections.sort(list);
            return list;
        }

        List<List<T>> lists = new ArrayList<>(k);

        final int size = list.size() / k + 1;

        for (int i = 0; i < k && i * size < list.size(); i++) {
            // Split the recursively sort each segment with length size.
            lists.add(this.sort(list.stream().skip(i * size).limit(size).collect(Collectors.toList())));
        }

        KMerger<T> merger = new KMerger<>(k);

        return merger.merge(lists);
    }

    public T[] sort(T[] arr) {
        T[] arrp = arr.clone();
        return sort(arrp, arr, 0, arr.length - 1);
    }

    private T[] sort(T[] A, T[] B, int p, int r) {
        if (r - p < threshold || r - p + 1 <= k * 10) {
            T[] tmp = Arrays.copyOfRange(A, p, r+1);
            Arrays.sort(tmp);
            int i = p;
            for (T t : tmp) {
                B[i] = t;
                i++;
            }
            assert i == r + 1;
        } else {
            int step = (r - p) / k + 1;
            for (int i = p; i <= r; i += step) {
                int start = i;
                int end = i + step - 1;
                if (end > r)
                    end = r;
                sort(B, A, start, end);
            }

            KMerger<T> merger = new KMerger<>(k);

            merger.merge(A, B, p, step, r);
        }

        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Random generate array, enter size (-1 for automated 5 times random testing): ");

        int size = sc.nextInt();

        if (size == -1) {
            Random r = new Random();

            for (int i = 0; i < 5; i++)
                test(r.nextInt(10000000), r.nextInt(18) + 2);
            return;
        }

        System.out.print("Enter k value for K-way merge sort: ");

        int k = sc.nextInt();

        test(size, k);
    }

    private static void test(int size, int k) {
        List<Integer> list = new ArrayList<>(size);
        Random r = new Random();

        System.out.println();
        System.out.print("Generating random array and list with length " + size + ".");
        for (int i = 0; i < size; i++) {
            list.add(r.nextInt(size));
            if (i % (size / 10) == 0)
                System.out.print(".");
        }
        System.out.println();

        assert !isSorted(list);

        System.out.println("K-way Sort with k = " + k + ".");

        KMergeSort<Integer> sort = new KMergeSort<>(k);

        {
            System.out.println("List<Integer>:");
            Timer t = new Timer();
            t.start();
            List<Integer> sortedList = sort.sort(list);
            System.out.println(t.end());

            assert sortedList.size() == list.size();
            assert isSorted(sortedList);
        }

        {
            System.out.println("Integer[]:");
            Timer t = new Timer();
            t.start();
            Integer[] arr = list.toArray(new Integer[0]);

            sort.sort(arr);

            System.out.println(t.end());
            assert (isSorted(arr));
        }
    }

    private static <T extends Comparable<? super T>> boolean isSorted(List<T> list) {
        Iterator<T> iterator = list.iterator();
        if (!iterator.hasNext())
            return true;
        T pre = iterator.next();

        while (iterator.hasNext()) {
            T cur = iterator.next();
            if (pre.compareTo(cur) > 0)
                return false;
        }

        return true;
    }

    private static <T extends Comparable<? super T>> boolean isSorted(T[] arr) {
        for (int i = 0; i < arr.length - 2; i++)
            if (arr[i].compareTo(arr[i+1]) > 0)
                return false;
        return true;
    }
}
