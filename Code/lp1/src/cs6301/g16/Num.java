/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Implement the class Num that stores and performs arithmetic operations on arbitrarily large
 * integers using {@code LinkedList}, where the digits are in the chosen base. Each node of the list
 * stores exactly one long integer. The base is defined to be 10 by default, but caller may modify
 * it. For base = 10, the number 4628 is represented by the list: 8-->2-->6-->4.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import java.util.*;

public class Num implements Comparable<Num> {

    /**
     * Constant ZERO.
     * <p>
     * The design of {@code ZERO} is to simplify some operations. However, introducing this constant
     * sometimes makes the situation more complicated. For example, {@code ZERO} has sign 0, forcing
     * the originally boolean sign variable to be int. Besides, {@code ZERO} has base 0. This
     * require of the asserion of {@code this.base == other.base} in the function must appear after
     * {@code isZero} is verified.
     */
    public static final Num ZERO = new Num((new LinkedList<>(Collections.singleton((long) 0))), 0, 0);

    /**
     * Constant for positive sign. Sign of zero can be access as ZERO.sign.
     */
    private static final int SIGN_POSITIVE = 1;

    /**
     * Constant for negative sign. Sign of zero can be access as ZERO.sign.
     */
    private static final int SIGN_NEGATIVE = -1;

    /**
     * Default base if not specified in constructor.
     */
    private static long defaultBase = 10;

    /**
     * Buffer used internally for some operations.
     */
    private static ArrayList<Long> buffer = new ArrayList<>();

    /**
     * Base of the number store internally.
     */
    private final long base;

    /**
     * The sign of the Num. -1 for negative, 0 for zero, 1 for positive number. Use int rather than
     * boolean could eliminate duplicate representation of zero. Final keyword requires the sign be
     * initialize in constructor.
     */
    private final int sign;

    /**
     * The number is store in LinkedList with most significant digit at the end of the LinkedList,
     * and the least significant digit at the beginning.
     */
    private LinkedList<Long> numList = new LinkedList<>();

    /**
     * Construct Num from String. No radix is specified, so the default base is used.
     *
     * @param s String representation of Num.
     */
    public Num(String s) {
        Num t = new Num(s, defaultBase);
        this.sign = t.sign;
        this.base = t.base;
        this.numList = t.numList;
    }

    /**
     * Construct Num from String with specific radix. The String representation consists of an
     * optional minus or plus sign followed by a sequence of one or more digits in the default radix
     * 10.
     *
     * @param s     String representation of Num.
     * @param radix Specific radix in long int.
     */
    public Num(String s, long radix) {
        int sign = SIGN_POSITIVE, cursor = 0;
        final int len = s.length();

        // Leading sign, if exists, must appear at beginning.
        int plusIndex = s.lastIndexOf('+');
        int minusIndex = s.lastIndexOf('-');
        if (plusIndex > 0 || minusIndex > 0)
            throw new NumberFormatException("Sign character in the middle.");

        // If leading sign exist, set cursor to 1.
        if (plusIndex >= 0 || minusIndex >= 0) {
            cursor = 1;

            // "-" at front, otherwise keep default value 1.
            if (minusIndex == 0) {
                sign = SIGN_NEGATIVE;
            }
        }

        // Skip leading zeros and compute number of digits in magnitude
        while (cursor < len && s.charAt(cursor) == '0') {
            cursor++;
        }

        if (cursor == len) {
            this.base = 0;
            this.sign = ZERO.sign;
            numList = ZERO.numList;
            return;
        }

        this.base = radix;
        this.sign = sign;
        Num ret = ZERO;

        for (int i = cursor; i <= s.length() - 1; i++) {
            ret = SingleDigitProduct(ret, 10);
            ret = add(ret, new Num(s.charAt(i) - '0', this.base));
        }
        this.numList = ret.numList;
    }

