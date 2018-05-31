/*
 * <h1>Fall 2017 Short Project 6 - 2</h1>
 * <p>
 * Implement Huffman Coding algorithm.  Create a class for representing coding
 * trees.  Use a priority queue to hold the trees.  In each step, the algorithm
 * removes two trees with the smallest frequencies, merges them, and inserts it
 * back into the priority queue.  At the end, there is a single coding tree.
 * Traverse the tree and output the binary codes for each symbol.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-02
 */

package cs6301.g16;

import java.util.PriorityQueue;

public class HuffmanCoding {

    /**
     * Helper class for Huffman Tree construction
     */
    static class TreeNode implements Comparable<TreeNode> {
        Float freq;
        String code = "";
        TreeNode father = null, left = null, right = null;

        TreeNode(float freq) {
            this.freq = freq;
        }

        void setLeft(TreeNode node) {
            left = node;
            if (node != null) {
                node.father = this;
                node.code = "0";
            }
        }

        void setRight(TreeNode node) {
            right = node;
            if (node != null) {
                node.father = this;
                node.code = "1";
            }
        }

        @Override
        public int compareTo(TreeNode o) {
            return freq.compareTo(o.freq);
        }
    }

    /**
     * Parallel Arrays for storing input & output
     */
    String[] keys;
    float[] freqs;
    String[] codes;

    HuffmanCoding(String[] keys, float[] freqs) {
        this.keys = keys;
        this.freqs = freqs;
        this.codes = new String[keys.length];
    }

    /**
     * Instance method to run HuffmanCoding algorithm and generate coding
     */
    void encode() {
        int length = keys.length;
        assert (freqs.length == length);

        TreeNode[] leaves = new TreeNode[length];

        PriorityQueue<TreeNode> pq = new PriorityQueue<>();
        for (int i = 0; i < length; i++) {
            leaves[i] = new TreeNode(freqs[i]);
            pq.add(leaves[i]);
        }

        while (pq.size() > 1) {
            TreeNode node1 = pq.remove(), node2 = pq.remove();
            TreeNode newNode = new TreeNode(node1.freq + node2.freq);
            newNode.setLeft(node1);
            newNode.setRight(node2);
            pq.add(newNode);
        }

        for (int i = 0; i < length; i++) {
            TreeNode node = leaves[i];
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(node.code);
                node = node.father;
            }
            while (node != null);
            codes[i] = sb.reverse().toString();
        }
    }

    /**
     * Helper function to calculate average code length of the encoding
     */
    float weightedAvgCodeLength() {
        float length = 0;
        for (int i = 0; i < freqs.length; i++)
            length += freqs[i] * (float) codes[i].length();
        return length;
    }

    /**
     * Helper function to print huffman coding result
     */
    void printCoding() {
        int length = keys.length;
        assert (freqs.length == length);
        assert (codes.length == length);
        System.out.println("Coding result:");
        for (int i = 0; i < length; i++) {
            System.out.printf("key:%s freq:%.2f code=\"%s\"\n", keys[i], freqs[i], codes[i]);
        }
        System.out.println("Weighted Average number of bits per character:" + weightedAvgCodeLength());
    }

    /**
     * Main function for testing
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] keys = new String[]{"a", "b", "c", "d", "e"};
        float[] freqs = new float[]{0.2f, 0.1f, 0.15f, 0.3f, 0.25f};
        HuffmanCoding huffman = new HuffmanCoding(keys, freqs);
        huffman.encode();
        huffman.printCoding();
    }
}
