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

* ``mst(WeightedGraph<T, E> g)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``lca(Graph<T> g, T root, T u, T v)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``addRedundant(Graph<T> g, T root)``: O(?)
    * *Insert description of why the method has the given runtime*

