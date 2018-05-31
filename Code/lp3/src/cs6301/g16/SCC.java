/**
 * <h1>Fall 2017 Long Project 3</h1>
 * <p>
 * Helper class to find SCC in the graph
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-11
 */

package cs6301.g16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SCC<T extends Graph.Vertex> extends DFS{

    List<Set<T>> sccSets; // list of SSCs

    SCC(Graph g) {
        super(g);
    }

    /**
     * Override extension point functions
     */
    @Override
	void beforeDFS() {
		super.beforeDFS();
		sccSets = new LinkedList<>();
	}
    @Override
    void outerLoop(Graph.Vertex v){
        super.outerLoop(v);
        sccSets.add(0, new HashSet<T>());
    }
    @Override
    void beforeVisitVertex(Graph.Vertex v){
        super.beforeVisitVertex(v);
        sccSets.get(0).add((T)v);
    }

    /**
     * Get number of strongly connected components in the graph
     * @return num of SCCs
     */
    public List<Set<T>> findSCCs(List<Graph.Vertex> topOrder) {

        // perform 2nd DFS in the topological order on the reversed graph
        dfs(topOrder.iterator(), true);
        return sccSets;
    }
}
