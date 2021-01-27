package com.megetood.util.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU缓存结构，该结构在构造时确定大小为K，有如下两个功能
 * set(key, value)：将记录(key, value)插入该结构
 * get(key)：返回key对应的value值
 * <p>
 * set和get方法的时间复杂度为O(1)
 * <p>
 * 某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的。
 * 当缓存的大小超过K时，移除最不经常使用的记录，即set或get最久远的。
 *
 * @author Chengdong Lei
 * @date 2021/1/25
 */
public class LRUCache<K, V> implements Cache<K, V> {
    private class Node<K, V> {
        K key;
        V val;
        Node prev;
        Node next;

        Node(Node prev, Node next, K key, V val) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            Node cur = this;
            while (cur != null) {
                res.append(cur.val).append(">");
                cur = cur.next;
            }
            res.append("NULL");
            return res.toString();
        }
    }

    private Map<K, Node> cache;
    private int capacity;
    private Node<K, V> first;
    private Node<K, V> last;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
    }

    @Override
    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }

        adjustToHead(node);

        return node.val;
    }

    @Override
    public void set(K key, V val) {
        Node node = cache.get(key);

        // node exists, update its position to head
        if (node != null) {
            node.val = val;
            // adjust current node to head
            adjustToHead(node);
        }

        // remove last node where the size of the cache reaches the capacity
        if (cache.size() == capacity) {
            removeNode(last);
        }

        // add the new node to the first where node doesn't exist
        addFirst(key, val);
    }

    @Override
    public int size() {
        return cache.size();
    }

    private void adjustToHead(Node<K, V> node) {
        K key = node.key;
        V val = node.val;

        removeNode(node);

        addFirst(key, val);
    }

    /**
     * remove a node from list
     *
     * @param node the node to be deleted
     */
    private void removeNode(Node node) {
        if (node == null) {
            return;
        }

        cache.remove(node.key);

        Node prev = node.prev;
        Node next = node.next;

        // Disconnect from the precursor node
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        // Disconnect from the next node
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        // help gc
        node = null;
    }

    private void addFirst(K key, V val) {
        Node<K, V> node;
        Node<K, V> f = first;
        node = new Node(null, first, key, val);
        first = node;
        if (f == null) {
            last = node;
        } else {
            f.prev = node;
        }

        cache.put(key, node);
    }
}
