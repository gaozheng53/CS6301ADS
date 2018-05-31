/*
 * Copyright (c) 2017.
 *
 * @author Hanlin He (hxh160630@utdallas.edu) ,
 * @author Binhan Wang (bxw161330@utdallas.edu),
 * @author Zheng Gao (zxg170430@utdallas.edu)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cs6301.g16;

import cs6301.g16.Graph.Edge;
import cs6301.g16.Graph.Vertex;

import java.util.*;

public class Flow {

    Graph g;
    ResidualGraph gf;
    Vertex s, t;
    HashMap<Edge, Integer> capacity;

    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
        this.g = g;
        this.gf = null;
        this.s = s;
        this.t = t;
        this.capacity = capacity;
    }

    // Return max flow found by Dinitz's algorithm
    public int dinitzMaxFlow() {
        this.gf = new ResidualGraph(g, capacity, false);

        List<List<Edge>> paths = new LinkedList<>();

        while (true) {
            BellmanFord bf = new BellmanFord(gf);
            boolean result = bf.computeShortestPaths(gf.getVertex(s), gf.getVertex(t), paths);
            if (!result) // t not reachable from s in residual graph.
                break;
            paths.forEach(gf::augment);
        }

        int maxFlow = 0;
        for (Edge e : s.adj) {
            maxFlow += gf.getEdge(e).getFlow();
        }

        return maxFlow;
    }

    // Return max flow found by relabelToFront algorithm
    public int relabelToFront() {
        this.gf = new ResidualGraph(g, capacity, false);

        // initialize
        gf.getVertex(s).setHeight(g.size());
        s.forEach(edge -> gf.push(edge, capacity(edge)));

        LinkedList<Vertex> L = new LinkedList<>();
        g.forEach(v -> {
            if (v.getName() != s.getName() && v.getName() != t.getName())
                L.add(v);
        });
        boolean done = false;
        while (!done) {
            Iterator<Vertex> it = L.iterator();
            done = true;
            Vertex u = null;
            while (it.hasNext()) {
                u = it.next();
                ResidualGraph.ResidualVertex ru = gf.getVertex(u);
                if (ru.getExcess() == 0)
                    continue;
                int oldHeight = ru.getHeight();
                gf.discharge(ru);
                if (ru.getHeight() != oldHeight) {
                    done = false;
                    break;
                }
            }
            if (!done) {
                it.remove();
                L.addFirst(u);
            }
        }

        return gf.getVertex(t).getExcess();
    }

    // flow going through edge e
    public int flow(Edge e) {
        return gf.getEdge(e).getFlow();
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return gf.getEdge(e).getCapacity();
    }


    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
        BFS bfs = new BFS(gf, s);

        Set<Vertex> ret = new HashSet<>();

        for (Vertex v : g) {
            if (bfs.seen(v))
                ret.add(v);
        }
        return ret;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {

        Set<Vertex> ret = new HashSet<>();
        Set<Vertex> minCutS = minCutS();
        for (Vertex v : g)
            if (!minCutS.contains(v))
                ret.add(v);
        return ret;
    }

    public boolean verify() {
        return !minCutS().contains(t);
    }
}
