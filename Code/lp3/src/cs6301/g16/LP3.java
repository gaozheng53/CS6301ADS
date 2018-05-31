/**
 * <h1>Fall 2017 Long Project 3</h1>
 * <p>
 * Driver class given for testing.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-11
 */
package cs6301.g16;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import cs6301.g16.Graph.Vertex;
import cs6301.g16.Graph.Edge;
import cs6301.g00.Timer;

public class LP3 {
    static int VERBOSE = 0;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
	if(args.length > 1) {
            VERBOSE = Integer.parseInt(args[1]);
        }

	int start = in.nextInt();  // root node of the MST
        Graph g = Graph.readDirectedGraph(in);
	Vertex startVertex = g.getVertex(start);
	List<Edge> dmst = new ArrayList<>();

        Timer timer = new Timer();
	int wmst = directedMST(g, startVertex, dmst);
        timer.end();

	System.out.println(wmst);
        if(VERBOSE > 0) {
	    System.out.println("_________________________");
            for(Edge e: dmst) {
                System.out.print(e);
            }
	    System.out.println();
	    System.out.println("_________________________");
        }
        System.out.println(timer);
    }

    /** TO DO: List dmst is an empty list. When your algorithm finishes,
     *  it should have the edges of the directed MST of g rooted at the
     *  start vertex.  Edges must be ordered based on the vertex into
     *  which it goes, e.g., {(7,1),(7,2),null,(2,4),(3,5),(5,6),(3,7)}.
     *  In this example, 3 is the start vertex and has no incoming edges.
     *  So, the list has a null corresponding to Vertex 3.
     *  The function should return the total weight of the MST it found.
     */  
    public static int directedMST(Graph g, Vertex start, List<Edge> dmst) {
        DMST dmstAlg = new DMST(g,start,dmst);
	    return dmstAlg.findDirectedMST();
    }
}
