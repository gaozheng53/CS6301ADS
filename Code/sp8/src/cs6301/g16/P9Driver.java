/**
 * <h1>Fall 2017 Short Project 8 - 9</h1>
 * <p>
 * Implement non-recursive version of Heap's algorithm for generating all n! permutations. See
 * Wikipedia page for the algorithm. https://en.wikipedia.org/wiki/Heap%27s_algorithm
 * <p>
 * Driver program of Heap's algorithm.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class P9Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter length followed by the integer sequence to compute lexicographic permutation.");
        System.out.println("For example: 5 0 1 2 3 4");
        System.out.println("Indicating the array with length 5 is {0, 1, 2, 3, 4}.");
        int l = in.nextInt();
        Integer[] arr = new Integer[l];

        for (int i = 0; i < l; i++)
            arr[i] = in.nextInt();

        List<Integer[]> ret = Heap.permute(arr);

        System.out.println("All n! = " + ret.size() + " permutations are:");

        ret.forEach(x -> System.out.println(Arrays.toString(x)));
    }
}
