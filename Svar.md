# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
    I'm using the Kruskal's algorithm to find the MST. The most challenging part of this task was realizing that I needed the Union-Find data structure. Martin showed this in the lecture. After that, it went well.

## Task 2 - lca
   To find the Lowest Common Ancestor, I use a DFS helpermethod that takes in the root of the graph, the node we want to find, and the graph itself. I use DFS to find the given node and return the path as a list of nodes. I then apply this helper method to nodes U and V. In this way, I obtain two paths, which I then compare based on the shorter list. If at any point the nodes in the two paths differ, it returns the last node they had in common, which is the LCA.

## Task 3 - addRedundant

    bfsSubTree(): This method creates new subtrees from the root's neighbors using BFS.

    biggestSubTreeList(): This method sorts the subtrees of the root's neighbors and arranges them in a list based on the number of nodes. The list is sorted in descending order. I do this to find the two largest subtrees.

    getDeepestParentWithMostNeighbours(): This method returns the best node to attach the powercable to, based on how deep the node is in a subtree (lowest parent), 
    and how many neighbors the ancestors have, which I keep track of in a hashMap. The idea behind this is to favor paths that traverse through nodes with many neighbors, as they contribute more to the accumulated score. This accumulated score acts as a heuristic to determine the "best" path through the graph based on the number of neighbors each node has. I use BFS for this.

    Actual Strategy:
    addRedundant(): The strategy can be divided into 2 cases.
    
    If the root has more than two neighbors, I find the two largest subtrees using bfsSubTree() and biggestSubTreeList(). Once this is done, I use getDeepestParentWithMostNeighbours() to find the best node. I do this for the first two elements that biggestSubTreeList() returns. This then gives me two nodes, which I create an edge between.

    If the root has only one neighbor, I will connect the cable from the root and use getDeepestParentWithMostNeighbours() to find the best node based on the subtree of the root's single neighbor.



# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

* ``mst(WeightedGraph<T, E> g)``: O(E * log E) 
    * * The primary contributors to this complexity are adding all edges to a list, sorting, and union find*
    * * Adding all the edges to the "edges" list = O(E)*
    * * Sorting the edges based on weight = O(E * log E)*
    * * Union-find uses O(V)*
    * * Therefore, the total calculation is O(E * log E) + O(V) + O(E) = O(E * log E) *
    * **
    
* ``lca(Graph<T> g, T root, T u, T v)``: O(n)
    * * I call dfsPath twice, once for u and once for v. Then, I use a loop to compare the two paths. In the worst case, this comparison will also be O(n). 
    * * This is beacuse: O(n) for the first dfsPath + O(n) for the second dfsPath + O(n) for the comparison = O(3n), which simplifies to O(n).*

# helper for lca
* ``dfsPath(V root, V target, Graph<V> graph)``: O(n)
    * * With the loop iterating through a node's neighbors, each node is processed only once in DFS due to the parentMap if test. Despite the nested loop, each node and * * edge is handled once, leading to a runtime of O(n+m). For a connected graph, with m being at least 1 n−1, the worst-case runtime is O(n).*


* ``addRedundant(Graph<T> g, T root)``: O(d×m)?
    * *Insert description of why the method has the given runtime*

    # helper for addRedundant
* ``bfsSubTree(V startNode, Graph<V> g, V root)``: O(n)
    * * The primary contributors to this complexity are The outer loop and The inner loop*
    * * The outer loop: Runs for every node that can be reached from the start node, contributing to O(V).*
    * * The inner loop: terates over the neighbors of each vertex. On average, this will run E/V times, giving a contribution of O(E)*
    * * Therefore, the total calculation is O(V+E).This can be referred to as linear, or O(n), where n represents the size of the input graph.*


* ``biggestSubTreeList(Graph<V> g, V root)``: O(n log n)
    * * The primary contributors to this complexity are iterating over roots neigbours and sorting the list of trees*
    * * Making the subtrees has the runtime: O(n) *
    * * Sorting the subtrees has the runtime: O(n* log(n)) *
    * * Therefore, the total calculation is O(n* log(n)) + O(n) = O(n log n) *

* ``getDeepestParentWithMostNeighbours(Graph<V> graph)``: O(n)
    *  * The primary contributors to this complexity are BFS and final loop*
    *  * I have allready proven that BFS = O(n) in bfsSubTree() *
    *  * The final loop iterates over the depthMap that contains V nodes and therefore is O(v)*
    *  * Therefore, the total calculation is O(v) + O(n) = O(n) *