    /**
     * Internal constructor used to create Num with same numList and specific sign, such as abs() or
     * constant ZERO. Note that, the numList inside Num should be immutable in general. Operations
     * on the numList inside Num often leads to new Num with newly allocated numList (for example
     * add/subtract/product). Thus, copying the reference to the numList would be sufficient.
     *
     * @param list List allocated already.
     * @param sign New sign to the number.
     * @param base Radix of the number.
     */
    private Num(LinkedList<Long> list, int sign, long base) {
        this.base = base;
        this.sign = (list.size() == 0 ? ZERO.sign : sign);
        this.numList = list;
    }

    /**
     * Construct Num from long integer.
     *
     * @param x Long representation of Num.
     */
    public Num(long x) {
        this.base = defaultBase;
        if (x == 0) {
            this.sign = ZERO.sign;
            this.numList = ZERO.numList;
            return;
        }

        this.sign = x > 0 ? SIGN_POSITIVE : SIGN_NEGATIVE;

        x = Math.abs(x);
        while (x != 0) {
            numList.add((x % base));
            x = x / base;
        }
    }

    /**
     * Construct Num from long integer.
     *
     * @param x     Long representation of Num.
     * @param radix Radix of the Num.
     */
    public Num(long x, long radix) {
        this.base = radix;

        if (x == 0) {
            this.sign = ZERO.sign;
            this.numList = ZERO.numList;
            return;
        }

        this.sign = x > 0 ? SIGN_POSITIVE : SIGN_NEGATIVE;

        x = Math.abs(x);
        while (x != 0) {
            numList.add((x % this.base));
            x = x / this.base;
        }
    }

    /**
     * Alternative iterator of a {@code LinkedList}, return the number if there exists a next
     * number, otherwise return 0. Internal use only.
     *
     * @param it Iterator of the digit list.
     * @return The next digit if exist, otherwise {@code 0}.
     */
    private static Long zeroNext(Iterator<Long> it) {
        if (it.hasNext())
            return it.next();
        else
            return (long) 0;
    }

    /**
     * Return the Num with value of {@code (a + b)}.
     *
     * @param a First value in add operation.
     * @param b Second value in add operation.
     * @return {@code (a + b)}.
     */
    public static Num add(Num a, Num b) {

        if (b.isZero())
            return a;
        if (a.isZero())
            return b;

        assert a.base == b.base;

        if (a.sign == b.sign) {
            return new Num(add(a.numList, b.numList, a.base), a.sign, a.base());
        }

        int cmp = a.modCompareTo(b);
        if (cmp == 0)
            return ZERO;

        LinkedList<Long> result = cmp > 0 ? subtract(a.numList, b.numList, a.base)
                : subtract(b.numList, a.numList, a.base);

        return new Num(result, cmp == a.sign ? 1 : -1, a.base());
    }

    /**
     * Return the Num with value of {@code (a - b)}.
     *
     * @param a Value to be subtracted from.
     * @param b Value to be subtracted with.
     * @return {@code (a + b)}.
     */
    public static Num subtract(Num a, Num b) {
        if (b.isZero())
            return a;
        if (a.isZero())
            return b.negate();

        assert a.base == b.base;

        if (a.sign != b.sign)
            return new Num(add(a.numList, b.numList, a.base), a.sign, a.base());

        int cmp = a.modCompareTo(b);
        if (cmp == 0)
            return ZERO;

        LinkedList<Long> result = cmp > 0 ? subtract(a.numList, b.numList, a.base)
                : subtract(b.numList, a.numList, a.base);

        return new Num(result, cmp == a.sign ? SIGN_POSITIVE : SIGN_NEGATIVE, a.base());
    }

    /**
     * Internal implementation of two numList add with specific base. Return a newly allocated
     * LinkedList of the result.
     *
     * @param a    First {@code LinkedList} to add.
     * @param b    Second {@code LinkedList} to add.
     * @param base Base of each {@code LinkedList}. Since only the {@code LinkedList} is given, the
     *             base is required here.
     * @return Newly allocated {@code LinkedList} of {@code a + b}.
     */
    private static LinkedList<Long> add(List<Long> a, LinkedList<Long> b, long base) {

        Iterator<Long> it1 = a.iterator();
        Iterator<Long> it2 = b.iterator();

        long carry = 0;

        LinkedList<Long> result = new LinkedList<>();

        while (it1.hasNext() || it2.hasNext() || carry > 0) {
            long sum = zeroNext(it1) + zeroNext(it2) + carry;
            result.add(sum % base);
            carry = sum / base;
        }
        return result;
    }

