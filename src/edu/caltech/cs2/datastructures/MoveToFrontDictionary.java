package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;

public class MoveToFrontDictionary<K, V> implements IDictionary<K,V> {

    public MoveToFrontDictionary() {
        // student: TODO fill this in
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        return this.values().contains(value);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ICollection<K> keys() {
        return null;
    }

    @Override
    public ICollection<V> values() {
        return null;
    }

    public V get(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
