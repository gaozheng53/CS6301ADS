/**
 * <h1>Fall 2017 Short Project 8 - 1\2</h1>
 * <p>
 * Implement BFS and DAG Shortest path algorithm
 * <p>
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import cs6301.g00.Graph;

import java.util.*;

public class DAGShortestPath extends GraphAlgorithm<DAGShortestPath.BFSVertex> {
    private static final int INFINITY = Integer.MAX_VALUE;

    // Class to store information about a vertex in this algorithm
    static class BFSVertex {
        boolean seen;
        Graph.Vertex parent;
        int distance;  // distance of vertex from source

        BFSVertex(Graph.Vertex u) {
            seen = false;
            parent = null;
            distance = INFINITY;
        }
    }

    private Graph.Vertex src;

    public DAGShortestPath(Graph g, Graph.Vertex s) {
        super(g);
        this.src = s;
        node = new BFSVertex[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            node[u.getName()] = new BFSVertex(u);
        }
        // Set source to be at distance 0
        getVertex(s).distance = 0;
    }

    // reinitialize allows running BFS many times, with different sources
    private void reinitialize(Graph.Vertex newSource) {
        src = newSource;
        for (Graph.Vertex u : g) {
            BFSVertex bu = getVertex(u);
            bu.seen = false;
            bu.parent = null;
            bu.distance = INFINITY;
        }
        getVertex(src).distance = 0;
    }


    private boolean seen(Graph.Vertex u) {
        return getVertex(u).seen;
    }

    private int distance(Graph.Vertex u) {
        return getVertex(u).distance;
    }


    // Visit a node v from u
    private void visit(Graph.Vertex u, Graph.Vertex v) {
        BFSVertex bv = getVertex(v);
        bv.seen = true;
        bv.parent = u;
        bv.distance = distance(u) + 1;
    }

    /**
     * Q1 Implement BFS.
     */
    public void bfs() {
        Queue<Graph.Vertex> q = new LinkedList<>();
        q.add(src);
        System.out.println("BFS order: ");
        while (!q.isEmpty()) {
            Graph.Vertex u = q.remove();
            System.out.print(u.getName() + 1 + " ");
            for (Graph.Edge e : u) {
                Graph.Vertex v = e.otherEnd(u);
                if (!seen(v)) {
                    visit(u, v);
                    q.add(v);
                }
            }
        }
        System.out.println();
    }

    /**
     * Q2: Implement DAG shortest paths:
     * public void dagShortestPaths() { ... }
     * Implement this algorithm without duplicating the DFS code for
     * finding topological order into the DAGShortestPath class.
     * Reuse your DFS code from previous projects.
     */

    private Deque<Graph.Vertex> topologicalOrder(Graph g) {
        DFS dfs = new DFS(g);
        dfs.dfs();
//        System.out.println(dfs.getDecFinList());
        return dfs.getDecFinList();
    }


    private boolean relax(Graph.Vertex u, Graph.Edge e) {
        Graph.Vertex v = e.otherEnd(u);
        if (distance(v) > distance(u) + e.getWeight()) {
            getVertex(v).distance = distance(u) + e.getWeight();
            getVertex(v).parent = u;
            return true;
        }
        return false;
    }

    public void dagShortestPaths(Graph g, Graph.Vertex s) {
        Deque<Graph.Vertex> topoOrder = topologicalOrder(g);
        while (topoOrder.peek() != s)  //Skip the unreachable nodes from s
            topoOrder.poll();
        reinitialize(s);
        while (!topoOrder.isEmpty()) {
            Graph.Vertex u = topoOrder.poll();
            for (Graph.Edge e : u) {
                relax(u, e);
            }
        }
        printPath(s);
    }

    private void printPath(Graph.Vertex s) {
        for (Graph.Vertex v : g) {
            if (v == s)
                System.out.println("Shortest path start from " + v);
            else if (getVertex(v).parent != null) {
                System.out.println(getVertex(v).parent + "-->" + v);

            }
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Graph g = Graph.readDirectedGraph(in);
        System.out.println("Enter the name of start point:");
        int startPoint = in.nextInt();
        DAGShortestPath sp = new DAGShortestPath(g, g.getVertex(startPoint));
        //Test Q1
        sp.bfs();
        //Test Q2
        if(TestDAG.isDAG(g))
            sp.dagShortestPaths(g, g.getVertex(startPoint));
        else
            System.out.println("Not a DAG");

    }
}
/*
Test:
6 10
1 2 11
2 3 8
4 3 -15
5 4 10
5 2 4
5 1 8
5 6 7
6 4 6
2 6 -5
6 3 2
BFS order:
1 2 3 6 4
Shortest path start from 1
1-->2
4-->3
6-->4
2-->6
 */