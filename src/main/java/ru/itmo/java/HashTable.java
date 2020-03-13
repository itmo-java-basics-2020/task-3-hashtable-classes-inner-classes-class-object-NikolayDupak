package ru.itmo.java;

import java.util.Map;

public class HashTable {
    private Entry[] Table;
    private boolean[] Been;
    private int CountOfEntry = 0;
    private double loadFactor = 0.5;


    HashTable() {
        Table = new Entry[10];
        Been = new boolean[10];

    }

    HashTable(int initialCapacity) {
        //System.out.println(initialCapacity);
        Table = new Entry[initialCapacity];
        Been = new boolean[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            Been[i] = false;
        }
    }

    HashTable(int initialCapacity, double loadFactor) {
        System.out.println(initialCapacity);
        Table = new Entry[initialCapacity];
        Been = new boolean[initialCapacity];
        this.loadFactor = loadFactor;
        for (int i = 0; i < initialCapacity; i++) {
            Been[i] = false;
        }
    }


    Object put(Object key, Object value) {
/*
        System.out.print("#put");
        System.out.print(key);
        System.out.print("-");
        System.out.print(value);
        System.out.println("#");*/
        Entry Elem = new Entry(key, value);

        int HashCode = Math.abs(key.hashCode());
        HashCode = HashCode % Table.length;

        if (CountOfEntry > Table.length*loadFactor) {
            resize();
        }
        Object LastValue = get(key);

        if (Table[HashCode] == null) {
            Table[HashCode] = Elem;
            CountOfEntry++;
            Been[HashCode] = true;
            return LastValue;
        }
        if (Table[HashCode].Key.equals(key)) {
            //Entry LastValue = Table[HashCode];
            Table[HashCode] = Elem;
            Been[HashCode] = true;
            return LastValue;
        }
        while (true){
            //System.out.print("-");
            HashCode++;
            if (HashCode >= Table.length){
                HashCode = 0;
            }

            if (Table[HashCode] == null) {
                Table[HashCode] = Elem;
                CountOfEntry++;
                Been[HashCode] = true;
                return LastValue;
            }
            if (Table[HashCode].Key.equals(key)) {

                Table[HashCode] = Elem;
                Been[HashCode] = true;
                return LastValue;
            }
        }


    }

    Object get(Object key) {
        //System.out.print("#get");
        //System.out.print(key);
        //System.out.println("-");
        //System.out.print(value);
        //System.out.println("#");


        int HashCode = Math.abs(key.hashCode());
        HashCode = HashCode % Table.length;
        //System.out.print("hash = ");
        //System.out.println(HashCode);

        while (Been[HashCode]){


            if (Table[HashCode] != null && Table[HashCode].Key.equals(key)) {
               /* System.out.print("Hashcode = ");
                System.out.println(HashCode);
                System.out.print(Table[HashCode].Value);
                System.out.println("#");*/
                return Table[HashCode].Value;
            }
            HashCode++;
            if (HashCode >= Table.length){
                HashCode = 0;

            }

        }
        //System.out.println("#");
        return null;
    }

    Object remove(Object key) {
        int HashCode = key.hashCode();
        HashCode = Math.abs(HashCode % Table.length);


        while (Been[HashCode]){


            if (Table[HashCode] != null && Table[HashCode].Key.equals(key)) {

                Entry LastValue = Table[HashCode];
                Table[HashCode] = null;
                CountOfEntry--;
                return LastValue.Value;
            }
            HashCode++;
            if (HashCode >= Table.length){
                HashCode = 0;

            }

        }
        return null;

    }

    int size() {
        return CountOfEntry;
    }

    void resize() {

        Entry[] NewTable = new Entry[Table.length*2];
        Been = new boolean[NewTable.length];
        //System.out.print("res = ");
        System.out.println(NewTable.length);
        for (int i = 0; i < Table.length; i++) {
            if (Table[i] != null){
                Entry Elem = Table[i];
                int HashCode = Math.abs(Elem.Key.hashCode());
                HashCode = HashCode % NewTable.length;
                NewTable[HashCode] = Elem;
                Been[HashCode] = true;
                //System.out.print(Elem.Key);
                //System.out.print("-");
                //System.out.print(HashCode);
            }
        }

        this.Table = NewTable;
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
