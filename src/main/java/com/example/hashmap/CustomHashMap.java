package com.example.hashmap;

import java.util.NoSuchElementException;

/**
 * Realization of custom HashMap with open addressing.
 * For collision resolving Linear probing scheme is used.
 *
 * Max capacity of this HashMap is Integer.MAX_VALUE - 5 (max size of array in recent HotSpot JVM)
 *
 * Throws IllegalArgumentException if constructor params values are incorrect.
 * Throws NoSuchElementException if there is no value mapped with the key.
 */

public class CustomHashMap {

    private Pair[] table;

    private int size = 0;
    private int threshold;
    private final float loadFactor;
    private int capacity;

    static final int MIN_INITIAL_CAPACITY = 16;
    static final int MAX_CAPACITY = 2147483642;
    static final int HALF_MAX_CAPACITY = 1073741821;
    static final float DEFAULT_LOAD_FACTOR = 0.75F;


    /**
     * Constructs an empty hashMap with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public CustomHashMap() {
        this(MIN_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty hashMap with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative or more than 2147483642.
     */
    public CustomHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty hashMap with the specified initial
     * capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative or more than 2147483642
     *      or the load factor is nonpositive or more than 1.
     */
    public CustomHashMap(int initialCapacity, float loadFactor) {

        if (initialCapacity < 0
                || initialCapacity > MAX_CAPACITY
                || loadFactor <= 0.0F
                || loadFactor > 1.0F
                || Float.isNaN(loadFactor)
        ) {
            throw new IllegalArgumentException("Illegal constructor arguments");
        } else {
            if(initialCapacity < MIN_INITIAL_CAPACITY){
                initialCapacity = MIN_INITIAL_CAPACITY;
            }
            this.capacity = initialCapacity;
            this.loadFactor = loadFactor;
            this.threshold = (int) (capacity * loadFactor);
            if(this.threshold == 0){
                this.threshold = 1;
            }
            table = new CustomHashMap.Pair[capacity];
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(int key, long value) {
        resize();
        for(int i = 0; i < capacity; i++) {
            int index = hash(key, i);
            if (table[index] == null) {
                table[index] = new Pair(key, value);
                size++;
                return;
            } else if(table[index].getKey() == key) {
                table[index].setValue(value);
                return;
            }
        }
        throw new RuntimeException("Can not put " + value + " with key " + key);
    }


    /**
     * Returns the value to which the specified key is mapped,
     * or throws NoSuchElementException if this map contains no mapping for the key.
     *
     * @param key key with which the specified value is to be associated
     * @return value to be associated with the specified key
     * @throws NoSuchElementException if this map contains no mapping for the key.
     */
    public long get(int key){

        for(int i = 0; i < capacity; i++) {
            int index = hash(key, i);
            if(table[index] == null) {
                throw new NoSuchElementException("There is no value mapped with key " + key);
            } else if (table[index].getKey() == key) {
                return table[index].getValue();
            }
        }
        throw new NoSuchElementException("There is no value mapped with key " + key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return this.size;
    }

    /**
     * Combines hash function and linear probing scheme for hash collisions resolving.
     *
     * @param key key with which the specified value is to be associated
     * @param k   linear probing shift
     * @return    index of table array where required Pair object should be
     */
    private int hash(int key, int k) {
        return (Math.abs(key) + k) % capacity;
    }


    /**
     * Doubles table size if necessary.
     */
    private void resize() {
        if(size >= threshold) {
            if (capacity > HALF_MAX_CAPACITY) {
                threshold = MAX_CAPACITY;
                return;
            }
            int oldCap = capacity;
            Pair[] oldTab = this.table;

            capacity = capacity << 1;
            threshold = threshold << 1;

            table = new Pair[capacity];

            for (int i = 0; i < oldCap; i++) {
                Pair pair;
                if ((pair = oldTab[i]) != null) {

                    int index;
                    int key = pair.getKey();

                    for (int j = 0; j < capacity; j++) {
                        index = hash(key, j);

                        if (table[index] == null) {
                            table[index] = pair;
                            break;
                        }
                    }
                }
            }

        }
    }

    /**
     * Class for storing key&value pairs.
     */
    static class Pair {
        private int key;
        private long value;

        public Pair(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return (getKey() == pair.getKey()) && (getValue() == pair.getValue());
        }

        @Override
        public int hashCode() {
            return 31 * getKey() + (int) (getValue() ^ (getValue() >>> 32));
        }
    }
}




