/**
 * <h1>Fall 2017 Short Project 8 - 9</h1>
 * <p>
 * Implement non-recursive version of Heap's algorithm for generating all n! permutations. See
 * Wikipedia page for the algorithm. https://en.wikipedia.org/wiki/Heap%27s_algorithm
 * <p>
 * Heap's algorithm implementation.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Heap {

    public static <T> List<T[]> permute(T[] x) {
        final int n = x.length;

        int[] c = new int[x.length];

        List<T[]> ret = new ArrayList<>();
        ret.add(Arrays.copyOf(x, x.length));

        int i = 0;
        while (i < n) {
            if (c[i] < i) {
                if ((i & 1) == 0) // if i is even
                    swap(x, 0, i);
                else
                    swap(x, c[i], i);
                ret.add(Arrays.copyOf(x, x.length));
                c[i] += 1;
                i = 0;
            } else {
                c[i] = 0;
                i += 1;
            }
        }

        return ret;
    }

    private static <T> void swap(T[] ar, int a, int b) {
        T tmp = ar[a];
        ar[a] = ar[b];
        ar[b] = tmp;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Integer[] A = new Integer[n];

        for (int i = 0; i < n; i++)
            A[i] = i;

        List<Integer[]> ret = Heap.permute(A);

        ret.forEach(x -> System.out.println(Arrays.toString(x)));
    }
}
