package com.haozileung.infra.cache.redis;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheException;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	private final static Logger log = LoggerFactory.getLogger(RedisCache.class);
	private final String region;

	public RedisCache(String region) {
		this.region = region;
	}

	/**
	 * 生成缓存的 key
	 *
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getKeyName(Object key) {

		if (key instanceof Number)
			return region + ":I:" + key;
		else {
			Class keyClass = key.getClass();
			if (String.class.equals(keyClass) || StringBuffer.class.equals(keyClass)
					|| StringBuilder.class.equals(keyClass))
				return region + ":S:" + key;
		}
		return region + ":O:" + key;
	}

	@Override
	public <T> T get(Object key) throws CacheException {
		T obj = null;
		Jedis cache = RedisCacheProvider.getResource();
		try {
			if (null == key)
				return null;
			byte[] b = cache.get(getKeyName(key).getBytes());
			if (b != null)
				obj = SerializationUtils.deserialize(b);
		} catch (Exception e) {
			log.error("Error occured when get data from L2 cache", e);
			if (e instanceof IOException || e instanceof NullPointerException)
				remove(key);
		} finally {
			cache.close();
		}
		return obj;
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		if (value == null)
			remove(key);
		else {
			Jedis cache = RedisCacheProvider.getResource();
			try {
				cache.set(getKeyName(key).getBytes(), SerializationUtils.serialize((Serializable) value));
			} catch (Exception e) {
				throw new CacheException(e);
			} finally {
				cache.close();
			}
		}
	}

	@Override
	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}

	@Override
	public void remove(Object key) throws CacheException {
		Jedis cache = RedisCacheProvider.getResource();
		try {
			cache.del(getKeyName(key));
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List keys() throws CacheException {
		Jedis cache = RedisCacheProvider.getResource();
		try {
			List<String> keys = new ArrayList<String>();
			keys.addAll(cache.keys(region + ":*"));
			for (int i = 0; i < keys.size(); i++) {
				keys.set(i, keys.get(i).substring(region.length() + 3));
			}
			return keys;
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@Override
	public void clear() throws CacheException {
		Jedis cache = RedisCacheProvider.getResource();
		try {
			cache.del(region + ":*");
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@Override
	public void destroy() throws CacheException {
		this.clear();
	}
}