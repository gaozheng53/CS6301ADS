/**
 * <h1>Fall 2017 Short Project 3</h1>
 * <p>
 * Revision of given Graph class, tailored especially for sp3
 * Modifications:
 * 1. Add Copy constructor for Graph object
 * 2. Add reverseGraph to reverse graph
 * 3. Add inDegree method to get in-degree of the vertex
 * 4. Add outDegree method to get out-degree of the vertex
 * 5. Some getter methods such as isDirected(), getNum(), etc.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import cs6301.g00.ArrayIterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Graph implements Iterable<Graph.Vertex> {
    Vertex[] v; // vertices of graph
    int n; // number of verices in the graph
    boolean directed;  // true if graph is directed, false otherwise


    /**
     * Nested class to represent a vertex of a graph
     */

    public static class Vertex implements Iterable<Edge> {
        int name; // name of the vertex
        List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList

        /**
         * Constructor for the vertex
         *
         * @param n
         *            : int - name of the vertex
         */
        Vertex(int n) {
            name = n;
            adj = new LinkedList<Edge>();
            revAdj = new LinkedList<Edge>();   /* only for directed graphs */
        }

        /**
         * Method to get name of a vertex.
         *
         */
        public int getName() {
            return name;
        }

        public Iterator<Edge> iterator() { return adj.iterator(); }

        // Helper function for parallel arrays used to store vertex attributes
        public static<T> T getVertex(T[] node, Vertex u) {
            return node[u.name];
        }

        /**
         * Method to get vertex number.  +1 is needed because [0] is vertex 1.
         */
        public String toString() {
            return Integer.toString(name+1);
        }

        /**
         * Get incoming edges count
         */
        public int inDegree() {
            return revAdj.size();
        }

        /**
         * Get outgoing edges count
         */
        public int outDegree() {
            return adj.size();
        }
    }

    /**
     * Nested class that represents an edge of a Graph
     */

    public static class Edge {
        Vertex from; // head vertex
        Vertex to; // tail vertex
        int weight;// weight of edge

        /**
         * Constructor for Edge
         *
         * @param u
         *            : Vertex - Vertex from which edge starts
         * @param v
         *            : Vertex - Vertex on which edge lands
         * @param w
         *            : int - Weight of edge
         */
        Edge(Vertex u, Vertex v, int w) {
            from = u;
            to = v;
            weight = w;
        }

        /**
         * Method to find the other end end of an edge, given a vertex reference
         * This method is used for undirected graphs
         *
         * @param u
         *            : Vertex
         * @return
        : Vertex - other end of edge
         */
        public Vertex otherEnd(Vertex u) {
            assert from == u || to == u;
            // if the vertex u is the head of the arc, then return the tail else return the head
            if (from == u) {
                return to;
            } else {
                return from;
            }
        }

        /**
         * Return the string "(x,y)", where edge goes from x to y
         */
        public String toString() {
            return "(" + from + "," + to + ")";
        }

        public String stringWithSpaces() {
            return from + " " + to + " " + weight;
        }
    }


    /**
     * Constructor for Graph
     *
     * @param n
     *            : int - number of vertices
     */
    public Graph(int n) {
        this.n = n;
        this.v = new Vertex[n];
        this.directed = false;  // default is undirected graph
        // create an array of Vertex objects
        for (int i = 0; i < n; i++)
            v[i] = new Vertex(i);
    }

    /**
     * Add Copy Constructor for Question 1 topologicalOrder1
     */
    public Graph(Graph g) {
        this.n = g.n;
        this.v = new Vertex[n];
        this.directed = g.directed;
        // create an array of Vertex objects
        for (int i = 0; i < n; i++)
            v[i] = new Vertex(i);
        // copy edges
        for (Vertex v1 : g) {
            Vertex v2 = this.v[v1.name];
            for(Edge e: v1.adj) {
                if(v1 == e.from) {
                    Vertex u2 = this.v[e.otherEnd(v1).name];
                    addEdge(v2, u2, e.weight);
                }
            }
        }
    }

    /**
     * Find vertex no. n
     * @param n
     *           : int
     */
    public Vertex getVertex(int n) {
        return v[n-1];
    }

    /**
     * Method to add an edge to the graph
     *
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    public void addEdge(Vertex from, Vertex to, int weight) {
        Edge e = new Edge(from, to, weight);
        if(this.directed) {
            from.adj.add(e);
            to.revAdj.add(e);
        } else {
            from.adj.add(e);
            to.adj.add(e);
        }
    }

    public int size() {
        return n;
    }

    /**
     * Method to create iterator for vertices of graph
     */
    public Iterator<Vertex> iterator() {
        return new ArrayIterator<Vertex>(v);
    }

    // read a directed graph using the Scanner interface
    public static Graph readDirectedGraph(Scanner in) {
        return readGraph(in, true);
    }

    // read an undirected graph using the Scanner interface
    public static Graph readGraph(Scanner in) {
        return readGraph(in, false);
    }

    public static Graph readGraph(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph

        // create a graph instance
        Graph g = new Graph(n);
        g.directed = directed;
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            g.addEdge(g.getVertex(u), g.getVertex(v), w);
        }
        return g;
    }

    /**
     * Getter method for graph properties
     */
    public boolean isDirected() {
        return directed;
    }

    public int getN() {
        return n;
    }

    /**
     * Helper function to reversed graph of this graph
     */
    public void reverseGraph() {

        if(directed) {
            for (Vertex vertex : this) {
                // switch adj with revAdj
                List<Edge> tmpEs = vertex.adj;
                vertex.adj = vertex.revAdj;
                vertex.revAdj = tmpEs;

                // reverse the order of from and to vertex in edge
                for(Edge e: vertex.adj) {
                    Vertex tmpV = e.from;
                    e.from = e.to;
                    e.to = tmpV;
                }
            }
        }
    }
}
