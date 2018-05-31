/**  Java inheritance example
 *   @author rbk
 *  Ver 1.0: 2017/08/08
 */

package cs6301.g00;
import java.util.Arrays;
import java.util.Comparator;

public class ItemExt extends Item implements Comparator<ItemExt> {
    static int n = 10;
    private int extra;

    ItemExt(int x) { super(x);  extra = -x; }

    public int compare(ItemExt a, ItemExt b) {
	if (a.extra < b.extra) { return -1; }
	else if (a.extra > b.extra) { return 1; }
	else return 0;
    }

    public static void main(String[] args) {
	ItemExt[] A = new ItemExt[n];
	for(int i=0; i<n; i++) {
	    A[i] = new ItemExt(i+1);
	}

	ItemExt x = new ItemExt(8);

	/* The following line throws an error message when compiled:

	System.out.println("Binary search for 8: " + Search.badlyDeclaredBinarySearch(A, 0, 9, x));
	ItemExt.java:23: error: method badlyDeclaredBinarySearch in class Search cannot be applied to given types;
	System.out.println("Binary search for 8: " + Search.badlyDeclaredBinarySearch(A, 0, 9, x));
	                                                   ^
	required: T[],int,int,T
	found: ItemExt[],int,int,ItemExt
	reason: inferred type does not conform to declared bound(s)
	inferred: ItemExt
	bound(s): Comparable<ItemExt>
	where T is a type-variable:
	T extends Comparable<T> declared in method <T>badlyDeclaredBinarySearch(T[],int,int,T)
	1 error

	But the following line compiles fine
	*/

	System.out.println("Binary search for 8: " + Search.binarySearch(A, 0, 9, x));

	Arrays.sort(A);
	System.out.print("Natural order: ");
	for(int i=0; i<n; i++) {
	    System.out.print(A[i] + " ");
	}
	System.out.println();

	Comparator<ItemExt> c = A[0];  // What??!!!!
	Arrays.sort(A, c);
	System.out.print("User defined order: ");
	for(int i=0; i<n; i++) {
	    System.out.print(A[i] + " ");
	}
	System.out.println();
    }
}

/* Sample output:
Binary search for 8: true
Natural order: 1 2 3 4 5 6 7 8 9 10 
User defined order: 10 9 8 7 6 5 4 3 2 1 
*/
