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

import java.util.List;

/**
 * Each expression is stored in a class implemented the {@code Expression} interface. The main
 * interface function is {@code getValue()}, which evaluate current expression and return the result
 * in a {@code Num} instance.
 */
public interface Expression {

    /**
     * Evaluate current expression and return the result in a {@code Num} instance.
     *
     * @return The evaluation result of current {@code Expression} instance.
     */
    Num getValue();

    /**
     * The class to store a variable expression.
     */
    class Var implements Expression {

        /**
         * The variable name in a {@code char}.
         */
        private char varname;

        /**
         * The store context of current variable.
         */
        private List<Num> store = null;

        /**
         * Construct a {@code Var} instance with specific name and store.
         *
         * @param varname The variable's name.
         * @param store   The store context of the variable.
         */
        public Var(char varname, List<Num> store) {
            this.varname = varname;
            this.store = store;
        }

        /**
         * Assign the current variable with specific value in format {@code Num}.
         *
         * @param right The right value to be assigned.
         */
        void assign(Num right) {
            store.set(varname - 'a', right);
        }

        @Override
        public Num getValue() {
            return this.store.get(this.varname - 'a');
        }
    }

    /**
     * The class to store a constance number expression.
     */
    class Number implements Expression {

        /**
         * Constant number value.
         */
        cs6301.g16.Num num;

        /**
         * Generate a constant number from a string.
         *
         * @param s The constant number in string format.
         */
        public Number(String s) {
            this.num = new Num(s);
        }

        /**
         * Generate a constant number from a string and specific radix.
         *
         * @param s     The constant number in string format.
         * @param radix The radix used to store the constant.
         */
        public Number(String s, long radix) {
            this.num = new Num(s, radix);
        }

        @Override
        public Num getValue() {
            return this.num;
        }
    }

    /**
     * The class to store a plus expression.
     */
    class Plus implements Expression {

        Expression a;
        Expression b;

        public Plus(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.add(a.getValue(), b.getValue());
        }
    }


    /**
     * The class to store a minus expression.
     */
    class Minus implements Expression {
        Expression a;
        Expression b;

        public Minus(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.subtract(a.getValue(), b.getValue());
        }
    }


    /**
     * The class to store a product expression.
     */
    class Product implements Expression {

        Expression a;
        Expression b;

        public Product(Expression a, Expression b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.product(a.getValue(), b.getValue());
        }
    }


    /**
     * The class to store a divide expression.
     */
    class Divide implements Expression {

        Expression a;
        Expression b;

        public Divide(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.divide(a.getValue(), b.getValue());
        }
    }


    /**
     * The class to store a mod expression.
     */
    class Mod implements Expression {

        Expression a;
        Expression b;

        public Mod(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.mod(a.getValue(), b.getValue());
        }
    }


    /**
     * The class to store a power expression.
     */
    class Power implements Expression {

        Expression b;
        Expression n;

        public Power(Expression n, Expression b) {
            this.b = b;
            this.n = n;
        }

        @Override
        public Num getValue() {
            return Num.power(b.getValue(), n.getValue());
        }
    }


    /**
     * The class to store a square root expression.
     */
    class SquareRoot implements Expression {

        Expression a;

        public SquareRoot(Expression a) {
            this.a = a;
        }

        @Override
        public Num getValue() {
            return Num.squareRoot(a.getValue());
        }
    }
}
