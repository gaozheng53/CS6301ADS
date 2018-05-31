/**
 * <h1>Fall 2017 Short Project 5-4</h1>
 * <p>
 * Implement dual pivot partition and its version of quick sort.
 * Compare its performance with regular quick sort.  Try inputs that
 * are distinct, and inputs that have many duplicates.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */
package cs6301.g16;

import cs6301.g00.Timer;
import java.util.Arrays;
import java.util.Random;

public class DualPivotQuickSort {

    static final int threshold = 20;

    /**
     * Dual pivot partition function
     * @param A - input array
     * @param p - prior index
     * @param r - rear index
     * @return array of 2 indexes of the 2 pivots used in the partition
     */
    private static int[] dPPartition(int[] A, int p, int r) {

        int i = p + 1;
        int k = p + 1;
        int j = r - 1;

        // Select x1<=x2 uniformly from array
        Random rand = new Random();
        int idx1 = p + rand.nextInt(r - p);
        int idx2 = idx1+ 1 + rand.nextInt(r - idx1);
        if(A[idx1]>A[idx2]) {
            int tmp = idx1;
            idx1 = idx2;
            idx2 = tmp;
        }
        int x1 = A[idx1];
        int x2 = A[idx2];

        // put x1 x2 to p and r respectively
        if(idx1!=r && idx2!=p) {
            ArrayHelper.exchange(A, p, idx1);
            ArrayHelper.exchange(A, r, idx2);
        }
        else if(idx1 == r && idx2!=p){
            ArrayHelper.exchange(A, p, idx1);
            ArrayHelper.exchange(A, r, idx2);
        }
        else if(idx1 != r && idx2==p){
            ArrayHelper.exchange(A, r, idx2);
            ArrayHelper.exchange(A, p, idx1);
        }
        else { //idx1 == r && idx2==p
            ArrayHelper.exchange(A,idx1,idx2);
        }

        // begin partition
        while (i <= j) {
            if (A[i] >= x1 && A[i] <= x2) {
                i++;
            } else if (A[i] < x1) {
                ArrayHelper.exchange(A, i, k);
                k++;
                i++;
            } else if (A[j] > x2) {
                j--;
            } else if (A[i] > x2 && A[j] < x1) {
                // Circular swap k, i, j, k
                ArrayHelper.exchange(A, k, i);
                ArrayHelper.exchange(A, k, j);
                k++;
                i++;
                j--;
            } else {
                // A[i]>x2 && x1<=A[j]<=x2
                ArrayHelper.exchange(A, i, j);
                i++;
                j--;
            }
        }

        // put pivots back
        ArrayHelper.exchange(A, p, k-1);
        ArrayHelper.exchange(A, r, j+1);

        return new int[]{k-1, j+1};
    }

    /**
     * Helper function for dualPivot quick sort
     * @param A - input array
     * @param p - prior index
     * @param r - rear index
     */
    private static void dPQuickSort(int[] A, int p, int r) {
        if (r - p < threshold) {
            ArrayHelper.insertionSort(A, p, r);
        }
        else if(p<r){
            int[] pResult = dPPartition(A,p,r);
            int idx1 = pResult[0];
            int idx2 = pResult[1];
            dPQuickSort(A,p,idx1-1);
            dPQuickSort(A,idx2+1, r);
            if(A[idx1]!=A[idx2])
                dPQuickSort(A,idx1+1,idx2-1);
        }
    }

    /**
     * Signature for dual pivot quick sort
     * @param A - input array
     */
    public static void dPQuickSort(int[] A) {
        dPQuickSort(A,0,A.length-1);
    }

    /**
     * main function for testing
     * @param args
     */
    public static void main(String[] args) {

        {
            System.out.println("====================\nTest with array with duplicate elements:\n");
            int[] A = ArrayHelper.getDuplicateElementArray(0.8f, 10000000);

            int[] B = ArrayHelper.shuffle(A);
            int[] C = Arrays.copyOf(B, B.length);

            System.out.println("Input Array:");
            ArrayHelper.printArray(B);

            System.out.println("Dual Pivot Quick Sort:");
            Timer t = new Timer();
            dPQuickSort(B);
            t.end();
            ArrayHelper.printArray(B);
            boolean isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");


            System.out.println("Normal Quick Sort:");
            B = C;
            t = new Timer();
            QuickSort.quickSort1(B);
            t.end();
            ArrayHelper.printArray(B);
            isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");
        }


        {
            System.out.println("\n====================\nTest with array with distinct elements:\n");
            int[] A = ArrayHelper.getArray(10000000);

            int[] B = ArrayHelper.shuffle(A);
            int[] C = Arrays.copyOf(B, B.length);

            System.out.println("Input Array:");
            ArrayHelper.printArray(B);

            System.out.println("Dual Pivot Quick Sort:");
            Timer t = new Timer();
            dPQuickSort(B);
            t.end();
            ArrayHelper.printArray(B);
            boolean isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");


            System.out.println("Normal Quick Sort:");
            B = C;
            t = new Timer();
            QuickSort.quickSort1(B);
            t.end();
            ArrayHelper.printArray(B);
            isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");
        }
    }

}
