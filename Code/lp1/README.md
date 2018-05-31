Long Project 1
================

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Feedback
--------

Score: 100. EC: 6

Level 4: t3, t7 - exception (base not properly set to default base, hence divideByZero exception) , t6 - totally wrong output.

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    └── g16
        ├── README.md               # Current readme file.
        ├── ExecutableLine.java     # Supporting interface to execute a line.
        ├── Expression.java         # Supporting interface to evaluate a expression.
        ├── LP1L1.java              # Level 1 driver class (unchanged).
        ├── LP1L2.java              # Level 2 driver class (unchanged).
        ├── LP1L3.java              # Level 3 driver class.
        ├── LP1L4.java              # Level 4 driver class.
        ├── Num.java                # Num class for arbitrary length integer.
        ├── NumTest.java            # Unittest class for Num.
        ├── ShuntingYard.java       # Algorithm to transform infix to postfix.
        ├── ShuntingYardTest.java   # Unittest class for ShuntingYard.
        └── Tokenizer.java          # A modified Tokenizer.

Javadoc
-------

<!-- An online Javadoc (including private variables and methods) for `Num` class is 
hosted at [Num](https://hanlin-he.github.io/G16/lp1/doc/cs6301/g16/Num.html). -->

Compile
-------

To compile `Num` class with unit test case, run the following command:

    javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar cs6301/g16/Num.java cs6301/g16/NumTest.java

To run unit test of `Num`, run the following command:

    java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore cs6301.g16.NumTest

_Note that, JUnit4 is used in unit test class. Thus, file **`junit-4.12.jar`** and
**`hamcrest-core-1.3.jar`** should be availeble to the compiler._

To compile and execute the driver class, use the following commands:

    javac cs6301/g16/LP1L1.java
    javac cs6301/g16/LP1L2.java
    javac cs6301/g16/LP1L3.java
    javac cs6301/g16/LP1L4.java

The compiler would compile each file respectively. Supporting interface like `ExecutableLine` and `Expression`
need not compile individually.

There is also a unit test class for `ShuntingYardTest.java`, same procedure
could be used for this.


Test Running
------------

-   Unit test for `Num`

        > java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore cs6301.g16.NumTest
        JUnit version 4.12
        ...............
        Time: 11.194

        OK (15 tests)


-   Level 1

        > java cs6301.g16.LP1L1
        1007
        992027944069944027992001
        10: 7 0 0 1

-   Level 2

        > java cs6301.g16.LP1L2
        1007
        992027944069944027992001
        10: 7 0 0 1

-   Level 3

        > java cs6301.g16.LP1L3
        a = 90569784495866770974195656280275310090138980613960953881501965823101 ;
        b = 75040970647524038461398929683905540248523961720824412136973299943953 ;
        c = a b - ;
        c = b a - ;
        ;
        90569784495866770974195656280275310090138980613960953881501965823101
        75040970647524038461398929683905540248523961720824412136973299943953
        15528813848342732512796726596369769841615018893136541744528665879148
        -15528813848342732512796726596369769841615018893136541744528665879148
        10: 8 4 1 9 7 8 5 6 6 8 2 5 4 4 7 1 4 5 6 3 1 3 9 8 8 1 0 5 1 6 1 4 8 9 6 7 9 6 3 6 9 5 6 2 7 6 9 7 2 1 5 2 3 7 2 4 3 8 4 8 3 1 8 8 2 5 5 1

        > java cs6301.g16.LP1L3
        x = 98765432123456789012456789012646378589165127456376 ;
        y = 56698364876147630847612984618476284587653095761286 ;
        z = 9876543212345678901 ;
        e = 85849037612648764376549098612765874365348765673543 ;
        f = 566983648761476308476145 ;
        c = x y - ;
        d = c z / ;
        g = e f % ;
        ;
        98765432123456789012456789012646378589165127456376
        56698364876147630847612984618476284587653095761286
        9876543212345678901
        85849037612648764376549098612765874365348765673543
        566983648761476308476145
        42067067247309158164843804394170094001512031695090
        4259290557725229672777304701870
        342342737580166640522218
        10: 8 1 2 2 2 5 0 4 6 6 6 1 0 8 5 7 3 7 2 4 3 2 4 3

-   Level 4

        > java cs6301.g16.LP1L4 233333333
        1 x = 98765432109876543210987654321098765432109876543210 ;
        2 y = 10 ;
        3 p = 1 ;
        5 p = p * x ;
        6 y = y - 1 ;
        7 y ? 5 ;
        p ;
        ;
        88318092714754613943968670238501940971401725530661007242162426723757793533014514149425314041471082583234278752746228481752203148399363485960053777526890061272997987443715444513826122720929500421367647121266951360333641806557699965476113495250206261817690324484674105393706009503180908769581575574888166186965111622754454606641034934785710007470269162331454571091737743739945252018321001115692537102124819367630535360182432522038593177436594146822740310001228783999096650649267716669405112010000000000
        233333333: 170248324 2089908 173639575 196597056 2475639 57351107 39687739 207445035 165379450 188435049 45374994 84358328 82852623 63124883 123122706 85484260 147583799 12207579 89463240 103748539 145311748 65398293 116583263 113681654 46573960 219891758 143581495 79533654 60901552 48458880 204188046 91428098 116829218 23908262 124926279 190882326 47773139 26013783 146960316 109594578 72146028 89114988 139927681 123963157 113182045 226521261 187732972 140251299 3644245 104349487 98207585 92389994 212205020 218105700 207888553 2168398 40794509 39374873 28873146 1719568

        > java cs6301.g16.LP1L4 2333
        5 c = 123456789012345678901234567890 ;
        6 d = 123456789012345678901234567895 ;
        7 m = d - c ;
        8 p = 232 ;
        9 p = p + 1 ;
        10 i = 2 ;
        11 a = p - 3 ;
        12 j = 2 ;
        13 b = p - 3 ;
        14 k = i * j ;
        15 d = p - k ;
        16 e = k - p ;
        17 d ? 20 ;
        18 e ? 20 : 9 ;
        20 j = j + 1 ;
        21 b = b - 1 ;
        22 b ? 14 ;
        23 i = i + 1 ;
        24 a = a - 1 ;
        25 a ? 12 ;
        p ;
        27 m = m - 1 ;
        28 m ? 9 ;
        ;
        233
        239
        241
        251
        257
        0: 0
