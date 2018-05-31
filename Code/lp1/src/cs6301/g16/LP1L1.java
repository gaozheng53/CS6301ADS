/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Driver function 1.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

public class LP1L1 {
    public static void main(String[] args) {
        Num x = new Num(999);
        Num y = new Num("8");
        Num z = Num.add(x, y);
        System.out.println(z);
        Num a = Num.power(x, 8);
        System.out.println(a);
        z.printList();
    }
}
