/**
 * <h1>Fall 2017 Short Project 4-2</h1>
 * <p>
 * Write functions to compute the nth Fibonacci number and compare
 * their running times.  Since the numbers grow fast, use BigInteger
 * class to represent the numbers.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */
package cs6301.g16;
import java.math.BigInteger;

public class Fibonacci {
    /**
     * Fibonacci Using Dynamic Programming to compute nth Fibonacci number
     *
     * @param n target compute number
     * @return nth Fibonacci number
     */
    static BigInteger linearFibonacci(int n) {
        BigInteger[] fibo = new BigInteger[n + 1];  // stores value in an array
        fibo[0] = BigInteger.ZERO;
        fibo[1] = BigInteger.ONE;
        for (int i = 2; i < fibo.length; i++) {
            fibo[i] = fibo[i - 1].add(fibo[i - 2]);
        }
        return fibo[n];
    }

    /**
     * Fibonacci Using matrix to compute nth Fibonacci number
     *
     * @param n target compute number
     * @return nth Fibonacci number
     */
    public static BigInteger logFibonacci(int n) {
        BigInteger F[][] = new BigInteger[][]{{BigInteger.valueOf(1), BigInteger.valueOf(1)}, {BigInteger.valueOf(1), BigInteger.valueOf(0)}};
        if (n == 0)
            return BigInteger.ZERO;
        power(F, n - 1);
        return F[0][0];
    }

    /**
     * Compute the n power of F matrix using Divide-and-Conquer
     *
     * @param F base matrix
     * @param n exponent
     */
    public static void power(BigInteger F[][], int n) {
        if (n == 0 || n == 1)
            return;
        BigInteger M[][] = new BigInteger[][]{{BigInteger.valueOf(1), BigInteger.valueOf(1)}, {BigInteger.valueOf(1), BigInteger.valueOf(0)}};
        power(F, n / 2);
        matrixMultiply(F, F);
        if (n % 2 != 0)
            matrixMultiply(F, M);
    }

    /**
     * Compute the multiply of two matrices
     *
     * @param F first matrix
     * @param M second matrix
     */
    public static void matrixMultiply(BigInteger F[][], BigInteger M[][]) {
        BigInteger ul = F[0][0].multiply(M[0][0]).add(F[0][1].multiply(M[1][0]));
        BigInteger ur = F[0][0].multiply(M[0][1]).add(F[0][1].multiply(M[1][1]));
        BigInteger ll = F[1][0].multiply(M[0][0]).add(F[1][1].multiply(M[1][0]));
        BigInteger lr = F[1][0].multiply(M[0][1]).add(F[1][1].multiply(M[1][1]));

        F[0][0] = ul;
        F[0][1] = ur;
        F[1][0] = ll;
        F[1][1] = lr;
    }

    /**
     * Main test function
     *
     * @param args A string array containing the command line arguments.
     */
    public static void main(String[] args) {
        int num = 23255;
        long startTime = System.currentTimeMillis();   //Test linear method's running time
        System.out.println(linearFibonacci(num).toString());
        System.out.println("linear Fibonacci running time is： " + (System.currentTimeMillis() - startTime) + "ms");
        long startTime2 = System.currentTimeMillis();
        System.out.println(logFibonacci(num).toString());
        System.out.println("matrix Fibonacci running time is： " + (System.currentTimeMillis() - startTime2) + "ms");
    }
}