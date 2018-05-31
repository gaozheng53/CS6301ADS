/**
 * <h1>Fall 2017 Short Project 8 - 7</h1>
 * <p>
 * Implement permutation and combination algorithms nPk and nCk. Use a VERBOSE flag to decide if the
 * output is just the number of permutations or combinations visited (VERBOSE = 0), or, a complete
 * listing.
 * <p>
 * Basic combination implementation.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combination<T> {
    private final T[] A;
    private final int length;
    private final int k;
    private List<T[]> combination;

    public Combination(T[] arr, int k) {
        this.A = arr;
        this.length = arr.length;
        this.k = k;
        this.combination = new ArrayList<>();

        ArrayList<T> chosen = new ArrayList<>();
        for (int i = 0; i < k; i++)
            chosen.add(A[0]);
        combine(0, k, chosen);
    }

    private void combine(int i, int c, ArrayList<T> chosen) {
        if (c == 0) {
            combination.add(chosen.toArray(Arrays.copyOf(A, 0)));
            return;
        }

        chosen.set(this.k - c, A[i]);
        combine(i + 1, c - 1, chosen);
        if (length - i > c)
            combine(i + 1, c, chosen);
    }

    public int size() {
        return combination.size();
    }

    public void printCombination() {
        combination.forEach(x -> System.out.println(Arrays.toString(x)));
    }

    public static void main(String[] args) {
        int n, k;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
        } else {
            n = 10;
            k = 3;
        }

        Integer[] A = new Integer[n];

        for (int i = 0; i < n; i++)
            A[i] = i;

        Combination<Integer> nCk = new Combination<>(A, k);

        System.out.println(nCk.combination.size());

        nCk.printCombination();
    }
}
