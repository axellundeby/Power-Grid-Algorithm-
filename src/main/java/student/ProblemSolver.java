package student;

import java.util.ArrayList;
import java.util.Collections;
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

		Collections.sort(edges,g);

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
		throw new UnsupportedOperationException(":)");
	}

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		throw new UnsupportedOperationException("Implement me :)");
	}
}
