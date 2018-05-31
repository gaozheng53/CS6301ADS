/**
 * <h1>Fall 2017 Short Project 3-3</h1>
 * <p>
 * Is a given directed graph Eulerian?
 *
 * A directed graph G is called Eulerian if it is strongly connected
 * and the in-degree of every vertex is equal to its out-degree.  It
 * is known that such graphs have a tour (cycle that may not be
 * simple) that goes through every edge of the graph exactly once.
 * Write a function that tests whether a given graph is Eulerian.
 * Your algorithm need not find an Euler tour of the graph.
 *
 * boolean testEulerian(Graph g) { ... }
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */


package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestEulerian extends SCC {
    public TestEulerian(Graph g) {
        super(g);
    }

    public boolean isEulerian() {

        // check in-degree and out-degree of each vertex
        for (Graph.Vertex v : g)
            if (v.inDegree() != v.outDegree())
                return false;

        // check if the graph is strongly connected
        if (getStronglyConnectedComponents() != 1)
            return false;

        return true;
    }

    public static boolean testEulerian(Graph g) {
        TestEulerian TE = new TestEulerian(g);
        return TE.isEulerian();
    }

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Test Graph 1: (not Eulerian)

        11 17
        1 11 1
        2 3 1
        2 7 1
        3 10 1
        4 1 1
        4 9 1
        5 4 1
        5 7 1
        5 8 1
        6 3 1
        7 8 1
        8 2 1
        9 11 1
        10 6 1
        11 3 1
        11 4 1
        11 6 1

        Test Graph 2: (is Eulerian)
        3 3
        1 2 1
        2 3 1
        3 1 1
         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in, true);

        System.out.println("\n==============\nInput Graph:");
        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        System.out.println("\n==============\nWhether the graph is Eulerian: " + testEulerian(g));

    }
}
