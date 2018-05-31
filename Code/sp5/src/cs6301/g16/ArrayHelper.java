/**
 * <h1>Fall 2017 Short Project 5</h1>
 * <p>
 * Helper class to implement array related helper functions
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */

package cs6301.g16;

import java.util.Arrays;
import java.util.Random;

public class ArrayHelper {

    /**
     * Helper function to exchange 2 elements in the given array
     * Utilizing XOR exchange algorithm
     *
     * @param A - input array
     * @param i - element index 1
     * @param j - element index 2
     */
    public static void exchange(int[] A, int i, int j) {
        // bitwise exchange doesn't work when value are equal, and we don't need exchange equal values.
        if(A[i] == A[j]) return;

        A[i] = A[i] ^ A[j];
        A[j] = A[i] ^ A[j];
        A[i] = A[i] ^ A[j];
    }

    /**
     * Print an array nicely
     **/
    public static void printArray(int[] A) {
        if(A.length>100) {
            System.out.print("[");
            for (int i = 0; i < 5; i++) {
                System.out.print(A[i]+" ");
            }
            System.out.print("...");
            for (int i = A.length-6; i < A.length; i++) {
                System.out.print(" "+A[i]);
            }
            System.out.print("]\n");
        }
        else
            System.out.println(Arrays.toString(A));
    }

    /**
     * Returns a random shuffling copy of the array.
     */
    public static int[] shuffle(int[] nums) {
        int[] rand = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int r = (int) (Math.random() * (i + 1));
            rand[i] = rand[r];
            rand[r] = nums[i];
        }
        return rand;
    }

    /**
     * Shuffle an array in place.
     * @param arr
     */
    public static void inPlaceShuffle(int[] arr) {

        Random r = new Random();

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = arr.length-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i);

            // Swap arr[i] with the element at random index
            exchange (arr,i,j);
        }
    }

    /**
     * Return a array with specific length and distinct elements
     * @param length
     * @return
     */
    public static int[] getArray(int length) {
        int[] A = new int[length];
        for (int i = 0; i < length; i++) {
            A[i] = i + 1;
        }
        return A;
    }

    /**
     * Return a random array with specified length all elements are distinct
     * @param length
     * @return
     */
    public static int[] getRandomArray(int length){
        int[] A = getArray(length);
        inPlaceShuffle(A);
        return A;
    }

    /**
     * Return an array in reversed order with specified length, all elements are distinct
     * @param length
     * @return
     */
    public static int[] getReversedArray(int length){
        int[] A = new int[length];
        for (int i = 0; i < length; i++) {
            A[i] = length - i;
        }
        return A;
    }

    /**
     * Return an array with duplicate element
     * @param duplicationRate
     * @param length
     * @return
     */
    public static int[] getDuplicateElementArray(float duplicationRate, int length) {
        int duplicateCount = (int)((float)length*duplicationRate);
        int currentValue = 1;
        int[] A = new int[length];
        Random r = new Random();
        for(int i=0;i<length;i++){

            A[i] = currentValue;

            if(r.nextBoolean()&&duplicateCount>0)
                duplicateCount--;
            else
                currentValue ++;
        }
        return A;
    }

    /**
     * Insertion sort to sort part of array.
     *
     * @param arr   The array need to be sorted.
     * @param start Start index of array will be sort.
     * @param l     End index of array will be sort.
     */
    public static void insertionSort(int[] arr, int start, int l) {
        int k;
        int key;
        for (int i = start + 1; i <= l; i++) {
            key = arr[i];
            k = i - 1;
            while (k > start - 1 && arr[k]>key) {
                arr[k + 1] = arr[k];
                k--;
            }
            arr[k + 1] = key;
        }
    }

}
