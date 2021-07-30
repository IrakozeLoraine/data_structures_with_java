public class UnionFind {
    /**
     * Implementation of a UnionFind/Disjoint Set
     * This is an array based UnionFind
     *
     * Author: Irakoze Loraine, mukezwa@gmail.com
     */

    private int size;
    private int[] componentSize;
    private int[] parentId;
    private int componentsTotal;

    public UnionFind(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size <=0 is not allowed");
        this.size = componentsTotal = size;
        componentSize = new int[size];
        parentId = new int[size];

        for (int i = 0; i < size; i++) {
            parentId[i] = i;
            componentSize[i] = 1;
        }
    }

    //get the total number of elements in this UnionFind
    public int getSize() {
        return size;
    }

    //get the number of remaining components
    public int getComponentsTotal() {
        return componentsTotal;
    }

    //Unify(Group) the components containing a and b
    public void unify(int a, int b) {
        int root1 = find(a);
        int root2 = find(b);

        //if these elements are already in the same group
        if (root1 == root2) return;

        //merge smaller components into larger ones.
        if (componentSize[root1] < componentSize(root2)) {
            componentSize[root2] += componentSize[root1];
            parentId[root1] = root2;
        }
        else
            componentSize[root1] += componentSize[root2];
            parentId[root2] = root1;
        componentsTotal--;
    }

    //Find which component i belongs to
    public int find(int i) {
        //find the root of this component
        int root = i;
        while (root != parentId[i])
            root = parentId[root];
        /*
        compress the path leading back to the root (path compression).
        In amortized constant time complexity
         */
        while (i != root) {
            int next = parentId[i];
            parentId[i] = root;
            i = next;
        }

        return root;
    }

    //Check if the elements are in the same components
    public boolean isConnected(int a, int b) {
        return find(a) == find(b);
    }

    //get the size of the components a belongs to
    public int componentSize(int a) {
        return componentSize[find(a)];
    }

}
