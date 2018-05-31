/**
 * <h1>Fall 2017 Long Project 3</h1>
 * <p>
 * Implementation of the graph algorithm to find DMST in a directed graph
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-11
 */

package cs6301.g16;

import java.util.*;

import cs6301.g16.DMSTGraph.DMSTEdge;
import cs6301.g16.DMSTGraph.DMSTVertex;

public class DMST extends DFS{

    DMSTGraph dmstG;
    Graph.Vertex startVertex;
	List<Graph.Edge> dmst;
	SCC<DMSTVertex> sccAlg;

	List<Graph.Vertex> topOrder;
	List<DMSTEdge> treeEdges;
	int numOfTrees;

	public DMST(Graph g, Graph.Vertex start, List<Graph.Edge> dmst) {
		super(new DMSTGraph(g, start));
		startVertex = start;
		dmstG = (DMSTGraph) this.g;
		sccAlg = new SCC(this.g); // pass in the DMSTGraph object
        this.dmst = dmst;
	}

	@Override
    void beforeDFS(){
	    super.beforeDFS();
	    topOrder = new LinkedList<>();
        numOfTrees = 0;
        treeEdges = new LinkedList<>();
    }
    @Override
    void outerLoop(Graph.Vertex v){
        super.outerLoop(v);
        numOfTrees++;
        treeEdges.clear();
    }
    @Override
    void encounterUnseenVertex(Graph.Vertex v, Graph.Vertex unseenVertex, Graph.Edge edge){
        treeEdges.add((DMSTEdge) edge);
    }
    @Override
    void finishVisitVertex(Graph.Vertex v){
        super.finishVisitVertex(v);
        topOrder.add(0,v);
    }
	
	public int findDirectedMST(){

        // shrink phase
        while (true){
            dmstG.decreaseWeight();
            dfs();
            if (numOfTrees == 1)
                break;
            List<Set<DMSTGraph.DMSTVertex>> sccSets = sccAlg.findSCCs(topOrder);
            boolean nothingToShrink = true;
            for (Set<DMSTGraph.DMSTVertex> scc : sccSets) {
                if (scc.size() > 1) {
                    dmstG.shrinkVertexes(scc);
                    nothingToShrink = false;
                }
            }
            if(nothingToShrink) {
                System.out.println("Cannot find MST rooted at "+startVertex);
                return -1;
            }
        }

        // expand phase
        expand();

        // set dmst and calculate final weight
        dmst.clear();
        dmstG.setStartVertex(dmstG.getVertex(1));
        int weight = 0;
        int nullCount = 0;
        for(Graph.Vertex v:dmstG){
            DMSTVertex dmstV = (DMSTVertex)v;
            Graph.Edge e = dmstV.getSelectedEdge();
            if(e!=null)
                weight += e.weight;
            else {
                nullCount++;
            }

            dmst.add(e);
        }
        if(nullCount!=1){
            System.out.println("Error - No null edge in result. num of null - "+nullCount);
        }
		return weight;
	}

	void expand(){

	    Queue<DMSTEdge> edgeToExplore = new LinkedList<>(treeEdges);

        while (!edgeToExplore.isEmpty()){
            DMSTEdge de = edgeToExplore.remove();
            DMSTVertex dmstTo = (DMSTVertex)de.to;
            DMSTVertex dmstFrom = (DMSTVertex)de.from;
            if(de.origEdge!=null) {
                // set selectedEdge to vertex
                dmstTo.setSelectedEdge(de.origEdge);
            }
            else if(de.superEdge!=null){
                DMSTEdge sEdge = de.superEdge;

                // add super edge to the queue
                edgeToExplore.add(de.superEdge);

                // find spanning tree within the expanded area
                if(dmstTo.isShrunkenVertex()){
                    dmstG.expand(dmstTo);
                    dmstG.setMaskVertices(dmstTo.shrunkenVSet);
                    dmstG.setStartVertex(sEdge.toVertex());
                    dfs();
                    dmstG.setMaskVertices(null);
                    edgeToExplore.addAll(treeEdges);
                }
            }
            else {
                System.out.println("Error - encounter an DMSTEdge with no origEdge and no superEdge.");
                return;
            }
        }
    }

	public static void main(String[] args) {
        Scanner in = new Scanner("5 7	1 5 8	1 4 7	1 3 6	4 3 3	3 5 6	5 3 2	5 2 1");
        Graph g = Graph.readGraph(in, true);
        List<Graph.Edge> dmst = new ArrayList<>(g.size());
        DMST dmstAlg = new DMST(g, g.getVertex(1),dmst);
        dmstAlg.findDirectedMST();
        System.out.println(dmst);
    }

}
