/**
 * <h1>Fall 2017 Long Project 2</h1>
 * <p>
 * A directed graph is called Eulerian if it is strongly connected,
 * and the number of incoming edges at each node is equal to the number
 * of outgoing edges from it. It is known that such a graph aways has a tour
 * (a cycle that may not be simple) that goes through every edge of the graph
 * exactly once. Such a tour (sometimes called a circuit) is called an Euler tour.
 * In this project, write code that finds an Euler tour in a given graph using the algorithm described in class.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */

package cs6301.g16;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cs6301.g00.*;

public class LP2 {
    static int VERBOSE = 10;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        int start = 1;
        if (args.length > 1) {
            start = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            VERBOSE = Integer.parseInt(args[2]);
        }
        Graph g = Graph.readDirectedGraph(in);
        Graph.Vertex startVertex = g.getVertex(start);

        Timer timer = new Timer();
        Euler euler = new Euler(g, startVertex);
        euler.setVerbose(VERBOSE);

        boolean eulerian = euler.isEulerian();
        if (!eulerian) {
            return;
        }
        List<Graph.Edge> tour = euler.findEulerTour();
        timer.end();

        if (VERBOSE > 0) {
            System.out.println("Output:\n_________________________");
            for (Graph.Edge e : tour) {
                System.out.print(e);
            }
            System.out.println();
            System.out.println("_________________________");
        }
        System.out.println(timer);
    }
}
