package com.haozileung.infra.cache.redis;

import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheException;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RedisCache implements Cache {

    private final static Logger log = LoggerFactory.getLogger(RedisCache.class);
    private final RedisCacheProvider provider;

    private String region;

    public RedisCache(RedisCacheProvider provider, String region) {
        this.provider = provider;
        this.region = region;
    }

    /**
     * 生成缓存的 key
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getKeyName(String key) {
        return region + ":" + key;
    }

    @Override
    public <T extends Serializable> T get(String key) throws CacheException {
        T obj = null;
        Jedis cache = provider.getResource();
        try {
            if (null == key)
                return null;
            byte[] b = cache.get(getKeyName(key).getBytes());
            if (b != null)
                obj = SerializationUtils.deserialize(b);
        } catch (Exception e) {
            log.error("Error when get data from redis cache", e);
            if (e instanceof IOException || e instanceof NullPointerException)
                remove(key);
        } finally {
            cache.close();
        }
        return obj;
    }

    @Override
    public <T extends Serializable> void put(String key, T value) throws CacheException {
        if (value == null)
            remove(key);
        else {
            Jedis cache = provider.getResource();
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
    public <T extends Serializable> void update(String key, T value) throws CacheException {
        put(key, value);
    }

    @Override
    public void remove(String key) throws CacheException {
        Jedis cache = provider.getResource();
        try {
            cache.del(getKeyName(key));
        } catch (Exception e) {
            throw new CacheException(e);
        } finally {
            cache.close();
        }
    }

    @Override
    public List<String> keys() throws CacheException {
        Jedis cache = provider.getResource();
        try {
            List<String> keys = new ArrayList<String>();
            keys.addAll(cache.keys(region + ":*"));
            for (int i = 0; i < keys.size(); i++) {
                keys.set(i, keys.get(i).substring(region.length() + 1));
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
        Jedis cache = provider.getResource();
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