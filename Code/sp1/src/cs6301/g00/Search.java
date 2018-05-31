/** Implementation of binary search
 *  @author rbk
 *  Ver 1.0: 2017/08/08
 */

package cs6301.g00;
import java.util.Comparator;
import java.util.Random;
import java.util.Arrays;

public class Search {

    private static int size = 2000000;
    private static int trials = 2000000;
    private static int phase = 0;

    // Linear search; inefficient
    public static boolean linearSearch(int[] A, int p, int r, int x) {
	for(int i=p; i<=r; i++) {
	    if(A[i] == x) {
		return true;
	    }
	}
	return false;
    }

    /** Procedure to run binary search on a sorted array of integers
     * Return true if there is p <= q <= r, with A[q] = x, and false otherwise
     * Runs in time O(log n), where n = r-p+1
     *
     * @param A : int array.  Precondition: A is sorted.
     * If A is not sorted, behavior of procedure is arbitrary
     * @param p : int : left index of subarray of A
     * @param r : int : right index of subarray of A
     * @param x : int : element being searched
     * @return true if there exists q in [p..r] with A[q] = x, and false otherwise
     */
    public static boolean recursiveBinarySearch(int[] A, int p, int r, int x) {
	// Compare middle element of A[p..r] to x to decide which half of the array to search
	if(p <= r) {
	    int q = (p+r)/2;
	    if(A[q] < x) {  // A[q] is too small, x is not in left half
		return recursiveBinarySearch(A, q+1, r, x);
	    } else if (A[q] == x) {  // x found
		return true;
	    } else { // A[q] > x, so x is not in the right half
		return recursiveBinarySearch(A, p, q-1, x);
	    }
	} else { // empty array, return false
	    return false;
	}
    }

    // Iterative version of binary search
    public static boolean iterativeBinarySearch(int[] A, int p, int r, int x) {
	while(p <= r) {
	    int q = (p+r)/2;
	    if(A[q] < x) {  // A[q] is too small, x is not in left half
		p = q+1;
	    } else if (A[q] == x) {  // x found
		return true;
	    } else { // A[q] > x, so x is not in the right half
		r = q-1;
	    }
	}
	// x not found, return false
	return false;
    }


    /** Implementation of Binary Search algorithm using generics.  Use on any 
	array of objects with a comparator that is supplied.
     */
    public static<T> boolean  binarySearch(T[] A, int p, int r, T x, Comparator<? super T> c) {
	while(p <= r) {
	    int q = (p+r) >>> 1;
            int cmp = c.compare(A[q], x);
            if (cmp < 0) {
		p = q+1;
	    } else if (cmp == 0) {  // x found
		return true;
	    } else { // A[q] > x, so x is not in the right half
		r = q-1;
	    }
	}
	// x not found, return false
	return false;
    }

    /** Implementation of Binary Search algorithm using generics.  This can only be used on a
     *  type that implements Comparable itself.  Item class can use it, but ItemExt will not.
     */
    public static<T extends Comparable<T>> boolean badlyDeclaredBinarySearch(T[] A, int p, int r, T x) {
	while(p <= r) {
	    int q = (p+r) >>> 1;
            int cmp = A[q].compareTo(x);
            if (cmp < 0) {
		p = q+1;
	    } else if (cmp == 0) {  // x found
		return true;
	    } else { // A[q] > x, so x is not in the right half
		r = q-1;
	    }
	}
	// x not found, return false
	return false;
    }

    /** Implementation of Binary Search algorithm using generics.  Use on any type that
     *  implements Comparable in its inheritance hierarchy
     */
    public static<T extends Comparable<? super T>> boolean binarySearch(T[] A, int p, int r, T x) {
	while(p <= r) {
	    int q = (p+r) >>> 1;
            int cmp = A[q].compareTo(x);
            if (cmp < 0) {
		p = q+1;
	    } else if (cmp == 0) {  // x found
		return true;
	    } else { // A[q] > x, so x is not in the right half
		r = q-1;
	    }
	}
	// x not found, return false
	return false;
    }

    public static void main(String[] args) {
	int succ;
	int[] arr = new int[size];
	Integer[] iarr = new Integer[size];
	Random rand = new Random();
	for(int i=0; i<size; i++) {
	    arr[i] = rand.nextInt(10*size);
	}
	Arrays.sort(arr);

	for(int i=0; i<size; i++) {
	    iarr[i] = new Integer(arr[i]);
	}

	int[] searchKey = new int[trials];
	for(int i=0; i<trials; i++) {
	    searchKey[i] = rand.nextInt(10*size);
	}

	/* Search for many random elements to find time taken */

	succ = 0;
	System.out.println("\nSearching for " + trials/1000 + " elements: linear search...");
	Timer t = new Timer();
	for(int i=0; i<trials/1000; i++) {
	    int x = searchKey[i];
	    if(linearSearch(arr, 0, size-1, x)) { succ++; }
	}
	System.out.println("Successful searches: " + succ);
	System.out.println(t.end());

	succ = 0;
	System.out.println("\nSearching for " + trials + " elements: recursive binary search...");
	t.start();
	for(int i=0; i<trials; i++) {
	    int x = searchKey[i];
	    if(recursiveBinarySearch(arr, 0, size-1, x)) { succ++; }
	}
	System.out.println("Successful searches: " + succ);
	System.out.println(t.end());

	succ = 0;
	System.out.println("\nSearching for " + trials + " elements: iterative binary search...");
	t.start();
	for(int i=0; i<trials; i++) {
	    int x = searchKey[i];
	    if(iterativeBinarySearch(arr, 0, size-1, x)) { succ++; }
	}
	System.out.println("Successful searches: " + succ);
	System.out.println(t.end());

	succ = 0;
	System.out.println("\nSearching for " + trials + " elements iteratively (generic)...");
	t.start();
	for(int i=0; i<trials; i++) {
	    Integer x = new Integer(searchKey[i]);
	    if(binarySearch(iarr, 0, size-1, x)) { succ++; }
	}
	System.out.println("Successful searches: " + succ);
	System.out.println(t.end());
    }
}

/*
Sample output:

Searching for 2000 elements: linear search...
Successful searches: 169
Time: 1494 msec.
Memory: 57 MB / 124 MB.

Searching for 2000000 elements: recursive binary search...
Successful searches: 190750
Time: 414 msec.
Memory: 57 MB / 124 MB.

Searching for 2000000 elements: iterative binary search...
Successful searches: 190750
Time: 397 msec.
Memory: 57 MB / 124 MB.

Searching for 2000000 elements iteratively (generic)...
Successful searches: 190750
Time: 992 msec.
Memory: 80 MB / 157 MB.
*/
