/** Java example to illustrate user-defined Iterator
 *  @author rbk
 *  Ver 1.0: 2017/08/08
 *  Code is complex, just to illustrate possibilities.
 **/

package cs6301.g00;
import java.util.Iterator;

public class IteratorExample implements Iterable<Item> {
    private static int ten = 10;
    boolean evens;
    Item[] arr;
    int size;

    /** Constructor
     *  @param x : int : value of element
     */
    IteratorExample(int s) {
	size = s;
	arr = new Item[size];
	evens = true;
    }


    public Iterator<Item> iterator() { return new ItemIterator<>(); }

    /** Iterator that iterates over the even indexed elements or odd indexed elements
     *  of the array "arr", depending on the value of boolean "evens".
     *  Odd indexed elements are iterated in descending order.
     *  Normally we would use "Item" instead of "T" in the following class
     */
    private class ItemIterator<T> implements Iterator<T> {
	int index;  // stores the state of the iterator
	ItemIterator() {
	    if(evens) {
		index = 0;
	    } else {
		index = size - 1;
		if(index%2 == 0) { index--;}
	    }
	}

	public boolean hasNext() { 
	    if(evens) {
		return index < size;
	    } else {
		return index > 0;
	    }
	}

	public T next() {
	    T rv = (T) arr[index];  // Casting needed due to type erasure
	    if(evens) {
		index += 2;
	    } else {
		index -= 2;
	    }
	    return rv;
	}
	public void remove() { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {
	IteratorExample y = new IteratorExample(ten);
	y.illustrateIterator(ten);
    }

    private void illustrateIterator(int size) {
	System.out.println("\nIterator illustration:");
	for(int i=0; i<size; i++) {
	    arr[i] = new Item(i);
	}

	evens = true;
	System.out.println("Iterating with evens = true");
	for(Item x:this) { System.out.println(x); }

	System.out.println("\nIterating with evens = false");
	evens = false;
	for(Item x:this) { System.out.println(x); }
    }
}

/*
Sample output:

Iterator illustration:
Iterating with evens = true
0
2
4
6
8

Iterating with evens = false
9
7
5
3
1
*/
