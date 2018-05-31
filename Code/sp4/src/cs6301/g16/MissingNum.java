/**
 * <h1>Fall 2017 Short Project 4-6</h1>
 * <p>
 * Given an array of n distinct integers, in sorted order, starting at 1 and
 * ending with n+k, find the k missing numbers in the sequence. Your algorithm
 * should run in O(k+log(n)) time.  Note that a simple linear scan of the
 * array can find the answer, but it will not meet the requirement on the
 * running time.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */

package cs6301.g16;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MissingNum {
    /**
     * Helper function to determine whether there are missing numbers in a range
     *
     * @param arr   - input array
     * @param start - start index of the range
     * @param end   - end index of the range
     * @return - boolean indicator
     */
    private static boolean hasMissingNum(int[] arr, int start, int end) {
        int indexDiff = end - start;
        int valueDiff = arr[end] - arr[start];
        return indexDiff != valueDiff;
    }

    /**
     * Helper function for finding missing numbers recursively
     *
     * @param arr         - input array
     * @param missingNums - output array of missing numbers
     * @param start       - start index
     * @param end         - end index
     */
    private static void findMissing(int[] arr, List<Integer> missingNums, int start, int end) {
        if(!hasMissingNum(arr,start,end))
            // if there are no missing value in the range do nothing
            return;

        if (start+1 == end) {
            // add missing numbers
            for(int i=arr[start]+1;i<arr[end];i++)
                missingNums.add(i);
        } else {
            int mid = (start + end) / 2;
            // find missing numbers in [start, mid]
            findMissing(arr, missingNums, start, mid);
            // find missing numbers in [mid,mid+1]
            findMissing(arr,missingNums,mid,mid+1);
            // find missing numbers in [mid+1,end]
            findMissing(arr, missingNums, mid + 1, end);
        }
    }

    /**
     * Find the missing numbers in the array
     *
     * @param arr - input array, distinct and sorted array starting at 1 and ending at n+k
     *            (k is the number of missing numbers)
     * @return - array of missing numbers
     */
    static List<Integer> missingNums(int[] arr) {

        if (arr == null || arr.length < 1 || arr[0] != 1 || arr[arr.length - 1] == arr.length) {
            System.out.println("Invalid input");
            return null;
        }

        List<Integer> missingNums = new LinkedList<>();

        findMissing(arr, missingNums, 0, arr.length - 1);

        return missingNums;
    }

    public static void main(String[] args) {

        {
            int[] arr = new int[]{1, 3, 4, 5, 8, 9, 10};
            System.out.println("\n-------------\nInput array: " + Arrays.toString(arr));
            System.out.println("\nOutput array: " + missingNums(arr));
        }

        {
            int[] arr = new int[]{1, 2, 4, 6, 8, 9, 10, 22};
            System.out.println("\n-------------\nInput array: " + Arrays.toString(arr));
            System.out.println("\nOutput array: " + missingNums(arr));
        }
    }
}
