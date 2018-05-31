Short Project 5
================

The problems set [click here](./sp5-quicksort-2017f.md)

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    ├── README.md
    └── g16
        ├── ArrayHelper.java                    # Helper Class with Array Related Functions
        ├── DualPivotQuickSort.java             # Solution for Problem 2
        ├── QuickSort.java                      # Solution for Problem 1
        ├── QuickSortVsMergeSort.java           # Solution for Problem 4
        └── SelectAlgorithm.java                # Solution for Problem 3


Compile
-------

To compile, run the following commands:

    # Problem 1
    javac cs6301/g16/QuickSort.java

    # Problem 2
    javac cs6301/g16/DualPivotQuickSort.java

    # Problem 3
    javac cs6301/g16/SelectAlgorithm.java

    # Problem 4
    javac cs6301/g16/QuickSortVsMergeSort.java

  


Test Running
------------
-   Problem 1

    Execute the command `java cs6301.g16.QuickSort`.

    The program implement two versions of partition of quick sort, and test their running time and memory
    with randomly and descending order.
    
    Example:

        > java cs6301.g16.QuickSort 
        Test 1 - Shuffled Input Array:
        [2333320 7442905 2258551 9919783 3654082 ... 2928539 1905658 1431271 293565 249048 1015919]
        ==========================
        
        Quick Sort with Partition Algorithm 1:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 2281 msec.
        Memory: 89 MB / 116 MB.
        
        ------------------------
        
        Quick Sort with Partition Algorithm 2:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 2896 msec.
        Memory: 104 MB / 113 MB.
        ==========================
        Test 2 - Reversed Input Array:
        [10000000 9999999 9999998 9999997 9999996 ... 6 5 4 3 2 1]
        ==========================
        
        Quick Sort with Partition Algorithm 1:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 1162 msec.
        Memory: 177 MB / 197 MB.
        
        ------------------------
        
        Quick Sort with Partition Algorithm 2:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 1428 msec.
        Memory: 184 MB / 249 MB.
        
-   Problem 2

    Execute the command `java cs6301.g16.DualPivotQuickSort`.

    The program implement dual pivot partition and its version of quick sort,and compare
    its performance with regular quick sort. Test two version of quick sort with 
    distinct or many duplicates array.
    
    Example:

        > java cs6301.g16.DualPivotQuickSort 
        ====================
        Test with array with duplicate elements:
        
        Input Array:
        [327095 1083692 677442 2721419 2540762 ... 1197033 4807903 3329180 2845406 1751615 402571]
        Dual Pivot Quick Sort:
        [1 2 3 3 3 ... 5001962 5001963 5001964 5001964 5001965 5001965]
        Sorting Success
        Time: 1351 msec.
        Memory: 116 MB / 161 MB.
        
        Normal Quick Sort:
        [1 2 3 3 3 ... 5001962 5001963 5001964 5001964 5001965 5001965]
        Sorting Success
        Time: 1864 msec.
        Memory: 128 MB / 153 MB.
        
        
        ====================
        Test with array with distinct elements:
        
        Input Array:
        [8351201 785850 4347372 6023624 9798537 ... 4876130 8844926 7363718 4526944 691754 9698228]
        Dual Pivot Quick Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 1440 msec.
        Memory: 256 MB / 267 MB.
        
        Normal Quick Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 1710 msec.
        Memory: 242 MB / 263 MB.
        
        

-   Problem 3

    Execute the command `java cs6301.g16.SelectAlgorithm`.

    The program implement 3 versions of the Select algorithm (finding k largest elements) by creating prority queue and recursion technique. Then we
    test with an arbitrary length integer array and compare their performance.
    
    Example:

        > java cs6301.g16.SelectAlgorithm 
        Test 1:
        array size=30000000
        k=22222
        Output:
        test (a):
        Kth element is: 29977778     Running time : 19730ms
        test (b):
        Kth element is: 29977778     Running time : 5184ms
        test (c):
        Kth element is: 29977778     Running time : 90ms
        ------------------------------
        Test 2:
        array size=7000000
        k=66
        Output:
        test (a):
        Kth element is: 6999934     Running time : 2460ms
        test (b):
        Kth element is: 6999934     Running time : 1023ms
        test (c):
        Kth element is: 6999934     Running time : 30ms

        

-   Problem 4

    Execute the command `java cs6301.g16.QuickSortVsMergeSort`.

    The program implement best version of Merge sort implemented before and quick sort that uses dual pivot partition. Compare
    their performance with distinct or many duplicates array.
    
    Example:

        > java cs6301.g16.QuickSortVsMergeSort 
        ====================
        Test with array with distinct elements:
        
        Input Array:
        [6832021 9193702 945726 9186324 6356509 ... 2961776 6113391 8387073 860687 2591765 9370019]
        Dual Pivot Quick Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 1456 msec.
        Memory: 143 MB / 161 MB.
        
        Merge Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 1490 msec.
        Memory: 181 MB / 200 MB.
        
        
        ====================
        Test with array with duplicate elements:
        
        Input Array:
        [3933021 586480 3150429 3605230 130723 ... 137510 3632490 4211770 1254549 1988895 3240681]
        Dual Pivot Quick Sort:
        [1 1 2 2 3 ... 4998655 4998656 4998657 4998658 4998658 4998658]
        Sorting Success
        Time: 1473 msec.
        Memory: 289 MB / 315 MB.
        
        Merge Sort:
        [1 1 2 2 3 ... 4998655 4998656 4998657 4998658 4998658 4998658]
        Sorting Success
        Time: 1602 msec.
        Memory: 327 MB / 354 MB.
