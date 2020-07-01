package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> mapSet = new HashSet<>();
        keySetHelper(mapSet, root);
        return mapSet;
    }

    public void keySetHelper(Set<K> set, Node p) {
        if (p == null) {
            return;
        }
        set.add(p.key);
        keySetHelper(set, p.left);
        keySetHelper(set, p.right);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V removed = get(key);
        if (removed == null) {
            return null;
        }
        root = removeHelper(key, root);
        size -= 1;
        return removed;
    }

    public Node removeHelper(K key, Node p) {
        if (key.compareTo(p.key) < 0) {
            p.left = removeHelper(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = removeHelper(key, p.right);
        } else {
            if (p.left == null) {
                return p.right;
            } else if (p.right == null) {
                return p.left;
            } else {
                Node toPromote = maxNode(p.left);
                p.key = toPromote.key;
                p.value = toPromote.value;
                p.left = removeHelper(toPromote.key, p.left);
            }
        }
        return p;
    }

    private Node maxNode(Node p) {
        if (p.right == null) {
            return p;
        } else {
            return maxNode(p.right);
        }
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V toRemove = get(key);
        if (value.equals(toRemove)) {
            remove(key);
            return toRemove;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<Integer, Integer> bstmap = new BSTMap<>();
        bstmap.put(8, 5);
        bstmap.put(7, 10);
        //bstmap.remove(8, 5);
        bstmap.remove(7);
        //bstmap.remove(7, 10);
        //System.out.println(bstmap.remove(8, 5));
        for (int i: bstmap) {
            System.out.println(i);
        }

    }
}
