/**
 * <h1>Fall 2017 Short Project 8 - 7</h1>
 * <p>
 * Implement permutation and combination algorithms nPk and nCk. Use a VERBOSE flag to decide if the
 * output is just the number of permutations or combinations visited (VERBOSE = 0), or, a complete
 * listing.
 * <p>
 * Driver program.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Scanner;

public class P7Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter VERBOSE, k and length followed by the integer sequence to compute \npermutation and combination.");
        System.out.println("For example: 1 3 5 0 1 2 3 4");
        System.out.println("Indicating VERBOSE = 1, k = 3, and the array with length 5 is {0, 1, 2, 3, 4}.");
        int v = in.nextInt();
        int k = in.nextInt();
        int l = in.nextInt();
        ArrayList<Integer> arr = new ArrayList<>(l);

        while (l > 0 && in.hasNext()) {
            arr.add(in.nextInt());
            l--;
        }

        Permutation<Integer> p = new Permutation<>(arr.toArray(new Integer[0]), k);
        Combination<Integer> c = new Combination<>(arr.toArray(new Integer[0]), k);

        System.out.println("Total number of permutations: " + p.size());
        System.out.println("Total number of combinations: " + c.size());

        if (v == 1) {
            System.out.println("\nList of permutations:");
            p.printPermutation();
            System.out.println("\nList of combinations:");
            c.printCombination();
        }
    }
}
