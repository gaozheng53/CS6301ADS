/**
 * <h1>Fall 2017 Long Project 3</h1>
 * <p>
 * Helper class extends GraphAlgorithm to provide basic but extensible functionality of DFS.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-11
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    // Class to store vertex information, using parallel vertex arrays
    static class DFSVertex {
        boolean seen = false;

        // need to override if subclass use extension of DFSVertex
        void reset() {
            seen = false;
        }
    }

    boolean printFootprint = false; // Flag for debug, printing out dfs footprint

    public DFS(Graph g) {
        super(g);
        // initiation of properties of DFS
        initParallelArray();
    }
    /**
     * Helper function to initiate parallel array.
     * This function need to be override if extending DFSVertex Class
     */
    void initParallelArray() {
        // Create array for storing vertex properties
    	// Extra space is allocated in array for nodes to be added later
        node = new DFSVertex[g.size()*2];
        for(int i=0;i<g.size()*2;i++) {
            node[i] = new DFSVertex();
        }
    }

    /**
     * Perform dfs on the graph, use default graph vertex iterator
     */
    public void dfs() {
        Iterator<Graph.Vertex> it = g.iterator();
        dfs(it, false);
    }

    /**
     * Perform dfs based on the order of a certain iterator
     * @param it the iterator used while searching
     */
    public void dfs(Iterator<Graph.Vertex> it, boolean reverse) {

        if(printFootprint) System.out.println("\n=============\nDFS Start\n=============\n");

        // reset all vertex before dfs
        for(Graph.Vertex u: g) {
            DFSVertex du = getVertex(u);
            du.reset();
        }
        beforeDFS(); // function extension point
        // Outer loop
        while (it.hasNext()) {
            Graph.Vertex u = it.next();
            if(!seen(u)){
                outerLoop(u);
                dfsVisit(u, reverse);
            }
        }
        afterDFS(); // function extension point

        if(printFootprint) System.out.print("\n=============\nDFS End\n=============\n");
    }

    /**
     * Helper function perform visit operation on a vertex
     * @param v the vertex to visit
     */
    void dfsVisit(Graph.Vertex v, boolean reverse) {

        if(printFootprint) System.out.print(v+" ");

        DFSVertex dv = getVertex(v);
        dv.seen = true;
        beforeVisitVertex(v); // function extension point
        Iterator<Graph.Edge> it = reverse?v.reverseIterator():v.iterator();
        while(it.hasNext()) {
        	Graph.Edge edge = it.next();
            Graph.Vertex u = edge.otherEnd(v);
            if(!seen(u)) {
                encounterUnseenVertex(v,u,edge); // function extension point
                dfsVisit(u, reverse);
            }
            else
                encounterSeenVertex(v, u, edge); // function extension point
        }
        finishVisitVertex(v); // function extension point
    }

    /**
     * Helper function to check whether a vertex is seen
     * @param u vertex to check
     * @return whether the vertex is seen
     */
    boolean seen(Graph.Vertex u) {
        return getVertex(u).seen;
    }

    /**
     * Methods to override in child class to extend functionality
     */

    void beforeDFS(){}
    void afterDFS() {}
    void outerLoop(Graph.Vertex v){}

    void beforeVisitVertex(Graph.Vertex v){}
    void encounterUnseenVertex(Graph.Vertex v, Graph.Vertex seenVertex, Graph.Edge e){}
    void encounterSeenVertex(Graph.Vertex v, Graph.Vertex unseenVertex, Graph.Edge e){}
    void finishVisitVertex(Graph.Vertex v){};

    /**
     * Main function for testing
     * @param args
     * @throws FileNotFoundException
     */

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Test Graph 1:
        9 12
        1 2 1
        1 3 1
        1 4 1
        2 3 1
        2 6 1
        3 5 1
        3 6 1
        4 3 1
        4 7 1
        5 8 1
        6 8 1
        6 9 1
        7 9 1

        Test Graph 2:
        6 9
        1 3 1
        2 4 1
        3 1 1
        4 6 1
        1 2 1
        2 3 1
        3 4 1
        4 5 1
        5 6 1
         */
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in,true);

        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        if (g.size()>0) {
            DFS dfSearch = new DFS(g);
            dfSearch.printFootprint = true;
            dfSearch.dfs();
            
            dfSearch.printFootprint = true;
            dfSearch.dfs(g.iterator(),true);
        }
    }
}