    /**
     * Internal implementation of two numList subtract with specific base. Return a newly allocated
     * LinkedList of the result. Note that first numList a must be larger than b.
     *
     * @param a    First {@code LinkedList} to subtract from.
     * @param b    Second {@code LinkedList} to subtract with.
     * @param base Base of each {@code LinkedList}. Since only the {@code LinkedList} is given, the
     *             base is required here.
     * @return Newly allocated {@code LinkedList} of {@code a - b}.
     */
    private static LinkedList<Long> subtract(List<Long> a, LinkedList<Long> b, long base) {

        Iterator<Long> it1 = a.iterator();
        Iterator<Long> it2 = b.iterator();

        int borrow = 0;

        LinkedList<Long> result = new LinkedList<>();

        while (it1.hasNext() || it2.hasNext()) {
            long sub = zeroNext(it1) - zeroNext(it2) - borrow;
            borrow = sub < 0 ? 1 : 0;
            if (sub < 0) {
                sub += base;
                borrow = 1;
            }
            result.add(sub);
        }

        // remove leading zero
        Iterator<Long> it = result.descendingIterator();
        long last;
        while (it.hasNext()) {
            last = it.next();
            if (last != 0)
                break;
            it.remove();
        }

        // Since the caller has already verify that a > b, here the result shouldn't be zero.
        assert result.size() > 0;
        return result;
    }


    /**
     * Return the Num with value {@code (a * n)}, where n be a long integer.
     *
     * @param a Num to be multiplied.
     * @param n Long value to be multiplied.
     * @return {@code (a * n)},
     */
    static Num SingleDigitProduct(Num a, long n) {
        if (n == 0 || a.isZero())
            return ZERO;

        assert a.base != 0;

        LinkedList<Long> result = new LinkedList<>();
        long carry = 0;
        long product;
        for (Long x : a.numList) {
            product = x * n + carry;
            carry = product / a.base;
            result.add(product % a.base);
        }
        while (carry != 0) {
            result.add(carry % a.base);
            carry /= a.base;
        }
        return new Num(result, a.sign == Long.signum(n) ? SIGN_POSITIVE : SIGN_NEGATIVE, a.base());
    }

    /**
     * Standard n-squared algorithm for product. Return the Num with value {@code (a * b)}. This
     * function is used to validate the result of Karatsuba Algorithm product.
     *
     * @param a First Num in product.
     * @param b Second Num in product.
     * @return {@code (a * b)}.
     */
    static Num standardProduct(Num a, Num b) {

        // Return ZERO if a or b == 0.
        if (a.isZero() || b.isZero())
            return ZERO;

        assert a.base == b.base;

        // For all function call, adjust the first Num to be a shorter (or less) one.
        if (a.numList.size() > b.numList.size())
            return standardProduct(b, a);

        // After adjusting size, b shouldn't be zero at this point.
        assert b.sign != 0;

        // If a has only one element, directly call SingleDigitProduct(b, a).
        if (a.numList.size() == 1)
            return SingleDigitProduct(b, a.numList.get(0));

        // Otherwise, perform standard product with n square algorithm.
        Num ret = new Num("0");
        int shift = 0;
        Num singleDigitProduct;

        for (Long n : a.numList) {
            singleDigitProduct = SingleDigitProduct(b, n);
            singleDigitProduct.shift(shift);
            ret = Num.add(ret, singleDigitProduct);
            shift++;
        }
        return a.sign == b.sign ? new Num(ret.numList, SIGN_POSITIVE, a.base()) : new Num(ret.numList, SIGN_NEGATIVE, a.base());
    }

    /**
     * Karatsuba algorithm implementation for product. Return the Num with value {@code (a * b)}.
     * Use the unsigned version to compute result and deal with sign afterward.
     *
     * @param a First Num in product.
     * @param b Second Num in product.
     * @return {@code (a * b)}.
     */
    static Num Karatsuba(Num a, Num b) {
        // Return ZERO if a or b == 0.
        if (a.isZero() || b.isZero())
            return ZERO;

        Num absRet = KaratsubaUnsigned(a.abs(), b.abs());
        return new Num(absRet.numList, a.sign * b.sign, a.base());
    }

