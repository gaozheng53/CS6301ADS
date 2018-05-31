/**
 * <h1>Fall 2017 Short Project 1</h1>
 * <p>
 * Implement the merge sort algorithm on generic arrays and on an int array and compare their
 * running times on arrays sizes from 1M-16M, and with an O(n^2) time algorithm, such as Insertion
 * sort. Write a report with a table and/or chart showing the times for different sizes.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.1
 * @since 2017-08-25
 */

package cs6301.g16;
import cs6301.g00.Item;
import cs6301.g00.Shuffle;
import cs6301.g00.Timer;

public class Sort {

    /**
     * Rearranges the array of type T in ascending order with merge sort, using the natural order.
     * @param arr : The array to be sorted.
     * @param tmp : The buffer array with same length as arr, it should be allocated before function call.
     * @param <T> : The element type in the array.
     */
    public static<T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    /**
     * Helper function for <code>static<T extends Comparable<? super T>> void mergeSort(T[] arr,T[] tmp) </code>.
     * Specify the part of array to be sorted at current call, and the same segment of array tmp is used as buffer.
     * @param arr   : Array to be sorted.
     * @param tmp   : Buffer in merge.
     * @param left  : Start index of the part of array to be sorted.
     * @param right : End index of the part of array to be sorted.
     * @param <T>   : The element type in the array.
     */
    private static<T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp, int left, int right) {
        if (left >= right)
            return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, tmp, left, mid);
        mergeSort(arr, tmp, mid + 1, right);
        merge(arr, tmp, left, mid, right);
    }

    /**
     * Merge two adjacent part of array of type T.
     * <ul>
     * <li>arr[left, mid]</li>
     * <li>arr[mid+1, right]</li>
     * </ul>
     * The two part should be sorted already
     * @param arr   : Array to be sorted.
     * @param tmp   : Buffer in merge.
     * @param left  : Start index of the part of array already sorted.
     * @param mid   : The end point of the left part sorted array.
     * @param right : End index of the part of array already sorted with start index of <code>mid + 1</code>.
     * @param <T>   : The element type in the array.
     */
    private static<T extends Comparable<? super T>> void merge(T[] arr, T[] tmp, int left, int mid, int right) {

        // Copy the two parts to corresponding location in buffer array tmp.
        System.arraycopy(arr, left, tmp, left, right - left + 1);

        for (int i = left, j = mid + 1, k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = tmp[j];
                j++;
            } else if (j > right) {
                arr[k] = tmp[i];
                i++;
            } else if (tmp[i].compareTo(tmp[j]) < 0) {
                arr[k] = tmp[i];
                i++;
            } else {
                arr[k] = tmp[j];
                j++;
            }
        }
    }

    /**
     * Rearranges the array of type int in ascending order with merge sort, using the natural order.
     * @param arr   : The array to be sorted.
     * @param tmp   : The buffer array with same length as arr.
     */
    public static void mergeSort(int[] arr, int[] tmp) {
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    /**
     * Helper function for <code>static void mergeSort(int[] arr, int[] tmp) </code>.
     * Specify the part of array to be sorted at current call, and the same segment of array tmp is used as buffer.
     * @param arr   : Array to be sorted.
     * @param tmp   : Buffer in merge.
     * @param left  : Start index of the part of array to be sorted.
     * @param right : End index of the part of array to be sorted.
     */
    private static void mergeSort(int[] arr, int[] tmp, int left, int right) {
        if (left >= right)
            return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, tmp, left, mid);
        mergeSort(arr, tmp, mid + 1, right);
        merge(arr, tmp, left, mid, right);
    }

    /**
     * Merge two adjacent part of int array.
     * <ul>
     * <li>arr[left, mid]</li>
     * <li>arr[mid+1, right]</li>
     * </ul>
     * The two part should be sorted already
     * @param arr   : Array to be sorted.
     * @param tmp   : Buffer in merge.
     * @param left  : Start index of the part of array already sorted.
     * @param mid   : The end point of the left part sorted array.
     * @param right : End index of the part of array already sorted with start index of <code>mid + 1</code>.
     */
    private static void merge(int[] arr, int[] tmp, int left, int mid, int right) {

        System.arraycopy(arr, left, tmp, left, right - left + 1);

        for (int i = left, j = mid + 1, k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = tmp[j];
                j++;
            } else if (j > right) {
                arr[k] = tmp[i];
                i++;
            } else if (tmp[i] < tmp[j]) {
                arr[k] = tmp[i];
                i++;
            } else {
                arr[k] = tmp[j];
                j++;
            }
        }
    }

    /**
     * Insertion/Bubble sort generic array arr in place.
     * @param arr   : The array to be sorted.
     * @param <T>   : The element type in the array.
     */
    public static <T extends Comparable<? super T>> void nSquareSort(T[] arr) {
        for (int p = 1; p < arr.length; p++) {
            T tmp = arr[p];
            int j;
            for (j = p; j > 0 && tmp.compareTo(arr[j - 1]) < 0; j--) {
                arr[j] = arr[j - 1]; //Move all bigger element right.
            }
            arr[j] = tmp; // Insert the value.
        }
    }

    /**
     * Bubble sort implementation.
     * @param arr   : The array to be sorted.
     * @param <T>   : The element type in the array.
     */
    public static <T extends Comparable<? super T>> void bubble(T[] arr) {
        int max;
        T tmp;
        for (int i = 0; i < arr.length; i++) {
            max = -1;
            for (int j = 0; j < arr.length - i; j++) {
                if (max == -1)
                    max = j;
                else if (arr[max].compareTo(arr[j]) < 0)
                    max = j;
            }
            tmp = arr[max];
            arr[max] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = tmp;
        }
    }

    /**
     * Check whether the array is sorted.
     * @param arr   : Array to be check.
     * @param <T>   : Array type.
     * @return      : True if array is sorted and false otherwise.
     */
    private static <T extends Comparable<? super T>> boolean isSorted(T[] arr) {
        for (int i = 0; i < arr.length - 2; i++)
            if (arr[i].compareTo(arr[i+1]) > 0)
                return false;
        return true;
    }

    /**
     * Test main function.
     * @param args: A string array containing the command line arguments.
     *            [0] := sorting function used, 1->generic mergeSort, 2->int mergeSort, 3->nSquareSort
     *            [1] := size of array in million.
     */
    public static void main(String[] args) {

        int sortType, size;
        if (args.length > 1) {
            sortType = Integer.parseInt(args[0]);
            size = Integer.parseInt(args[1]) * 1_000_000;
        } else {
            // default value
            sortType = 1;
            size = 1_000_000;
        }

        if (sortType == 1) {
            // Generic merge sort.
            Item[] tmp = new Item[size];
            Item[] arr = new Item[size];
            for (int i = 0; i < size; i++)
                arr[i] = new Item(i);
            Shuffle.shuffle(arr);
            Timer timer = new Timer();
            mergeSort(arr, tmp);
            System.out.println(timer.end());
            assert isSorted(arr);
        } else if (sortType == 2) {
            // Int merge sort.
            int[] arr = new int[size];
            int[] tmp = new int[size];
            Integer[] tmpArr = new Integer[size];
            for (int i = 0; i < size; i++)
                tmpArr[i] = i;
            Shuffle.shuffle(tmpArr);
            for (int i = 0; i < size; i++)
                arr[i] = tmpArr[i];
            Timer timer = new Timer();
            mergeSort(arr, tmp);
            System.out.println(timer.end());
        } else {
            // Generic n square sort.
            Integer[] arr = new Integer[size];
            for (int i = 0; i < size; i++)
                arr[i] = i;
            Shuffle.shuffle(arr);
            Timer timer = new Timer();
            nSquareSort(arr);
            System.out.println(timer.end());
            assert isSorted(arr);
        }
    }
}
