package com.microcore.jcf.pojo.dto.base;

/**
 * DESC:K-V结构
 *
 * @author leizhenyang
 * @date 2018/6/6
 */
public class KV<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public KV() {
    }

    public KV(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
