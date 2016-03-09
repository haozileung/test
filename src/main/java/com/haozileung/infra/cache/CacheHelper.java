package com.haozileung.infra.cache;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.haozileung.infra.utils.ThreadExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 自动缓存数据重加载
 */
public class CacheHelper {

    private static final Logger logger = LoggerFactory.getLogger(CacheHelper.class);

    @Inject
    @Named("ehcache")
    private CacheManager l1cache;
    @Inject
    @Named("memcached")
    private CacheManager l2cache;
    @Inject
    private ThreadExecution excutor;

    /**
     * 获取缓存数据
     *
     * @param region  缓存分区
     * @param key     缓存key
     * @param invoker 回调方法
     * @return 返回对应类型的数据
     */
    public <T> T get(final String region, final String key, final ICacheInvoker<T> invoker) {
        // 1. 从正常缓存中获取数据
        T data = l1cache.get(region, key);
        if (data == null) {
            logger.debug("在L1缓存中未找到内容！{} - {}", region, key);
            // 2. 从全局二级缓存中获取数据,执行自动更新数据策略，结果直接返回
            data = l2cache.get(region, key);
            if (invoker != null) {
                if (data == null) {
                    logger.debug("在L2缓存中未找到内容！{} - {}", region, key);
                    data = invoker.callback();
                    l1cache.set(region, key, data);
                    l2cache.set(region, key, data);
                } else {
                    logger.debug("执行自动更新数据策略{} - {}", region, key);
                    String thread_name = String.format("CacheUpdater-%s-%s", region, key);
                    excutor.addTask(new Thread(thread_name) {
                        @Override
                        public void run() {
                            Object result = invoker.callback();
                            if (result != null) {
                                l1cache.set(region, key, result);
                                l2cache.set(region, key, result);
                            }
                        }
                    });
                }
            }
        }
        return data;
    }

    public void update(final String region, final String key) {
        l2cache.evict(region, key);
    }

    public void evict(final String region, final String key) {
        l1cache.evict(region, key);
        l2cache.evict(region, String.valueOf(key));
    }

    public void updateNow(final String region, final String key, final Serializable value) {
        l1cache.set(region, String.valueOf(key), value);
        l2cache.set(region, key, value);
    }

    public void close() {
        l1cache.destroy();
        l2cache.destroy();
    }
}
