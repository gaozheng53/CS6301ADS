/**
 * <h1>Fall 2017 Short Project 2-5</h1>
 * <p>
 * Implement array-based, bounded-sized queues, that support the following operations: offer, poll,
 * peek, isEmpty (same behavior as in Java's Queue interface).  In addition, implement the method
 * resize(), which doubles the queue size if the queue is mostly full (over 90%, say), or halves it
 * if the queue is mostly empty (less then 25% occupied, say).  Let the queue have a minimum size of
 * 16, at all times.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.1
 * @since 2017-09-03
 */
package cs6301.g16;


public class ArrayQueue<T> {
    /**
     * Implement array-based, bounded-sized queues, and its offer, poll, peek, isEmpty, resize operations.
     * @param <T> : The element type in the array.
     * @param array : Array to be stored.
     * @param minSize : The queue's minimum size.
     * @param fullBoundary : The boundary need to double size.
     * @param emptyBoundary : The boundary need to halves size.
     * @param corrocc : Current ocupied of array.
     * @param front : The index of queue's head.
     * @param rear :  The index of queue's tail.
     *
     */
    private static final int minSize = 16;
    private static final double fullBoundary = 0.9;
    private static final double emptyBoundary = 0.25;
    private T[] array;
    private int front = 0;
    private int rear = 0;

    /**
     * Define array's initial size
     * @param inisize : The array's initial size set by user.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue(int inisize) {
        if (inisize >= minSize)
            array = (T[]) new Object[inisize];
        else
            array = (T[]) new Object[minSize];
    }

    /**
     * Set array's default size = minSize
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        array = (T[]) new Object[minSize];
    }

    /**
     * Resize the array when reach full or empty boundary, and make sure array size should over 16 at all times.
     * Double size when occupied over fullBoundary, halves size when occupied less than emptyBoundary.
     */
    @SuppressWarnings("unchecked")
    public void resize() {
        int currocc = 0;
        if (rear > front)
            currocc = rear - front;
        else if (rear < front) {
            currocc = array.length - front + rear;
        }
        if (currocc > array.length * fullBoundary) {   //resize when over fullBoundary occupied
            T[] items = (T[]) new Object[array.length * 2];

            for (int i = 0; i < currocc; i++) {
                items[i] = array[(i + front) % array.length];
            }
            array = items;
            items = null;
            front = 0;
            rear = currocc;
        }
        if (array.length > 16 && currocc < array.length * emptyBoundary && currocc > 0) {   //resize when less than emptyBoundary occupied
            T[] items;
            if (array.length / 2 >= minSize) {
                items = (T[]) new Object[array.length / 2];
                for (int i = 0; i < currocc; i++) {
                    items[i] = array[(i + front) % array.length];
                }
                array = items;
                items = null;
                front = 0;
                rear = currocc;
            }
        }
    }

    /**
     * Compute array's total space.
     */
    public int queSize() {    //the array size
        return array.length;
    }

    /**
     * Add element to array. When front reach the end of array, it will change to 0 and add from the beginning of array,like a circle.
     * @param element : Object should be added to array.
     */
    public boolean offer(T element) {
        array[rear] = element;
        rear = (rear + 1) % array.length;
        resize();
        return true;
    }

    /**
     * Delete element from array. If array become empty after poll element, let next element store from the beginning of array.
     */
    public T poll() {
        if (front == rear) {   // Poll empty.
            front = 0;
            rear = 0;
            return null;
        }
        T item = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        resize();
        return item;
    }

    /**
     * Get the object on the queue's front.
     */
    public T peek() {
        return array[front];
    }

    /**
     * Determine the array is empty or not.
     */
    public boolean isEmpty() {
        return (front == rear);
    }

    /**
     * Get the element on array[index]
     * @param index : The element's position need to get.
     */
    public T get(int index) {
        return array[index];
    }

    /**
     * Main function to test.
     */
    public static void main(String[] args) {
        ArrayQueue<Integer> queue2 = new ArrayQueue<Integer>();
        System.out.println("Default initial size: " + queue2.queSize());
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>(17);
        System.out.println("Initial size for test: " + queue.queSize());
        System.out.println("-------------- Test offer/poll/peek/isEmpty --------------");
        System.out.println("Offer element:" + queue.offer(-1));
        System.out.println("Get peek element:" + queue.peek());
        System.out.println("Poll element:" + queue.poll());
        System.out.println("Poll null queue:" + queue.poll());
        System.out.println("isEmpty():" + queue.isEmpty());
        System.out.println("-------------- Initial Queue --------------");
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.poll();
        queue.poll();
        queue.poll();
        for (int i = 6; i <= 18; i++)
            queue.offer(i);
        for (int i = 0; i < queue.queSize(); i++)
            System.out.print(queue.get(i) + " ");
        System.out.println();
        System.out.println("Original size: " + queue.queSize());
        System.out.println("-------------- Test Double Size --------------");
        queue.offer(19);
        for (int i = 0; i < queue.queSize(); i++)
            System.out.print(queue.get(i) + " ");
        System.out.println();
        System.out.println("New size: " + queue.queSize());
        System.out.println("-------------- Test Halve Size ---------------");
        for (int i = 0; i < 8; i++)
            queue.poll();
        for (int i = 0; i < queue.queSize(); i++)
            System.out.print(queue.get(i) + " ");
        System.out.println();
        System.out.println("New size: " + queue.queSize());
    }

}
