
/**
 * <h1>Fall 2017 Short Project 7 - 5</h1>
 * <p>
 * Implement BSTMap (like a TreeMap), on top of one of the BST classes.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-22
 */

package cs6301.g16;

import java.util.Iterator;

public class BSTMap<K extends Comparable<? super K>, V> implements Iterable<K> {

    public enum BSTMapType{ BST, AVL, RB, SPLAY}

    private static class BSTMapElement<K extends Comparable<? super K>, V> implements Comparable<BSTMapElement<K,V>>{
        K key;
        V value;
        BSTMapElement(K key, V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(BSTMapElement<K, V> o) {
            return this.key.compareTo(o.key);
        }
    }

    // BST tree object used by map
    BST<BSTMapElement<K,V>> bst;

    // Constructor
    BSTMap(){
        this(BSTMapType.BST);
    }

    BSTMap(BSTMapType type){
        super();
        switch (type){
            case BST: bst = new BST<>(); break;
            case AVL: bst = new AVLTree<>(); break;
            case RB: bst = new RedBlackTree<>(); break;
            case SPLAY: bst = new SplayTree<>(); break;
            default: bst = new BST<>(); break;
        }
    }

    public V get(K key) {
        BSTMapElement<K,V> keyElement = new BSTMapElement<>(key,null); // key element for search
        BSTMapElement<K,V> valueElement = bst.get(keyElement);
        if(valueElement!=null)
            return valueElement.value;
        else
            return null;
    }

    public boolean put(K key, V value) {
        return bst.add(new BSTMapElement<>(key,value));
    }

    // Map Iterator
    private class BSTMapIterator implements Iterator<K> {
        Iterator<BSTMapElement<K,V>> it;

        public BSTMapIterator(BST<BSTMapElement<K,V>> bst) {
            it = bst.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public K next() {
            return it.next().key;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation for in-order iterator.");
        }

    }

    // Iterate over the keys stored in the map, in order
    public Iterator<K> iterator() {
        return new BSTMapIterator(bst);
    }

    /**
     * Main function for testing
     */
    public static void main(String[] args){

        BSTMap<Integer,String> map = new BSTMap<>(BSTMapType.SPLAY); // using Splay Tree

        System.out.println("map.put(3,\"C\")");
        map.put(3,"C");
        System.out.println("map.put(1,\"A\")");
        map.put(1,"A");
        System.out.println("map.put(2,\"B\")");
        map.put(2,"B");

        System.out.println("Iterate Through Map:");
        for(Integer key : map){
            System.out.println(key+"-"+map.get(key));
        }

        System.out.println("map.put(3,\"CC\")");
        map.put(3,"CC");
        System.out.println(3+"-"+map.get(3));

        System.out.println("Get a key doesn't exist:");
        System.out.println("map.get(0) = "+map.get(0));

    }
}
