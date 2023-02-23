

package edu.caltech.cs2.datastructures;

import com.github.javaparser.ast.stmt.BlockStmt;
import edu.caltech.cs2.interfaces.*;
import edu.caltech.cs2.textgenerator.MarkovTextGenerator;

import java.util.Iterator;

public class BSTDictionary<K extends Comparable<? super K>, V>
        implements IDictionary<K, V> {

    private BSTNode<K, V> root;
    private int size;

    /**
     * Class representing an individual node in the Binary Search Tree
     */
    private static class BSTNode<K, V> {
        public final K key;
        public V value;

        public BSTNode<K, V> left;
        public BSTNode<K, V> right;

        /**
         * Constructor initializes this node's key, value, and children
         */
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public BSTNode(BSTNode<K, V> o) {
            this.key = o.key;
            this.value = o.value;
            this.left = o.left;
            this.right = o.right;
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;

        }

        public boolean hasBothChildren() {
            return this.left != null && this.right != null;
        }
    }

    /**
     * Initializes an empty Binary Search Tree
     */
    public BSTDictionary() {
        this.root = null;
        this.size = 0;
    }


    @Override
    public V get(K key) {
        // start searching for the key from the root
        if(root==null){
            return null;
        }
        if(root.key.equals(key)){
            return root.value;
        }

            return getKey(root, key);


    }

    private V getKey(BSTNode<K,V> node, K key) {
        if(node==null){
            return null;
        }
        if(node.key.equals(key)){
            return node.value;
        }
        else {
//swap left & right?
            if (key.compareTo(node.key) < 0) {
                return getKey(node.left, key);
            }
            if(key.compareTo(node.key) > 0){
                return getKey(node.right, key);
            }
        }
        return null;
    }


    @Override
    public V remove(K key) {
        if(containsKey(key)) {
            V value = get(key);
            root = removeKey(root, key);
                size--;
                return value;
            }


        return null;
    }

    private BSTNode<K,V> removeKey(BSTNode<K,V> node, K key) {

        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = removeKey(node.left, key);
            //System.out.println("Going left  from " + node.key + "to " + ( node.left != null ? node.left.key:" ") );
        } else if (key.compareTo(node.key) > 0) {
            node.right = removeKey(node.right, key);
            // System.out.println("Going right from " + node.key + "to " + (node.right != null ? node.right.key: " "));
        } else {
            return minfind(node);
            // System.out.println("This is the node to remove " +( node.left != null ? node.left.key:" ") + ", "
            //    + (node.right != null ? node.right.key: " "));
//

        }
        return node;
    }

    private BSTNode<K,V> minfind(BSTNode<K,V> node){
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }

        BSTNode<K, V> parent = node.right;
        //BSTNode<K, V> child =  parent.left;
        //take right and go down

        while (parent.left != null) {
            parent = parent.left;
        }
        removeKey(node, parent.key);
        parent.right = node.right;
        parent.left = node.left;

      //  minfind(parent, parent.key);
//
//        removeKey(node, successor.key);
//        successor.right = node.right;
//        successor.left = node.left;
////
       return parent;

    }


    @Override
    public V put(K key, V value) {
        BSTNode<K,V> newNode = new BSTNode<>(key, value);
        if(root == null){
            size++;
            root = newNode;
            return null;
        }
        return putRecursive(root, newNode);

//        if(root==null) {
//            return null;
//
    }

    private V putRecursive(BSTNode<K,V> node, BSTNode<K,V> newNode) {

        if(newNode.key.compareTo(node.key)<0){
            if(node.left == null){
                node.left = newNode;
                size++;
                return null;
            }
            return putRecursive(node.left, newNode);
        }
        else if(newNode.key.compareTo(node.key)>0){
            if(node.right == null) {
                node.right = newNode;
                size++;
                return null;
            }
            return putRecursive(node.right, newNode);
        }
        V val = node.value;
        node.value = newNode.value;

        return val;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }
    private boolean containsKey(BSTNode<K,V> node, K key){
        if(node == null){
            return false;
        }
        if(node.key.equals(key)){
            return true;
        }
        //key k of a node is always greater than the keys
        //present in its left sub tree
        //key k of a node is always lesser thann the keys present in
        //its right sub tree
        if(key.compareTo(node.key)<0){
            return containsKey(node.left, key);
        }
        if(key.compareTo(node.key)>0){
            return containsKey(node.right, key);
        }
       return false;
    }

    @Override
    public boolean containsValue(V value) {
       return containsValue(root, value);
    }
        private boolean containsValue(BSTNode<K,V> node, V value){

            if (node == null) {
                return false;
            }
            if (node.value.equals(value)) {
                return true;
            }
            //key k of a node is always greater than the keys
            //present in its left sub tree
            //key k of a node is always lesser thann the keys present in
            //its right sub tree
            //
            if (containsValue(node.left,value) || containsValue(node.right, value)){
                return true;
            }
//            if ((value.hashCode() - node.value.hashCode()) < 0) {
//                return containsValue(node.left, value);
//            }
//            if ((value.hashCode() - node.value.hashCode()) > 0){
//                return containsValue(node.right, value);
//            }
//            //return false;
//            return false;
            return false;
        }


    /**
     * @return number of key/value pairs in the BST
     */
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keys() {
        MarkovTextGenerator.ArrayDeque<K> collection =
                new MarkovTextGenerator.ArrayDeque<>();
        keysRecursive(root, collection);
        return collection;
    }

    private void keysRecursive(BSTNode<K,V> node,
                               MarkovTextGenerator.ArrayDeque<K> collection) {
        if(node != null){
            keysRecursive(node.left, collection);
            collection.add(node.key);
            keysRecursive(node.right, collection);
        }
    }

    @Override
    public ICollection<V> values() {
        MarkovTextGenerator.ArrayDeque<V> collection =
                new MarkovTextGenerator.ArrayDeque<>();
        valuesRecursive(root, collection);
        return collection;
    }

    private void valuesRecursive(BSTNode<K,V> node,
                                 MarkovTextGenerator.ArrayDeque<V> collection) {
        if(node != null){
            valuesRecursive(node.left, collection);
            collection.add(node.value);
            valuesRecursive(node.right, collection);
        }
    }

    /**
     * Implementation of an iterator over the BST
     */

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
        //comapre key to key at node that you are at, if less than call on the left side
        //get reurn value instead of returning true
        //for contains value go thorugh whole tree
        ///iterate til you get to leaf, if 4 is less than 3, put it to the left
        //sort up, sort up. You could/
        //if that they add in could, re-sort.

    }

    @Override
    public String toString() {
        if (this.root == null) {
            return "{}";
        }

        StringBuilder contents = new StringBuilder();

        IQueue<BSTNode<K, V>> nodes = new MarkovTextGenerator.ArrayDeque<>();
        BSTNode<K, V> current = this.root;
        while (current != null) {
            contents.append(current.key + ": " + current.value + ", ");

            if (current.left != null) {
                nodes.enqueue(current.left);
            }
            if (current.right != null) {
                nodes.enqueue(current.right);
            }

            current = nodes.dequeue();
        }

        return "{" + contents.toString().substring(0, contents.length() - 2) + "}";
    }


}
