/**
 * <h1>Fall 2017 Short Project 3-1</h1>
 * <p>
 * For a connected, undirected graph G=(V,E), an edge e in E is
 * called a bridge if the removal of e from G breaks the graph into 2
 * components.  A vertex u in V is called a cut vertex if the removal
 * of u, along with its incident edges from G breaks it into 2 or more
 * components.  The problem of finding bridges and cut vertices of a
 * given graph will be discussed in class (see also Problem 22-2 in
 * Cormen et al's Introduction to Algorithms, 3rd ed).
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */
package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BridgeCut extends TestDAG {
    static class BridgeCutVertex extends TestDAG.DAGVertex{

        boolean cut; // indicator to show whether the vertex is a cutß
        int dis; // discover time
        int low; // low time
        Graph.Vertex parent; // parent vertex during the search
        @Override
        void reset() {
            super.reset();
            dis = 0;
            low = 0;
            cut = false;
            parent = null;
        }

        @Override
        public String toString() {
            return "dis:"+dis+" low:"+low+" cut:"+cut+" parent:"+parent;
        }
    }

    private int time;
    private List<Graph.Edge> bridges;
    private List<Graph.Vertex> cutVertexes;

    BridgeCut(Graph g){
        super(g);
    }

    /**
     * Override/Overload parallel array functions to use BridgeCutVertex in the algorithm
     */
    @Override
    void initParallelArray() {
        node = new BridgeCutVertex[g.size()];
        // Create array for storing vertex properties
        for(Graph.Vertex u: g) {
            node[u.getName()] = new BridgeCutVertex();
        }
    }

    BridgeCutVertex getVertex(Graph.Vertex v){
        return  (BridgeCutVertex) super.getVertex(v);
    }

    /**
     * Override DFS extension functions
     */
    @Override
    void beforeDFS(){
        super.beforeDFS();
        time = 0;
    }
    @Override
    void encounterUnseenVertex(Graph.Vertex v, Graph.Vertex unseenVertex){
        getVertex(unseenVertex).parent = v;
    }
    @Override
    void encounterSeenVertex(Graph.Vertex v, Graph.Vertex seenVertex){
        super.beforeDFS();
        BridgeCutVertex bv = getVertex(v);
        BridgeCutVertex bu = getVertex(seenVertex);
        if(bu.dis<bv.low&&bv.parent!=seenVertex) {
            // when encounter a seen vertex whose dis value is less than current low,
            // set the lower dis to current low and propagate this change upwards
            bv.low = bu.dis;

            Graph.Vertex p = bv.parent;
            while (p!=null && getVertex(p).low>bv.low){
                // set lower low upward
                getVertex(p).low = bv.low;
                bv = getVertex(p);
                p = bv.parent;
            }
        }
    }
    @Override
    void beforeVisitVertex(Graph.Vertex v){
        super.finishVisitVertex(v);
        getVertex(v).dis = ++time;
        getVertex(v).low = time;
    }

    /** Find bridges and cut vertices of an undirected graph g.  Assume that g is connected.
     *  The list of bridges of g is returned by the function.  Cut vertices are marked
     *  by setting to true a boolean field "cut" defined for each vertex.
     */
    void findBridgeCut() {
        dfs();
        for(Graph.Vertex v : g){
            System.out.println(v+" "+getVertex(v));
        }
        Set<Graph.Edge> bridges = new HashSet<>();
        Set<Graph.Vertex> cuts = new HashSet<>();

        for(Graph.Vertex v: g) {
            BridgeCutVertex bv = getVertex(v);
            //set cut in BridgeCutVertex
            Graph.Vertex p = bv.parent;
            if((p==null&&v.adj.size()>=2)) {
                // root with 2 or more children
                bv.cut = true;
                cuts.add(v);
            }
            else if(p!=null)
            {
                BridgeCutVertex bp = getVertex(p);
                if(bp.parent!=null && bv.low>=bp.dis) {
                    // parent is not root, and v.low>p.low
                    bp.cut = true;
                    cuts.add(p);
                }
            }

            //find bridges
            for(Graph.Edge e : v) {
                Graph.Vertex o = e.otherEnd(v);
                BridgeCutVertex bo = getVertex(o);
                if(bo.parent == v && bo.low>=bo.dis) {
                    bridges.add(e);
                }
                else if(bv.parent == o && bv.low>=bv.dis) {
                    bridges.add(e);
                }
            }
        }
        this.bridges = new LinkedList<>(bridges);
        this.cutVertexes = new LinkedList<>(cuts);
    }


    /**
     * Signature required in the question
     * @param g graph to check
     * @return a list of bridge in the graph
     */
    public static List<Graph.Edge> findBridgeCut(Graph g) throws FileNotFoundException {
        BridgeCut bc = new BridgeCut(g);
        bc.printFootprint = true;
        bc.findBridgeCut();

        // Print out cuts for testing
        System.out.println("Cut Vertexes: "+bc.cutVertexes);

        return bc.bridges;
    }

    /**
     * Main function for testing
     * @param args
     * @throws FileNotFoundException
     */

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Test Graph 1:
        10 14
        1 2 1
        1 3 1
        1 5 1
        2 3 1
        2 4 1
        3 4 1
        5 6 1
        5 10 1
        6 7 1
        6 8 1
        6 9 1
        6 10 1
        7 8 1
        8 9 1

         */
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in);

        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        if (g.size()>0) {
            System.out.println("Bridges: "+BridgeCut.findBridgeCut(g));
        }
    }

}