    /**
     * Unsigned version of Karatsuba algorithm for product. Return the Num with value {@code (a *
     * b)}.
     *
     * @param a First Num in product.
     * @param b Second Num in product.
     * @return {@code (a * b)}.
     */
    private static Num KaratsubaUnsigned(Num a, Num b) {

        // Return ZERO if a or b == 0.
        if (a.isZero() || b.isZero())
            return ZERO;

        assert a.numList.size() != 0;
        assert b.numList.size() != 0;

        // If one Num has only one digit, use SingleDigitProduct directly.
        if (a.numList.size() == 1)
            return SingleDigitProduct(b, a.numList.get(0));
        if (b.numList.size() == 1)
            return SingleDigitProduct(a, b.numList.get(0));

        // Otherwise, take the half of the smaller length of Num.
        int m = Integer.min(a.numList.size(), b.numList.size());
        int m2 = m / 2;

        // Allocate new LinkedList for split the Num in two.
        LinkedList<Long> alow = new LinkedList<>();
        LinkedList<Long> ahigh = new LinkedList<>();
        LinkedList<Long> blow = new LinkedList<>();
        LinkedList<Long> bhigh = new LinkedList<>();

        // Split both Num based on half length of the shorter one.
        split(a.numList, alow, ahigh, m2);
        split(b.numList, blow, bhigh, m2);

        // Generate lower and higher part of both a and b respectively.
        Num al = alow.size() == 0 ? ZERO : new Num(alow, SIGN_POSITIVE, a.base());
        Num ah = new Num(ahigh, SIGN_POSITIVE, a.base());
        Num bl = blow.size() == 0 ? ZERO : new Num(blow, SIGN_POSITIVE, a.base());
        Num bh = new Num(bhigh, SIGN_POSITIVE, a.base());

        assert al.numList.size() != 0;
        assert bl.numList.size() != 0;
        assert ah.numList.size() != 0;
        assert bh.numList.size() != 0;

        // Recursive call on Karatsuba Product itself.
        Num z0 = KaratsubaUnsigned(al, bl);
        Num z2 = KaratsubaUnsigned(ah, bh);
        Num z1 = KaratsubaUnsigned(add(ah, al), add(bh, bl));

        Num z3 = subtract(subtract(z1, z2), z0);

        z3.shift(m2);
        z2.shift(m2 * 2);

        return add(add(z2, z3), z0);
    }

    /**
     * Return the product of two {@code Num} instance {@code a} and {@code b}. Internally, Karatsuba
     * Algorithm is used.
     *
     * @param a First Num in product.
     * @param b Second Num in product.
     * @return {@code a * b}.
     */
    public static Num product(Num a, Num b) {
        return standardProduct(a, b);
    }

    /**
     * Split list x into two part, with first part in length k. Upon calling of this function, the k
     * should always be less then the length of x, i.e., split based on the shorted list when
     * perform production.
     *
     * @param x     The list to be split.
     * @param xlow  The fist part (lower significant part in Num) store.
     * @param xhigh The second part (higher significant part in Num) store.
     * @param k     The length of first part.
     */
    public static void split(LinkedList<Long> x, LinkedList<Long> xlow, LinkedList<Long> xhigh, int k) {
        int i = 0;
        long d;
        buffer.clear();
        Iterator<Long> it = x.iterator();
        while (i < k) {
            assert it.hasNext();

            i++;
            d = it.next();

            // Possible leading zero in lowe part, put in buffer first before another non-zero digit occured.
            if (d == 0) {
                buffer.add(d);
                continue;
            }

            // Non-zero digit, put all zero before to xlow, clear buffer and add current digit.
            xlow.addAll(buffer);
            buffer.clear();
            xlow.add(d);
        }
        buffer.clear();

        // No leading zero should appear with high part.
        while (it.hasNext())
            xhigh.add(it.next());

    }

