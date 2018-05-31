/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Supporting class of driver function.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import java.util.*;

/**
 * Each line of input is stored in a class which implemented the {@code ExecutableLine} interface.
 * The main interface function is {@code execute()}, which execute the current line and return the
 * next line number.
 */
public interface ExecutableLine {

    /**
     * Return the value representing current {@code ExecutableLine} (left value of a in/post fix
     * expression or the condition value for the control expression).
     *
     * @return The Num representing current {@code ExecutableLine}.
     */
    Num getNum();

    /**
     * Execute current line and return the next line number. If no specific line number, return -1.
     *
     * @return Next line number.
     */
    int execute();

    enum LineType {POSFIX, INFIX, CTRL}

    /**
     * Print current line result to standard output.
     */
    void print();

    /**
     * Class to store the post fix expression line. A post fix expression consists of two part, the
     * left value (which should be a variable), and the right value (which should be an {@code
     * Expression} instance). To execute a line, evaluate the right value and assign the value (in a
     * {@code Num} instance) to the left value.
     */
    class PosFixExpressionLine implements ExecutableLine {

        /**
         * The left value of the post fix expression.
         */
        private Expression.Var left;

        /**
         * The right expression of the post fix expression.
         */
        private Expression right = null;

        /**
         * Postfix expression constructor from an {@code ArrayList} of {@code Token} in {@code
         * String} format.
         *
         * @param line Array of token in String for a line.
         */
        public PosFixExpressionLine(ArrayList<String> line, List<Num> store, long base) throws Exception {
            assert Tokenizer.tokenize(line.get(0)) == Tokenizer.Token.VAR;

            this.left = new Expression.Var(line.get(0).charAt(0), store);

            if (line.size() == 1)
                return;

            assert line.size() > 2;
            assert Tokenizer.tokenize(line.get(1)) == Tokenizer.Token.EQ;

            Deque<Expression> expressions = new ArrayDeque<>();
            for (int i = 2; i < line.size(); i++) {
                if (line.get(i).matches("[a-z]"))
                    expressions.push(new Expression.Var(line.get(i).charAt(0), store));
                else if (line.get(i).matches("\\d+"))
                    expressions.push(new Expression.Number(line.get(i), base));
                else {

                    assert Tokenizer.tokenize(line.get(i)) == Tokenizer.Token.OP;

                    switch (line.get(i)) {
                        case "+":
                            expressions.push(new Expression.Plus(expressions.pop(), expressions.pop()));
                            break;
                        case "-":
                            expressions.push(new Expression.Minus(expressions.pop(), expressions.pop()));
                            break;
                        case "*":
                            expressions.push(new Expression.Product(expressions.pop(), expressions.pop()));
                            break;
                        case "/":
                            expressions.push(new Expression.Divide(expressions.pop(), expressions.pop()));
                            break;
                        case "%":
                            expressions.push(new Expression.Mod(expressions.pop(), expressions.pop()));
                            break;
                        case "^":
                            expressions.push(new Expression.Power(expressions.pop(), expressions.pop()));
                            break;
                        case "|":
                            expressions.push(new Expression.SquareRoot(expressions.pop()));
                            break;
                    }
                }
            }
            assert expressions.size() == 1;

            this.right = expressions.pop();
        }

        @Override
        public Num getNum() {
            return left.getValue();
        }

        @Override
        public int execute() {
            if (this.right != null)
                this.left.assign(this.right.getValue());
            this.print();
            return -1;
        }

        @Override
        public void print() {
            assert this.left != null;
            System.out.println(this.left.getValue());
        }
    }

    /**
     * Class to store the in fix expression line. A in fix expression consists of three part, the
     * specific line number, the left value (which should be a variable), and the right value (which
     * should be an {@code Expression} instance). To execute a line, evaluate the right value and
     * assign the value (in a {@code Num} instance) to the left value.
     */
    class InFixExpressionLine implements ExecutableLine {

        /**
         * The left value of the in fix expression.
         */
        private Expression.Var left;

        /**
         * The right expression of the in fix expression.
         */
        private Expression right = null;

        /**
         * The specific line number of the in fix expression.
         */
        private Map<Integer, Integer> lineno;

