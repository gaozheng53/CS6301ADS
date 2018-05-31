Short Project 6
================

The problems set [click here](./sp6-pq-2017f.md)

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
        ├── BinaryHeap.java             # BinaryHeap Implementation.
        ├── HuffmanCoding.java          # Solution for Problem 2.
        ├── KMergeSort.java             # Solution for Problem 1.
        ├── KMerger.java                # Merger Helper class for K-way Merge.
        ├── P5Driver.java               # Driver class for Problem 5.
        ├── PerfectPower.java           # Solution for Problem 3-1 PerfectPower
        ├── PrimeFactor.java            # Solution for Problem 3-2 PrimeFactor
        └── README.md

Compile
-------

To compile, run the following commands:

    # Problem 1
    javac cs6301/g16/KMergeSort.java

    # Problem 2
    javac cs6301/g16/HuffmanCoding.java

    # Problem 3
    javac cs6301/g16/PerfectPower.java
    javac cs6301/g16/PrimeFactor.java
    
    # Problem 5
    javac cs6301/g16/P5Driver.java
  


Test Running
------------
-   Problem 1

    Execute the command `java cs6301.g16.KMergeSort`.

    Follow the prumpt, specify the length of array to be generated and k value.

    Example:

        > java cs6301.g16.KMergeSort
        Random generate array, enter size (-1 for automated 5 times random testing): 1000000
        Enter k value for K-way merge sort: 13

        Generating random array and list with length 1000000...........
        K-way Sort with k = 13.
        List<Integer>:
        Time: 823 msec.
        Memory: 44 MB / 256 MB.
        Integer[]:
        Time: 675 msec.
        Memory: 65 MB / 256 MB.

        > java cs6301.g16.KMergeSort
        Random generate array, enter size (-1 for automated 5 times random testing): -1

        Generating random array and list with length 9583233............
        K-way Sort with k = 16.
        List<Integer>:
        Time: 8446 msec.
        Memory: 370 MB / 681 MB.
        Integer[]:
        Time: 8986 msec.
        Memory: 426 MB / 681 MB.

        Generating random array and list with length 7141678............
        K-way Sort with k = 4.
        List<Integer>:
        Time: 7565 msec.
        Memory: 785 MB / 1343 MB.
        Integer[]:
        Time: 6144 msec.
        Memory: 893 MB / 1343 MB.

        Generating random array and list with length 3081433............
        K-way Sort with k = 10.
        List<Integer>:
        Time: 2726 msec.
        Memory: 210 MB / 1343 MB.
        Integer[]:
        Time: 1909 msec.
        Memory: 253 MB / 1343 MB.

        Generating random array and list with length 5440423............
        K-way Sort with k = 8.
        List<Integer>:
        Time: 4811 msec.
        Memory: 641 MB / 1343 MB.
        Integer[]:
        Time: 3786 msec.
        Memory: 724 MB / 1343 MB.

        Generating random array and list with length 1619193............
        K-way Sort with k = 19.
        List<Integer>:
        Time: 1178 msec.
        Memory: 671 MB / 1343 MB.
        Integer[]:
        Time: 1074 msec.
        Memory: 528 MB / 1343 MB.

-   Problem 2

    Execute the command `java cs6301.g16.HuffmanCoding`.
    
    The program will generate huffman tree and perform huffman coding algorithm 
    on the key-frequency pairs given in main function.

    Example:

        > java cs6301.g16.HuffmanCoding
        Coding result:
        key:a freq:0.20 code="00"
        key:b freq:0.10 code="010"
        key:c freq:0.15 code="011"
        key:d freq:0.30 code="11"
        key:e freq:0.25 code="10"
        Weighted Average number of bits per character:2.25

-   Problem 3

    Execute the command `java cs6301.g16.PerfectPower`.
        
        The program will generate huffman tree and perform huffman coding algorithm 
        on the key-frequency pairs given in main function.
    
        Example:
    
            > java cs6301.g16.PerfectPower
            4 8 9 16 25 27 32 36 49 64 81 100 121 125 128 144 169 196 216 225 243 256 289 324 343 361 400 441 484 512 529 576 625 676 729 784 841 900 961 1000 1024 1089 1156 1225 1296 1331 1369 1444 1521 1600 1681 1728 1764 1849 1936 2025 2048 2116 2187 2197 2209 2304 2401 2500 2601 2704 2744 2809 2916 3025 3125 3136 3249 3364 3375 3481 3600 3721 3844 3969 4096 4225 4356 4489 4624 4761 4900 4913 5041 5184 5329 5476 5625 5776 5832 5929 6084 6241 6400 6561 
    
    Execute the command `java cs6301.g16.PrimeFactor`.
    
        The program will generate numbers with only prime factors given in main function.
        
        Example:
        
            > java cs6301.g16.PrimeFactor
            Input Prime List:[3, 7]
            3 7 9 21 27 49 63 81 147 189 243 343 441 567 729 1029 1323 1701 2187 2401 3087 3969 5103 6561 7203 9261 11907 15309 16807 19683 21609 27783 35721 45927 50421 59049 64827 83349 107163 117649 137781 151263 177147 194481 250047 321489 352947 413343 453789 531441 583443 750141 823543 964467 1058841 1240029 1361367 1594323 1750329 2250423 2470629 2893401 3176523 3720087 4084101 4782969 5250987 5764801 6751269 7411887 8680203 9529569 11160261 12252303 14348907 15752961 17294403 20253807 22235661 26040609 28588707 33480783 36756909 40353607 43046721 47258883 51883209 60761421 66706983 78121827 85766121 100442349 110270727 121060821 129140163 141776649 155649627 182284263 200120949 234365481 

-   Problem 5

    Execute the command `java cs6301.g16.P5Driver`.

    Follow the prump, specify the length of binary heap to be generated.

    Example:

        > java cs6301.g16.P5Driver
        Enter length of array to be generated: 1000
        Generating random array within 1000.
        The array is The array is [450,984,997,276,6,915,23,433,591,409,...]

        Sorting the array in ascending order.
        Time: 3 msec.
        Memory: 2 MB / 256 MB.

        The array is The array is [0,1,3,4,4,5,6,9,10,11,...]

        Sorting the array in descending order again.
        Time: 1 msec.
        Memory: 3 MB / 256 MB.

        The array is The array is [999,998,997,996,994,993,990,989,987,985,...]

        > java cs6301.g16.P5Driver
        Enter length of array to be generated: 1000000
        Generating random array within 1000000.
        The array is The array is [358913,703852,330664,560970,526013,326989,601757,365320,399564,164915,...]

        Sorting the array in ascending order.
        Time: 866 msec.
        Memory: 22 MB / 256 MB.

        The array is The array is [0,0,0,0,2,2,3,3,3,5,...]

        Sorting the array in descending order again.
        Time: 596 msec.
        Memory: 23 MB / 256 MB.

        The array is The array is [999998,999996,999996,999995,999994,999993,999992,999992,999991,999989,...]




