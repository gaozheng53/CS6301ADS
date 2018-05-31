/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Unit test case for supporting class ShuntingYard.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class ShuntingYardTest {
    @Test
    public void infixToPostfix() throws Exception {
        ArrayList<String> in0 = new ArrayList<>(Arrays.asList("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3".split(" ")));
        String[] po0 = new String[]{"3", "4", "2", "*", "1", "5", "-", "2", "3", "^", "^", "/", "+"};
        assertArrayEquals(po0, ShuntingYard.InfixToPostfix(in0).toArray(new String[1]));

        ArrayList<String> ina = new ArrayList<>(Arrays.asList("a + b * c / ( d - e ) ^ f ^ g".split(" ")));
        String[] poa = new String[]{"a", "b", "c", "*", "d", "e", "-", "f", "g", "^", "^", "/", "+"};
        assertArrayEquals(poa, ShuntingYard.InfixToPostfix(ina).toArray(new String[1]));

        ArrayList<String> in1 = new ArrayList<>(Arrays.asList("1 + 2 * ( 3 - 4 ) / 1".split(" ")));
        String[] po1 = new String[]{"1", "2", "3", "4", "-", "*", "1", "/", "+"};
        assertArrayEquals(po1, ShuntingYard.InfixToPostfix(in1).toArray(new String[1]));

        ArrayList<String> in2 = new ArrayList<>(Arrays.asList("1 % 2 + 2 * 3 - 4 / 2 ^ 20 * 20 |".split(" ")));
        String[] po2 = new String[]{"1", "2", "%", "2", "3", "*", "+", "4", "2", "20", "20", "|", "*", "^", "/", "-"};
        assertArrayEquals(po2, ShuntingYard.InfixToPostfix(in2).toArray(new String[1]));

        List<String> in3 = new ArrayList<>(Arrays.asList("( ( 9 + 5 * 3 ) * 2 + 1 * ( 8 - 6 * 4 ) ) * 7 + 1".split(" ")));
        List<String> a =  ShuntingYard.InfixToPostfix(in3);
        System.out.print(Arrays.toString(ShuntingYard.InfixToPostfix(in3).toArray(new String[1])));
//        String[] po3 = new String[]{"1", "2", "%", "2", "3", "*", "+", "4", "2", "20", "20", "|", "*", "^", "/", "-"};
//        assertArrayEquals(po2, ShuntingYard.InfixToPostfix(in2).toArray(new String[1]));
    }

}