    /**
     * Unsigned version of divide.
     *
     * @param a Dividend.
     * @param b Divisor.
     * @return {@code a / b}.
     */
    static Num DivideUnsigned(final Num a, final Num b) {
        Num ONE = new Num(1, a.base());
        if (b.isZero())
            throw new ArithmeticException("divide by zero");

        if (b.compareTo(ONE) == 0)
            return a;

        if (b.abs().compareTo(a.abs()) > 0)
            return ZERO;

        Num ret;
        Num left = ONE;
        Num right = a;
        while (true) {
            ret = add(left, right).getHalf();
            if (product(ret, b).compareTo(a) > 0) {
                right = ret;
                continue;
            }
            if (product(add(ret, ONE), b).compareTo(a) <= 0) {
                left = ret;
                continue;
            }
            break;
        }

        return ret;
    }

    /**
     * Return the Num with value of {@code a / b}.
     *
     * @param a Dividend.
     * @param b Divisor.
     * @return {@code a / b}.
     */
    public static Num divide(final Num a, final Num b) {
        return a.sign == b.sign ? DivideUnsigned(a.abs(), b.abs()) : DivideUnsigned(a.abs(), b.abs()).negate();
    }

    /**
     * Return the Num with value of {@code b^n}.
     *
     * @param b Base in format Num.
     * @param n Exponent in format long integer.
     * @return {@code b^n}.
     */
    static Num power(Num b, long n) {
        Num ONE = new Num(1, b.base());
        if (n == 0)
            return ONE;

        if (n == 1)
            return b;

        Num s = power(b, n / 2);
        // n is even
        if (n % 2 == 0)
            return product(s, s);
        else
            return product(s, product(s, b));
    }

    /**
     * Return the Num with value of {@code a % b}.
     *
     * @param a Dividend.
     * @param b Divisor.
     * @return {@code a % b}.
     */
    public static Num mod(final Num a, final Num b) {
        return subtract(a, product(b, divide(a, b)));
    }

    /**
     * Return the Num with value of {@code b^n}.
     *
     * @param b Base in format Num.
     * @param n Exponent in format Num.
     * @return {@code b^n}.
     */
    public static Num power(Num b, Num n) {
        Num ONE = new Num(1, b.base());
        if (n.isZero())
            return ONE;

        if (n.numList.size() == 1)
            return power(b, n.numList.get(0));

        long a0 = n.numList.get(0);
        n.shift(-1);
        return product(power(power(b, n), n.base), power(b, a0));
    }

    /**
     * Return the Num with value of {@code sqrt(a)}.
     *
     * @param a Number to perform square root.
     * @return {@code sqrt(a)}.
     */
    static Num squareRoot(final Num a) {
        if (a.sign < 0)
            throw new ArithmeticException("square root of non-negative number.");

        Num ONE = new Num(1, a.base());

        if (a.compareTo(ONE) == 0)
            return a;

        Num ret = null;
        Num left = ZERO;
        Num right = a;
        while (left.compareTo(right) <= 0) {
            ret = add(left, right).getHalf();

            assert ret.base() == a.base();

            if (power(ret, 2).compareTo(a) > 0) {
                right = ret;
                continue;
            }
            if (power(add(ret, ONE), 2).compareTo(a) <= 0) {
                left = ret;
                continue;
            }
            break;
        }

        return ret == null ? ZERO : ret;
    }

    /**
     * Returns a Num whose value is {@code (-this)}.
     *
     * @return {@code -this}
     */
    public Num negate() {
        return new Num(this.numList, -this.sign, this.base());
    }

    /**
     * Returns a Num whose value is {@code abs(this)}.
     *
     * @return {@code abs(this)}
     */
    public Num abs() {
        return new Num(this.numList, SIGN_POSITIVE, this.base());
    }

