Long Project 4
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
        ├── ArrayIterator.java          # Copy from g00.
        ├── BFS.java                    # BFS to determine reachability.
        ├── BellmanFord.java            # Shortest path implementation of c/d/e/f.
        ├── Graph.java                  # Graph class with minor modification.
        ├── GraphAlgorithm.java         # Graph Algorithm class.
        ├── LP4.java                    # Implementation of LP4 driver.
        ├── LP4a.java
        ├── LP4b.java
        ├── LP4c.java
        ├── LP4d.java
        ├── LP4e.java
        ├── LP4f.java
        ├── TopologicalPermutation.java # Implementation for Topological Permutation
        └── XGraph.java                 # XGraph class copy from g00.

Compile
-------

To compile, run the following commands:


    # LP4-a
    javac cs6301/g16/LP4a.java

    # LP4-b
    javac cs6301/g16/LP4b.java

    # LP4-c
    javac cs6301/g16/LP4c.java

    # LP4-d
    javac cs6301/g16/LP4d.java

    # LP4-e
    javac cs6301/g16/LP4e.java

    # LP4-f
    javac cs6301/g16/LP4f.java

Note that, `Graph` and `XGraph` are copied to `g16` package.

Test Running
------------

-   a

    Execute with command `java cs6301.g16.LP4a` followed by input graph.

        java cs6301.g16.LP4a
        7 8
        1 2 1
        1 3 1
        2 4 1
        3 4 1
        4 5 1
        4 6 1
        5 7 1
        6 7 1
        4
        Time: 8 msec.
        Memory: 2 MB / 256 MB.

-   b

    Execute with command `java cs6301.g16.LP4b` followed by input graph.

        java cs6301.g16.LP4b
        7 8
        1 2 1
        1 3 1
        2 4 1
        3 4 1
        4 5 1
        4 6 1
        5 7 1
        6 7 1
        1 2 3 4 5 6 7
        1 2 3 4 6 5 7
        1 3 2 4 5 6 7
        1 3 2 4 6 5 7
        4
        Time: 28 msec.
        Memory: 2 MB / 256 MB.

-   c

    Execute with command `java cs6301.g16.LP4c` followed by input graph.

        java cs6301.g16.LP4c
        11 13
        1 2 2
        1 3 3
        2 4 5
        3 4 4
        4 5 1
        5 1 -7
        6 7 -1
        7 6 -1
        2 8 0
        8 9 0
        9 10 0
        10 11 0
        11 2 1
        1 5
        2
        Time: 2 msec.
        Memory: 2 MB / 256 MB.

-   d

    Execute with command `java cs6301.g16.LP4d` followed by input graph.

        java cs6301.g16.LP4d
        11 13
        1 2 2
        1 3 3
        2 4 5
        3 4 4
        4 5 1
        5 1 -7
        6 7 -1
        7 6 -1
        2 8 0
        8 9 0
        9 10 0
        10 11 0
        11 2 1
        1 5
        1 2 4 5
        1 3 4 5
        2
        Time: 24 msec.
        Memory: 2 MB / 256 MB.

-   e

    Execute with command `java cs6301.g16.LP4e` followed by input graph.

        java cs6301.g16.LP4e
        10 11
        1 2 1
        1 7 3
        1 9 6
        2 3 1
        3 4 1
        4 5 1
        5 6 1
        6 10 1
        7 8 3
        8 10 3
        9 10 6
        1 10
        3
        9
        Time: 3 msec.
        Memory: 2 MB / 256 MB.

-   f

    Execute with command `java cs6301.g16.LP4f` followed by input graph.

        java cs6301.g16.LP4f
        7 7
        1 2 2
        1 4 4
        2 3 5
        4 5 4
        3 6 2
        5 6 4
        6 7 4
        1
        0 10 10 5 5 10 1000
        30
        1 2 3 6 5 4 1
        Time: 25 msec.
        Memory: 2 MB / 256 MB.
