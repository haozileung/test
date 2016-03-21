/**
 *
 */
package com.haozileung.web.init;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.CacheManager;
import com.haozileung.infra.cache.CacheProvider;
import com.haozileung.infra.cache.ehcache.EhCacheManager;
import com.haozileung.infra.cache.ehcache.EhCacheProvider;
import com.haozileung.infra.cache.memcache.MemcacheManager;
import com.haozileung.infra.cache.memcache.MemcachedProvider;

/**
 * @author liang
 */
class CacheModule : AbstractModule() {

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    override fun configure() {
        bind(CacheHelper::class.java).toInstance(CacheHelper());
        bind(CacheManager::class.java).annotatedWith(Names.named("CacheOne")).to(EhCacheManager::class.java);
        bind(CacheManager::class.java).annotatedWith(Names.named("CacheTwo")).to(MemcacheManager::class.java);
        //bind(CacheManager::class.java).annotatedWith(Names.named("redisCache")).to(RedisCacheManager::class.java);
        // bind(CacheProvider::class.java).annotatedWith(Names.named("redisCacheProvider")).to(RedisCacheProvider::class.java);
        bind(CacheProvider::class.java).annotatedWith(Names.named("memcachedProvider")).to(MemcachedProvider::class.java);
        bind(CacheProvider::class.java).annotatedWith(Names.named("ehcacheProvider")).to(EhCacheProvider::class.java);
    }

}
