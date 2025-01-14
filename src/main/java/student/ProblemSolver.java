package student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import graph.*;

public class ProblemSolver implements IProblem {

@Override
public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) {//O(m * log m) + O(m) + O(n) = O(m * log m)
	ArrayList<Edge<V>> mst = new ArrayList<>();
	UnionFind<V> connected = new UnionFindBySize<>(g.vertices());
	ArrayList<Edge<V>> edges = new ArrayList<>();

	for (Edge<V> edge : g.edges()) {
		edges.add(edge);
	}

	Collections.sort(edges, g);

	for (Edge<V> edge : edges) {
		if (!connected.same(edge.a, edge.b)) {
			mst.add(edge);
			connected.union(edge.a, edge.b);
		}
	}

	return mst;
}

@Override
public <V> V lca(Graph<V> g, V root, V u, V v) {// O(n) + O(n) + O(n) = O(n)
	List<V> pathU = dfsPath(root, u, g);
	List<V> pathV = dfsPath(root, v, g);


	int size = pathV.size();
	if (size > pathU.size()) {
		size = pathU.size();
	}

	// når stiene ikke er like, retirerer vi noden før de ble ulike
	V lastCommon = root;
	for (int i = 0; i < size; i++) {
		if (!pathU.get(i).equals(pathV.get(i))) {
			return lastCommon;
		}
		lastCommon = pathU.get(i);
	}
	return lastCommon;
}

private <V> LinkedList<V> dfsPath(V root, V target, Graph<V> graph) { //O(n)
	Stack<V> stack = new Stack<>();
	Map<V, V> parentMap = new HashMap<>();// hashMap med <child, parent>
	LinkedList<V> path = new LinkedList<>();

	stack.push(root);// legger til root i stakken
	parentMap.put(root, null); // root er stamfar, har ingen foreldre

	while (!stack.isEmpty()) {// imens stakken ikke er tom
		V current = stack.pop();// currentNode er øverst i stakken.

		if (current.equals(target)) {// hvis vi har funnet target
			for (V node = target; node != null; node = parentMap.get(node)) {
				path.addFirst(node); // legger til alle foreldrene til vi kommer til root
			}
			return path;
		}

		// legge til currents barn i stakken, legge til barna til current i parentMap
		for (V child : graph.neighbours(current)) {
			if (!parentMap.containsKey(child)) {
				stack.push(child);
				parentMap.put(child, current);
			}
		}
	}

	return path; // returner tom liste hvis målnoden ikke ble funnet
}

@Override
public <V> Edge<V> addRedundant(Graph<V> g, V root) {//O(n)
	LinkedList<Graph<V>> biggestTreeList = biggestSubTreeList(g, root);

	Graph<V> firstBiggestTree = biggestTreeList.poll();
	Graph<V> secondBiggestTree = biggestTreeList.poll();

	V node1 = null;
	V node2 = null;
	
	if(firstBiggestTree != null) {
		node1 = getDeepestParentWithMostNeighbours(firstBiggestTree);
	}
	
	if(secondBiggestTree != null) {
		node2 = getDeepestParentWithMostNeighbours(secondBiggestTree);
	}
	
	Edge<V> edge = new Edge<V>(root, node1);
	
	if (node1 != null && node2 != null) {
		edge = new Edge<V>(node1, node2);
		}
	return edge;
}


private <V> V getDeepestParentWithMostNeighbours(Graph<V> graph) {//O(n)
	Map<V, Integer> depthMap = new HashMap<>();//dybde map
	Map<V, Integer> scoreMap = new HashMap<>();//socring map
	Queue<V> queue = new LinkedList<>();
	
	V root = graph.getFirstNode();
	queue.add(root);
	depthMap.put(root, 0);//dybden til root er null
	scoreMap.put(root, graph.degree(root));//scoren til root er dens naboer

	int maxDepth = 0;// highScore variabel
	
	while (!queue.isEmpty()) {//imens køen ikke er tom
		V currentNode = queue.poll();// henter og fjerner frøste node fra kjøen
		for (V neighbour : graph.neighbours(currentNode)) {// for naboene til current
			if (!depthMap.containsKey(neighbour)) {// hvis naboen ikke finnes i depthMap
				int depth = depthMap.get(currentNode) + 1;// dybden plusses med 1
				depthMap.put(neighbour, depth);// legger til naboen og dybden i hashmap
				scoreMap.put(neighbour, scoreMap.get(currentNode) + graph.degree(neighbour)); //naboens score er summen av CurrentNode og gradtallet til naboen til Current
				//Dette gjør at jeg kan vektlegge den beste pahten
				queue.add(neighbour);//legger til naboen i køen
				if (depth > maxDepth) { //om dybden er større en max
					maxDepth = depth;// ny highScore
				}
			}
		}
	}
	V champNode = null;//seiers node 
	int maxScore = 0;//highScore

	for (Map.Entry<V, Integer> entry : depthMap.entrySet()) {//O(n)
		if (entry.getValue() == maxDepth - 1) {//bare noder som har max dybde -1 
			int currentScore = scoreMap.get(entry.getKey());// henter scoren fra scoreMapet
			if (currentScore > maxScore) {//hvis currentGradtall er større en maxGradtall
				maxScore = currentScore;//// settes currentGradtall til maxGradtall
				champNode = entry.getKey();// champNode blir kandidat for noden som skal returneres
			}
		}
	}

	return champNode;
}

private <V> LinkedList<Graph<V>> biggestSubTreeList(Graph<V> g, V root) {//O(n) + O(n) + O(n) = O(n)
    List<Graph<V>> treeList = new ArrayList<>();//tom liste

    for (V node : g.neighbours(root)) {//roots naboer skal bli trær
        treeList.add(bfsSubTree(node, g, root));//legger til subtrær i den tomme listen
    }

    Graph<V> largestTree = null;
    Graph<V> secondLargestTree = null;

    if (!treeList.isEmpty()) {
        largestTree = Collections.max(treeList, Comparator.comparingInt(Graph::numVertices));// henter ut største tree fra listen
        treeList.remove(largestTree);//fjerner så dette treet
    }

    if (!treeList.isEmpty()) {
        secondLargestTree = Collections.max(treeList, Comparator.comparingInt(Graph::numVertices));// henter det nye søtste
    }
	//legger de til i ny liste
    LinkedList<Graph<V>> biggestTreeList = new LinkedList<>();
    if (largestTree != null) biggestTreeList.add(largestTree);
    if (secondLargestTree != null) biggestTreeList.add(secondLargestTree);
    return biggestTreeList;
}



private <V> Graph<V> bfsSubTree(V startNode, Graph<V> g, V root) {//O(n)
	Graph<V> subTree = new Graph<>();// nytt tomt tree
	subTree.addVertex(startNode);// legger til ny root

	Set<V> visited = new HashSet<>();// tomt hashset
	Queue<V> queue = new LinkedList<>();// tom queue

	visited.add(startNode);// legger til startNode i visited
	queue.add(startNode);// legger til startNode i køen

	while (!queue.isEmpty()) {// i mens køen ikke er tom
		V current = queue.poll();// current er køens førstemann

		for (V neighbor : g.neighbours(current)) {// for barna til current
			if (!current.equals(neighbor) && !visited.contains(neighbor) && !current.equals(root)) {	
				visited.add(neighbor);// legg til nabo i besøkt
				queue.add(neighbor);// legg til nabo i kø
				subTree.addVertex(neighbor);// legger til noden i treet
				subTree.addEdge(current, neighbor);// legger til kanten i treet
			}
		}
	}
	return subTree;
}

}
