/**
 * <h1>Fall 2017 Long Project 2</h1>
 * <p>
 * Helper class to find SCC in a graph using Tarjan Algorithm (https://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm)
 * The original algorithm is recursive, and this implementation is a non-recursive version using custom stack.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-21
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class TarjanSCC extends GraphAlgorithm<TarjanSCC.TarjanVertex> {

    public static class TarjanVertex {
        int dis; // discover time
        int low; // low link value
        int cno; // component number
        boolean onStack; // flag indicates whether the vertex is currently on stack
        Iterator<Graph.Edge> it; // edge iterator

        TarjanVertex(Graph.Vertex v) {
            reset(v);
        }

        void reset(Graph.Vertex v) {
            dis = 0;
            low = 0;
            cno = 0;
            onStack = false;
            it = v.iterator();
        }
    }

    int VERBOSE;
    private int time;
    private int cno;

    public TarjanSCC(Graph g) {
        super(g);
        VERBOSE = 1;

        node = new TarjanVertex[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            node[u.getName()] = new TarjanVertex(u);
        }
    }

    public int scc() {
        // initialization
        time = 0;
        cno = 0;
        LinkedList<Graph.Vertex> stack = new LinkedList<>();
        for (Graph.Vertex v : g) {
            getVertex(v).reset(v);
        }

        // begin dfs
        for (Graph.Vertex v : g) {
            if (getVertex(v).dis == 0) {
                // unseen
                strongConnect(v, stack);
            }
        }
        return cno;
    }

    private void strongConnect(Graph.Vertex u, LinkedList<Graph.Vertex> stack) {

        LinkedList<Graph.Vertex> dfsStack = new LinkedList<>();
        dfsStack.push(u);

        while (!dfsStack.isEmpty()) {

            Graph.Vertex v = dfsStack.peek();
            TarjanVertex tv = getVertex(v);

            if (tv.dis == 0) {
                if (VERBOSE > 9)
                    System.out.println("Visit point: " + v);

                stack.push(v);
                tv.onStack = true;
                tv.dis = ++time;
                tv.low = time;
            }

            if (tv.it.hasNext()) {
                // has unseen child
                Graph.Edge e = tv.it.next();
                Graph.Vertex w = e.otherEnd(v);
                TarjanVertex tw = getVertex(w);
                if (tw.dis == 0) {
                    // push child vertex to dfsStack to visit later
                    dfsStack.push(w);
                } else if (tw.onStack) {
                    // Successor w is in stack S and hence in the current SCC
                    // Note: The next line may look odd - but is correct.
                    // It says w.dis not w.low; that is deliberate and from the original paper
                    tv.low = Math.min(tv.low, tw.dis);
                }
            } else {
                // finish visit the subtree with root v
                if (tv.low == tv.dis) {
                    // start a new strongly connected component
                    Graph.Vertex w;
                    cno++;
                    if (VERBOSE > 9)
                        System.out.print("SCC " + cno + ":{");
                    do {
                        //add w to current strongly connected component
                        w = stack.pop();
                        TarjanVertex tw = getVertex(w);
                        tw.onStack = false;
                        tw.cno = cno;
                        if (VERBOSE > 9)
                            System.out.print(" " + w);
                    }
                    while (w != v);
                    if (VERBOSE > 9)
                        System.out.println("}");
                }

                dfsStack.remove();// remove v from dfsStack
                //update parent's low value
                Graph.Vertex p = dfsStack.peek();
                if (p != null) {
                    TarjanVertex tp = getVertex(p);
                    tp.low = Math.min(tp.low, tv.low);
                }
            }
        }
    }

    public void setVERBOSE(int VERBOSE) {
        this.VERBOSE = VERBOSE;
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

        Graph 2:
        7 9
        1 2 1
        1 6 1
        2 3 1
        3 1 1
        3 4 1
        4 5 1
        5 3 1
        6 7 1
        7 3 1

        Graph 3:
        3 2
        1 2 1
        2 1 1

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

        TarjanSCC scc = new TarjanSCC(g);
        scc.VERBOSE = 10;
        scc.scc();
        for (Graph.Vertex v : g)
            System.out.println(v.toString() + " " + scc.getVertex(v).cno + " " + scc.getVertex(v).dis + " " + scc.getVertex(v).low);

        System.out.println("\n==============\nNumber of Strongly Connected Components:" + scc.cno);

    }
}
