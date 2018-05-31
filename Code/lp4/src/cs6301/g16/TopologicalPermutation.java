package cs6301.g16;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class TopologicalPermutation extends GraphAlgorithm<TopologicalPermutation.TopVertex> {

    static class TopVertex {
        int inDegree = 0;
        boolean visited = false;
        void reset(Graph.Vertex v){
            inDegree = v.revAdj.size();
            visited = false;
        }
    }

    TopologicalPermutation(Graph g) {
        super(g);

        node = new TopVertex[g.size()];
        // Create array for storing vertex properties
        for(Graph.Vertex u: g) {
            node[u.getName()] = new TopVertex();
        }
    }

    boolean allTopologicalOrder(List<List<Graph.Vertex>> permutations, List<Graph.Vertex> curOrder) {

        boolean flag = false;

        for(Graph.Vertex u : g) {
            TopVertex tu = getVertex(u);
            if (tu.inDegree == 0 && !tu.visited)
            {
                //  reducing indegree of adjacent vertices
                for (Graph.Edge e : u.adj){
                    getVertex(e.otherEnd(u)).inDegree--;
                }
                //  including in result
                curOrder.add(u);
                tu.visited = true;

                if(!allTopologicalOrder(permutations, curOrder)){
                    return false;
                }

                // resetting visited, res and indegree for
                // backtracking
                tu.visited = false;
                curOrder.remove(curOrder.size()-1);
                for (Graph.Edge e : u.adj){
                    getVertex(e.otherEnd(u)).inDegree++;
                }

                flag = true;
            }
        }

        //  We reach here if all vertices are visited.
        //  So we print the solution here
        if (!flag)
        {
            if(curOrder.size()!=g.size())
                return false;

            permutations.add(new LinkedList<>(curOrder));
        }
        return true;
    }

    boolean findPermutation(List<List<Graph.Vertex>> permutations, List<Graph.Vertex> topListTemplate){
        // the graph has to be directed
        if(!g.isDirected())
            return false;

        // set initial inDegree
        for (Graph.Vertex v : g) {
            getVertex(v).reset(v); // update in-degree
        }

        allTopologicalOrder(permutations, topListTemplate);

        return true;
    }

    public static void main(String args[]) throws IOException {

         /*
        Test Graph 1:
        9 12
        1 2 1
        1 3 1
        1 4 1
        2 3 1
        2 6 1
        3 5 1
        3 6 1
        4 3 1
        4 7 1
        5 8 1
        6 8 1
        6 9 1
        7 9 1

        Test Graph 2:
        6 6
        1 3 1
        1 4 1
        2 4 1
        2 5 1
        3 6 1
        6 5 1
         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in,true);

        TopologicalPermutation alg = new TopologicalPermutation(g);
        List<List<Graph.Vertex>> permutations = new LinkedList<>();
        alg.findPermutation(permutations, new ArrayList<>());

        System.out.printf("Find %d topological order in the graph:\n", permutations.size());
        for(List<Graph.Vertex> topOrder : permutations){
            System.out.println(topOrder);
        }
    }
}