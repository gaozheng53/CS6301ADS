/**
 * <h1>Fall 2017 Short Project 3-2</h1>
 * <p>
 * Strongly connected components of a directed graph.  Implement the
 * algorithm for finding strongly connected components of a directed
 * graph (see page 617 of Cormen et al, Introduction to algorithms,
 * 3rd ed.).  Run DFS on G and create a list of nodes in decreasing
 * finish time order.  Find G^T, the graph obtained by reversing all
 * edges of G.  Note that the Graph class has a field revAdj that is
 * useful for this purpose.  Run DFS on G^T, but using the order of
 * the list output by the first DFS.  Each DSF tree in the second DFS
 * is a strongly connected component.
 *
 * int stronglyConnectedComponents(Graph g) { ... }
 * Each node is marked with a component number, and the function returns
 * the number of strongly connected components of G.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SCC <T extends SCC.SCCVertex> extends TopologicalOrder.TopAlg2<TestDAG.DAGVertex> {

    /**
     * Custom vertex wrapper class to hold vertex's component number.
     * Extend DAGVertex since the outer class is extended from TopAlg2 <- TestDAG which uses DAGVertex
     */
    static class SCCVertex extends DAGVertex {
        int cno; // component number of the vertex
        @Override
        void reset() {
            super.reset();
            cno = 0;
        }
    }

    int cno=0; // number of component;

    public SCC(Graph g) {
        super(g);
    }

    /**
     * Override/Overload Parallel array related functions to use DAGVertex instead
     */
    @Override
    void initParallelArray() {
        node = new SCCVertex[g.size()];
        // Create array for storing vertex properties
        for(Graph.Vertex u: g) {
            node[u.getName()] = new SCCVertex();
        }
    }
    //Overload
    SCCVertex getVertex(Graph.Vertex u) {
        return (SCCVertex)super.getVertex(u);
    }

    /**
     * Override extension point functions
     */
    void beforeDFS(){
        super.beforeDFS();
        this.cno = 0; // reset cno
    }
    void outerLoop(Graph.Vertex v){
        super.outerLoop(v);
        getVertex(v).cno = ++this.cno;
    }
    void beforeVisitVertex(Graph.Vertex v){
        super.beforeVisitVertex(v);
        //Set current cno to vertex
        getVertex(v).cno = cno;
    }

    /**
     * Get number of strongly connected components in the graph
     * @return num of SCC
     */
    public int getStronglyConnectedComponents() {
        // reference for input graph
        Graph tmpGraph = g;
        // perform the algorithm on a copied graph object, avoid modifying the input graph
        this.g = new Graph(g);
        this.dfs();

        // get the topOrderList aka decreasing finish time list of the first DFS
        List<Graph.Vertex> firstDecFinList = this.topOrderList;

        // reverse the graph
        this.g.reverseGraph();
        // perform 2nd DFS in the descending finish time order of 1st DFS
        this.dfs(firstDecFinList.iterator());

        // print SCCs found in graph
        printSCCs();

        // restore the original graph object
        this.g = tmpGraph;
        return this.cno;
    }

    /**
     * Debug helper - print out SCCs found in the graph
     */
    private void printSCCs() {

        System.out.println("\n==============\nSCCs found:");

        List<Graph.Vertex>[] SCCs = new List[cno];
        for(Graph.Vertex v : g) {
            if(SCCs[getVertex(v).cno-1]==null)
                SCCs[getVertex(v).cno-1] = new LinkedList<>();
            SCCs[getVertex(v).cno-1].add(v);
        }

        for(int i=0; i<SCCs.length;i++)
            System.out.println("SCC "+(1+i)+":"+SCCs[i]);
    }

    /**
     * Signature required in question
     * @param g the graph to check
     * @return number of strongly connected components in the graph
     */

    public static int stronglyConnectedComponents(Graph g) {
        SCC scc = new SCC(g);
        return scc.getStronglyConnectedComponents();
    }

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Test Graph 1: (the on we used in class)

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

         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in,true);

        System.out.println("\n==============\nInput Graph:");
        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        System.out.println("\n==============\nNumber of Strongly Connected Components:"+stronglyConnectedComponents(g));

    }
}
