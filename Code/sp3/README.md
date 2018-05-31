Short Project 3
================

The problems set [click here](./sp3-dfs-2017f.md).

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Feedback
--------

Complex code. Try to simplify design. Unnecessary use of inheritance.

-   Q5: Root of tree is cut vertex if it has 2 or more children in DFS, and not if it has 2 or more neighbors in G.

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    ├── README.md
    └── g16
        ├── BridgeCut.java                  # Solution for Problem 5
        ├── DFS.java                        # Perform dfs on the graph
        ├── Graph.java                      # Sample code with some modification.
        ├── GraphAlgorithm.java             # Sample code with some modification.
        ├── SCC.java                        # Solution for Problem 2
        ├── TestEulerian.java               # Solution for Problem 3
        ├── TopologicalOrder.java           # Solution for Problem 1
        └── TestDAG.java                    # Solution for Problem 4

Compile
-------

To compile, run the following commands:

```bash
# Problem 1
javac cs6301/g16/TopologicalOrder.java

# Problem 2
javac cs6301/g16/SCC.java

# Problem 3
javac cs6301/g16/TestEulerian.java

# Problem 4
javac cs6301/g16/TestDAG.java

# Problem 5
javac cs6301/g16/BridgeCut.java
```

There is no dependency with file from package `cs6301.g00`.

Test Running
------------

- Problem 1

    Execute the command `java cs6301.g16.TopologicalOrder`.

    The program contains two algorithms. First algorithm remove vertices
    with no incoming edges and its incident eadges from a DAG, then generate
    and return a list stores topological order. Second algorithm Run DFS on g 
    and generate a list contains vertices with decline finish time.

    Example:

    ```
    > java cs6301.g16.TopologicalOrder 
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
    Result 1:
    [1, 2, 4, 3, 7, 5, 6, 8, 9]

    Result 2:
    [1, 4, 7, 2, 3, 6, 9, 5, 8]

    Test Graph 2:
        6 9
        1 3 1
        2 4 1
        3 1 1
        4 6 1
        1 2 1
        2 3 1
        3 4 1
        4 5 1
        5 6 1
    Result 1:
    null

    Result 2:
    null
    ```
    
- Problem 2

    Execute the command `java cs6301.g16.SCC`.

    The program will generate a directed graph and compute its strongly
    connected components. Each node is marked with a component number, 
    and the function returns the number of strongly connected components 

    Example:

    ```
    > java cs6301.g16.SCC
    Test Graph 1: 
        11 16
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
    ==============
    Input Graph:
    1: (1,11) 
    2: (2,3) (2,7) 
    3: (3,10) 
    4: (4,1) (4,9) 
    5: (5,4) (5,7) (5,8) 
    6: (6,3) 
    7: (7,8) 
    8: (8,2) 
    9: (9,11) 
    10: (10,6) 
    11: (11,3) (11,4) 
    ==============
    Number of Strongly Connected Components:4

    ```

- Problem 3

    Execute the command `java cs6301.g16.TestEulerian`.

    The program will determine whether a graph is Eulerian or not. Traverse
    the graph, if each vertex has the same indegree and outdegree, and the 
    graph is strongly connected, the graph is a Eulerian.

    Example:

    ```
     > java cs6301.g16.TestEulerian 
    Test Graph 1:
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
      ==============
      Input Graph:
      1: (1,11) 
      2: (2,3) (2,7) 
      3: (3,10) 
      4: (4,1) (4,9) 
      5: (5,4) (5,7) (5,8) 
      6: (6,3) 
      7: (7,8) 
      8: (8,2) 
      9: (9,11) 
      10: (10,6) 
      11: (11,3) (11,4) (11,6) 
      ==============
      Whether the graph is Eulerian: false  

      Test Graph 2: (is Eulerian)
        3 3
        1 2 1
        2 3 1
        3 1 1
      ==============
      Input Graph:
      1: (1,2) 
      2: (2,3) 
      3: (3,1) 
      ==============
      Whether the graph is Eulerian: true
    ```

- Problem 4

    Execute the command `java cs6301.g16.TestDAG`.

   The program will determine whether a graph is DAG or not. If the graph
   has directed and detect there hasn't back edge during dfs, the graph is
   DAG.

    Example:

    ```
     > java cs6301.g16.TestDAG 
     Test Graph 1: 
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
    ==============
    Input Graph:
    1: (1,11) 
    2: (2,3) (2,7) 
    3: (3,10) 
    4: (4,1) (4,9) 
    5: (5,4) (5,7) (5,8) 
    6: (6,3) 
    7: (7,8) 
    8: (8,2) 
    9: (9,11) 
    10: (10,6) 
    11: (11,3) (11,4) (11,6) 
    ==============
    Whether the graph is DAG: false

    Test Graph 2:
        3 3
        1 2 1
        2 3 1
        3 1 1
    ==============
    Input Graph:
    1: (1,2) 
    2: (2,3) 
    3: (3,1) 
    ==============
    Whether the graph is DAG: false

    Test Graph 3: (not DAG)
        5 7
        1 2 1
        2 3 1
        3 4 1
        4 5 1
        1 5 1
        2 5 1
        2 4 1
    ==============
    Input Graph:
    1: (1,2) (1,5) 
    2: (2,3) (2,5) (2,4) 
    3: (3,4) 
    4: (4,5) 
    5: 
    ==============
    Whether the graph is DAG: true

    ```
    
- Problem 5

    Execute the command `java cs6301.g16.BridgeCut`.

    The program will find out the bridge edges and cut vertexes in the input graph.

    Example:

    ```
     > java cs6301.g16.BridgeCut 
     
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
    1: (1,2) (1,3) (1,5) 
    2: (1,2) (2,3) (2,4) 
    3: (1,3) (2,3) (3,4) 
    4: (2,4) (3,4) 
    5: (1,5) (5,6) (5,10) 
    6: (5,6) (6,7) (6,8) (6,9) (6,10) 
    7: (6,7) (7,8) 
    8: (6,8) (7,8) (8,9) 
    9: (6,9) (8,9) 
    10: (5,10) (6,10) 
    
    =============
    DFS Start
    =============
    
    1 2 3 4 5 6 7 8 9 10 
    =============
    DFS End
    =============
    1 dis:1 low:1 cut:false parent:null
    2 dis:2 low:1 cut:false parent:1
    3 dis:3 low:1 cut:false parent:2
    4 dis:4 low:2 cut:false parent:3
    5 dis:5 low:5 cut:false parent:1
    6 dis:6 low:5 cut:false parent:5
    7 dis:7 low:6 cut:false parent:6
    8 dis:8 low:6 cut:false parent:7
    9 dis:9 low:6 cut:false parent:8
    10 dis:10 low:5 cut:false parent:6
    Cut Vertexes: [5, 6, 1]
    Bridges: [(1,5)]

    ```
