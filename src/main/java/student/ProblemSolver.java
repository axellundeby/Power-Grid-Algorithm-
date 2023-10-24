package student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import graph.*;

public class ProblemSolver implements IProblem {

	@Override
	public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) {
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
	public <V> V lca(Graph<V> g, V root, V u, V v) {
		List<V> pathU = new ArrayList<>();
		List<V> pathV = new ArrayList<>();
		pathU = dfsPath(root, u, g);
		pathV = dfsPath(root, v, g);

		// finner den korteste listen
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

	private <V> LinkedList<V> dfsPath(V root, V target, Graph<V> graph) {
		Stack<V> stack = new Stack<>();
		Map<V, V> parentMap = new HashMap<>();// hashMap med <child, parent>
		LinkedList<V> path = new LinkedList<>();

		stack.push(root);// legger til root i stakken
		parentMap.put(root, null); // root er stamfar, har ingen foreldre

		while (!stack.isEmpty()) {// imens stakken ikke er tom
			V current = stack.pop();// currentNode er øverst i stakken.

			if (current.equals(target)) {// hvis vi har funnet target
				for (V node = target; node != null; node = parentMap.get(node)) {
					path.add(0, node); // legger til alle foreldrene til vi kommer til root
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
public <V> Edge<V> addRedundant(Graph<V> g, V root) {
    LinkedList<Graph<V>> Trees = biggestSubTreeList(g, root);

    Graph<V> firstBiggestTree = Trees.poll();
    Graph<V> secondBiggestTree = Trees.poll();

    V node1 = null;
    V node2 = null;
    
    if(firstBiggestTree != null) {
        node1 = getDeepestNodeWithMostNeighbours(firstBiggestTree);
    }
    
    if(secondBiggestTree != null) {
        node2 = getDeepestNodeWithMostNeighbours(secondBiggestTree);
    }
    
    Edge<V> edge = new Edge<V>(root, node1);
    
	if (node1 != null && node2 != null) {
		edge = new Edge<V>(node1, node2);
		}
	return edge;
}

	//fester til groveste foreldrer
	private static <V> V getDeepestNodeWithMostNeighbours(Graph<V> graph) {
		Map<V, Integer> depthMap = new HashMap<>();// map med dybden til en node
		Queue<V> queue = new LinkedList<>();
		V root = graph.getFirstNode();// rooten
		queue.add(root);// legger til root i køen
		depthMap.put(root, 0);// root har dybde 0

		int maxDepth = 0; // max depth settes til 0

		// bryr oss bare om dybden
		while (!queue.isEmpty()) {// imens køen ikke er tom
			V currentNode = queue.poll();// henter og fjerner frøste node fra kjøen
			for (V neighbour : graph.neighbours(currentNode)) {// for naboene til current
				if (!depthMap.containsKey(neighbour)) {// hvis naboen ikke finnes i depthMap
					int depth = depthMap.get(currentNode) + 1;// dybden plusses med 1
					depthMap.put(neighbour, depth);// legger til naboen og dybden i hashmap
					queue.add(neighbour);// legger til naboen i køen
					if (depth > maxDepth) {// om dybden er større en max
						maxDepth = depth; // blir max den gitte dybden
					}
				}
			}
		}

		V champNode = null;// seiers node
		int maxNeighbours = 0;// highscore variabel

		for (Map.Entry<V, Integer> entry : depthMap.entrySet()) {// entry gjør det lett å iterere over
			if (entry.getValue() == maxDepth - 1) {// bare noder som har max dybde, minus 1 så får jeg foreldre
				int currentNeighbourCount = graph.degree(entry.getKey());// teller naboene til en gitt node
				if (currentNeighbourCount > maxNeighbours) {// hvis currentGradtall er større en maxGradtall
					maxNeighbours = currentNeighbourCount;// settes currentGradtall til maxGradtall
					champNode = entry.getKey();// champNode blir kandidat for noden som skal returneres
				}
			}
		}

		return champNode;
	}

	private <V> LinkedList<Graph<V>> biggestSubTreeList(Graph<V> g, V root) {
		LinkedList<Graph<V>> treeList = new LinkedList<>();

		
		for (V node : g.neighbours(root)) {
			treeList.add(bfsSubTree(node, g, root));
		}
		
		treeList.sort(Comparator.comparingInt((Graph<V> graph) -> graph.numVertices()).reversed());

		return treeList;
	}

	
	private <V> Graph<V> bfsSubTree(V startNode, Graph<V> g, V root) {// lagger subtree fra en gitt node. Vil at den
																		// skal finne
		Graph<V> subTree = new Graph<>();// nytt tomt tree
		subTree.addVertex(startNode);// legger til ny root

		Set<V> visited = new HashSet<>();// tomt hashset
		Queue<V> queue = new LinkedList<>();// tom queue

		visited.add(startNode);// legger til startNode i visited
		queue.add(startNode);// legger til startNode i køen

		while (!queue.isEmpty()) {// i mens køen ikke er tom
			V current = queue.poll();// current er køens førstemann

			for (V neighbor : g.neighbours(current)) {// for barna til current
				if (!current.equals(neighbor) && !visited.contains(neighbor) && !current.equals(root)) {// hvis naboene
																										// ikke er
																										// besøkt
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
