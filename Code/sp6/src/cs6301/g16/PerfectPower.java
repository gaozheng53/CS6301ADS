/*
 * <h1>Fall 2017 Short Project 6 - 3.1</h1>
 * <p>
 * Perfect powers: Write an algorithm to output exact powers (numbers of the
 * form a^b, b>1), in the range 2 to n.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-02
 */

package cs6301.g16;

import java.util.PriorityQueue;

public class PerfectPower implements Comparable<PerfectPower> {
    /**
     * Class to store a power element storing its value, base and exponent
     */
    private final Long value;
    private final int a; // base
    private final int b; // exponent

    public PerfectPower(int a, int b) {
        this.value = (long) Math.pow(a, b);
        this.a = a;
        this.b = b;
    }

    public int compareTo(PerfectPower o) {
        return value.compareTo(o.value);
    }

    /**
     * Main function for testing
     * @param args
     */
    public static void main(String[] args) {

        // input number to end the long loop.
        int n = 100;

        // initialize priority queue
        PriorityQueue<PerfectPower> pq = new PriorityQueue<>();

        // maximum value representable by long is 2^62
        // pre insert element with all the possible exponent
        for (int b = 2; b <= 62; b++) {
            pq.add(new PerfectPower(2, b));
        }

        // find smallest power, print out, and update
        int c = 0;
        long pre = 0;
        while (!pq.isEmpty() && c < n) {
            PerfectPower p = pq.remove();
            if (p.value != pre) { // skip element with same value
                System.out.print(p.value + " ");
                c++;
            }
            pre = p.value;
            // add next perfect power if it doesn't overflow a long
            if (Math.pow(p.a + 1, p.b) < Integer.MAX_VALUE)
                // add element back by increasing the base
                pq.add(new PerfectPower(p.a + 1, p.b));
        }
    }

}