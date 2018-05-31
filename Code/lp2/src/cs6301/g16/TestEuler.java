/**
 * <h1>Fall 2017 Long Project 2</h1>
 * <p>
 * Helper class to test the correctness of the implemented EulerPath Algorithm
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 2.0
 * @since 2017-09-21
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestEuler {
    static public void main(String[] args) throws FileNotFoundException {

        // read test folder
        String testFolderPath = null;
        if(args.length>0) {
            testFolderPath = args[0];
        }
        else {
            System.out.println("Please Enter the test cases path:");
            Scanner sc = new Scanner(System.in);
            testFolderPath = sc.next();
        }

        File folder = new File(testFolderPath);
        if(!folder.isDirectory()){
            System.out.println("Invalid folder path!");
            System.exit(2);
        }

        // read test cases
        List<File> files = Arrays.asList(folder.listFiles());
        for(File inputFile:files)
        {
            System.out.println("===================\nTesting "+inputFile.getName());

            // get edge count
            Scanner in;
            in = new Scanner(inputFile);
            in.nextInt(); // skip vertex count
            int edgeCount = in.nextInt();

            // read test graph
            in = new Scanner(inputFile);
            int start = 1;
            if (args.length > 1) {
                start = Integer.parseInt(args[1]);
            }

            Graph g = Graph.readDirectedGraph(in);
            Graph.Vertex startVertex = g.getVertex(start);

            // get euler tour
            Euler euler = new Euler(g, startVertex);
            euler.setVerbose(0);

            boolean eulerian = euler.isEulerian();
            if (!eulerian) {
                continue;
            }
            List<Graph.Edge> tour = euler.findEulerTour();

            // test correctness
            if(tour.size()!=edgeCount) {
                System.out.println("Edge Count not consistent");
            }
            else {

                HashSet<Graph.Edge> testSet = new HashSet<>();
                for (Graph.Edge e : tour) {
                    if (testSet.contains(e)) {
                        System.out.printf("Error - edge %s used twice\n.", e.toString());
                        return;
                    }
                }
                System.out.println("Test Success");
            }

        }
    }
}
