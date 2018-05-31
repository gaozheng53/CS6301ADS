Short Project 7
================

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
      ├── AVLTree.java            # AVL Tree
      ├── BST.java                # Binary Search Tree
      ├── BSTMap.java             # BSTMap using BST classes 
      ├── RedBlackTree.java       # Red Black Tree
      └── SplayTree.java          # Splay Tree

Compile
-------

To compile, run the following commands:

    # BST
    javac cs6301/g16/BST.java
    
    # AVL Tree
    javac cs6301/g16/AVLTree.java
    
    # BSTMap
    javac cs6301/g16/BSTMap.java

    # Red Black Tree
    javac cs6301/g16/RedBlackTree.java
    
    # Splay Tree
    javac cs6301/g16/SplayTree.java


Test Running
------------

-   BST

        > java cs6301.g16.BST
        1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0
        
        Output:
        Add 1 : [1] 1
        Add 3 : [2] 1 3
        Add 5 : [3] 1 3 5
        Add 7 : [4] 1 3 5 7
        Add 9 : [5] 1 3 5 7 9
        Add 2 : [6] 1 2 3 5 7 9
        Add 4 : [7] 1 2 3 4 5 7 9
        Add 6 : [8] 1 2 3 4 5 6 7 9
        Add 8 : [9] 1 2 3 4 5 6 7 8 9
        Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
        Remove -3 : [9] 1 2 4 5 6 7 8 9 10
        Remove -6 : [8] 1 2 4 5 7 8 9 10
        Remove -3 : [8] 1 2 4 5 7 8 9 10
        Final: 1 2 4 5 7 8 9 10 


-   AVL Tree

        > java cs6301.g16.AVLTree
        Test 1.Test add(),remove(),toArray()
        4
        Add 4 : [1] 4
        3
        Add 3 : [2] 3 4
        7
        Add 7 : [3] 3 4 7
        6
        Add 6 : [4] 3 4 6 7
        9
        Add 9 : [5] 3 4 6 7 9
        1
        Add 1 : [6] 1 3 4 6 7 9
        -6
        Remove -6 : [5] 1 3 4 7 9
        2
        Add 2 : [6] 1 2 3 4 7 9
        -1
        Remove -1 : [5] 2 3 4 7 9
        5
        Add 5 : [6] 2 3 4 5 7 9
        0
        Final: 2 3 4 5 7 9 
        Test 2.Test Iterator()
        2 3 4 5 7 9 
        Is a balance tree? false


-   BST Map

        > java cs6301.g16.BSTMap
        map.put(3,"C")
        map.put(1,"A")
        map.put(2,"B")
        Iterate Through Map:
        1-A
        2-B
        3-C
        map.put(3,"CC")
        3-CC
        Get a key doesn't exist:
        map.get(0) = null


-   Red Black Tree

        > java cs6301.g16.RedBlackTree
        Test 1.Test add(),remove(),toArray()
        1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0
        Add 1 : [1] 1
        Add 3 : [2] 1 3
        Add 5 : [3] 1 3 5
        Add 7 : [4] 1 3 5 7
        Add 9 : [5] 1 3 5 7 9
        Add 2 : [6] 1 2 3 5 7 9
        Add 4 : [7] 1 2 3 4 5 7 9
        Add 6 : [8] 1 2 3 4 5 6 7 9
        Add 8 : [9] 1 2 3 4 5 6 7 8 9
        Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
        Remove -3 : [9] 1 2 4 5 6 7 8 9 10
        Remove -6 : [8] 1 2 4 5 7 8 9 10
        Remove -3 : [8] 1 2 4 5 7 8 9 10
        Final: 1 2 4 5 7 8 9 10
        Test 2.Test Iterator()
        1 2 4 5 7 8 9 10
        t is valid RedBlackTree.


-   Splay Tree

        > java cs6301.g16.SplayTree
        t.add(1)
        [1] (1)
        t.add(2)
        [2] 1 (2)
        t.get(1)
        [2] (1) 2
        t.add(5)
        [3] 1 2 (5)
        t.get(2)
        [3] 1 (2) 5
        t.add(3)
        [4] 1 2 (3) 5
        t.add(-1)
        [5] -1 1 2 (3) 5
        t.min() = -1
        [5] (-1) 1 2 3 5
        t.contains(1) = true
        [5] -1 (1) 2 3 5