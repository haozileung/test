//$Id: EhCacheProvider.java 9964 2006-05-30 15:40:54Z epbernard $
/**
 * Copyright 2003-2006 Greg Luck, Jboss Inc
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haozileung.infra.cache.ehcache;

import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.cache.CacheException;
import com.haozileung.infra.cache.CacheProvider;

import java.util.Hashtable;

/**
 * Cache Provider plugin
 * <p>
 * Taken from EhCache 1.3 distribution
 */
public class EhCacheProvider implements CacheProvider {

    private static final Logger log = LoggerFactory
            .getLogger(EhCacheProvider.class);

    private CacheManager manager;
    private Hashtable<String, EhCache> _cacheManager;

    /**
     * Builds a Cache.
     * <p>
     * Even though this method provides properties, they are not used.
     * Properties for EHCache are specified in the ehcache.xml file.
     * Configuration will be read from ehcache.xml for a cache declaration where
     * the name attribute matches the name parameter in this builder.
     *
     * @param name the name of the cache. Must match a cache configured in
     *             ehcache.xml
     * @return a newly built cache will be built and initialised
     * @throws CacheException inter alia, if a cache of the same name already exists
     */
    @Override
    public EhCache buildCache(String name) throws CacheException {
        EhCache ehcache = _cacheManager.get(name);
        if (ehcache != null)
            return ehcache;
        try {
            net.sf.ehcache.Cache cache = manager.getCache(name);
            if (cache == null) {
                log.warn("Could not find configuration [" + name
                        + "]; using defaults.");
                manager.addCache(name);
                cache = manager.getCache(name);
                log.debug("started EHCache region: " + name);
            }
            synchronized (_cacheManager) {
                ehcache = new EhCache(cache);
                _cacheManager.put(name, ehcache);
                return ehcache;
            }
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    /**
     * Callback to perform any necessary initialization of the underlying cache
     * implementation during SessionFactory construction.
     */
    @Override
    public void start() throws CacheException {
        if (manager != null) {
            log.warn("Attempt to restart an already started EhCacheProvider");
            return;
        }
        manager = CacheManager.create();
        _cacheManager = new Hashtable<>();
    }

    /**
     * Callback to perform any necessary cleanup of the underlying cache
     * implementation during SessionFactory.close().
     */
    @Override
    public void stop() {
        if (manager != null) {
            manager.shutdown();
            manager = null;
        }
    }

}