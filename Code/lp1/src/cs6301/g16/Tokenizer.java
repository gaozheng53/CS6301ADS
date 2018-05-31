/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * A modified Tokenizer.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import java.util.Scanner;

public class Tokenizer {
    public static Token tokenize(String s) throws Exception {
        if (s.matches("\\d+")) {  // one or more digits
            return Token.NUM;
        } else if (s.matches("[a-z]")) {  // letter
            int index = s.charAt(0) - 'a';  // Convert var to index: a-z maps to 0-25
            return Token.VAR;
        } else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("^") || s.equals("|")) {
            return Token.OP;
        } else if (s.equals("=")) {
            return Token.EQ;
        } else if (s.equals("(")) {
            return Token.OPEN;
        } else if (s.equals(")")) {
            return Token.CLOSE;
        } else if (s.equals(";")) {
            return Token.EOL;
        } else if (s.equals("?")) {
            return Token.CTRL;
        } else {  // Error
            throw new Exception("Unknown token: " + s);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String s = in.next();
            Token t = tokenize(s);
            System.out.print(t + " ");
            if (t == Token.EOL) {
                System.out.println();
            }
        }
    }

    public enum Token {VAR, NUM, OP, EQ, OPEN, CLOSE, EOL, CTRL}
}