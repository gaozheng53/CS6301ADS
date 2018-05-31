/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Supporting class of to transform in fix expression into post fix expression.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ShuntingYard {

    /**
     * Transform in fix expression to post fix expression using Shunting-Yard algorithm.
     *
     * @param in The in fix expression in format of {@code List<String>}.
     */
    static ArrayList<String> InfixToPostfix(List<String> in) throws Exception {

//        while there are tokens to be read:
//            read a token.
//            if the token is a number, then push it to the output queue.
//            if the token is an operator, then:
//                while there is an operator at the top of the operator stack with
//                    greater than or equal to precedence and the operator is left associative:
//                        pop operators from the operator stack, onto the output queue.
//                push the read operator onto the operator stack.
//            if the token is a left bracket (i.e. "("), then:
//                push it onto the operator stack.
//            if the token is a right bracket (i.e. ")"), then:
//                while the operator at the top of the operator stack is not a left bracket:
//                    pop operators from the operator stack onto the output queue.
//                pop the left bracket from the stack.
//                /* if the stack runs out without finding a left bracket, then there are
//                mismatched parentheses. */
//        if there are no more tokens to read:
//            while there are still operator tokens on the stack:
//                /* if the operator token on the top of the stack is a bracket, then
//                there are mismatched parentheses. */
//                pop the operator onto the output queue.
//        exit.

        ArrayDeque<String> output = new ArrayDeque<>(in.size());
        ArrayDeque<String> operator = new ArrayDeque<>();

        for (String token : in) {
            if (Tokenizer.tokenize(token) == Tokenizer.Token.NUM || Tokenizer.tokenize(token) == Tokenizer.Token.VAR) {
                output.offer(token);
                continue;
            }

            if (Tokenizer.tokenize(token) == Tokenizer.Token.OP) {
                while (operator.peek() != null && priority(operator.peek()) >= priority(token) && leftAssociative(operator.peek())) {
                    output.offer(operator.pop());
                }
                operator.push(token);
                continue;
            }

            if (Tokenizer.tokenize(token) == Tokenizer.Token.OPEN) {
                operator.push(token);
                continue;
            }

            if (Tokenizer.tokenize(token) == Tokenizer.Token.CLOSE) {
                while (operator.peek() != null && Tokenizer.tokenize(operator.peek()) != Tokenizer.Token.OPEN)
                    output.offer(operator.pop());
                String close = operator.pop();
                assert Tokenizer.tokenize(close) == Tokenizer.Token.OPEN;
            }
        }

        while (operator.peek() != null) {
            output.offer(operator.pop());
        }

        return new ArrayList<>(output);
    }

    private static int priority(String s) {
        if (s.equals("+") || s.equals("-"))
            return 1;
        if (s.equals("*") || s.equals("/") || s.equals("%"))
            return 2;
        if (s.equals("^"))
            return 3;
        if (s.equals("|"))
            return 4;
        return 0;
    }

    private static boolean leftAssociative(String s) {
        if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("|"))
            return Boolean.TRUE;
        assert s.equals("^");
        return Boolean.FALSE;
    }
}
