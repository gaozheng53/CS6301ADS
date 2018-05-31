/**
 * <h1>Fall 2017 Short Project 8 - 3</h1>
 * <p>
 * Implement Dijkstra Shortest path algorithm with Indexed Heap.
 * <p>
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;

import cs6301.g00.Graph;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

public class Dijkstra extends GraphAlgorithm<Dijkstra.DJVertex> {

    static class DJVertex implements Index, Comparable<DJVertex> {
        Integer dis;
        Edge pe;
        boolean seen;

        private int index;
        private Vertex v;

        DJVertex(Vertex v) {
            super();
            this.v = v;
            index = v.getName();
            reset();
        }

        void reset() {
            dis = Integer.MAX_VALUE;
            pe = null;
            seen = false;
        }

        Vertex getParent() {
            if (pe == null)
                return null;
            else
                return pe.otherEnd(v);
        }

        @Override
        public void putIndex(int index) {
            this.index = index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        @Override
        public int compareTo(DJVertex otherVertex) {
            return this.dis.compareTo(otherVertex.dis);
        }

        @Override
        public String toString() {
            return v.toString() + " " + dis + " " + index;
        }

    }

    private Vertex start;
    private boolean negWeight;

    public Dijkstra(Graph g) {
        super(g);
        node = new DJVertex[g.size()];
        for (Vertex v : g) {
            node[v.getName()] = new DJVertex(v);
        }
        start = null;
    }

    private void runDJ(Vertex s) throws Exception {
        IndexedHeap<DJVertex> q = new IndexedHeap<>(new DJVertex[g.size()], DJVertex::compareTo, g.size());

        for (int i = 0; i < node.length; i++) {
            node[i].reset();
            q.insert(node[i]);
        }
        negWeight = false;

        DJVertex ds = getVertex(s);
        ds.dis = 0;
        q.decreaseKey(ds);

        while (!q.isEmpty()) {
            DJVertex du = q.remove();
            Vertex u = du.v;
            du.seen = true;
            for (Edge e : u) {
                if (e.getWeight() < 0) {
                    negWeight = true;
                    start = s;
                    return;
                }
                Vertex v = e.otherEnd(u);
                DJVertex dv = getVertex(v);
                if ((long) dv.dis > (long) du.dis + (long) e.getWeight()) {
                    // cast to long because inf is represent as integer.max_value
                    // keep using int might encounter integer overflow
                    dv.dis = du.dis + e.getWeight();
                    dv.pe = e;
                    q.decreaseKey(dv);
                }
            }
        }

        start = s;
    }

    public boolean dijkstra(Vertex s, Vertex u, List<Edge> path) throws Exception {
        path.clear();

        if (start != null && !start.equals(s)) {
            start = null;
        }

        if (start == null) {
            runDJ(s);
        }

        if (negWeight) {
            System.out.printf("Negative weight edge detected!\n");
            return false;
        } else {
            Vertex v = u;
            while (!v.equals(s)) {
                DJVertex bv = getVertex(v);
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

    public static void main(String[] args) throws Exception {

        System.out.println("Graph: 5 10	1 2 8	1 3 18	1 4 19	1 5 17	2 3 4	2 4 8	2 5 6	3 4 3	3 5 1	4 5 4");
        Scanner in = new Scanner("5 10	1 2 8	1 3 18	1 4 19	1 5 17	2 3 4	2 4 8	2 5 6	3 4 3	3 5 1	4 5 4");
        Graph g = Graph.readDirectedGraph(in);
        List<Edge> shortestPath = new LinkedList<>();
        Dijkstra dj = new Dijkstra(g);

        System.out.println("Shortest Path from 1 to 5:");
        if (dj.dijkstra(g.getVertex(1), g.getVertex(5), shortestPath))
            System.out.println(shortestPath);
        System.out.println();

        System.out.println("Shortest Path from 2 to 5:");
        if (dj.dijkstra(g.getVertex(2), g.getVertex(5), shortestPath))
            System.out.println(shortestPath);
        System.out.println();

        System.out.println("Shortest Path from 2 to 1:");
        if (dj.dijkstra(g.getVertex(2), g.getVertex(1), shortestPath))
            System.out.println(shortestPath);
        System.out.println();

        System.out.println("Change to graph with negative weight edge\n");
        System.out.println("Graph: 3 3	1 2 1	2 3 -1	3 1 1");

        in = new Scanner("3 3	1 2 1	2 3 -1	3 1 1");
        g = Graph.readDirectedGraph(in);
        dj = new Dijkstra(g);
        System.out.println("Shortest Path from 1 to 2:");
        if (dj.dijkstra(g.getVertex(1), g.getVertex(2), shortestPath))
            System.out.println(shortestPath);
        System.out.println();
    }

}
