package com.haozileung.infra.cache.redis;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheManager;
import com.haozileung.infra.cache.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 缓存助手
 */
public class RedisCacheManager implements CacheManager {

    private final static Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    private CacheProvider provider;

    @Override
    @Inject
    public void init(@Named("redisCacheProvider") CacheProvider provider) {
        if (provider != null) {
            this.provider = provider;
            provider.start();
            logger.debug("RedisCacheManager started...");
        }
    }

    @Override
    public void destroy() {
        if (provider != null) {
            provider.stop();
            provider = null;
        }
        logger.debug("RedisCacheManager stopped...");
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
    @Override
    public final <T extends Serializable> T get(String name, String key) {
        if (name != null && key != null)
            return _GetCache(name).get(key);
        return null;
    }

    /**
     * 获取缓存中的数据
     *
     * @param <T extends Serializable>
     * @param resultClass
     * @param name
     * @param key
     * @return
     */
    @Override
    public final <T extends Serializable> T get(Class<T> resultClass, String name, String key) {
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
    @Override
    public final <T extends Serializable> void set(String name, String key, T value) {
        if (name != null && key != null && value != null)
            _GetCache(name).put(key, value);
    }

    /**
     * 清除缓冲中的某个数据
     *
     * @param name
     * @param key
     */
    @Override
    public final void evict(String name, String key) {
        if (name != null && key != null)
            _GetCache(name).remove(key);
    }

}