        /**
         * Infix expression constructor from an {@code ArrayList} of {@code Token} in {@code String}
         * format.
         *
         * @param line Array of token in String for a line.
         */
        public InFixExpressionLine(ArrayList<String> line, List<Num> store, Map<Integer, Integer> lineno, int actLine, long base) throws Exception {

            assert Tokenizer.tokenize(line.get(0)) == Tokenizer.Token.NUM;
            this.lineno = lineno;
            this.lineno.put(Integer.valueOf(line.get(0)), actLine);

            assert Tokenizer.tokenize(line.get(1)) == Tokenizer.Token.VAR;
            this.left = new Expression.Var(line.get(1).charAt(0), store);

            assert line.size() > 2;
            assert Tokenizer.tokenize(line.get(2)) == Tokenizer.Token.EQ;

            List<String> infix = line.subList(3, line.size());
            List<String> posfix = ShuntingYard.InfixToPostfix(infix);

            Deque<Expression> expressions = new ArrayDeque<>();

            for (String e : posfix) {
                if (e.matches("[a-z]"))
                    expressions.push(new Expression.Var(e.charAt(0), store));
                else if (e.matches("\\d+"))
                    expressions.push(new Expression.Number(e, base));
                else {

                    assert Tokenizer.tokenize(e) == Tokenizer.Token.OP;

                    switch (e) {
                        case "+":
                            expressions.push(new Expression.Plus(expressions.pop(), expressions.pop()));
                            break;
                        case "-":
                            expressions.push(new Expression.Minus(expressions.pop(), expressions.pop()));
                            break;
                        case "*":
                            expressions.push(new Expression.Product(expressions.pop(), expressions.pop()));
                            break;
                        case "/":
                            expressions.push(new Expression.Divide(expressions.pop(), expressions.pop()));
                            break;
                        case "%":
                            expressions.push(new Expression.Mod(expressions.pop(), expressions.pop()));
                            break;
                        case "^":
                            expressions.push(new Expression.Power(expressions.pop(), expressions.pop()));
                            break;
                        case "|":
                            expressions.push(new Expression.SquareRoot(expressions.pop()));
                            break;
                    }
                }
            }

            assert expressions.size() == 1;

            this.right = expressions.pop();
        }

        @Override
        public Num getNum() {
            return left.getValue();
        }

        @Override
        public int execute() {
            if (this.right != null)
                this.left.assign(this.right.getValue());
            return -1;
        }

        @Override
        public void print() {
            assert this.left != null;
            System.out.println(this.left.getValue());
        }
    }

    /**
     * Class to store the control line. A control line consists of three part, the specific line
     * number, the condition variable, and the next line number whether the condition hold or not.
     * To execute a line, evaluate the condition variable, if the number is not zero, execute
     * specific line and otherwise the other line (if exist). If no other line is specified, execute
     * the directly next line in original sense. to the left value.
     */
    class ControlLine implements ExecutableLine {
        private Expression.Var condition;
        private int pos;
        private int neg = -1;
        private Map<Integer, Integer> lineno;

        public ControlLine(ArrayList<String> line, List<Num> store, Map<Integer, Integer> lineno, int actLine) throws Exception {

            assert Tokenizer.tokenize(line.get(0)) == Tokenizer.Token.NUM;
            this.lineno = lineno;
            this.lineno.put(Integer.valueOf(line.get(0)), actLine);

            assert Tokenizer.tokenize(line.get(1)) == Tokenizer.Token.VAR;
            this.condition = new Expression.Var(line.get(1).charAt(0), store);

            assert Tokenizer.tokenize(line.get(2)) == Tokenizer.Token.CTRL;
            assert line.size() >= 4;
            this.pos = Integer.valueOf(line.get(3));

            if (line.size() > 5) {
                assert line.get(4).equals(":");
                this.neg = Integer.valueOf(line.get(5));
            }
        }

        @Override
        public int execute() {
            if (this.condition.getValue().compareTo(Num.ZERO) != 0)
                return this.lineno.get(this.pos);
            return this.lineno.get(this.neg);
        }

        @Override
        public Num getNum() {
            return condition.getValue();
        }

        @Override
        public void print() {
            assert this.condition != null;
            System.out.println(this.condition.getValue());
        }
    }
}
