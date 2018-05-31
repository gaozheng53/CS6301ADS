package cs6301.g16;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PrimeFactor {

    public static void main(String[] args) {
        int n = 100;
        long[] primeList = {3, 7};

        System.out.println("Input Prime List:"+ Arrays.toString(primeList));

        long[] thresholds = new long[primeList.length];

        // initialize priority queue
        PriorityQueue<Long> pq = new PriorityQueue<>();

        // add prime values to the queue and set thresholds for overflow test
        for (int i = 0; i < primeList.length; i++) {
            pq.add(primeList[i]);
            thresholds[i] = Long.MAX_VALUE / primeList[i];
        }

        // find smallest power, print out, and update
        long pre = 0;
        int c = 0;
        while (!pq.isEmpty() && c < n) {
            Long p = pq.remove();
            if (p != pre) {
                System.out.print(p + " ");
                c++;
            }
            pre = p;
            // add the production of current number by each given prime, if it doesn't overflow a long
            for (int i = 0; i < primeList.length; i++) {
                if (p < thresholds[i])
                    pq.add(p * primeList[i]);
            }
        }
    }

}