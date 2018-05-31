/**
 * <h1>Fall 2017 Short Project 8 - 4</h1>
 * <p>
 * Implement BellmanFord Shortest Path
 * <p>
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import cs6301.g00.Graph;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

public class BellmanFord extends GraphAlgorithm<BellmanFord.BFVertex> {

    static class BFVertex {
        int dis, count;
        Edge pe; // edge connect to parent
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
            pe = null;
            seen = false;
        }

        Vertex getParent() {
            if (pe == null)
                return null;
            else
                return pe.otherEnd(v);
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

    private void runBF(Vertex s) {

        for (int i = 0; i < node.length; i++) {
            node[i].reset();
        }
        negCycle = false;

        Queue<Vertex> q = new LinkedList<>();
        BFVertex bs = getVertex(s);
        bs.dis = 0;
        bs.seen = true;
        q.add(s);

        while (!q.isEmpty()) {
            Vertex u = q.remove();
            BFVertex bu = getVertex(u);
            bu.seen = false;
            bu.count += 1;
            if (bu.count >= g.size()) {
                negCycle = true;
                break;
            }
            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                BFVertex bv = getVertex(v);
                if (bv.dis > bu.dis + e.getWeight()) {
                    bv.dis = bu.dis + e.getWeight();
                    bv.pe = e;
                    if (!bv.seen) {
                        q.add(v);
                        bv.seen = true;
                    }
                }
            }
        }

        start = s;
    }

    public boolean bellmanFord(Vertex s, Vertex u, List<Edge> path) {
        path.clear();

        if (start != null && !start.equals(s)) {
            start = null;
        }

        if (start == null) {
            runBF(s);
        }

        if (negCycle) {
            System.out.printf("Negtive cycle detected!\n");
            return false;
        } else {
            Vertex v = u;
            while (!v.equals(s)) {
                BFVertex bv = getVertex(v);
                if (bv.pe == null) {
                    System.out.printf("No path from %s to %s!\n", s, u);
                    return false;
                }
                path.add(bv.pe);
                v = bv.getParent();
            }
            Collections.reverse(path);
            return true;
        }

    }

    public static void main(String[] args) {

        System.out.println("Graph: 5 10	1 2 8	1 3 18	1 4 19	1 5 17	2 3 4	2 4 8	2 5 6	3 4 3	3 5 1	4 5 4");
        Scanner in = new Scanner("5 10	1 2 8	1 3 18	1 4 19	1 5 17	2 3 4	2 4 8	2 5 6	3 4 3	3 5 1	4 5 4");
        Graph g = Graph.readDirectedGraph(in);
        List<Edge> shortestPath = new LinkedList<>();
        BellmanFord bf = new BellmanFord(g);

        System.out.println("Shortest Path from 1 to 5:");
        if (bf.bellmanFord(g.getVertex(1), g.getVertex(5), shortestPath))
            System.out.println(shortestPath);
        System.out.println();

        System.out.println("Shortest Path from 2 to 5:");
        if (bf.bellmanFord(g.getVertex(2), g.getVertex(5), shortestPath))
            System.out.println(shortestPath);
        System.out.println();

        System.out.println("Shortest Path from 2 to 1:");
        if (bf.bellmanFord(g.getVertex(2), g.getVertex(1), shortestPath))
            System.out.println(shortestPath);
        System.out.println();

        System.out.println("Change to graph with negative cycle\n");
        System.out.println("Graph: 3 3	1 2 -1	2 3 -1	3 1 -1");

        in = new Scanner("3 3	1 2 -1	2 3 -1	3 1 -1");
        g = Graph.readDirectedGraph(in);
        bf = new BellmanFord(g);
        System.out.println("Shortest Path from 1 to 2:");
        if (bf.bellmanFord(g.getVertex(1), g.getVertex(2), shortestPath))
            System.out.println(shortestPath);
        System.out.println();
    }

}
