import java.util.*;

public class PriorityQueue<T extends  Comparable<T>> {
    /**
     * Implementation of a min priority queue using a binary heap.
     *
     * Author: Irakoze Loraine, mukezwa@gmail.com
     */
    private int heapSize = 0;
    private int heapCapacity = 0;
    private List<T> heap;

    /* A map for possible indices of node values in a heap.
    This will allow us to have a 0(log(n)) for elements removal.
     */
    private Map<T, TreeSet<Integer>> map = new HashMap<>();

    public PriorityQueue() {
        this(1);
    }
    public PriorityQueue(int capacity) {
        heap = new ArrayList<>(capacity);
    }

    //using heapify process to add an array of elements
    public PriorityQueue(T[] element) {
        heapSize = heapCapacity = element.length;
        heap = new ArrayList<>(heapCapacity);

        //Placing elements in the heap
        for (int i = 0; i < heapSize; i++) {
            mapAdd(element[i], i);
            heap.add(element[i]);
        }

        for (int i = Math.max(0, (heapSize/2)-1); i >= 0; i--) {
            navigateDown(i);
        }
    }

    public PriorityQueue(Collection<T> elements) {
        this(elements.size());
        for(T element: elements) add(element);
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void clear() {
        for (int i = 0; i < heapCapacity; i++)
            heap.set(i, null);
        heapSize = 0;
        map.clear();
    }

    public int size() {
        return  heapSize;
    }

    public T peek() {
        if(isEmpty()) return null;
        return heap.get(0);
    }

    public T poll() {
        return removeAt(0);
    }

    public boolean contains(T element) {
        if (element == null) return false;
        return map.containsKey(element);
    }

    //Add element to priority queue
    public void add(T element) {
        if (element == null) throw new IllegalArgumentException();

        if (heapSize < heapCapacity)
            heap.set(heapSize, element);
        else {
            heap.add(element);
            heapCapacity++;
        }
        mapAdd(element, heapSize);
        navigateUp(heapSize);
        heapSize++;
    }

    //remove an element in the heap
    public boolean remove(T element) {
        if (element == null) return false;

        Integer index = mapGet(element);
        if (index != null) removeAt(index);
        return index != null;
    }

    //Check if the heap is a min heap, suppose i=0
    public boolean isMinHeap(int i) {
        if (i >= heapSize) return true;

        int left = 2 * i + 1;
        int right = 2 * i +2;

        //check if current node i is less than both children to be valid
        if (left < heapSize && !isLess(i, left)) return false;
        if (right < heapSize && !isLess(i, right)) return false;

        //recurse on both children for heap invariant validity
        return  isMinHeap(left) && isMinHeap(right);
    }

    //Convert to string
    @Override public String toString () {
        return heap.toString();
    }

    //Compare values of two nodes
    private boolean isLess(int a, int b) {
        T nodeA = heap.get(a);
        T nodeB = heap.get(b);

        return nodeA.compareTo(nodeB) <= 0;
    }

    //Navigate node upwards
    private void navigateUp(int i) {
        int parent = (i-1)/2;
        while (i>0 && isLess(i, parent)) {
            swap(parent, i);
            i = parent;
            parent = (i-1)/2;
        }
    }

    //Navigate node downwards
    private void navigateDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = left;

            //If right is smaller than left, set right to smallest
            if(right<heapSize && isLess(right, left))
                smallest = right;
            //Stop if we cannot navigate down anymore or have reached the bounds
            if(left>=heapSize || isLess(i, smallest))
                break;
            //now, move down the tree following the smallest node
            swap(smallest, i);
            i = smallest;
        }
    }

    //Swap the two nodes
    private void swap(int a, int b) {
        T elementA = heap.get(a);
        T elementB = heap.get(b);

        heap.set(a, elementB);
        heap.set(b, elementA);

        mapSwap(elementA, elementB, a, b);
    }

    //remove a node at a specific index
    private T removeAt(int i) {
        if (isEmpty()) return null;

        heapSize--;
        T removed = heap.get(i);
        swap(i, heapSize);

        heap.set(heapSize, null);
        mapRemove(removed, heapSize);

        if (i == heapSize) return removed;

        T element = heap.get(i);
        navigateDown(i);

        if(heap.get(i).equals(element))
            navigateUp(i);

        return removed;
    }

    //Add a node element and its index to the map
    private void mapAdd(T element, int index) {
        TreeSet<Integer> set = map.get(element);

        if (set == null) {
            set = new TreeSet<>();
            set.add(index);
            map.put(element, set);
        } else set.add(index);
    }

    //Remove the index at a specific node element
    private void mapRemove(T element, int index) {
        TreeSet<Integer> set = map.get(element);
        set.remove(index);
        if (set.size() == 0) map.remove(element);
    }

    //Get an index position for the given element
    private Integer mapGet(T element) {
        TreeSet<Integer> set = map.get(element);
        if (set != null) return set.last();
        return  null;
    }

    //Swap the index of two nodes within the map
    private void mapSwap(T el1, T el2, int el1Index, int el2Index) {
        Set<Integer> set1 = map.get(el1);
        Set<Integer> set2 = map.get(el2);

        set1.remove(el1Index);
        set2.remove(el2Index);

        set1.add(el2Index);
        set2.add(el1Index);
    }
}
