/**  Java example: Simple class that stores just a single int
 *   @author rbk
 *  Ver 1.0: 2017/08/08
 */

package cs6301.g00;
public class Item implements Comparable<Item> {
    private int element;

    Item(int x) { element = x; }

    public int getItem() { return element; }

    public void setItem(int x) { element = x; }

    public int compareTo(Item another) {
	if (this.element < another.element) { return -1; }
	else if (this.element > another.element) { return 1; }
	else return 0;
    }

    public String toString() { return Integer.toString(element); }

    public static void main(String[] args) {
	final int n = 10;
	Item[] A = new Item[n];
	for(int i=0; i<n; i++) {
	    A[i] = new Item(i+1);
	}

	Item x = new Item(8);

	System.out.println("Binary search for 8: " + Search.binarySearch(A, 0, 9, x));
    }
}

/* Sample output:
Binary search for 8: true
*/
