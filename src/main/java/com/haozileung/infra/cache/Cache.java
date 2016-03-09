package com.haozileung.infra.cache;

import java.util.List;

/**
 * Implementors define a caching algorithm. All implementors <b>must</b> be
 * threadsafe.
 */
public interface Cache {

    /**
     * Get an item from the cache, nontransactionally
     *
     * @param key
     * @return the cached object or <tt>null</tt>
     * @throws CacheException
     */
    <T> T get(String key) throws CacheException;

    /**
     * Add an item to the cache, nontransactionally, with failfast semantics
     *
     * @param key
     * @param value
     * @throws CacheException
     */
    <T> void put(String key, T value) throws CacheException;

    /**
     * Add an item to the cache
     *
     * @param key
     * @param value
     * @throws CacheException
     */
    void update(String key, Object value) throws CacheException;

    List<String> keys() throws CacheException;

    /**
     * Remove an item from the cache
     */
    void remove(String key) throws CacheException;

    /**
     * Clear the cache
     */
    void clear() throws CacheException;

    /**
     * Clean up
     */
    void destroy() throws CacheException;

}
