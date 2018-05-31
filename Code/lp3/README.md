Long Project 3
================

Project Description

Optimal branchings (directed MST) in directed graphs

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
        ├── DFS.java                      # DFS implementation 
        ├── DMST.java                     # Implementation of Euler's alg to find DMST. 
        ├── DMSTGraph.java                # Subclass of Graph, to enable certaion graph operation used in DMST Algorthm
        ├── Graph.java                    # Graph class for easily extands
        ├── GraphAlgorithm.java           # GraphAlgorithm class to extands
        ├── LP3.java                      # Driver class
        └── SCC.java                      # Subclass of DFS to find strongly connected components

Compile
-------

To compile, run the following commands:

```bash
javac cs6301/g16/LP3.java
```

*ArrayIterator.java* and *Timer.java* is needed from package `cs6301.g00`.

Test Running
------------

- Find DMST

    Execute the command `java cs6301.g16.LP3 <Folder for Testcases>`.

    Running the test cases under a certain folder and check whether the Euler tour found is correct.
    Be sure to enlarge stack size to avoid stack overflow when running large test cases. (-Xss8g -Xms8g) 

    ```
    > java cs6301.g16.LP3 <Test Case File> <VERBOSE>
      47
      _________________________
      (6,1)(5,2)(2,3)(3,4)null(7,6)(3,7)(4,8)
      _________________________
      Time: 12 msec.
      Memory: 122 MB / 7851 MB.

    ```