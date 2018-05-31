/**
 * <h1>Fall 2017 Short Project 8 - 7</h1>
 * <p>
 * Implement permutation and combination algorithms nPk and nCk. Use a VERBOSE flag to decide if the
 * output is just the number of permutations or combinations visited (VERBOSE = 0), or, a complete
 * listing.
 * <p>
 * Basic permutation implementation.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation<T> {

    private final T[] A;
    private final int length;
    private final int k;
    private List<T[]> permutation;

    public Permutation(T[] arr, int k) {
        this.A = arr;
        this.length = arr.length;
        this.k = k;
        this.permutation = new ArrayList<>();

        permute(k);
    }

    private void permute(int c) {
        if (c == 0) {
            permutation.add(Arrays.copyOf(A, k));
            return;
        }

        int d = k - c;
        permute(c - 1);

        for (int i = d + 1; i <= length - 1; i++) {
            T tmp = A[d];
            A[d] = A[i];
            A[i] = tmp;
            permute(c - 1);
            A[i] = A[d];
            A[d] = tmp;
        }
    }

    public int size() {
        return permutation.size();
    }

    public void printPermutation() {
        permutation.forEach(x -> System.out.println(Arrays.toString(x)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        Integer[] A = new Integer[n];

        for (int i = 0; i < n; i++)
            A[i] = i;

        Permutation<Integer> nPk = new Permutation<>(A, k);

        System.out.println(nPk.permutation.size());

        nPk.printPermutation();
    }
}
