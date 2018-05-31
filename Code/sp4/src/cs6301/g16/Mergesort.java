/**
 * <h1>Fall 2017 Short Project 4-3</h1>
 * <p>
 * Comapre 4 different versions of mergesort discussed in class (Sep 15)
 * and evaluate the improvement in running times on int[] arrays.
 * (1) Merge sort as described in text books, where temp array is
 * allocated in each instance of merge.
 * (2) Keep one temp array that is passed as a parameter to merge.
 * (3) Improvement in (2) + use insertion sort for base case when
 * the size of array is below some threshold.
 * (4) Improvements in (2) and (3) + avoid copying to tmp array.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */

package cs6301.g16;

import java.util.ArrayList;


public class Mergesort {
    private static final int threshold = 20;

    //(1) Merge sort in text book

    /**
     * Sort array A[p...r]
     *
     * @param A   Array will be sorted.
     * @param p   Start index of array will be sorted.
     * @param r   End index of array will be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeSortOne(T[] A, int p, int r) {
        int mid = (p + r) / 2;
        if (p < r) {
            mergeSortOne(A, p, mid);
            mergeSortOne(A, mid + 1, r);
            mergeOne(A, p, mid, r);
        }

    }

    /**
     * Merge sorted A[p...q] and sorted A[q+1...r] into A[p...r] in sorted order.
     *
     * @param A   Array will be sorted.
     * @param p   Start index of array will be merge.
     * @param q   The split position index of A[p...r].
     * @param r   End index of array will be merge.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeOne(T[] A, int p, int q, int r) {
        ArrayList<T> L = new ArrayList<T>();
        ArrayList<T> R = new ArrayList<T>();
        for (int i = p; i <= q; i++) {  //Copy A[p...q] into L
            L.add(A[i]);
        }
        for (int j = q + 1; j <= r; j++) {   //Copy A[q+1,r] into R
            R.add(A[j]);
        }
        int i = 0, j = 0;
        for (int k = p; k <= r; k++) {
            if (i <= L.size() - 1 && (j >= R.size() || (j <= R.size() - 1 && L.get(i).compareTo(R.get(j)) <= 0))) {
                A[k] = L.get(i);
                i++;
            } else if (j <= R.size() - 1 && (i >= L.size() || (i <= L.size() - 1 && L.get(i).compareTo(R.get(j)) > 0))) {
                A[k] = R.get(j);
                j++;
            }
        }
    }

    //(2) Keep one temp array that is passed as a parameter to merge.

    /**
     * Sort array A[p...r]
     *
     * @param A   Array will be sorted.
     * @param p   Start index of array will be sorted.
     * @param r   End index of array will be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeSortTwo(T[] A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSortTwo(A, p, q);
            mergeSortTwo(A, q + 1, r);
            mergeTwo(A, p, q, r);
        }
    }

    /**
     * Merge sorted A[p...q] and sorted A[q+1...r] into A[p...r] in sorted order.
     *
     * @param A   Array will be sorted.
     * @param p   Start index of array will be merge.
     * @param q   The split position index of A[p...r].
     * @param r   End index of array will be merge.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeTwo(T[] A, int p, int q, int r) {
        ArrayList<T> B = new ArrayList<T>();
        for (int i = p; i <= r; i++) {   //Copy A[p...r] into B
            B.add(A[i]);
        }
        int midList = q - p;
        int i = 0, j = midList + 1;
        for (int k = p; k <= r; k++) {
            if (i <= midList && (j >= B.size() || (j < B.size() && B.get(i).compareTo(B.get(j)) <= 0))) {
                A[k] = B.get(i);
                i++;
            } else if (j < B.size() && (i > midList || (i <= midList && B.get(i).compareTo(B.get(j)) > 0))) {
                A[k] = B.get(j);
                j++;
            }
        }
    }

    //(3) Merge sort:Improvement in (2) + use insertion sort for base case when the size of array is below some threshold.

    /**
     * Sort array A[p...r],if this part of array size is lower than threshold, program will using insertion sort.
     *
     * @param A   Array will be sorted.
     * @param p   Start index of array will be sorted.
     * @param r   End index of array will be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeSortThree(T[] A, int p, int r) {
        if (r - p < threshold) {
            insertion_srt(A, p, r);
        } else if (p < r) {
            int q = (p + r) / 2;
            mergeSortThree(A, p, q);
            mergeSortThree(A, q + 1, r);
            mergeThree(A, p, q, r);
        }
    }

    /**
     * Merge sorted A[p...q] and sorted A[q+1...r] into A[p...r] in sorted order.
     *
     * @param A   Array will be sorted.
     * @param p   Start index of array will be merge.
     * @param q   The split position index of A[p...r].
     * @param r   End index of array will be merge.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeThree(T[] A, int p, int q, int r) {
        ArrayList<T> B = new ArrayList<T>();
        for (int i = p; i <= r; i++) {   //copy A into B
            B.add(A[i]);
        }
        int midList = q - p;
        int i = 0, j = midList + 1;
        for (int k = p; k <= r; k++) {
            if (i <= midList && (j >= B.size() || (j < B.size() && B.get(i).compareTo(B.get(j)) <= 0))) {
                A[k] = B.get(i);
                i++;
            } else if (j < B.size() && (i > midList || (i <= midList && B.get(i).compareTo(B.get(j)) > 0))) {
                A[k] = B.get(j);
                j++;
            }
        }
    }


    //(4) Improvements in (2) and (3) + avoid copying to tmp array.

    /**
     * @param A   The array need to be sorted.
     * @param B   Copy of A.
     * @param p   Start index of array will be sorted.
     * @param r   End index of array will be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeSortFour(T[] A, T[] B, int p, int r) {
        if (r - p < threshold) {
            insertion_srt(A, p, r);
        } else if (p < r) {
            int q = (p + r) / 2;
            mergeSortFour(B, A, p, q);
            mergeSortFour(B, A, q + 1, r);
            mergeFour(B, A, p, q, r);
        }
    }

    /**
     * Merge sorted A[p...q] and sorted A[q+1...r] into A[p...r] in sorted order.
     *
     * @param A   The array need to be sorted.
     * @param B   Copy of A.
     * @param p   Start index of array will be merge.
     * @param q   The split position index of A[p...r].
     * @param r   End index of array will be merge.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeFour(T[] A, T[] B, int p, int q, int r) {
        int i = p, j = q + 1;
        for (int k = p; k <= r; k++) {
            if (j > r || (i <= q && A[i].compareTo(A[j]) <= 0))
                B[k] = A[i++];
            else
                B[k] = A[j++];
        }
    }

    /**
     * Insertion sort to sort part of array.
     *
     * @param arr   The array need to be sorted.
     * @param start Start index of array will be sort.
     * @param l     End index of array will be sort.
     * @param <T>   Element type in array.
     */
    public static <T extends Comparable<? super T>> void insertion_srt(T[] arr, int start, int l) {
        int k;
        T key;
        for (int i = start + 1; i <= l; i++) {
            key = arr[i];
            k = i - 1;
            while (k > start - 1 && arr[k].compareTo(key) > 0) {
                arr[k + 1] = arr[k];
                k--;
            }
            arr[k + 1] = key;
        }
    }

