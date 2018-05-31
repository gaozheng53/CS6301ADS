/**
 * <h1>Fall 2017 Short Project 3-4</h1>
 * <p>
 * Is a given directed graph a DAG (directed, acyclic graph)?
 * Solve the problem by running DFS on the given graph, and checking
 * if there are any back edges.
 *
 * boolean isDAG(Graph g) { ... }
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestDAG<T extends TestDAG.DAGVertex> extends DFS<DFS.DFSVertex>{

    public static class DAGVertex extends DFSVertex {
        boolean finished = false; // whether the vertex is finished

        @Override
        void reset(){
            super.reset();
            finished = false;
        }
    }

    private boolean isCyclic;

    /**
     * Constructor
     */
    TestDAG(Graph g){
        super(g);
    }

    /**
     * Parallel array functions to use DAGVertex instead
     */
    void initParallelArray() {
        node = new DAGVertex[g.size()];
        // Create array for storing vertex properties
        for(Graph.Vertex u: g) {
            node[u.getName()] = new DAGVertex();
        }
    }
    DAGVertex getVertex(Graph.Vertex u) {
        return (DAGVertex)super.getVertex(u);
    }

    /**
     * Override extension functions
     */
    @Override
    void beforeDFS(){
        super.beforeDFS();
        isCyclic = false;
    }
    @Override
    void encounterSeenVertex(Graph.Vertex v, Graph.Vertex unseenVertex){
        super.beforeDFS();
        if(!getVertex(unseenVertex).finished)
            // when encounter unfinished vertex, the edge to this vertex is a back edge
            isCyclic = true;
    }
    @Override
    void finishVisitVertex(Graph.Vertex v){
        super.finishVisitVertex(v);
        getVertex(v).finished = true;
    }

    /**
     * Determine whether the graph is DAG by checking:
     * 1. Whether it is a directed graph
     * 2. Whether it is acyclic graph
     * @return boolean value as test result
     */
    public boolean dfsWithDAGTest() {
        if(!g.isDirected())
            return false;
        else {
            dfs();
            return !isCyclic;
        }
    }

    /**
     * Signature for SP3-4
     * @param g Graph to test
     * @return boolean value indicate whether the graph is DAG
     */
    public static boolean isDAG(Graph g){
        TestDAG test = new TestDAG(g);
        return test.dfsWithDAGTest();
    }

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Test Graph 1: (not DAG)

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

        Test Graph 2: (not DAG)
        3 3
        1 2 1
        2 3 1
        3 1 1

        Test Graph 3: (is DAG)
        5 7
        1 2 1
        2 3 1
        3 4 1
        4 5 1
        1 5 1
        2 5 1
        2 4 1
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

        System.out.println("\n==============\nWhether the graph is DAG: " + isDAG(g));

    }
}
