/**
 * <h1>Fall 2017 Short Project 5-3</h1>
 * <p>
 * Implement 3 versions of the Select algorithm (finding k largest elements)
 * and empirically evaluate their performance:
 * (a) Create a priority queue (max heap) of the n elements, and use remove() k times.
 * (b) Use a priority queue (min heap) of size k to keep track of the
 * k largest elements seen so far, as you iterate over the array.
 * (c) Implement the O(n) algorithm for Select discussed in class.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */
package cs6301.g16;


import java.util.*;
import java.util.stream.Stream;


public class SelectAlgorithm {
    private static final int bound = 50;   //Threshold of insertion sort

    /**
     * Create a priority queue (max heap) of the n elements, and use remove() k times.
     *
     * @param A Array to select.
     * @param k most k largest we want to get.
     * @return kth largest number in array A.
     */
    public static int PriorityQueueSort1(int[] A, int k) {
        Queue<Integer> queue = new PriorityQueue<>(k, Collections.reverseOrder());   // Trans to max heap
        List<Integer> list = new ArrayList<>();
        for (int j : A) {   //Initial max-heap
            queue.offer(j);
        }
        for (int i = 1; i <= k; i++) {
            list.add(queue.remove());   //List is in decreasing order
        }
        return list.get(k - 1);
    }

    /**
     * Use a priority queue (min heap) of size k to keep track of the k largest elements seen so far, as you iterate over the array.
     *
     * @param A Integer stream A to be select.
     * @param k most k largest we want to get.
     * @return kth largest number in array A.
     */
    public static Queue<Integer> PriorityQueueSort2(Stream<Integer> A, int k) {
        Iterator<Integer> it = A.iterator();
        Queue<Integer> q = new PriorityQueue<>();  //New a min heap
        for (int i = 1; i <= k; i++) {    //Initial min-heap
            if (it.hasNext()) {
                q.add(it.next());
            }else
                return q;
        }
        while (it.hasNext()) {
            int x = it.next();
            if (q.peek() < x) {
                q.remove();
                q.add(x);
            }
        }
        return q;
    }

    /**
     * Implement the O(n) algorithm for Select discussed in class.
     *
     * @param A Integer stream A to be select.
     * @param k Most k largest we want to get.
     * @return Subarray of A
     */

    public static int[] Select(int[] A, int k) {
        int n = A.length;
        if(k<=0)
            return new int[n];
        if (k > n)
            return A;
        Select(A, 0, n, k);
        return Arrays.copyOfRange(A, n - k, n);
    }

    /**
     * Selection sort to get kth largest element in array A[p,p+n-1]
     *
     * @param A array name
     * @param p array start index
     * @param n array's length
     * @param k kth largest element need to find
     * @return the element we find
     */
    public static int Select(int[] A, int p, int n, int k) {
        int r = p + n - 1;
        if (n < bound) {
            insertion_srt(A, p, r);
            return A[p + n - k];
        } else {
            int q = QuickSort.partition2(A, p, r);
            int left = q - p, right = r - q;
            if (right >= k)
                return Select(A, q + 1, right, k);
            else if (right + 1 == k)
                return A[q];
            else
                return Select(A, p, left, k - (right + 1));
        }
    }

    /**
     * Insertion sort of array arr[start...start+l-1]
     *
     * @param arr   Array to be sort
     * @param start Start index of sort.
     * @param l     Length of array need to be sort.
     */
    public static void insertion_srt(int[] arr, int start, int l) {
        int k;
        int key;
        for (int i = start + 1; i <= l; i++) {
            key = arr[i];
            k = i - 1;
            while (k > start - 1 && arr[k] > key) {
                arr[k + 1] = arr[k];
                k--;
            }
            arr[k + 1] = key;
        }
    }

    /**
     * Main test function.
     */
    public static void main(String[] args) {
        int[] item = new int[30000000];
        System.out.println("Input K(Kth largest element) of array (1-array.length):");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        while (k <= 0 || k > item.length) {
            System.out.println("Input K(Kth largest element) of array (1-array.length):");
            k = scanner.nextInt();
        }
        for (int i = 0; i < item.length; i++) {
            item[i] = i;
        }
        ArrayHelper.shuffle(item);
        System.out.println("test (a):");
        long startTime = System.currentTimeMillis();
        int resA = PriorityQueueSort1(item, k);
        System.out.println("Kth element is: " + resA + "     Running time : " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println("test (b):");
        ArrayList<Integer> a = new ArrayList<>(40000000);
        for (int i : item)
            a.add(i);
        startTime = System.currentTimeMillis();
        int resB = PriorityQueueSort2(a.stream(), k).peek();
        System.out.println("Kth element is: " + resB + "     Running time : " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println("test (c):");
        startTime = System.currentTimeMillis();
        int[] resC = Select(item, k);
        System.out.println("Kth element is: " + resC[0] + "     Running time : " + (System.currentTimeMillis() - startTime) + "ms");

    }
}



