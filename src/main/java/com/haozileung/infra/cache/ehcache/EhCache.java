//$Id: EhCache.java 10716 2006-11-03 19:05:11Z max.andersen@jboss.com $
/**
 * Copyright 2003-2006 Greg Luck, Jboss Inc
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haozileung.infra.cache.ehcache;

import com.google.common.base.Strings;
import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.util.List;

/**
 * EHCache
 */
public class EhCache implements Cache {

    private net.sf.ehcache.Cache cache;

    /**
     * @param cache
     */
    public EhCache(net.sf.ehcache.Cache cache) {
        this.cache = cache;
    }

    @Override
    public List<String> keys() throws CacheException {
        return this.cache.getKeys();
    }

    /**
     * @param key
     * @param <T>
     * @return
     * @throws CacheException
     */
    @Override
    public <T extends Serializable> T get(String key) throws CacheException {
        try {
            if (Strings.isNullOrEmpty(key)) {
                return null;
            } else {
                Element element = cache.get(key);
                if (element != null)
                    return (T) element.getObjectValue();
            }
            return null;
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    /**
     * Puts an object into the cache.
     *
     * @param key   a key
     * @param value a value
     * @throws CacheException if the {@link CacheManager} is shutdown or another
     *                        {@link Exception} occurs.
     */
    @Override
    public <T extends Serializable> void update(String key, T value) throws CacheException {
        put(key, value);
    }

    /**
     * Puts an object into the cache.
     *
     * @param key   a key
     * @param value a value
     * @throws CacheException if the {@link CacheManager} is shutdown or another
     *                        {@link Exception} occurs.
     */
    @Override
    public <T extends Serializable> void put(String key, T value) throws CacheException {
        try {
            Element element = new Element(key, value);
            cache.put(element);
        } catch (IllegalArgumentException e) {
            throw new CacheException(e);
        } catch (IllegalStateException e) {
            throw new CacheException(e);
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }

    }

    /**
     * Removes the element which matches the key.
     * <p/>
     * If no element matches, nothing is removed and no Exception is thrown.
     *
     * @param key the key of the element to remove
     * @throws CacheException
     */
    @Override
    public void remove(String key) throws CacheException {
        try {
            cache.remove(key);
        } catch (IllegalStateException e) {
            throw new CacheException(e);
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    /**
     * Remove all elements in the cache, but leave the cache in a useable state.
     *
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        try {
            cache.removeAll();
        } catch (IllegalStateException e) {
            throw new CacheException(e);
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    /**
     * Remove the cache and make it unuseable.
     *
     * @throws CacheException
     */
    @Override
    public void destroy() throws CacheException {
        try {
            cache.getCacheManager().removeCache(cache.getName());
        } catch (IllegalStateException e) {
            throw new CacheException(e);
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

}