    /**
     * Shift current num specific digit. Internal use only.
     *
     * @param n Number of digits to shift. If {@code n > 0}, right shift (i.e., {@code num * base ^ n}), if
     *          {@code n < 0}, left shift (i.e., {@code num / (base ^ n)})
     * @return The newly allocated number after shifting.
     */
    Num shift(int n) {
        if (this.isZero())
            return this;
        if (n > 0) {
            for (int i = 1; i <= n; i++)
                this.numList.add(0, (long) 0);
            return this;
        }
        if (n < 0) {
            for (int i = n; i < 0; i++)
                this.numList.remove(0);
        }
        return this;
    }

    /**
     * Compare the numList with other Num, ignoring sign.
     *
     * @param other The other numList to be compared to.
     * @return -1, 0 or 1 as this numList is less than, equal to or greater than the other numList.
     */
    private int modCompareTo(Num other) {
        if (this.numList.size() > other.numList.size())
            return 1;
        if (this.numList.size() < other.numList.size())
            return -1;

        Iterator<Long> it1 = this.numList.iterator();
        Iterator<Long> it2 = other.numList.iterator();
        int result = 0, dresult;
        while (it1.hasNext()) {
            dresult = zeroNext(it1).compareTo(zeroNext(it2));
            if (dresult != 0)
                result = dresult;
        }
        return result;
    }

    /**
     * compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise.
     *
     * @return {@code +1} if this is greater, {@code 0} if equal, {@code -1} otherwise.
     */
    public int compareTo(Num other) {
        if (this.isZero())
            return -other.sign;
        if (other.isZero())
            return this.sign;

        assert base == other.base;

        if (this.sign == other.sign) {
            switch (this.sign) {
                case 1:
                    return modCompareTo(other);
                case -1:
                    return other.modCompareTo(this);
                default:
                    return 0;
            }
        }
        return this.sign > other.sign ? 1 : -1;
    }

    /**
     * Output using the format "base: elements of list ..." For example, if base=100, and the number
     * stored corresponds to 10965, then the output is "100: 65 9 1"
     */
    public void printList() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.base).append(":");

        for (long i : this.numList)
            stringBuilder.append(' ').append(i);

        System.out.println(stringBuilder.toString());
    }

    /**
     * An internal function used by {@code divide} and {@code squareRoot}, for binary search
     * method.
     *
     * @return A new Num with value half of current Num.
     */
    public Num getHalf() {
        if (this.isZero())
            return this;

        // If the base is even, multiply by half base and left shift.
        if (this.base / 2 * 2 == this.base)
            return Num.SingleDigitProduct(this, this.base / 2).shift(-1);

        // Otherwise, manually compute the half number from left to right (higher significance to lower significance).
        Iterator<Long> it = this.numList.descendingIterator();

        LinkedList<Long> halfList = new LinkedList<>();

        long remain = 0;
        long pre;
        long cur;
        boolean leadingZero = true;

        while (it.hasNext()) {
            pre = it.next() + this.base * remain;
            cur = pre / 2;

            assert cur < this.base;

            // If leading zero, do not put in linked list.
            leadingZero = leadingZero && cur == 0;

            if (!leadingZero)
                halfList.add(0, cur);
            remain = pre - cur * 2;
        }
        return new Num(halfList, this.sign, this.base);
    }

    /**
     * Return whether current number is {@code ZERO}.
     *
     * @return {@code true} if current number is {@code ZERO}, and {@code false} otherwise.
     */
    public boolean isZero() {
        return this.sign == ZERO.sign;
    }

    /**
     * Return number to a string in base 10.
     *
     * @return Current number in a string of base 10.
     */
    @Override
    public String toString() {
        Num ret = new Num(0);

        Iterator<Long> it = this.numList.descendingIterator();

        while (it.hasNext()) {
            ret = add(SingleDigitProduct(ret, this.base()), new Num(it.next()));
        }

        it = ret.numList.descendingIterator();

        if (this.sign == 0)
            return "0";

        StringBuilder stringBuilder = new StringBuilder(ret.numList.size() + 1);

        if (this.sign == SIGN_NEGATIVE)
            stringBuilder.append('-');

        while (it.hasNext())
            stringBuilder.append(it.next());

        return stringBuilder.toString();
    }

    /**
     * Return current number's base.
     *
     * @return Current number's base.
     */
    public long base() {
        return this.base;
    }
}
