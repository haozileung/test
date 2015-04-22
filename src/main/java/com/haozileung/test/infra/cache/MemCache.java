package com.haozileung.test.infra.cache;

import java.util.Calendar;
import java.util.List;

import com.whalin.MemCached.MemCachedClient;

/**
 * MemCached
 */
public class MemCache implements Cache {

	private MemCachedClient mc;
	private int secondToLive;
	private int hash;

	/**
	 * Creates a new Hibernate pluggable cache based on a cache name.
	 */
	public MemCache(String name, int secondToLive) {
		mc = new MemCachedClient();
		this.secondToLive = secondToLive;
		this.hash = name.hashCode();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		return null;
	}

	/**
	 * Gets a value of an element which matches the given key.
	 *
	 * @param key
	 *            the key of the element to return.
	 * @return The value placed into the cache with an earlier put, or null if
	 *         not found or expired
	 * @throws CacheException
	 */
	@Override
	public Object get(Object key) throws CacheException {
		return (key != null) ? mc.get(String.valueOf(key), hash) : null;
	}

	/**
	 * Puts an object into the cache.
	 *
	 * @param key
	 *            a key
	 * @param value
	 *            a value
	 * @throws CacheException
	 *             if the {@link L1CacheManager} is shutdown or another
	 *             {@link Exception} occurs.
	 */
	@Override
	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}

	/**
	 * Puts an object into the cache.
	 *
	 * @param key
	 * @param value
	 * @throws CacheException
	 *             if the {@link L1CacheManager} is shutdown or another
	 *             {@link Exception} occurs.
	 */
	@Override
	public void put(Object key, Object value) throws CacheException {
		if (key == null) {
			return;
		}
		if (secondToLive <= 0) {
			mc.set(String.valueOf(key), value, hash);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, secondToLive);
			mc.set(String.valueOf(key), value, cal.getTime(), hash);
		}
	}

	/**
	 * Removes the element which matches the key.
	 * <p>
	 * If no element matches, nothing is removed and no Exception is thrown.
	 *
	 * @param key
	 *            the key of the element to remove
	 * @throws CacheException
	 */
	@Override
	public void remove(Object key) throws CacheException {
		if (key != null) {
			mc.delete(String.valueOf(key));
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
