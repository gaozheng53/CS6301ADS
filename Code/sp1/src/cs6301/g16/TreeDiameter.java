/**
 * <h1>Fall 2017 Short Project 1</h1>
 * <p>
 * Implement breadth-first search (BFS), and solve the problem of finding the diameter of a tree
 * that works as follows: Run BFS, starting at an arbitrary node as root. Let u be a node at maximum
 * distance from the root. Run BFS again, with u as the root. Output diameter: path from u to a node
 * at maximum distance from u.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.1
 * @since 2017-08-25
 */

package cs6301.g16;

import cs6301.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.LinkedList;

public class TreeDiameter {
    /**
     * Return a longest path in g.  Algorithm is correct only if g is a tree.
     *
     * @param g : The graph to be processed.
     * @return A list containing the vertexes in the longest path.
     */

    /* Graph.Vertex in Graph.java is not public, cannot access from outside. If no modification
     * the source file is not allowed, need to add Graph.java into this package. */
    public static LinkedList<Graph.Vertex> diameter(Graph g) {

        // handle corner cases
        if(g == null)
            return null;
        if(!g.iterator().hasNext())
            return new LinkedList<>();

        // perform BFS with the first vertex in graph
        BFS bfs1 = new BFS(g.iterator().next());

        // the last Vertex visited in bfs1 should be a leaf in the tree
        Graph.Vertex leaf = bfs1.getVisitedArray()[bfs1.getVisitedArray().length-1];

        // perform BFS start from the leaf
        BFS bfs2 = new BFS(leaf);

        // find the longest path by tracing back from the last visited vertex to the root.
        LinkedList<Graph.Vertex> diameter = new LinkedList<>();
        Graph.Vertex lastVertex = bfs2.getVisitedArray()[bfs2.getVisitedArray().length-1];

        while (lastVertex != null) {
            diameter.add(0,lastVertex);
            lastVertex = bfs2.getParent(lastVertex);
        }

        return diameter;

    }
    
    public static void main(String[] args) throws FileNotFoundException {

        /* Test Tree:
        8 7
        1 2 1
        1 3 1
        1 4 1
        2 5 1
        2 6 1
        4 7 1
        7 8 1
         */

        /* Test Tree:
        22 21
        8   4   1
        4   21  1
        7   1   1
        5   1   1
        6   1   1
        21  3   1
        21  2   1
        2   9   1
        15  9   1
        16  9   1
        17  16  1
        22  3   1
        10  22  1
        11  10  1
        11  12  1
        13  11  1
        14  13  1
        18  22  1
        18  20  1
        19  18  1
        21  1   1

        Diameter: [14, 13, 11, 10, 22, 3, 21, 2, 9, 16, 17]
         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in);

        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

		LinkedList<Graph.Vertex> d = diameter(g);
        System.out.println("A diameter in the tree is:");
		System.out.print(d);
	}
}
