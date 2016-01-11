package com.haozileung.infra.cache.memcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheManager;
import com.haozileung.infra.cache.CacheProvider;

/**
 * 缓存助手
 */
public class MemcacheManager implements CacheManager {

	private final static Logger logger = LoggerFactory.getLogger(MemcacheManager.class);
	private CacheProvider provider;

	public void init() {
		if (provider == null) {
			provider = new MemcachedProvider();
			provider.start();
			logger.info("MemcacheManager started...");
		}
	}

	public void destroy() {
		if (provider != null) {
			provider.stop();
			provider = null;
			logger.info("MemcacheManager stopped...");
		}
		
	}

	private final Cache _GetCache(String cache_name) {
		return provider.buildCache(cache_name);
	}

	/**
	 * 获取缓存中的数据
	 *
	 * @param name
	 * @param key
	 * @return
	 */
	public final <T> T get(String name, String key) {
		if (name != null && key != null)
			return _GetCache(name).get(key);
		return null;
	}

	/**
	 * 获取缓存中的数据
	 *
	 * @param <T>
	 * @param resultClass
	 * @param name
	 * @param key
	 * @return
	 */
	public final <T> T get(Class<T> resultClass, String name, String key) {
		if (name != null && key != null)
			return _GetCache(name).get(key);
		return null;
	}

	/**
	 * 写入缓存
	 *
	 * @param name
	 * @param key
	 * @param value
	 */
	public final <T> void set(String name, String key, T value) {
		if (name != null && key != null && value != null)
			_GetCache(name).put(key, value);
	}

	/**
	 * 清除缓冲中的某个数据
	 *
	 * @param name
	 * @param key
	 */
	public final void evict(String name, String key) {
		if (name != null && key != null)
			_GetCache(name).remove(key);
	}

}