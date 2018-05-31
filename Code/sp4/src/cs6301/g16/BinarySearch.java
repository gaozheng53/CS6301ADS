/**
 * <h1>Fall 2017 Short Project 4-4</h1>
 * <p>
 * Binary search: in class we saw a version of binary search that returned
 * a boolean to indicate whether x occurs in the array or not.
 * Rewrite the function to return the index of the largest element that
 * is less than or equal to x.
 *
 * // Preconditions: arr[0..n-1] is sorted, and arr[0] <= x < arr[n-1].
 * // Returns index i such that arr[i] <= x < arr[i+1].
 * public static<T extends Comparable<? super T>> int binarySearch(T[] arr, T x)
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */
package cs6301.g16;

import java.util.Arrays;

public class BinarySearch {

    /**
     * Helper function for binary search
     * @param arr
     * @param x
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    private static <T extends Comparable<? super T>> int binarySearch(T[] arr, T x, int start, int end) {
        // when start==end, return the index of element immediately smaller than or equal to x
        if(start==end) {
            int cm = arr[start].compareTo(x);
            if(cm==0||cm==-1)
                // this element is the one we want
                return start;
            else // previous element is the one smaller than x
                return start-1;
        }

        // bisect the array recursively
        int mid = (start+end)/2;
        int cm = arr[mid].compareTo(x);
        if(cm==0)
            return mid;
        else if(cm==1)
            return binarySearch(arr,x,start,mid);
        else if(cm==-1)
            return binarySearch(arr,x,mid+1,end);

        return -1;
    }

    /**
     * Required signature
     * Preconditions: arr[0..n-1] is sorted, and arr[0] <= x < arr[n-1].
     * Returns index i such that arr[i] <= x < arr[i+1].
     * @param arr
     * @param x
     * @param <T>
     * @return
     */
    public static<T extends Comparable<? super T>> int binarySearch(T[] arr, T x){
        return binarySearch(arr,x,0,arr.length-1);
    }

    /**
     * Main function for test
     */
    public static void main(String[] args) {

        {
            Integer[] arr = {1, 7, 12};
            Integer x = 11;
            System.out.println("-----------\nInput: arr:" + Arrays.asList(arr) + "; x=" + x + ";\nOutput:" + binarySearch(arr, x));
        }

        {
            Integer[] arr = {1};
            Integer x = 1;
            System.out.println("-----------\nInput: arr:" + Arrays.asList(arr) + "; x=" + x + ";\nOutput:" + binarySearch(arr, x));
        }

        {
            Integer[] arr = {1,2,3};
            Integer x = 3;
            System.out.println("-----------\nInput: arr:" + Arrays.asList(arr) + "; x=" + x + ";\nOutput:" + binarySearch(arr, x));
        }

        {
            Integer[] arr = {1,3};
            Integer x = 2;
            System.out.println("-----------\nInput: arr:" + Arrays.asList(arr) + "; x=" + x + ";\nOutput:" + binarySearch(arr, x));
        }

        {
            Integer[] arr = {1,3,8,10,22,80};
            Integer x = 50;
            System.out.println("-----------\nInput: arr:" + Arrays.asList(arr) + "; x=" + x + ";\nOutput:" + binarySearch(arr, x));
        }

    }
}
