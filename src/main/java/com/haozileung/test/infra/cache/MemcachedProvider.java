package com.haozileung.test.infra.cache;

import com.alibaba.fastjson.util.IOUtils;
import com.whalin.MemCached.SockIOPool;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MemcachedProvider implements CacheProvider {

    private static final Logger log = LoggerFactory
            .getLogger(MemcachedProvider.class);

    private final static String DEFAULT_REGION_NAME = "____DEFAULT_CACHE_REGION";
    private final static String CACHE_IDENT = "cache.";
    private final static String SERVERS_CONF = "servers";
    private static boolean started = true;
    private Hashtable<String, MemCache> cacheManager;
    private Properties _cache_properties = new Properties();

    private static int _GetSeconds(String str) {
        try {
            switch (str.charAt(str.length() - 1)) {
                case 's':
                    return Integer.parseInt(str.substring(0, str.length() - 1));
                case 'm':
                    return Integer.parseInt(str.substring(0, str.length() - 1)) * 60;
                case 'h':
                    return Integer.parseInt(str.substring(0, str.length() - 1)) * 3600;
                case 'd':
                    return Integer.parseInt(str.substring(0, str.length() - 1)) * 86400;
                default:
                    return Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            log.warn("Illegal configuration value : " + str, e);
        }
        return -1;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.cache.CacheProvider#start(java.util.Properties)
     */
    @SuppressWarnings({"rawtypes"})
    public void start() throws CacheException {
        String conf = "/memcached.properties";
        InputStream in = getClass().getResourceAsStream(conf);
        Properties memcached_conf = new Properties();
        try {
            memcached_conf.load(in);
        } catch (IOException e) {
            throw new CacheException("Unabled to load properties from " + conf,
                    e);
        } finally {
            IOUtils.close(in);
        }

        String servers = memcached_conf.getProperty(SERVERS_CONF);
        if (StringUtils.isBlank(servers)) {
            throw new CacheException(
                    "configuration 'memcached.servers' get a empty value");
        }
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers.split(","));

        Properties base_conf = (Properties) memcached_conf.clone();
        base_conf.remove(SERVERS_CONF);
        Iterator keys = base_conf.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (key.startsWith(CACHE_IDENT)) {
                _cache_properties.put(key.substring(CACHE_IDENT.length()),
                        base_conf.getProperty(key));
                keys.remove();
            }
        }
        Map<String, String> map = new HashMap<>();
        for (String key : _cache_properties.stringPropertyNames()) {
            map.put(key, _cache_properties.getProperty(key));
        }
        try {
            BeanUtils.populate(pool, map);
        } catch (Exception e) {
            throw new CacheException("Unabled to set properties to SockIOPool",
                    e);
        }
        try {
            pool.initialize();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            started = false;
        }

        cacheManager = new Hashtable<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.cache.CacheProvider#buildCache(java.lang.String,
     * java.util.Properties)
     */
    public MemCache buildCache(String name) throws CacheException {
        if (!started) {
            throw new CacheException("MemcachedCacheProvider Not Started...");
        }
        if (StringUtils.isEmpty(name)) {
            name = DEFAULT_REGION_NAME;
        }
        MemCache mCache = cacheManager.get(name);
        if (mCache == null) {
            String timeToLive = _cache_properties.getProperty(name);
            int secondToLive = -1;
            if (StringUtils.isNotBlank(timeToLive)) {
                timeToLive = timeToLive.toLowerCase().trim();
                secondToLive = _GetSeconds(timeToLive);
            }
            log.debug("Building cache named {} using secondToLive is {}", name, secondToLive);
            mCache = new MemCache(name, secondToLive);
            cacheManager.put(name, mCache);
        }
        return mCache;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.cache.CacheProvider#stop()
     */
    public void stop() {
        cacheManager.values().forEach(MemCache::clear);
        cacheManager = null;
    }

}
