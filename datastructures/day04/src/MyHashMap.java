import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    // average number of entries per bucket before we grow the map
    private static final double ALPHA = 1.0;
    // average number of entries per bucket before we shrink the map
    private static final double BETA = .25;

    // resizing factor: (new size) = (old size) * (resize factor)
    private static final double SHRINK_FACTOR = 0.5, GROWTH_FACTOR = 2.0;

    private static final int MIN_BUCKETS = 16;

    // array of buckets
    protected LinkedList<Entry>[] buckets;
    private int size = 0;

    public MyHashMap() {
        initBuckets(MIN_BUCKETS);
    }

    public class Entry implements Map.Entry<K, V> {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            value = newValue;
            return value;
        }
    }

    /**
     * given a key, return the bucket where the `K, V` pair would be stored if it were in the map. O(1)
     */
    private LinkedList<Entry> chooseBucket(Object key) {
        // hint: use key.hashCode() to calculate the key's hashCode using its built in hash function
        // then use % to choose which bucket to return.
        return buckets[key.hashCode() % buckets.length]; //don't know if this is right!!!

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * return true if key is in map. O(N/M)
     */
    @Override
    public boolean containsKey(Object key) {
        LinkedList<Entry> bucket = chooseBucket(key);
        for (int i = 0; i < bucket.size(); i++) {
            //bucket.get(i).getKey().hashCode() == key.hashCode()
            if (bucket.get(i).getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * return true if value is in map O(N)
     */
    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < buckets.length; i++) {
            LinkedList<Entry> bucket = buckets[i];
            for (int j = 0; j < bucket.size(); j++) {
                if (bucket.get(j).getValue() == value){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    //O(N/M)
    public V get(Object key) {
        // find what bucket it is in and then look for it and return its value
        LinkedList<Entry> bucket = chooseBucket(key);
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getKey().equals(key)){
                return bucket.get(i).getValue();
            }
        }
        return null;
    }

    /**
     * add a new key-value pair to the map. Grow if needed, according to `ALPHA`.
     * @return the value associated with the key if it was previously in the map, otherwise null.
     * O(1)
     */
    @Override
    public V put(K key, V value) {
        V value2 = null;
        if (this.containsKey(key)){
            value2 = get(key);
            remove(key);
        }
        LinkedList<Entry> bucket = chooseBucket(key);
        // hint: use chooseBucket() to determine which bucket to place the pair in
        Entry entry = new Entry(key, value);
        bucket.add(entry);
        // hint: use rehash() to appropriately grow the hashmap if needed
        size++;
        if (this.size >= buckets.length*ALPHA){
            rehash(GROWTH_FACTOR);
        }
        return value2;
    }

    /**
     * Remove the key-value pair associated with the given key. Shrink if needed, according to `BETA`.
     * Make sure the number of buckets doesn't go below `MIN_BUCKETS`. Do nothing if the key is not in the map.
     * @return the value associated with the key if it was in the map, otherwise null.
     * O(N/M)
     */
    @Override
    public V remove(Object key) {
        V old_value = null;
        if (!this.containsKey(key)){
            return old_value;
        }

        // hint: use chooseBucket() to determine which bucket the key would be
        LinkedList<Entry> bucket = chooseBucket(key);
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getKey().equals(key)){
                old_value = bucket.get(i).getValue();
                bucket.remove(i); //doesn't make this O(n)?
                break;
            }
        }
        size--;
        if (this.size < buckets.length*BETA && buckets.length/2 >= MIN_BUCKETS){
            rehash(SHRINK_FACTOR);
        }

        return old_value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Changes the number of buckets and rehashes the existing entries.
     * If growthFactor is 2, the number of buckets doubles. If it is 0.25,
     * the number of buckets is divided by 4.
     * O(n)
     */
    private void rehash(double growthFactor) {
        LinkedList<Entry>[] old_buckets = this.buckets;
        int old_size = buckets.length;
        //System.out.println(old_size);
        clear();
        initBuckets((int) (old_size * growthFactor));
        //System.out.println(size);
        for (int i = 0; i < old_buckets.length; i++) {
            LinkedList<Entry> bucket = old_buckets[i];
            for (int j = 0; j < bucket.size(); j++) {
                put(bucket.get(j).getKey(), bucket.get(j).getValue());
            }
        }

        // hint: once you have removed all values from the buckets, use put(k, v) to add them back in the correct place
    }

    private void initBuckets(int size) {
        buckets = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void clear() {
        initBuckets(buckets.length);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> e : entrySet()) {
            keys.add(e.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Map.Entry<K, V> e : entrySet()) {
            values.add(e.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<Entry> map : buckets) {
            set.addAll(map);
        }
        return set;
    }
}
