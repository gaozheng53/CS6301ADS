/**
 * <h1>Fall 2017 Long Project 4</h1>
 * <p>
 * Extend BellmanFord Shortest Path to print all shortest path between 2 vertices
 * <p>
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import cs6301.g16.Graph.Edge;
import cs6301.g16.Graph.Vertex;

import java.util.*;

public class BellmanFord extends GraphAlgorithm<BellmanFord.BFVertex> {

    static class BFVertex {
        int dis, count, edgeCount;
        List<Edge> pe; // edge connect to parent
        boolean seen;

        private Vertex v;

        BFVertex(Vertex v) {
            super();
            this.v = v;
            reset();
        }

        void reset() {
            dis = Integer.MAX_VALUE;
            count = 0;
            edgeCount = 0;
            pe = new LinkedList<>();
            seen = false;
        }
    }

    private boolean negCycle;
    private Vertex start;

    public BellmanFord(Graph g) {
        super(g);
        node = new BFVertex[g.size()];
        for (Vertex v : g) {
            node[v.getName()] = new BFVertex(v);
        }
        start = null;
    }

    /**
     * Bellman-Ford algorithm to compute shortest path from {@code s}. After execution, the shortest
     * distance of each vertex was computed and store in {@code v.dis}.
     *
     * @param s Source vertex.
     */
    private void runBF(Vertex s) {

        // Initialize all vertices.
        for (BFVertex n : node) {
            n.reset();
        }
        negCycle = false;

        Queue<Vertex> vertexQueue = new LinkedList<>();

        // Put source into queue.
        BFVertex bs = getVertex(s);
        bs.dis = 0;
        bs.seen = true;
        vertexQueue.offer(s);

        while (!vertexQueue.isEmpty()) { // still vertices and edges to be relaxed.
            Vertex u = vertexQueue.poll();
            BFVertex bu = getVertex(u);
            bu.seen = false; // since removed from queue, switch flag.
            bu.count += 1; // increase operation count on bu

            // count >= |V| ==> negative circle.
            if (bu.count >= g.size()) {
                negCycle = true;
                break;
            }

            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                BFVertex bv = getVertex(v);
                if (bv.dis > bu.dis + e.weight) { // tense edges found
                    bv.dis = bu.dis + e.weight; // relax edges
                    bv.edgeCount = bu.edgeCount + 1; // update edge count to v

                    // replace existing parent vertices list.
                    bv.pe.clear();
                    bv.pe.add(e);

                    // add v to queue if v was not already in queue.
                    if (!bv.seen) {
                        vertexQueue.offer(v);
                        bv.seen = true;
                    }
                } else if (bv.dis == bu.dis + e.weight) {
                    bv.pe.add(e);

                    // update minimum edge count to v
                    if (bu.edgeCount + 1 < bv.edgeCount)
                        bv.edgeCount = bu.edgeCount + 1;
                }
            }
        }

        start = s;
    }

    /**
     * Compute all possible shortest paths from {@code s} to {@code u}, store in {@code paths} and
     * return {@code true} if correct paths found and {@code false} if <ul> <li>non-positive circle
     * found,</li> <li>{@code u} is not reachable from {@code v}.</li> </ul>
     *
     * @param s     Start vertex.
     * @param u     Target vertex.
     * @param paths List to store shortest paths from {@code s} to {@code u}.
     *
     * @return {@code true} if correct paths found and {@code false} otherwise.
     */
    public boolean computeShortestPaths(Vertex s, Vertex u, List<List<Vertex>> paths) {

        if (start != null && !start.equals(s)) {
            start = null;
        }

        // Use Bellman Ford algorithm to compute all shortest paths from s.
        if (start == null) {
            runBF(s);
        }

        if (negCycle)
            return false;

        paths.clear();

        // Enumerate all shortest paths from s to u.
        return enumerateShortestPaths(s, u, new LinkedList<>(), paths);
    }


    public void computeShortestPaths(Vertex s, List<List<Vertex>> paths) {

        if (start != null && !start.equals(s)) {
            start = null;
        }

        // Use Bellman Ford algorithm to compute all shortest paths from s.
        if (start == null) {
            runBF(s);
        }

        if (negCycle)
            return;

        paths.clear();

        // Enumerate all shortest paths from s to u.

        for(Vertex u : g) {
            if(u!=s)
                enumerateShortestPaths(s, u, new LinkedList<>(), paths);
        }
    }

    /**
     * Enumerate all shortest paths from {@code s} to {@code u}.
     *
     * @param s     Start vertex.
     * @param u     Target vertex.
     * @param path  Path to build recursively.
     * @param paths List to store shortest paths from {@code s} to {@code u}.
     *
     * @return {@code true} if correct paths found and {@code false} otherwise.
     */
    private boolean enumerateShortestPaths(Vertex s, Vertex u, List<Vertex> path, List<List<Vertex>> paths) {

        path.add(0, u);
        if (path.size() > g.size())
            return false;

        if (u.getName() == s.getName()) {
            paths.add(new LinkedList<>(path));

        } else {
            BFVertex bu = getVertex(u);
            if (bu.pe.isEmpty()) {
                // No path from s to u.
                return false;
            } else {
                for (Edge e : bu.pe) {
                    Vertex p = e.otherEnd(u);
                    if (!enumerateShortestPaths(s, p, path, paths)) // Non-positive cycle
                        return false;
                }
            }
        }

        path.remove(0);
        return true;
    }

    /**
     * Compute shortest paths from {@code s} to {@code u} with specified maximum edge, store in
     * {@code paths} and return {@code true} if correct paths found and {@code false} if
     *
     * @param s     Start vertex.
     * @param u     Target vertex.
     * @param k     Maximum number of vertices in shortest path.
     * @param paths List of vertices to store shortest path from {@code s} to {@code u}.
     *
     * @return The length of shortest path with specific maximum number of edges.
     */
    public int computeShortestPaths(Vertex s, Vertex u, int k, List<Vertex> paths) {

        if (start != null && !start.equals(s)) {
            start = null;
        }

        // Use Bellman Ford algorithm to compute all shortest paths from s.
        if (start == null) {
            runBF(s);
        }

        if (negCycle)
            return -1;

        BFVertex bu = getVertex(u);
        if (bu.edgeCount <= k)
            return bu.dis;

        int min = Integer.MAX_VALUE;
        for (Edge e : u.revAdj) {
            Vertex p = e.otherEnd(u);
            BFVertex bp = getVertex(p);
            if (bp.edgeCount < k) {
                int ndist = bp.dis + e.weight;
                if (min > ndist)
                    min = ndist;
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static void printAllShortestPath(List<List<Vertex>> paths) {

        for (List<Vertex> path : paths) {
            for (Vertex v : path)
                System.out.print(v + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {

        System.out.println("Graph:\n    5 6 \n" +
                "    1 2 2\n" +
                "    1 3 3\n" +
                "    2 4 5\n" +
                "    3 4 4\n" +
                "    4 5 1\n" +
                "    5 1 -7\n" +
                "    1 5\n");
        Scanner in = new Scanner("5 6 \n" +
                "    1 2 2\n" +
                "    1 3 3\n" +
                "    2 4 5\n" +
                "    3 4 4\n" +
                "    4 5 1\n" +
                "    5 1 -7\n" +
                "    1 5");
        Graph g = Graph.readDirectedGraph(in);
        List<List<Edge>> shortestPath = new LinkedList<>();
        BellmanFord bf = new BellmanFord(g);

        List<List<Vertex>> paths = new LinkedList<>();

        bf.computeShortestPaths(g.getVertex(1), g.getVertex(5), paths);

        printAllShortestPath(paths);
    }

}
