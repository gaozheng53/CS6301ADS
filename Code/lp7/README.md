Long Project 7
==============

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    └── g16
        ├── ArrayIterator.java      # Supporting class.
        ├── BFS.java                # Supporting class to generate min cut.
        ├── BellmanFord.java        # Supporting class to compute all shortest path.
        ├── Flow.java               # Flow class implemented.
        ├── Graph.java              # Graph class.
        ├── GraphAlgorithm.java     # Supporint class.
        ├── LP7.java                # Driver class.
        ├── ResidualGraph.java      # Residual graph implementation.
        └── Timer.java              # Supporting class.

Compile
-------

To compile, run the following commands:

    javac cs6301/g16/LP7.java

__Java 8 is required to compile.__

Test Running
------------

Driver code is modified to execute only the Dinitz's algorithm and
relabelToFront algorithm.

The following test commands were run on following Java version.

    java -version
    java 9.0.1
    Java(TM) SE Runtime Environment (build 9.0.1+11)
    Java HotSpot(TM) 64-Bit Server VM (build 9.0.1+11, mixed mode)

    java -version
    java version "1.8.0_131"
    Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
    Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)

To execute large input, an optional second input parameter was added to read graph from a file.

    java cs6301.g16.LP7 [VERBOSE] [Path-to-graph-file]

If `Path-to-graph-file` was not provided, driver program would read from standard input by default.

### Dinitz's Algorithm performance

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in1.txt
    35
    Max flow is verified
    Time: 69 msec.
    Memory: 8 MB / 245 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in2.txt
    994
    Max flow is verified
    Time: 183 msec.
    Memory: 32 MB / 245 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in3.txt
    9924
    Max flow is verified
    Time: 686 msec.
    Memory: 82 MB / 245 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in4.txt
    14
    Max flow is verified
    Time: 69 msec.
    Memory: 7 MB / 245 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in5.txt
    23
    Max flow is verified
    Time: 99 msec.
    Memory: 7 MB / 245 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in6.txt
    521
    Max flow is verified
    Time: 144 msec.
    Memory: 26 MB / 245 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in7.txt
    1344
    Max flow is verified
    Time: 338 msec.
    Memory: 109 MB / 309 MB.

### RelabelToFront algorithm performance

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in1.txt
    Max flow is verified
    35
    Time: 85 msec.
    Memory: 4 MB / 31 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in2.txt
    Max flow is verified
    994
    Time: 51440 msec.
    Memory: 46 MB / 97 MB.
    
    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in3.txt
    Max flow is verified
    9924
    Time: 42834754 msec.
    Memory: 56 MB / 80 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in4.txt
    Max flow is verified
    14
    Time: 134 msec.
    Memory: 2 MB / 31 MB.

    > java cs6301.g16.LP7 0 cs6301/g16/lp7-data/lp7-in5.txt
    Max flow is verified
    23
    Time: 136 msec.
    Memory: 2 MB / 31 MB.

Note that, the `RelabelToFront` algorithm for `lp7-in3.txt` took approximately
12 hours to converge. The running times for `lp7-in6.txt` and `lp7-in7.txt`
were not computed due to exponential time complexity. All these three results
can be viewed as infinity.
