/**
 * <h1>Fall 2017 Short Project 8</h1>
 * <p>
 * Copied from sp3
 * Helper class extends GraphAlgorithm to provide basic but extensible functionality of DFS.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import cs6301.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Scanner;


public class DFS<T extends DFS.DFSVertex> extends GraphAlgorithm<DFS.DFSVertex> {
    public Deque<Graph.Vertex> getDecFinList() {
        return decFinList;
    }

    Deque<Graph.Vertex> decFinList = new ArrayDeque<>();
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
        node = new DFSVertex[g.size()];
        // Create array for storing vertex properties
        for(Graph.Vertex u: g) {
            node[u.getName()] = new DFSVertex();
        }
    }

    /**
     * Perform dfs on the graph, use default graph vertex iterator
     */
    public void dfs() {
        Iterator<Graph.Vertex> it = g.iterator();
        dfs(it);
    }

    /**
     * Perform dfs based on the order of a certain iterator
     * @param it the iterator used while searching
     */
    public void dfs(Iterator<Graph.Vertex> it) {

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
                dfsVisit(u);
            }
        }
        afterDFS(); // function extension point

        if(printFootprint) System.out.print("\n=============\nDFS End\n=============\n");
    }

    /**
     * Helper function perform visit operation on a vertex
     * @param v the vertex to visit
     */
    void dfsVisit(Graph.Vertex v) {

        if(printFootprint) System.out.print(v+" ");

        DFSVertex dv = getVertex(v);
        dv.seen = true;
        beforeVisitVertex(v); // function extension point
        for(Graph.Edge edge : v) {
            Graph.Vertex u = edge.otherEnd(v);
            if(!seen(u)) {
                encounterUnseenVertex(v,u); // function extension point
                dfsVisit(u);
            }
            else
                encounterSeenVertex(v, u); // function extension point
        }
        finishVisitVertex(v); // function extension point
        decFinList.addFirst(v);
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
     * Methods to override by child class to extend functionality
     */

    void beforeDFS(){}
    void afterDFS() {}
    void outerLoop(Graph.Vertex v){}

    void beforeVisitVertex(Graph.Vertex v){}
    void encounterUnseenVertex(Graph.Vertex v, Graph.Vertex seenVertex){}
    void encounterSeenVertex(Graph.Vertex v, Graph.Vertex unseenVertex){}
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
        }
    }
}
