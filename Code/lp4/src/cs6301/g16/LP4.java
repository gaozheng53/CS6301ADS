
// Starter code for LP4
// Do not rename this file or move it away from cs6301/g??

// change following line to your group number
package cs6301.g16;

import cs6301.g16.Graph.Vertex;
import cs6301.g16.Graph.Edge;

import java.util.*;

public class LP4 {

    static class RewardPathPair implements Comparable<RewardPathPair> {

        List<Vertex> path;
        Integer reward;
        Vertex lastVertex;

        RewardPathPair(List<Vertex> path, HashMap<Vertex, Integer> rewardMap) {
            this.path = path;
            reward = 0;
            for (Vertex v : path) {
                reward += rewardMap.get(v);
            }
            lastVertex = ((LinkedList<Vertex>) path).getLast();
        }

        @Override
        public int compareTo(RewardPathPair o) {
            return reward.compareTo(o.reward);
        }

        @Override
        public String toString() {
            return reward + "-" + path;
        }
    }

    Graph g;
    Vertex s;

    // common constructor for all parts of LP4: g is graph, s is source vertex
    public LP4(Graph g, Vertex s) {
        this.g = g;
        this.s = s;
    }


    // Part a. Return number of topological orders of g
    public long countTopologicalOrders() {
        TopologicalPermutation alg = new TopologicalPermutation(g);
        List<List<Vertex>> permutations = new LinkedList<>();
        if (alg.findPermutation(permutations, new ArrayList<>()))
            return permutations.size();
        return 0;
    }


    // Part b. Print all topological orders of g, one per line, and 
    //	return number of topological orders of g
    public long enumerateTopologicalOrders() {
        TopologicalPermutation alg = new TopologicalPermutation(g);
        List<List<Vertex>> permutations = new LinkedList<>();
        if (alg.findPermutation(permutations, new ArrayList<>())) {
            for (List<Vertex> topOrder : permutations) {
                for (Vertex v : topOrder) {
                    System.out.print(v + " ");
                }
                System.out.println();
            }
            return permutations.size();
        }
        return 0;
    }


    // Part c. Return the number of shortest paths from s to t
    // 	Return -1 if the graph has a negative or zero cycle
    public long countShortestPaths(Vertex t) {
        BellmanFord bf = new BellmanFord(g);
        List<List<Vertex>> paths = new LinkedList<>();
        boolean result = bf.computeShortestPaths(s, t, paths);
        if (result)
            return paths.size();
        System.out.println("Non-positive cycle in graph or target vertex not reachable.  Unable to solve problem.");
        return 0;
    }


    // Part d. Print all shortest paths from s to t, one per line, and 
    //	return number of shortest paths from s to t.
    //	Return -1 if the graph has a negative or zero cycle.
    public long enumerateShortestPaths(Vertex t) {
        BellmanFord bf = new BellmanFord(g);
        List<List<Vertex>> paths = new LinkedList<>();
        boolean result = bf.computeShortestPaths(s, t, paths);
        if (result) {
            BellmanFord.printAllShortestPath(paths);
            return paths.size();
        }
        System.out.println("Non-positive cycle in graph or target vertex not reachable.  Unable to solve problem.");
        return 0;
    }


    // Part e. Return weight of shortest path from s to t using at most k edges
    public int constrainedShortestPath(Vertex t, int k) {
        BellmanFord bf = new BellmanFord(g);
        List<Vertex> path = new LinkedList<>();
        int result = bf.computeShortestPaths(s, t, k, path);
        return result;
    }


    // Part f. Reward collection problem
    // Reward for vertices is passed as a parameter in a hash map
    // tour is empty list passed as a parameter, for output tour
    // Return total reward for tour
    public int reward(HashMap<Vertex, Integer> vertexRewardMap, List<Vertex> tour) {

        BellmanFord bf = new BellmanFord(g);
        List<List<Vertex>> paths = new LinkedList<>();
        bf.computeShortestPaths(s, paths);

        PriorityQueue<RewardPathPair> pq = new PriorityQueue<>(Comparator.reverseOrder());

        for (List<Vertex> path : paths) {
            pq.add(new RewardPathPair(path, vertexRewardMap));
        }

        XGraph xg = new XGraph(g);

        while (!pq.isEmpty()) {
            RewardPathPair pair = pq.poll();

            BFS bfs = new BFS(xg, xg.getVertex(pair.lastVertex));

            for (Vertex v : pair.path) {
                xg.getVertex(v).disable();
            }

            xg.getVertex(pair.lastVertex).disabled = false;
            xg.getVertex(pair.path.get(0)).disabled = false;

            bfs.bfs();

            if (bfs.seen(s)) {
                LinkedList<Vertex> returnPath = new LinkedList<>();

                for (Vertex v = s; v.getName() != pair.lastVertex.getName(); v = bfs.getParent(v))
                    returnPath.addFirst(v);

                tour.addAll(pair.path);
                tour.addAll(returnPath);

                return pair.reward;
            }

            for (Vertex v : pair.path) {
                xg.getVertex(v).disabled = false;
            }
        }

        return 0;
    }

    // Do not modify this function
    static void printGraph(Graph g, HashMap<Vertex, Integer> map, Vertex s, Vertex t, int limit) {
        System.out.println("Input graph:");
        for (Vertex u : g) {
            if (map != null) {
                System.out.print(u + "($" + map.get(u) + ")\t: ");
            } else {
                System.out.print(u + "\t: ");
            }
            for (Edge e : u) {
                System.out.print(e + "[" + e.weight + "] ");
            }
            System.out.println();
        }
        if (s != null) {
            System.out.println("Source: " + s);
        }
        if (t != null) {
            System.out.println("Target: " + t);
        }
        if (limit > 0) {
            System.out.println("Limit: " + limit + " edges");
        }
        System.out.println("___________________________________");
    }
}
