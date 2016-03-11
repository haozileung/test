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
public class CacheModule extends AbstractModule {

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        bind(CacheHelper.class).toInstance(new CacheHelper());
        bind(CacheManager.class).annotatedWith(Names.named("CacheOne")).to(EhCacheManager.class);
        bind(CacheManager.class).annotatedWith(Names.named("CacheTwo")).to(MemcacheManager.class);
        //bind(CacheManager.class).annotatedWith(Names.named("redisCache")).to(RedisCacheManager.class);
        // bind(CacheProvider.class).annotatedWith(Names.named("redisCacheProvider")).to(RedisCacheProvider.class);
        bind(CacheProvider.class).annotatedWith(Names.named("memcachedProvider")).to(MemcachedProvider.class);
        bind(CacheProvider.class).annotatedWith(Names.named("ehcacheProvider")).to(EhCacheProvider.class);
    }

}
