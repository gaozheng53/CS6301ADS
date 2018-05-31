// Ver 1.0:  Starter code for Indexed heaps

package cs6301.g16;

import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
    /**
     * Build a priority queue with a given array q
     */
    public IndexedHeap(T[] q, Comparator<T> comp, int n) {
        super(q, comp, n);
    }

    /**
     * restore heap order property after the priority of x has decreased
     */
    public void decreaseKey(T x) {
        percolateUp(x.getIndex());
    }

    @Override
    protected void setPq(int i, T value) {
        super.setPq(i, value);
        if (value != null)
            value.putIndex(i);
    }
}
