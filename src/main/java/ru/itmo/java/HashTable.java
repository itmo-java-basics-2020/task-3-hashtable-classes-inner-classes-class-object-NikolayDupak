package ru.itmo.java;

public class HashTable {
    private Entry[] tableOfElements;
    private boolean[] wasUsed;
    private int countOfEntry = 0;
    private double loadFactor;
    private static double startLoadFactor = 0.5;


    public HashTable(int initialCapacity) {
        this(initialCapacity, startLoadFactor);
    }

    public HashTable(int initialCapacity, double loadFactor) {
        this.tableOfElements = new Entry[initialCapacity];
        this.wasUsed = new boolean[initialCapacity];
        this.loadFactor = loadFactor;
        for (int i = 0; i < initialCapacity; i++) {
            this.wasUsed[i] = false;
        }
    }


    public Object put(Object key, Object value) {
        if (this.countOfEntry > this.tableOfElements.length * this.loadFactor
                || this.countOfEntry == this.tableOfElements.length) {
            resize();
        }

        Entry elem = new Entry(key, value);
        Object lastValue = get(key);
        boolean check = false;

        if (lastValue == null) {
            check = true;
            this.countOfEntry++;
        }

        int ind = getIndex(key, check);
        this.tableOfElements[ind] = elem;
        this.wasUsed[ind] = true;
        return lastValue;
    }

    public Object get(Object key) {
        int ind = getIndex(key, false);
        if (this.tableOfElements[ind] == null) {
            return null;
        }
        return this.tableOfElements[ind].getValue();
    }


    public Object remove(Object key) {
        int ind = getIndex(key, false);
        Entry LastElem = this.tableOfElements[ind];
        if (LastElem != null) {
            this.tableOfElements[ind] = null;
            this.countOfEntry--;
            return LastElem.getValue();
        }
        return null;
    }

    public int size() {
        return this.countOfEntry;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        if (loadFactor >= 1) {
            this.loadFactor = 1;
        }
        this.loadFactor = loadFactor;
    }

    /**
     * @param key
     * @param freeSpace true - if put new element
     *                  false - if find last value or change value
     * @return int
     * index for hash of key
     */
    private int getIndex(Object key, boolean freeSpace) {
        int hash = Math.abs(key.hashCode());
        hash = hash % this.tableOfElements.length;
        if (freeSpace) {
            while (true) {
                if (this.tableOfElements[hash] == null) {
                    return hash;
                }
                hash++;
                if (hash == this.tableOfElements.length) {
                    hash = 0;
                }
            }
        }

        while (true) {
            if (this.tableOfElements[hash] == null && !this.wasUsed[hash]) {
                return hash;
            }
            if (this.tableOfElements[hash] != null && this.tableOfElements[hash].getKey().equals(key)) {
                return hash;
            }
            hash++;
            if (hash == this.tableOfElements.length) {
                hash = 0;
            }
        }
    }

    private void resize() {

        Entry[] lastTable = this.tableOfElements;
        this.tableOfElements = new Entry[this.tableOfElements.length * 2];
        this.wasUsed = new boolean[this.tableOfElements.length];
        this.countOfEntry = 0;

        for (Entry Elem : lastTable) {
            if (Elem != null) {
                put(Elem.getKey(), Elem.getValue());
            }
        }
    }

    private class Entry {
        private Object Key;
        private Object Value;


        Entry(Object key, Object value) {
            this.Key = key;
            this.Value = value;
        }


        public Object getKey() {
            return Key;
        }

        public Object getValue() {
            return Value;
        }

    }
}
