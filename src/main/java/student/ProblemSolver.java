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
		pathU = bfsPath(root, u, g);
		pathV = bfsPath(root, v, g);

		// finner den korteste listen
		int size = pathV.size();
		if (size > pathU.size()) {
			size = pathU.size();
		}

		// når stiene ikke er like, retirerer vi noden før de ble ulike
		for (int i = 0; i < size; i++) {
			if (!(pathU.get(i).equals(pathV.get(i))))
				return pathU.get(i - 1);
		}
		return root;
	}

	private <V> List<V> bfsPath(V root, V target, Graph<V> graph) {
		Queue<V> queue = new LinkedList<>();
		Map<V, V> parentMap = new HashMap<>();// hashMap med <child, parent>
		List<V> path = new ArrayList<>();

		queue.add(root);// legger til root i kø
		parentMap.put(root, null); // root er stamfar, har ingen foreldre

		while (!queue.isEmpty()) {// imens køen ikke er full
			V current = queue.poll();// currentNode er første i køen.
			if (current == target) {// hvis vi har funnet target
				for (V node = target; node != null; node = parentMap.get(node)) {
					path.add(0, node); // legger til alle foreldrene til vi kommer til root
				}
				return path;
			}

			// legge til currents barn i queue, legge til barna til current i parentMap
			for (V child : graph.neighbours(current)) {
				if (!parentMap.containsKey(child)) {
					queue.add(child);
					parentMap.put(child, current);
				}
			}
		}

		return path; // returner tom liste hvis målnoden ikke ble funnet
	}

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		LinkedList<Graph<V>> Trees = biggestSubTreeList(g, root);
		
		Graph<V> firstTree =  Trees.removeFirst();//strøste treet i grafen
		Graph<V> secondTree =  Trees.removeFirst();//nest største treet i grafen



		//feste nederst på siste utgrening
		//sjekke hvilke av de nederste som er 

	}

	// lag en hjelpe metode som går igjennom subtrær og teller hvor mange noder. De
	// to med flest etter root, skal vaier gå mellom
	// root må være nabo
	private <V> LinkedList<Graph<V>> biggestSubTreeList(Graph<V> g, V root) {
		LinkedList<Graph<V>> treeList = new LinkedList<>();

		for (V node : g.neighbours(root)) {
			treeList.add(bfsSubTree(node, g));
		}
		treeList.sort(Comparator.comparingInt((Graph<V> graph) -> graph.numVertices()).reversed());//sorterer denne listen? Sorterer på numVertices()
		return treeList;
	}

	private <V> Graph<V> bfsSubTree(V startNode, Graph<V> g) {

		Graph<V> subTree = new Graph<>();//nytt tomt tree
		subTree.addVertex(startNode);//legger til ny root

		Set<V> visited = new HashSet<>();//tomt hashset
		ArrayList<V> queue = new ArrayList<>();//tom queue

		visited.add(startNode);//legger til startNode i visited
		queue.add(startNode);//legger til startNode i køen

		while (!queue.isEmpty()) {//i mens køen ikke er tom
			V current = queue.remove(0);//current er køens førstemann

			for (V neighbor : g.neighbours(current)) {//for barna til current
				if (!visited.contains(neighbor)) {//hvis naboene ikke er besøkt
					visited.add(neighbor);//legg til nabo i besøkt
					queue.add(neighbor);//legg til nabo i kø

					subTree.addVertex(neighbor);//legger til noden i treet
					subTree.addEdge(current, neighbor);//legger til kanten i treet
				}
			}
		}
		return subTree;
	}

}

// feste vaier på bunnen av subtree som redder flest hus. ferdig
