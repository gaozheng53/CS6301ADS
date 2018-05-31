/**
 * <h1>Fall 2017 Short Project 2-2</h1>
 * <p>
 * Write the Merge sort algorithm that works on linked lists.  This will
 * be a member function of a linked list class, so that it can work with
 * the internal details of the class.  The function should use only
 * O(log n) extra space (mainly for recursion), and not make copies of
 * elements unnecessarily.  You can start from the SinglyLinkedList class
 * provided or create your own.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-05
 */

package cs6301.g16;

public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {

    /**
     * Helper function for merge sort - merge current list with another list
     * Assuming 2 lists are both sorted
     * @param otherList
     */
	void merge(SortableList<T> otherList) {
		// set size
		size += otherList.size;

		// merge 2 sorted array
		Entry<T> curTail = header; // current progress of the merge
        Entry<T> tc = header.next; // processing pointer for this list
		Entry<T> oc = otherList.header.next; // processing pointer for other list
		while (tc!=null && oc!=null) {
            switch (tc.element.compareTo(oc.element)) {
                case 0:
                case -1: {
                    // this value <= other value
                    curTail.next = tc;
                    tc = tc.next;
                    break;
                }
                case 1: {
                    // this value > other value
                    curTail.next = oc;
                    oc = oc.next;
                    break;
                }
                default:
                    System.out.println("Unexpected compare result");
            }
            curTail=curTail.next;
		}
		if(oc!=null){
		    // attach the rest of other list, and change tail
		    curTail.next = oc;
		    tail = otherList.tail;
        }
        else {
		    // attach the rest of this list, tail is unchanged
		    curTail.next = tc;
        }
	}

    /**
     * Perform in-place merge sort on current linked list
     */
	void mergeSort() {
	    // no need to do anything if the list is of size 1 or less
		if (size <= 1)
			return;

        // new linked list to store another half of element after division
		SortableList<T> otherList = new SortableList<T>();

		// config list properties for division
		otherList.tail = tail;
		int steps = size / 2;
		otherList.size = size - steps;
		size = steps;
		tail = header.next;

		for (int i = 0; i < steps - 1; i++)
			tail = tail.next;

		otherList.header.next = tail.next;
		tail.next = null;

		// divide both linked lists recursively
		this.mergeSort();
		otherList.mergeSort();

		// merge
		this.merge(otherList);
	}

    /**
     *
     * Static method to perform  merge sort on a linked list
     *
     * @param list the list to sort
     * @param <T>
     */
	public static <T extends Comparable<? super T>> void mergeSort(SortableList<T> list) {
		list.mergeSort();
	}

    /**
     * Helper function for testing
     * @param min the min value of random number
     * @param max the max value of random number
     * @return a random value between min and max
     */
	private static Integer randomNumber(int min,int max) {
        Integer randomNum = min + (int)(Math.random() * max);
        return randomNum;
    }

    /**
     * Main Method for testing
     * @param args
     */
	public static void main(String[] args){

        System.out.println("==================");
        System.out.println("Test case 1 (0 element):");
        {
            SortableList<Integer> lst = new SortableList<>();

            lst.printList();
            lst.mergeSort();
            lst.printList();
        }

        System.out.println("==================");
        System.out.println("Test case 2 (1 element):");
        {
            SortableList<Integer> lst = new SortableList<>();

            lst.add(new Integer(9));

            lst.printList();
            mergeSort(lst);
            lst.printList();
        }

        System.out.println("==================");
        System.out.println("Test case 3 (2 elements):");
        {
            SortableList<Integer> lst = new SortableList<>();

            lst.add(new Integer(9));
            lst.add(new Integer(1));

            lst.printList();
            lst.mergeSort();
            lst.printList();
        }

        System.out.println("==================");
        System.out.println("Test case 4 (3 elements):");
        {
            SortableList<Integer> lst = new SortableList<>();

            lst.add(new Integer(9));
            lst.add(new Integer(1));
            lst.add(new Integer(3));

            lst.printList();
            mergeSort(lst);
            lst.printList();
        }

        System.out.println("==================");
        System.out.println("Test case 5:");
        {
            SortableList<Integer> lst = new SortableList<>();

            lst.add(new Integer(100));
            lst.add(new Integer(1));
            lst.add(new Integer(2));
            lst.add(new Integer(5));
            lst.add(new Integer(3));
            lst.add(new Integer(4));
            lst.add(new Integer(6));
            lst.add(new Integer(6));
            lst.add(new Integer(10));

            lst.printList();
            lst.mergeSort();
            lst.printList();
        }

        System.out.println("==================");
        System.out.println("Test case 6 (Same values):");
        {
            SortableList<Integer> lst = new SortableList<>();

            lst.add(new Integer(100));
            lst.add(new Integer(100));
            lst.add(new Integer(100));
            lst.add(new Integer(100));

            lst.printList();
            mergeSort(lst);
            lst.printList();
        }


        System.out.println("==================");
        System.out.println("Test case 7 (Random test):");
        {
            SortableList<Integer> lst = new SortableList<>();


            for(int i=0;i<randomNumber(20,50);i++)
            {
                lst.add(randomNumber(0,100));
            }

            lst.printList();
            lst.mergeSort();
            lst.printList();
        }
	}
}