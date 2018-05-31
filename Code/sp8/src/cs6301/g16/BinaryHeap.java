/*
 * <h1>Fall 2017 Short Project 8</h1>
 * <p>
 * Helper Class BinaryHeap implementation
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-02
 */

package cs6301.g16;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

public class BinaryHeap<T> {
    T[] pq;
    final Comparator<T> c;
    final int MAX_Size;
    int size;

    /**
     * Build a priority queue with a given array q, using q[0..n-1]. It is not necessary that n ==
     * q.length. Extra space available can be used to add new elements.
     */
    public BinaryHeap(T[] q, Comparator<T> comp, int n) {
        pq = q;
        c = comp;
        MAX_Size = n;
        size = 0;
    }

    public void insert(T x) throws Exception {
        add(x);
    }

    public T deleteMin() throws Exception {
        return remove();
    }

    public T min() throws Exception {
        return peek();
    }

    public void add(T x) throws Exception {
        if (size == MAX_Size)
            throw new Exception("The priority queue is full.");

        setPq(size,x);
        percolateUp(size);
        size++;
    }

    public T remove() throws NoSuchElementException {
        if (size == 0)
            throw new NoSuchElementException("Priority queue is empty.");
        T min = pq[0];
        size--;
        setPq(0,pq[size]);
        setPq(size,null);
        
        percolateDown(0);
        return min;
    }

    public T peek() throws NoSuchElementException {
        if (size == 0)
            throw new NoSuchElementException("Priority queue is empty.");
        return pq[0];
    }

    public void replace(T x) {
    /* TO DO.  Replaces root of binary heap by x if x has higher priority
         (smaller) than root, and restore heap order.  Otherwise do nothing.
	   This operation is used in finding largest k elements in a stream.
	 */
    }
    
    public boolean isEmpty(){
    	return size == 0;
    }

    /**
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) {
        T tmp = pq[i];
        while (i > 0 && c.compare(tmp, pq[parent(i)]) < 0) {
        	setPq(i,pq[parent(i)]);
            i = parent(i);
        }
        setPq(i,tmp);
    }

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) {
        T tmp = pq[i];
        int child = 2 * i + 1;
        while (child <= size - 1) {
            if (child < size - 1 && c.compare(pq[child], pq[child + 1]) > 0)
                child++;
            if (c.compare(tmp, pq[child]) <= 0)
                break;
            setPq(i,pq[child]);
            i = child;
            child = 2 * i + 1;
        }
        setPq(i,tmp);
    }

    int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap() {
        for (T item : pq) {
            if (item == null)
                break;
            size++;
        }
        int i = (pq.length - 2) / 2;
        if (i * 2 < pq.length - 2)
            i++;
        for (; i >= 0; i--)
            percolateDown(i);
    }
    
    /**
     * modify pq Array
     */
    protected void setPq(int i, T value){
    	pq[i] = value;
    }

    /* sort array A[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static <T> void heapSort(T[] A, Comparator<T> comp) {
        BinaryHeap<T> bh = new BinaryHeap<>(A, comp, A.length);
        bh.buildHeap();
        for (int i = bh.size - 1; i > 0; i--) {
            try {
                bh.setPq(i, bh.remove());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "[" + Arrays.toString(pq) + "]";
    }

    public static void main(String[] args) throws Exception {
        Random r = new Random();

        Integer[] A = new Integer[100];
        for (int i = 0; i < 100; i++)
            A[i] = r.nextInt(100);

        BinaryHeap.heapSort(A, Comparator.naturalOrder());


    }
}
