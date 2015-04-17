package com.haozileung.test.infra.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 缓存助手
 */
public class L2CacheManager {

    private final static Logger logger = LoggerFactory
            .getLogger(L2CacheManager.class);
    private static CacheProvider provider;

    public static void init() {
        provider = new MemcachedProvider();
        provider.start();
        logger.info("L2 Cache started...");
    }

    public static void destroy() {
        if (provider != null) {
            provider.stop();
            provider = null;
        }
        logger.info("L2 Cache stopped...");
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
