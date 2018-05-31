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

import java.util.*;
import java.util.stream.Stream;

public class KMerger<T extends Comparable<? super T>> {
    private int k;

    public KMerger(int k) {
        this.k = k;
    }

    public List<T> merge(List<List<T>> kList) {
        PriorityQueue<ListWrapper<T>> queue = new PriorityQueue<>(k);

        Stream<ListWrapper<T>> kListWrapper = kList.stream().map(x -> new ListWrapper<>(x));
        List<T> m = new ArrayList<>(this.k * kList.get(0).size());

        kListWrapper.forEach(queue::offer);

        while (queue.peek() != null) {
            ListWrapper<T> next = queue.poll();
            m.add(next.next());
            if (next.isEmpty())
                continue;
            queue.offer(next);
        }

        return m;
    }

    public T[] merge(T[] A, T[] B, int p, int step, int r) {
        PriorityQueue<TWrapper<T>> queue = new PriorityQueue<>(k);

        for (int i = p; i <= r; i += step) {
            int start = i;
            int end = i + step - 1;
            if (end > r)
                end = r;
            queue.offer(new TWrapper<>(A, start, end));
        }

        int i = p;
        while (queue.peek() != null) {
            TWrapper<T> next = queue.poll();
            B[i++] = next.next();
            if (next.isEmpty())
                continue;
            queue.offer(next);
        }

        return B;
    }

    private static class ListWrapper<T extends Comparable<? super T>> implements Comparable<ListWrapper<T>> {
        List<T> l;
        T val;
        Iterator<T> iterator;

        public ListWrapper(List<T> l) {
            this.l = l;
            this.iterator = this.l.iterator();
            this.val = iterator.hasNext() ? iterator.next() : null;
        }

        public T next() {
            T ret = val;
            val = iterator.hasNext() ? iterator.next() : null;
            return ret;
        }

        public boolean isEmpty() {
            return val == null;
        }

        @Override
        public int compareTo(ListWrapper<T> o) {
            return this.val.compareTo(o.val);
        }

        @Override
        public String toString() {
            return val + ": " + l;
        }
    }

    private class TWrapper<T extends Comparable<? super T>> implements Comparable<TWrapper<T>> {
        T[] arr;
        int p, r;
        T val;
        int index;

        public TWrapper(T[] arr, int p, int r) {
            this.arr = arr;
            this.p = p;
            this.r = r;
            this.val = arr[p];
            this.index = p;
        }

        public T next() {
            if (index == -1)
                return null;
            T ret = val;
            if (index < r) {
                val = arr[index + 1];
                index++;
            } else {
                val = null;
                index = -1;
            }
            return ret;
        }

        public boolean isEmpty() {
            return val == null;
        }

        @Override
        public int compareTo(TWrapper<T> o) {
            return this.val.compareTo(o.val);
        }

    }

    public static void main(String[] args) throws Exception {
        KMerger<Integer> merger = new KMerger<>(4);

        Integer[] A = new Integer[]{1,4,7,11,2,5,8,13,3,9,19};
        Integer[] B = new Integer[20];

        merger.merge(A, B, 0, 4, 10);

        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(B));
    }
}
