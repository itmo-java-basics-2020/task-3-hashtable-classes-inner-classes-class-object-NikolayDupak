package ru.itmo.java;

public class HashTable {
    private Entry[] Table;
    private boolean[] Been;
    private int CountOfEntry = 0;
    private double loadFactor = 0.5;


    HashTable(int initialCapacity) {

        Table = new Entry[initialCapacity];
        Been = new boolean[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            Been[i] = false;
        }
    }

    HashTable(int initialCapacity, double loadFactor) {
        Table = new Entry[initialCapacity];
        Been = new boolean[initialCapacity];
        this.loadFactor = loadFactor;
        for (int i = 0; i < initialCapacity; i++) {
            Been[i] = false;
        }
    }


    Object put(Object key, Object value) {
        if (CountOfEntry > Table.length * loadFactor || CountOfEntry == Table.length) {
            resize();
        }

        Entry Elem = new Entry(key, value);
        Object LastValue = get(key);
        boolean check = false;

        if (LastValue == null) {
            check = true;
            CountOfEntry++;
        }

        int ind = GetIndex(key, check);
        Table[ind] = Elem;
        Been[ind] = true;
        return LastValue;
    }

    Object get(Object key) {
        int ind = GetIndex(key, false);
        if (Table[ind] == null) {
            return null;
        }
        return Table[ind].Value;
    }


    Object remove(Object key) {
        int ind = GetIndex(key, false);
        Entry LastElem = Table[ind];
        if (LastElem != null) {
            Table[ind] = null;
            CountOfEntry--;
            return LastElem.Value;
        }
        return null;
    }

    int size() {
        return CountOfEntry;
    }

    /**
     * @param key
     * @param FreeSpace true - if put new element
     *                  false - if find last value or change value
     * @return int
     * index for hash of key
     */
    private int GetIndex(Object key, boolean FreeSpace) {
        int Hash = Math.abs(key.hashCode());
        Hash = Hash % Table.length;
        if (FreeSpace) {
            while (true) {
                if (Table[Hash] == null) {
                    return Hash;
                }
                Hash++;
                if (Hash == Table.length) {
                    Hash = 0;
                }
            }
        }

        while (true) {
            if (Table[Hash] == null && !Been[Hash]) {
                return Hash;
            }
            if (Table[Hash] != null && Table[Hash].Key.equals(key)) {
                return Hash;
            }
            Hash++;
            if (Hash == Table.length) {
                Hash = 0;
            }
        }
    }

    void resize() {

        Entry[] LastTable = Table;
        Table = new Entry[Table.length * 2];
        Been = new boolean[Table.length];
        CountOfEntry = 0;

        for (Entry Elem : LastTable) {
            if (Elem != null) {
                put(Elem.Key, Elem.Value);
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
    }
}
