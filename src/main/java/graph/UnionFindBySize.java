package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UnionFindBySize<E> implements UnionFind<E> {

    HashMap<E, Optional<E>> parent = new HashMap<>();
    HashMap<E, Integer> size = new HashMap<>();
    int numGroups = 0;

    public UnionFindBySize(Iterable<E> universe) {
        for (E e : universe) {
            parent.put(e, Optional.empty());
            size.put(e, 1);
            numGroups++;
        }
    }

    @Override
    public void union(E elem1, E elem2) {
        E root1 = find(elem1);
        E root2 = find(elem2);

        if (groupSize(root1) > groupSize(root2)) {
            connect(root1, root2);
        } else {
            connect(root2, root1);
        }
    }

    private void connect(E root1, E root2) {
        if (root1.equals(root2))
            return;
        int curSize = size.get(root1) + size.get(root2);
        parent.put(root2, Optional.of(root1));
        size.remove(root2);
        size.put(root1, curSize);
        numGroups--;
    }

    private int groupSize(E e) {
        E root = find(e);
        return size.get(root);
    }

    @Override
    public E find(E elem) {
        ArrayList<E> path = new ArrayList<>();
        
        while (!isRoot(elem)) {
            path.add(elem);
            elem = parent.get(elem).orElse(null); // Using orElse to get the value or null
        }

        // Path compression
        for (E e : path) {
            parent.put(e, Optional.of(elem));
        }
        
        return elem;
    }

    private boolean isRoot(E elem) {
        return !parent.get(elem).isPresent();
    }

    @Override
    public int numGroups() {
        return numGroups;
    }

    @Override
    public Iterable<E> group(E elem) {
        E root = find(elem);
        ArrayList<E> elems = new ArrayList<>();
        for (E e : parent.keySet()) {
            if (find(e).equals(root)) {
                elems.add(e);
            }
        }
        return elems;
    }

    @Override
    public boolean same(E a, E b) {
        return find(a).equals(find(b));
    }
}
