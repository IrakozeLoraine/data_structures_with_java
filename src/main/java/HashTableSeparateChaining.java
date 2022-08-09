import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HashTableSeparateChaining<K, V> {
    /**
     * Implementation of a HashTable
     * using separate chaining technique
     *
     * Author: Irakoze Loraine, mukezwa@gmail.com
     */
    private class Entry<K, V> {
        int hash; K key; V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
            this.hash = key.hashCode();
        }

        public boolean equals(Entry<K,V> other) {
            if (hash != other.hash) return false;
            return key.equals(other.key);
        }

        @Override public String toString(){
            return key + "=> " + val;
        }
    }

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private LinkedList <Entry<K, V>> [] table;

    private double maxLoadFactor;
    private int capacity, threshold, size = 0;

    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity<0)
            throw new IllegalArgumentException("Illegal capacity");
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal maxLoadFactor");
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.maxLoadFactor = maxLoadFactor;
        threshold = (int) (this.capacity * maxLoadFactor);
        table = new LinkedList[this.capacity];
    }

    //convert a hash value to an index
    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(table, null);
        size=0;
    }

    public boolean containsKey(K key) {
        return  hashKey(key);
    }

    public boolean hashKey(K key) {
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    public V put(K key, V val) {
        return insert(key, val);
    }
    public V add(K key, V val) {
        return insert(key, val);
    }
    public V insert(K key, V val) {
        if (key == null) throw new IllegalArgumentException("Null key");
        Entry<K, V> newEntry = new Entry<>(key, val);
        int bucketIndex = normalizeIndex(newEntry.hash);
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    public V get(K key) {
        if (key == null) return null;
        int bucketIndex = normalizeIndex(key.hashCode());
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) return entry.val;
        return null;
    }
    public V remove(K key) {
        if (key == null) return null;
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    private V bucketRemoveEntry(int bucketIndex, K key) {
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            LinkedList<Entry<K, V>> list = table[bucketIndex];
            list.remove(entry);
            --size;
            return entry.val;
        }
        else return null;
    }
    private V bucketInsertEntry(int bucketIndex, Entry<K,V> entry) {
        LinkedList<Entry<K,V>> bucket = table[bucketIndex];
        if (bucket == null) table[bucketIndex] = bucket = new LinkedList<>();
        Entry<K, V> existEntry = bucketSeekEntry(bucketIndex, entry.key);
        if (existEntry == null) {
            bucket.add(entry);
            if (++size > threshold) resizeTable();
            return null;
        }
        else {
            V oldVal = existEntry.val;
            existEntry.val = entry.val;
            return oldVal;
        }
    }
    private Entry<K,V> bucketSeekEntry(int bucketIndex, K key) {
        if (key == null) return null;
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null) return null;
        for (Entry<K, V> entry: bucket)
            if (entry.key.equals(key))
                return entry;
        return null;
    }

    private void resizeTable() {
        capacity*=2;
        threshold=(int) (capacity*maxLoadFactor);
        LinkedList<Entry<K,V>> [] newTable = new LinkedList[capacity];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    int bucketIndex = normalizeIndex(entry.hash);
                    LinkedList<Entry<K, V>> bucket = newTable[bucketIndex];
                    if (bucket == null) newTable[bucketIndex] = bucket = new LinkedList<>();
                    bucket.add(entry);
                }
                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }
    public List<K> keys(){
        List<K> keys = new ArrayList<>(size());
        for (LinkedList<Entry<K,V>> bucket: table)
            if (bucket != null)
                for (Entry<K,V> entry: bucket)
                    keys.add(entry.key);
        return keys;
    }
    public List<V> values(){
        List<V> values = new ArrayList<>(size());
        for (LinkedList<Entry<K,V>> bucket: table)
            if (bucket != null)
                for (Entry<K,V> entry: bucket)
                    values.add(entry.val);
        return values;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            if (table[i] == null) continue;
            for (Entry<K,V> entry: table[i])
                sb.append(entry+", ");
        }
        sb.append("}");
        return sb.toString();
    }

}
