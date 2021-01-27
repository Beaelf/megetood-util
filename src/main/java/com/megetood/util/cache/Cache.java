package com.megetood.util.cache;

/**
 * Cache interface
 *
 * @author Chengdong Lei
 * @date 2021/1/25
 */
public interface Cache<K, V> {
    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this cache contains no mapping for the key.
     *
     * @param key the specified key you provide
     * @return the value to which the specified key is mapped
     */
    V get(K key);

    /**
     * Associates the specified value with the specified key in this map
     *
     * @param key the key to find the value
     * @param val the value mapped by the key
     */
    void set(K key, V val);

    /**
     * Return the number of key-value mappings in zhe cache
     *
     * @return
     */
    int size();
}