    /**
     * Call (1) merge sort function to sort array.
     *
     * @param arr The array need to be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeEntryOne(T[] arr) {
        mergeSortOne(arr, 0, arr.length - 1);
    }

    /**
     * Call (2) merge sort function to sort array.
     *
     * @param arr The array need to be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeEntryTwo(T[] arr) {
        mergeSortTwo(arr, 0, arr.length - 1);
    }

    /**
     * Call (3) merge sort function to sort array.
     *
     * @param arr The array need to be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeEntryThree(T[] arr) {
        mergeSortThree(arr, 0, arr.length - 1);
    }

    /**
     * Call (4) merge sort function to sort array.
     *
     * @param arr The array need to be sorted.
     * @param <T> Element type in array.
     */
    public static <T extends Comparable<? super T>> void mergeEntryFour(T[] arr) {
        T[] temp = arr.clone();
        mergeSortFour(arr, temp, 0, arr.length - 1);
    }

    /**
     * Main test function.
     *
     * @param args A string array containing the command line arguments.
     */
    public static void main(String[] args) {
        Integer[] items1 = new Integer[400000];
        Integer[] items2 = new Integer[400000];
        Integer[] items3 = new Integer[400000];
        Integer[] items4 = new Integer[400000];
        Integer[] items5 = new Integer[400000];
        Integer[] items6 = new Integer[400000];
        Integer[] items7 = new Integer[400000];
        Integer[] items8 = new Integer[400000];
        Integer[] items9 = new Integer[400000];
        Integer[] items10 = new Integer[400000];

        for (int i = 0; i < items1.length; i++) {
            items1[i] = (int) (Math.random() * 100000);
            items2[i] = (int) (Math.random() * 100000);
            items3[i] = (int) (Math.random() * 100000);
            items4[i] = (int) (Math.random() * 100000);
            items5[i] = (int) (Math.random() * 100000);
            items6[i] = (int) (Math.random() * 100000);
            items7[i] = (int) (Math.random() * 100000);
            items8[i] = (int) (Math.random() * 100000);
            items9[i] = (int) (Math.random() * 100000);
            items10[i] = (int) (Math.random() * 100000);
        }
        long startTimeOne = System.currentTimeMillis();
        mergeEntryOne(items1);   //test sort one
        mergeEntryOne(items2);
        mergeEntryOne(items3);
        mergeEntryOne(items4);
        mergeEntryOne(items5);
        mergeEntryOne(items6);
        mergeEntryOne(items7);
        mergeEntryOne(items8);
        mergeEntryOne(items9);
        mergeEntryOne(items10);
        System.out.println("Average running time 1:" + (System.currentTimeMillis() - startTimeOne) / 10 + "ms");
        long startTimeTwo = System.currentTimeMillis();
        mergeEntryTwo(items1);    //test sort two
        mergeEntryTwo(items2);
        mergeEntryTwo(items3);
        mergeEntryTwo(items4);
        mergeEntryTwo(items5);
        mergeEntryTwo(items6);
        mergeEntryTwo(items7);
        mergeEntryTwo(items8);
        mergeEntryTwo(items9);
        mergeEntryTwo(items10);
        System.out.println("Average running time 2:" + (System.currentTimeMillis() - startTimeTwo) / 10 + "ms");
        long startTimeThree = System.currentTimeMillis();
        mergeEntryThree(items1);   //test sort three
        mergeEntryThree(items2);
        mergeEntryThree(items3);
        mergeEntryThree(items4);
        mergeEntryThree(items5);
        mergeEntryThree(items6);
        mergeEntryThree(items7);
        mergeEntryThree(items8);
        mergeEntryThree(items9);
        mergeEntryThree(items10);
        System.out.println("Average running time 3:" + (System.currentTimeMillis() - startTimeThree) / 10 + "ms");
        long startTimeFour = System.currentTimeMillis();
        mergeEntryFour(items1);   //test sort four
        mergeEntryFour(items2);
        mergeEntryFour(items3);
        mergeEntryFour(items4);
        mergeEntryFour(items5);
        mergeEntryFour(items6);
        mergeEntryFour(items7);
        mergeEntryFour(items8);
        mergeEntryFour(items9);
        mergeEntryFour(items10);
        System.out.println("Average running time 4:" + (System.currentTimeMillis() - startTimeFour) / 10 + "ms");
    }
}


//修改collections_addall 复制数组到list
