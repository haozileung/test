package com.haozileung.infra.cache;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	private final static Logger log = LoggerFactory.getLogger(RedisCache.class);
	private String region;

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
	public Object get(Object key) throws CacheException {
		Object obj = null;
		boolean broken = false;
		Jedis cache = RedisCacheProvider.getResource();
		try {
			if (null == key)
				return null;
			byte[] b = cache.get(getKeyName(key).getBytes());
			if (b != null)
				obj = SerializationUtils.deserialize(b);
		} catch (Exception e) {
			log.error("Error occured when get data from L2 cache", e);
			broken = true;
			if (e instanceof IOException || e instanceof NullPointerException)
				remove(key);
		} finally {
			RedisCacheProvider.returnResource(cache, broken);
		}
		return obj;
	}

	@Override
	public void put(Object key, Serializable value) throws CacheException {
		if (value == null)
			remove(key);
		else {
			boolean broken = false;
			Jedis cache = RedisCacheProvider.getResource();
			try {
				cache.set(getKeyName(key).getBytes(), SerializationUtils.serialize(value));
			} catch (Exception e) {
				broken = true;
				throw new CacheException(e);
			} finally {
				RedisCacheProvider.returnResource(cache, broken);
			}
		}
	}

	@Override
	public void update(Object key, Serializable value) throws CacheException {
		put(key, value);
	}

	@Override
	public void remove(Object key) throws CacheException {
		boolean broken = false;
		Jedis cache = RedisCacheProvider.getResource();
		try {
			cache.del(getKeyName(key));
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(cache, broken);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		Jedis cache = RedisCacheProvider.getResource();
		boolean broken = false;
		try {
			List<String> keys = new ArrayList<String>();
			keys.addAll(cache.keys(region + ":*"));
			for (int i = 0; i < keys.size(); i++) {
				keys.set(i, keys.get(i).substring(region.length() + 3));
			}
			return keys;
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(cache, broken);
		}
	}

	@Override
	public void clear() throws CacheException {
		Jedis cache = RedisCacheProvider.getResource();
		boolean broken = false;
		try {
			cache.del(region + ":*");
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(cache, broken);
		}
	}

	@Override
	public void destroy() throws CacheException {
		this.clear();
	}
}