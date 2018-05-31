/**
 * <h1>Fall 2017 Short Project 1</h1>
 * <p>
 * Implement breadth-first search (BFS), and solve the problem of finding the diameter of a tree
 * that works as follows: Run BFS, starting at an arbitrary node as root. Let u be a node at maximum
 * distance from the root. Run BFS again, with u as the root. Output diameter: path from u to a node
 * at maximum distance from u.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.1
 * @since 2017-08-25
 */

package cs6301.g16;

import cs6301.g00.Graph;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.LinkedHashSet;

/**
 * BFS class used to perform BFS from a vertex
 * It also stores a list of visited vertexes and the relationship from a visited vertex to its parent during the BFS.
 */
public class BFS {

    // Map from a vertex to its parent vertex during the BFS
    private Map<Graph.Vertex,Graph.Vertex> parentMap;
    // Array to store visited Vertexes in visiting order
    private Graph.Vertex[] visitedArray;

    /**
     * Init a BFS Object with the startPoint Vertex and perform BFS
     * @param startPoint
     */
    public BFS(Graph.Vertex startPoint) {
        parentMap = new HashMap<>();

        // Queue of vertexes about to visit
        Queue<Graph.Vertex> aboutToVisitQueue = new LinkedList<>();
        // LinkedHashSet to store visited vertexes
        LinkedHashSet<Graph.Vertex> visitedLinkedSet = new LinkedHashSet<>();

        // Start BFS, add startPoint to queue.
        aboutToVisitQueue.add(startPoint);

        while (!aboutToVisitQueue.isEmpty()) {
            // Take a vertex from queue.
            Graph.Vertex s = aboutToVisitQueue.remove();
            for (Graph.Edge e : s) {
                // For each edge s->v, if v is not visited, put v in queue, and put (v, s) to map.
                Graph.Vertex v = e.otherEnd(s);
                if (!visitedLinkedSet.contains(v)) {
                    aboutToVisitQueue.add(v);
                    parentMap.put(v, s);
                }
            }
            visitedLinkedSet.add(s);
        }
        visitedArray = new Graph.Vertex[visitedLinkedSet.size()];
        visitedLinkedSet.toArray(visitedArray);
    }

    /**
     *
     * @param v a vertex object
     * @return the parent of the vertex during the BFS, null will be returned if the vertex is not visited
     */
    public Graph.Vertex getParent(Graph.Vertex v) {
        if (parentMap.containsKey(v))
            return parentMap.get(v);
        else
            return null;
    }

    /**
     * Getter for visitedArray
     */
    public Graph.Vertex[] getVisitedArray() {
        return visitedArray;
    }
}
