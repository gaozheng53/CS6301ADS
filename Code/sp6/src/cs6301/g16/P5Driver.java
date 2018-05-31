package cs6301.g16;

import cs6301.g00.Timer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class P5Driver {


    /**
     * Helper function to print array
     */
    static void printArray(Integer[] arr) {
        StringBuilder sb = new StringBuilder("The array is ");

        if (arr.length <= 10) {
            sb.append(Arrays.toString(arr));
        } else {
            sb.append("[");
            for(int i=0; i<10; i++)
                sb.append(arr[i]).append(", ");
            sb.append("...]");
        }

        System.out.println(sb.toString());
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter length of array to be generated: ");

        final int size = scanner.nextInt();

        System.out.println("Generating random array within " + size + ".");

        Random r = new Random();

        Integer[] arr = new Integer[size];

        for (int i = 0; i < size; i++) {
            arr[i] = r.nextInt(size);
        }

        printArray(arr);

        {
            System.out.println("Sorting the array in ascending order.");

            Timer t = new Timer();
            BinaryHeap.heapSort(arr, Comparator.reverseOrder());
            System.out.println(t.end());

            System.out.println();
            printArray(arr);

        }

        {
            System.out.println("Sorting the array in descending order again.");

            Timer t = new Timer();
            BinaryHeap.heapSort(arr, Comparator.naturalOrder());
            System.out.println(t.end());

            System.out.println();
            printArray(arr);
        }
    }
}

