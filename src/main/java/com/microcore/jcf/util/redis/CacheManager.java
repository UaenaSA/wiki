package com.microcore.jcf.util.redis;

import java.io.Serializable;
import java.util.Set;

/**
 * 缓存管理器
 */
public interface CacheManager {

    /**
     * 获取
     *
     * @param key
     * @return
     */
    public Object get(final String key);

    /**
     * 获取全部
     *
     * @param pattern
     * @return
     */
    public Set<Object> getAll(final String pattern);

    /**
     * 置入
     *
     * @param key
     * @param value
     * @param seconds
     */
    public void set(final String key, final Serializable value, int seconds);

    /**
     * 置入
     *
     * @param key
     * @param value
     */
    public void set(final String key, final Serializable value);

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public Boolean exists(final String key);

    /**
     * 删除
     *
     * @param key
     */
    public void del(final String key);

    /**
     * 删除全部
     *
     * @param pattern
     */
    public void delAll(final String pattern);

    /**
     * 类型
     *
     * @param key
     * @return
     */
    public String type(final String key);

    /**
     * 解释
     *
     * @param key
     * @param seconds
     * @return
     */
    public Boolean expire(final String key, final int seconds);

    /**
     * 解释
     *
     * @param key
     * @param unixTime
     * @return
     */
    public Boolean expireAt(final String key, final long unixTime);

    /**
     * @param key
     * @return
     */
    public Long ttl(final String key);

    /**
     * 获取并设置
     *
     * @param key
     * @param value
     * @return
     */
    public Object getSet(final String key, final Serializable value);

    /**
     * @param key
     * @param value
     * @return
     */
    public boolean setnx(final String key, final Serializable value);

    /**
     * 解锁
     *
     * @param key
     */
    public void unlock(String key);
}
