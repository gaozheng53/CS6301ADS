/**
 * <h1>Fall 2017 Short Project 4-5</h1>
 * <p>
 * Reorder an int array A[] by moving negative elements to the front, followed by its positive
 * elements. The relative order of positive numbers must be the same as in the given array.
 * Similarly, the relative order of its negative numbers should also be retained. Write an algorithm
 * that runs in O(nlogn), and uses only O(1) extra space (for variables), but can use O(log n) space
 * for recursion.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-24
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Random;

public class RearrangeMinusPlus {
    public static void rearrangeMinusPlus(int[] arr) {
        rearrangeMinusPlus(arr, 0, arr.length - 1);
    }

    /**
     * Rearrange and return the index of the last negative number in segment l~>r.
     */
    private static int rearrangeMinusPlus(int[] arr, int l, int r) {
        if (l == r)
            return arr[l] < 0 ? l : l - 1;

        final int m = (l + r) / 2;
        final int lm = rearrangeMinusPlus(arr, l, m);
        final int rm = rearrangeMinusPlus(arr, m + 1, r);

        // After recursive call, just swap the total segment of the right part of left half and the left part of the right half.
        return merge(arr, lm + 1, m, rm);
    }

    /**
     * Swap array segment of l~>m with m+1~>r, return the index of last negative number after swap.
     */
    private static int merge(int[] arr, int l, int m, int r) {
        for (int i = 0; l + i < m - i; i++)
            swap(arr, l + i, m - i);
        for (int i = 0; m + 1 + i < r - i; i++)
            swap(arr, m + 1 + i, r - i);
        for (int i = 0; l + i < r - i; i++)
            swap(arr, l + i, r - i);
        return r - m + l - 1;
    }

    private static void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public static void main(String[] args) {
        Random x = new Random();
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            r.add(x.nextInt(1000) * (x.nextInt(2) == 1 ? -1 : 1));
        }
        int[] arr = new int[r.size()];

        int c = 0;
        for (Integer i : r)
            arr[c++] = i;

        System.out.println("Original array:");
        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();

        rearrangeMinusPlus(arr);

        System.out.println("Rearranged array:");
        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();
    }

}
