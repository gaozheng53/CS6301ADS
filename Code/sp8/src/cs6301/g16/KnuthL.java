/**
 * <h1>Fall 2017 Short Project 8 - 8</h1>
 * <p>
 * Implement Knuth's L algorithm.
 * <p>
 * Knuth's L algorithm implementation.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnuthL {

    static <T extends Comparable<? super T>> List<T[]> permute(T[] x) {
        final int lastIndex = x.length - 1;
        List<T[]> ret = new ArrayList<>();
        ret.add(Arrays.copyOf(x, x.length));

        while (!isDescendOrder(x)) {
            int j = lastIndex - 1;
            while (x[j].compareTo(x[j + 1]) >= 0) j--;
            int k = lastIndex;
            while (x[j].compareTo(x[k]) >= 0) k--;
            swap(x, j, k);
            reverse(x, j + 1, lastIndex);

            ret.add(Arrays.copyOf(x, x.length));
        }

        return ret;
    }

    private static <T extends Comparable<? super T>> boolean isDescendOrder(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i].compareTo(arr[i + 1]) < 0)
                return false;
        return true;
    }

    private static <T> void reverse(T[] arr, int l, int r) {
        while (l < r) {
            swap(arr, l, r);
            l++;
            r--;
        }
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

        List<Integer[]> ret = KnuthL.permute(A);

        ret.forEach(x -> System.out.println(Arrays.toString(x)));
    }
}
