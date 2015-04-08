package com.haozileung.test.infra.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存助手
 * 
 * @author liudong
 */
public class CacheManager {

	private final static Logger logger = LoggerFactory
			.getLogger(CacheManager.class);
	private static CacheProvider provider;

	public static void init(String prv_name) {
		try {
			CacheManager.provider = (CacheProvider) Class.forName(prv_name)
					.newInstance();
		} catch (Exception e) {
			logger.error(
					"Unabled to initialize cache provider:{}, using ehcache default.",
					prv_name);
			CacheManager.provider = new EhCacheProvider();
		}
		CacheManager.provider.start();
	}

	public static void destroy() {
		if (CacheManager.provider != null) {
			CacheManager.provider.stop();
			CacheManager.provider = null;
		}
	}

	private final static Cache _GetCache(String cache_name) {
		if (provider == null) {
			provider = new EhCacheProvider();
		}
		return provider.buildCache(cache_name);
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	public final static Object get(String name, Serializable key) {
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
	@SuppressWarnings("unchecked")
	public final static <T> T get(Class<T> resultClass, String name,
			Serializable key) {
		if (name != null && key != null)
			return (T) _GetCache(name).get(key);
		return null;
	}

	/**
	 * 写入缓存
	 * 
	 * @param name
	 * @param key
	 * @param value
	 */
	public final static void set(String name, Serializable key,
			Serializable value) {
		if (name != null && key != null && value != null)
			_GetCache(name).put(key, value);
	}

	/**
	 * 清除缓冲中的某个数据
	 * 
	 * @param name
	 * @param key
	 */
	public final static void evict(String name, Serializable key) {
		if (name != null && key != null)
			_GetCache(name).remove(key);
	}

}
