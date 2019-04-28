package com.microcore.jcf.util.redis;

import com.microcore.jcf.util.SpringHelper;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 *
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:17:22
 */
public final class RedisHelper implements CacheManager {

    private RedisTemplate<Serializable, Serializable> redisTemplate = null;
    private Integer expire = 6000;

    // 获取连接
    @SuppressWarnings("unchecked")
    private RedisTemplate<Serializable, Serializable> getRedis() {
        synchronized (RedisHelper.class) {
            if (redisTemplate == null) {
                redisTemplate = SpringHelper.getBean("redisTemplate", RedisTemplate.class);
            }
        }
        return redisTemplate;
    }

    @Override
    public final Object get(final String key) {
        expire(key, expire);
        return getRedis().boundValueOps(key).get();
    }

    @Override
    public final Set<Object> getAll(final String pattern) {
        Set<Object> values = new HashSet<>();
        Set<Serializable> keys = getRedis().keys(pattern);
        for (Serializable key : keys) {
            expire(key.toString(), expire);
            values.add(getRedis().opsForValue().get(key));
        }
        return values;
    }

    @Override
    public final void set(final String key, final Serializable value, int seconds) {
        getRedis().boundValueOps(key).set(value);
        expire(key, seconds);
    }

    @Override
    public final void set(final String key, final Serializable value) {
        getRedis().boundValueOps(key).set(value);
        expire(key, expire);
    }

    @Override
    public final Boolean exists(final String key) {
        return getRedis().hasKey(key);
    }

    @Override
    public final void del(final String key) {
        getRedis().delete(key);
    }

    @Override
    public final void delAll(final String pattern) {
        getRedis().delete(getRedis().keys(pattern));
    }

    @Override
    public final String type(final String key) {
        expire(key, expire);
        return getRedis().type(key).getClass().getName();
    }

    /**
     * 在某段时间后失效
     *
     * @return
     */
    @Override
    public final Boolean expire(final String key, final int seconds) {
        return getRedis().expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime
     * @return
     */
    @Override
    public final Boolean expireAt(final String key, final long unixTime) {
        return getRedis().expireAt(key, new Date(unixTime));
    }

    @Override
    public final Long ttl(final String key) {
        return getRedis().getExpire(key, TimeUnit.SECONDS);
    }

    public final void setrange(final String key, final long offset, final String value) {
        expire(key, expire);
        getRedis().boundValueOps(key).set(value, offset);
    }

    public final String getrange(final String key, final long startOffset, final long endOffset) {
        expire(key, expire);
        return getRedis().boundValueOps(key).get(startOffset, endOffset);
    }

    @Override
    public final Object getSet(final String key, final Serializable value) {
        expire(key, expire);
        return getRedis().boundValueOps(key).getAndSet(value);
    }

    @Override
    public boolean setnx(String key, Serializable value) {
        getRedis().boundValueOps(key).setIfAbsent(value);
        return false;
    }

    @Override
    public void unlock(String key) {
        del(key);
    }

    // 未完，待续...
}
