# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst

    # Idea:

    Using Kruskal to find MST, I googled and found out what the algorithm was supposed to do.

    # Challenges faced: 

    When I began writing the algorithm, I didn't understand what union-find was. I attended a lecture where Martin explained it.

    # Description:

    I'm using the Kruskal's algorithm to find the MST. The most challenging part of this task was realizing that I needed the Union-Find data structure. Martin showed this in the lecture. After that, it went well.  Kruskal's algorithm sorts all the edges from the least weight to the greatest, then selecting the smallest edges one by one, ensuring that the addition of each edge does not form a cycle. The process continues until a spanning tree is formed or all edges are considered.

## Task 2 - lca

    # Idea: 

    My idea was to use DFS to store a path from the root to a given node in a helper method, I do this for the two given nodes. Then, I compare the paths to find the  Lowest Common Ancestor.

    # Description

    To find the Lowest Common Ancestor, I use a DFS helpermethod that takes in the root of the graph, the node we want to find, and the graph itself. I use DFS to find the given node and return the path as a list of nodes. I then apply this helper method to nodes U and V. In this way, I obtain two paths, which I then compare based on the shorter list. If at any point the nodes in the two paths differ, it returns the last node they had in common, which is the LCA.

## Task 3 - addRedundant   

    # Idea:

    My initial idea was to first identify the two largest subtrees of the root and then find the deepest parent node that scores the highest based on the number of neighbors. Therefore, I use an accumulated score and score the nodes based on their previous neighbors and a current node's neighbors. If the node is deep enough and has the best neighbor path score, I choose this node. I do this for the two largest subtrees and create an edge between these nodes.

    # Challenges faced: 

    An issue I encountered was that the bfsSubTree() method looped back to the root, resulting in getting the same tree every time. I realized this after much trial and error. After addressing this, things went fairly smoothly.

    # Description

    bfsSubTree(): This method creates new subtrees from the root's neighbors using BFS.

    biggestSubTreeList(): Identifies the two largest direct subtrees of a given root node in a graph, based on the number of vertices. It returns these subtrees in a linked list. If only one subtree exists, the list contains just that; if none, it returns an empty list.

    getDeepestParentWithMostNeighbours(): This method returns the best node to attach the powercable to, based on how deep the node is in a subtree (lowest parent), 
    and how many neighbors the ancestors have, which I keep track of in a hashMap. The idea behind this is to favor paths that traverse through nodes with many neighbors, as they contribute more to the accumulated score. This accumulated score acts as a heuristic to determine the "best" path through the graph based on the number of neighbors each node has. I use BFS for this.

    Actual Strategy:
    addRedundant(): The strategy can be divided into 2 cases.
    
    If the root has more than two neighbors, I find the two largest subtrees using bfsSubTree() and biggestSubTreeList(). Once this is done, I use getDeepestParentWithMostNeighbours() to find the best node. I do this for the first two elements that biggestSubTreeList() returns. This then gives me two nodes, which I create an edge between.

    If the root has only one neighbor, I will connect the cable from the root and use getDeepestParentWithMostNeighbours() to find the best node based on the subtree of the root's single neighbor.



# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

## Proving that BFS and DFS is O(n) for this graph
   In a graph without loops, which is a tree structure, the number of edges will always be less than the number of nodes. Therefore, when *
   performing a BFS or DFS traversal on such a tree, both will have a time complexity of O(n) because they will explore each node exactly once, and the *
   number of edges is n-1. As a result, the overall runtime is O(n). *

## mst
* ``mst(WeightedGraph<T, E> g)``: O(m * log m) 
    * The primary contributors to this complexity are adding all edges to a list, sorting, and union find*
    * Adding all the edges to the "edges" list = O(n)*
    * Sorting the edges based on weight = O(m * log m)*
    * Union-find uses O(V)*
    * Therefore, the total calculation is O(m * log m) + O(n) + O(m) = O(m * log m) *


## lca
* ``lca(Graph<T> g, T root, T u, T v)``: O(n)
    * I call dfsPath twice, once for u and once for v. Then, I use a loop to compare the two paths. In the worst case, this comparison will be O(n).* 
    * This is beacuse: O(n) for the first dfsPath + O(n) for the second dfsPath + O(n) for the comparison = O(3n), which simplifies to O(n).*

# helper for lca 
* ``dfsPath(V root, V target, Graph<V> graph)``: O(n)
    * The primary contributors to this complexity are DFS
    * I have allready proven (above) that DFS is O(n) for this graph 
    * Therefore, the total calculation is O(n)


## addRedundant
* ``addRedundant(Graph<T> g, T root)``: O(n)
    * The primary contributors to this complexity are creating subtrees and finding the 2 nodes.
    * biggestSubTreeList(): Retrieving subtrees is O(n).
    * getDeepestParentWithMostNeighbours(): Finding the 1st node is O(n).
    * getDeepestParentWithMostNeighbours(): Finding the 2nd node is O(n).
    * Therefore, the total calculation is O(n) + O(n) + O(n) = O(n)

    # helper for addRedundant
* ``bfsSubTree(V startNode, Graph<V> g, V root)``: O(n)
    * The primary contributors to this complexity are BFS
    * I have allready proven (above) that BFS is O(n) for this graph
    * Therefore, the total calculation is O(n)

* ``biggestSubTreeList(Graph<V> g, V root)``: O(n)
    * The primary contributors to this complexity are looping over roots neighbors, and Collections.max() function
    * Iterating over roots neighbors = O(n) 
    * Collections.max() = O(n) 
    * Collections.max() = O(n)  
    * O(n) + O(n) + O(n), which simplifies to O(n) in Big O notation. Thus, the worst-case runtime complexity of the biggestSubTreeList() function is O(n). 

* ``getDeepestParentWithMostNeighbours(Graph<V> graph)``: O(n)
    * The primary contributors to this complexity are BFS and final loop
    * I have allready proven (above) that BFS is O(n) for this graph 
    * The final loop iterates over the depthMap that contains nodes and therefore is O(n)
    * Therefore, the total calculation is O(n) + O(n) = O(n) 


