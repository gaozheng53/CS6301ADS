/**
 * <h1>Fall 2017 Short Project 3-1</h1>
 * <p>
 * Topological ordering of a DAG.
 * Implement two algorithms for ordering the nodes of a DAG topologically.
 * Both algorithms should return null if the given graph is not a DAG.
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
import java.util.Queue;
import java.util.Scanner;

public class TopologicalOrder {

    /** Algorithm 1. Remove vertices with no incoming edges, one at a
     *  time, along with their incident edges, and add them to a list.
     */
    static class TopAlg1 extends GraphAlgorithm<TopAlg1.TopVertex> {

        static class TopVertex {
            int inDegree = 0;
            void reset(){
                inDegree = 0;
            }
        }

        TopAlg1(Graph g) {
            super(g);

            node = new TopVertex[g.size()];
            // Create array for storing vertex properties
            for(Graph.Vertex u: g) {
                node[u.getName()] = new TopVertex();
            }
        }

        List<Graph.Vertex> getTopologicalOrder() {
            // the graph has to be directed
            if(!g.isDirected())
                return null;

            List<Graph.Vertex> topList = new LinkedList<>();
            Queue<Graph.Vertex> q = new LinkedList<>(); // Queue for vertex with in-degree of 0
            // set initial inDegree
            for (Graph.Vertex v : g) {
                getVertex(v).inDegree = v.inDegree();
                if(v.inDegree()==0)
                    q.add(v);
            }

            while (!q.isEmpty()) {
                Graph.Vertex u = q.remove();
                topList.add(u);
                for(Graph.Edge e : u) {
                    Graph.Vertex o = e.otherEnd(u);
                    getVertex(o).inDegree -=1;
                    if(getVertex(o).inDegree==0){
                        q.add(o);
                    }
                }
            }

            if(topList.size()!=g.getN()) // the graph is not a DAG
                return null;
            else
                return topList;
        }

        /**
         * Required Signature in question to get topological order
         * @param g
         * @return
         */
        public static List<Graph.Vertex> topologicalOrder1(Graph g) {
            TopAlg1 alg = new TopAlg1(g);
            return alg.getTopologicalOrder();
        }
    }


    /** Algorithm 2. Run DFS on g and add nodes to the front of the output list,
     *  in the order in which they finish.  Try to write code without using global variables.
     */
    public static class TopAlg2<T extends TestDAG.DAGVertex> extends TestDAG<TestDAG.DAGVertex> {

        List<Graph.Vertex> topOrderList;

        TopAlg2(Graph g) {
            super(g);
        }

        /**
         * Perform algorithm to get topological order of the graph
         * @return List of vertexes in topological order
         */
        List<Graph.Vertex> getTopOrder() {
            boolean isDAG = dfsWithDAGTest();
            if(isDAG)
                return topOrderList;
            else
                return null;
        }

        /**
         * Override extension functions
         */
        @Override
        void beforeDFS(){
            super.beforeDFS();
            topOrderList = new LinkedList<>();
        }
        @Override
        void finishVisitVertex(Graph.Vertex v){
            super.finishVisitVertex(v);
            topOrderList.add(0,v);
        }

        /**
         * Required Signature in question to get topological order
         * @param g
         * @return
         */
        public static List<Graph.Vertex> topologicalOrder2(Graph g) {
            TopAlg2 topAlg = new TopAlg2(g);
            return topAlg.getTopOrder();
        }
    }

    /**
     * Main function for testing
     * @param args
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

        System.out.println("\nResult 1:\n"+TopAlg1.topologicalOrder1(g));
        System.out.println("\nResult 2:\n"+TopAlg2.topologicalOrder2(g));

    }
}
