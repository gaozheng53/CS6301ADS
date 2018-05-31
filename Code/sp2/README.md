Short Project 2
================

The problems set [click here](./sp2-lists-2017f.md).

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Feedback
--------

Using flag to decide which iterator to forward is unnatural. Code can be simpler. difference() returns wrong answer if l2 is empty. It should return l1, but returns empty list.

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    ├── README.md
    └── g16
        ├── ArrayQueue.java                 # Solution for Problem 5
        ├── ReversibleSinglyLinkedList.java # Solution for Problem 4
        ├── SinglyLinkedList.java           # Sample code with some modification.
        ├── SortableList.java               # Solution for Problem 2
        ├── SortedSetList.java              # Solution for Problem 1
        └── SparsePolynomial.java           # Solution for Problem 9

Compile
-------

To compile, run the following commands:

```bash
# Problem 1
javac cs6301/g16/SortedSetList.java

# Problem 2
javac cs6301/g16/SortableList.java

# Problem 4
javac cs6301/g16/ReversibleSinglyLinkedList.java

# Problem 5
javac cs6301/g16/ArrayQueue.java

# Problem 9
javac cs6301/g16/SparsePolynomial.java
```

There is no dependency with file from package `cs6301.g00`.

Test Running
------------

- Problem 1

    Execute the command `java cs6301.g16.SortedSetList [size:default 40]`.

    The program would generate two Sorted Set List from range from 0 to `size`
    with steps 2 and 3, and compute print the intersect/union/difference in
    both direction. Apparently, result of _intersect_ and _union_ should be
    reflexive, and _difference_ would have different result.

    Example:

    ```
    > java cs6301.g16.SortedSetList 20
    List 1: [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
    List 2: [0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39]

    Intersection(List1, List2): [0, 6, 12, 18]
    Intersection(List2, List1): [0, 6, 12, 18]

    Union(List1, List2):: [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 24, 27, 30, 33, 36, 39]
    Union(List2, List1):: [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 24, 27, 30, 33, 36, 39]

    Difference(List1, List2): [2, 4, 8, 10, 14, 16]
    Difference(List2, List1): [3, 9, 15, 24, 27, 30, 33, 36, 39]
    ```
    
- Problem 2

    Execute the command `java cs6301.g16.SortableList`.

    The program would generate two Sorted Set List from range from 0 to `size`
    with steps 2 and 3, and compute print the intersect/union/difference in
    both direction. Apparently, result of _intersect_ and _union_ should be
    reflexive, and _difference_ would have different result.

    Example:

    ```
    > java cs6301.g16.SortedSetList
    Test case 1 (0 element):
    0: 
    0: 
    ==================
    Test case 2 (1 element):
    1: 9 
    1: 9 
    ==================
    Test case 3 (2 elements):
    2: 9 1 
    2: 1 9 
    ==================
    Test case 4 (3 elements):
    3: 9 1 3 
    3: 1 3 9 
    ==================
    Test case 5:
    9: 100 1 2 5 3 4 6 6 10 
    9: 1 2 3 4 5 6 6 10 100 
    ==================
    Test case 6 (Same values):
    4: 100 100 100 100 
    4: 100 100 100 100 
    ==================
    Test case 7 (Random test):
    32: 47 33 88 96 4 36 47 86 10 73 2 40 53 0 32 2 51 32 79 2 0 36 23 62 9 59 81 85 64 61 42 76 
    32: 0 0 2 2 2 4 9 10 23 32 32 33 36 36 40 42 47 47 51 53 59 61 62 64 73 76 79 81 85 86 88 96 
    ```

- Problem 4

    Execute the command `java cs6301.g16.ReversibleSinglyLinkedList
    [size:default 40]`.

    The program would generate a list of `range(size)`, first reverse the list
    iteratively, and then reverse the list back to original state in recursion.
    At last reversely print the the list (which has been in original state once
    again) in both recursive way and iterative way.

    Example:

    ```
    java cs6301.g16.ReversibleSinglyLinkedList 20
    20: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20

    After iterative reversion.
    20: 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1

    After recursive reversion again.
    20: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20

    (Reverse order)20: 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
    (Reverse order)20: 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
    ```

- Problem 5

    Execute the command `java cs6301.g16.ArrayQueue [size:default 16]`.

    The program initial the queue size to be 17, first add and poll a single
    element to test offer/poll/peek/isEmpty operations. Then repeat add or 
    poll elements to show the storage of array. Finally, the result shows array
    elements after double size or halve size through offer and poll operations.

    Example:

    ```
    Default initial size: 16
    Initial size for test: 17
    -------------- Test offer/poll/peek/isEmpty --------------
    Offer element:true
    Get peek element:-1
    Poll element:-1
    Poll null queue:null
    isEmpty():true
    -------------- Initial Queue --------------
    18 null null 4 5 6 7 8 9 10 11 12 13 14 15 16 17 
    Original size: 17
    -------------- Test Double Size --------------
    4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 null null null null null null null null null null null null null null null null null null 
    New size: 34
    -------------- Test Halve Size ---------------
    12 13 14 15 16 17 18 19 null null null null null null null null null 
    New size: 17
    ```

- Problem 9

    Execute the command `java cs6301.g16.SparsePolynomial`.

    Implement arithmetic with sparse polynomials, implementing the
    following operations: addition, multiplication, evaluation.
    Terms of the polynomial should be stored in a linked list, ordered by
    the exponent field.  Implement multiplication without using HashMaps.

    Example:

    ``` 
    Polynomial 1:
    4.0x^(7.0)-3.0x^(8.0)
    Polynomial 2:
    100.0x^(-1.0)-10.0x^(0.5)+1.0x^(1.0)
    =======================
    Evaluation:
    4.0x^(7.0)-3.0x^(8.0)
    x=4 => y=-262144.0
    =======================
    Addition Poly1+Poly2:
    100.0x^(-1.0)-10.0x^(0.5)+1.0x^(1.0)+4.0x^(7.0)-3.0x^(8.0)
    =======================
    Multiplication Poly1*Poly2:
    400.0x^(6.0)-300.0x^(7.0)-40.0x^(7.5)+4.0x^(8.0)+30.0x^(8.5)-3.0x^(9.0)
    
    ```
