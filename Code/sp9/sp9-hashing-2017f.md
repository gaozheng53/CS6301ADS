
## CS 6301.502. Implementation of advanced data structures and algorithms

Fall 2017
Short Project 9: Hashing
Wed, Nov 8, 2017

Version 1.0: Initial description (Wed, Nov 8).

Due: 11:59 PM, Sun, Nov 19.

Solve as many problems as you wish.  Maximum score: 50.
Use HashMap or HashSet to solve problems 1-5.  Times given are not worst-case, but expected times.

1.  [20 points]

    Given an array A of integers, and an integer X, find how many pairs of
    elements of A sum to X:
   
        static int howMany(int[] A, int X) { // RT = O(n)
        // How many indexes i,j (with i != j) are there with A[i] + A[j] = X?
        // A is not sorted, and may contain duplicate elements
        // If A = {3,3,4,5,3,5} then howMany(A,8) returns 6
        }

2.  [20 points]

    Given an array A, return an array B that has those elements of A that
    occur exactly once, in the same order in which they appear in A:

        static<T extends Comparable<? super T>> T[] exactlyOnce(T[] A) { // RT = O(n)
        // Ex: A = {6,3,4,5,3,5}.  exactlyOnce(A) returns {6,4}
        }

3.  [20 points]

    Given an array A of integers, find the length of a longest streak of
    consecutive integers that occur in A (not necessarily contiguously):
   
        static int longestStreak(int[] A) { // RT = O(n)
        // Ex: A = {1,7,9,4,1,7,4,8,7,1}.  longestStreak(A) return 3,
        //    corresponding to the streak {7,8,9} of consecutive integers
        //    that occur somewhere in A.
        }

4.  [30 points]

    Given a directed graph that may have parallel edges (more than
    one edge (u,v)), write a method that removes all but one copy of
    each parallel edge, keeping an edge with minimum cost among the copies
    of that edge.
   
        static void compactGraph(Graph g) {  // RT = O(n)

5.  [30 points]

    Given a directed graph and a list of edges, test whether it is an Euler tour:
   
        boolean verifyEulerTour(Graph g, Vertex startVertex, List<Edge> tour) { // RT = O(n)
   
    Try to write this solution either without using Edge as key of HashMap,
    or by writing your own hashCode for Edge to replace the one in Edge class.

6.  [30 points for each hashing technique]
   
    Implement Robin Hood / Hopscotch / Cuckoo / 2-choice Hashing and compare
   its performance with Java's HashMap/HashSet on millions of operations:
   add, contains, and remove.
   
   Generate an array of random integers, and calculate how many distinct
   numbers it has:  static<T> int distinctElements(T[ ] arr) { ... }
   Compare running times of HashSet and your hashing implementation, for large n.

7. [20 points]
   
   If you implement more than one hashing algorithm, compare their performances.
