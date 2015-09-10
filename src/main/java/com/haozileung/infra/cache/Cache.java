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
	Object get(Object key) throws CacheException;

	/**
	 * Add an item to the cache, nontransactionally, with failfast semantics
	 *
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	void put(Object key, Object value) throws CacheException;

	/**
	 * Add an item to the cache
	 *
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	void update(Object key, Object value) throws CacheException;

	@SuppressWarnings("rawtypes")
	List keys() throws CacheException;

	/**
	 * Remove an item from the cache
	 */
	void remove(Object key) throws CacheException;

	/**
	 * Clear the cache
	 */
	void clear() throws CacheException;

	/**
	 * Clean up
	 */
	void destroy() throws CacheException;

}
