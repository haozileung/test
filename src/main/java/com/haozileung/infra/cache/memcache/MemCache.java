package com.haozileung.infra.cache.memcache;

import com.google.common.base.Strings;
import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheException;
import com.haozileung.infra.cache.ehcache.EhCacheManager;
import com.whalin.MemCached.MemCachedClient;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * MemCached
 */
public class MemCache implements Cache {

    private final int secondToLive;
    private final int hash;
    private MemCachedClient mc;

    /**
     * Creates a new Hibernate pluggable cache based on a cache name.
     */
    public MemCache(String name, int secondToLive) {
        mc = new MemCachedClient();
        this.secondToLive = secondToLive;
        this.hash = name.hashCode();
    }

    @Override
    public List<String> keys() throws CacheException {
        return null;
    }

    /**
     * Gets a value of an element which matches the given key.
     *
     * @param key the key of the element to return.
     * @return The value placed into the cache with an earlier put, or null if
     * not found or expired
     * @throws CacheException
     */
    @Override
    public <T extends Serializable> T get(String key) throws CacheException {
        return !Strings.isNullOrEmpty(key) ? (T) mc.get(key, hash) : null;
    }

    /**
     * Puts an object into the cache.
     *
     * @param key   a key
     * @param value a value
     * @throws CacheException if the {@link EhCacheManager} is shutdown or another
     *                        {@link Exception} occurs.
     */
    @Override
    public <T extends Serializable> void update(String key, T value) throws CacheException {
        put(key, value);
    }

    /**
     *
     * @param key
     * @param value
     * @param <T>
     * @throws CacheException
     */
    @Override
    public <T extends Serializable> void put(String key, T value) throws CacheException {
        if (Strings.isNullOrEmpty(key)) {
            return;
        }
        if (secondToLive <= 0) {
            mc.set(key, value, hash);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, secondToLive);
            mc.set(key, value, cal.getTime(), hash);
        }
    }

    /**
     * Removes the element which matches the key.
     * <p/>
     * If no element matches, nothing is removed and no Exception is thrown.
     *
     * @param key the key of the element to remove
     * @throws CacheException
     */
    @Override
    public void remove(String key) throws CacheException {
        if (!Strings.isNullOrEmpty(key)) {
            mc.delete(key);
        }
    }

    /**
     * Remove all elements in the cache, but leave the cache in a useable state.
     *
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        mc.flushAll();
    }

    /**
     * Remove the cache and make it unuseable.
     *
     * @throws CacheException
     */
    @Override
    public void destroy() throws CacheException {
        this.clear();
    }
}
