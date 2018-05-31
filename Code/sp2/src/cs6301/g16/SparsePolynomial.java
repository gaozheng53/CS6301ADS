/**
 * <h1>Fall 2017 Short Project 2-9</h1>
 * <p>
 * Implement arithmetic with sparse polynomials, implementing the
 * following operations: addition, multiplication, evaluation.
 * Terms of the polynomial should be stored in a linked list, ordered by
 * the exponent field.  Implement multiplication without using HashMaps.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-05
 */

package cs6301.g16;

public class SparsePolynomial extends SinglyLinkedList<SparsePolynomial.Term> {

    /**
     * Internal Class, term of polynomial
     */
    static class Term implements Comparable<Term> {
        Double coefficient;
        Double exponent;

        Term(Term term) {
            this.coefficient = term.coefficient;
            this.exponent = term.exponent;
        }

        Term(double coefficient, double exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }

        int compareExponent(Term otherTerm) {
            return exponent.compareTo(otherTerm.exponent);
        }

        double evaluate(double x) {
            return coefficient*Math.pow(x,exponent);
        }

        @Override
        public String toString() {
            String str = coefficient>=0?"+":"";
            str +=coefficient.toString()+"x^("+exponent.toString()+")";
            return str;
        }

        public int compareTo(Term otherTerm) {
            return exponent.compareTo(otherTerm.exponent);
        }
    }

    /**
     * Constructors
     */
    public SparsePolynomial() { super();}

    public SparsePolynomial(SparsePolynomial poly) {
        super();
        for(Term term : poly)
            add(new Term(term));
    }

    public SparsePolynomial(Term[] terms) {
        super();
        // add terms
        for(Term term: terms)
            add(new Term(term));
        // format the polynomial as required
        format();
    }

    /**
     * Add the other formatted polynomials into this one
     * Also can be used as In-place addition
     * @param otherPoly the other polynomial to merge
     */
    private void inPlaceAdd (SparsePolynomial otherPoly) {
        Entry<Term> tc = header.next; // processing term pointer for this polynomial
        Entry<Term> oc = otherPoly.header.next; // processing term pointer for otherPoly
        Entry<Term> curTail = header; // keep track of the merge progress
        while(tc!=null && oc!=null) {
            if(tc.element.compareExponent(oc.element)==-1) {
                curTail.next = tc;
                tc = tc.next;
            }
            else if(tc.element.compareExponent(oc.element)==0) {
                // when the exponent are same, merge the two terms
                Term mergedTerm = new Term(tc.element.coefficient + oc.element.coefficient,tc.element.exponent);
                curTail.next = new Entry<>(mergedTerm,null);
                tc = tc.next;
                oc = oc.next;
            }
            else if(tc.element.compareExponent(oc.element)==1) {
                curTail.next = oc;
                oc = oc.next;
            }
            curTail = curTail.next;
        }
        if(tc==null&&oc!=null) { // attach the rest of otherPoly
            curTail.next = oc;
            tail = otherPoly.tail;
        }
        else if(tc!=null&&oc==null) { // attach the rest of this list, tail doesn't change
            curTail.next = tc;
        } else { // Both lists are ran out at the same time, curTail is the final tail
            tail = curTail;
        }
    }

    /**
     * Merge terms with same exponent and order terms by the exponent field
     */
    private void format() {
        // no need to do anything if the list is of size 1 or less
        if (size <= 1)
            return;

        // use divide and conquer to merge and sort the polynomial terms
        SparsePolynomial other = new SparsePolynomial();

        // config list properties for division
        other.tail = tail;
        int steps = size / 2;
        other.size = size - steps;
        size = steps;
        tail = header;

        for (int i = 0; i < steps; i++)
            tail = tail.next;

        other.header.next = tail.next;
        tail.next = null;

        // merge and sort recursively
        this.format();
        other.format();

        // add 2 polynomials together
        this.inPlaceAdd(other);
    }

    /**
     * Evaluate the polynomial by plugging in value x
     * @param x the value plugged in
     * @return the value of the polynomial when plug in x value
     */
    public double evaluation(double x) {
        double value = 0.0;
        for (Term term : this) {
            value += term.evaluate(x);
        }
        return value;
    }

    /**
     * Addition for 2 polynomial
     * @param poly1 polynomial 1
     * @param poly2 polynomial 2
     * @return result of addition
     */
    public static SparsePolynomial addition(SparsePolynomial poly1, SparsePolynomial poly2) {
        SparsePolynomial result = (new SparsePolynomial(poly1));
        result.inPlaceAdd(new SparsePolynomial(poly2));
        return result;
    }

    /**
     * Helper function for multiplication, multiply a term with this polynomial
     * @param term the term multiplied
     */
    private void multiplyByTerm(Term term) {
        for(Term t : this) {
            t.coefficient *= term.coefficient;
            t.exponent += term.exponent;
        }
    }

    /**
     * Multiplication of 2 polynomial
     * @param poly1 polynomial 1
     * @param poly2 polynomial 2
     * @return production of multiplication
     */
    public static SparsePolynomial multiplication(SparsePolynomial poly1, SparsePolynomial poly2) {
        SparsePolynomial product = new SparsePolynomial();

        for(Term term : poly2) {
            SparsePolynomial interResult = new SparsePolynomial(poly1);
            interResult.multiplyByTerm(term);
            product.inPlaceAdd(interResult);
        }

        return product;
    }

    @Override
    public String toString() {
        String str = "";
        for(Term term : this)
            str += term.toString();

        // remove the leading '+' if necessary
        if (str.length()>0 && str.charAt(0)=='+')
            str = str.substring(1);
        return str;
    }

    public static void main(String[] args) {

        Term[] terms1 = {
                new Term(-3,8),
                new Term(4,7)
        };

        SparsePolynomial poly1 = new SparsePolynomial(terms1);
        Term[] terms2 = {
                new Term(-10,0.5),
                new Term(1,1),
                new Term(100,-1)
        };

        SparsePolynomial poly2 = new SparsePolynomial(terms2);

        System.out.println("=======================");
        System.out.println("Polynomial 1:\n"+poly1);
        System.out.println("Polynomial 2:\n"+poly2);
        System.out.println("=======================");
        System.out.println("Evaluation:\n"+poly1);
        System.out.println("x="+4+" => y="+poly1.evaluation(-4));
        System.out.println("=======================");
        System.out.println("Addition Poly1+Poly2:\n"+addition(poly1,poly2));

        System.out.println("=======================");
        System.out.println("Multiplication Poly1*Poly2:");
        System.out.println(multiplication(poly1,poly2));

    }
}
