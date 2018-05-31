/**
 * <h1>Fall 2017 Short Project 8 - 8</h1>
 * <p>
 * Implement Knuth's L algorithm.
 * <p>
 * Driver program of Knuth's L algorithm.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class P8Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter length followed by the integer sequence to compute lexicographic permutation.");
        System.out.println("For example: 5 0 1 2 3 4");
        System.out.println("Indicating the array with length 5 is {0, 1, 2, 3, 4}.");
        int l = in.nextInt();
        Integer[] arr = new Integer[l];

        for (int i = 0; i < l; i++)
            arr[i] = in.nextInt();

        List<Integer[]> ret = KnuthL.permute(arr);

        System.out.println("Lexicographic order permutations are:");

        ret.forEach(x -> System.out.println(Arrays.toString(x)));
    }
}
