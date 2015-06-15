package com.haozileung.infra.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动缓存数据重加载
 */
public class CacheHelper {

	final static CacheUpdater g_ThreadPool = new CacheUpdater();
	private static final Logger logger = LoggerFactory
			.getLogger(CacheHelper.class);
	private static final String GLOBAL_CACHE = "global_cache";

	public static void init() {
		EhCacheManager.init();
		MemcacheManager.init();
	}

	public static void destroy() {
		EhCacheManager.destroy();
		MemcacheManager.destroy();
	}

	/**
	 * 获取缓存数据
	 *
	 * @param region
	 *            缓存分区
	 * @param key
	 *            缓存key
	 * @param invoker
	 *            回调方法
	 * @return 返回对应类型的数据
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(final String region, final Serializable key,
			final ICacheInvoker<T> invoker) {
		// 1. 从正常缓存中获取数据
		T data = (T) EhCacheManager.get(region, key);
		if (data == null) {
			logger.debug("在L1缓存中未找到内容！{} - {}", region, key);
			// 2. 从全局二级缓存中获取数据,执行自动更新数据策略，结果直接返回
			data = (T) MemcacheManager.get(GLOBAL_CACHE, key);
			if (invoker != null) {
				String thread_name = String.format("CacheUpdater-%s-%s",
						region, key);
				g_ThreadPool.execute(new Thread(thread_name) {
					@Override
					public void run() {
						Object result = invoker.callback();
						if (result != null) {
							EhCacheManager.set(region, key,
									(Serializable) result);
							MemcacheManager.set(GLOBAL_CACHE, key,
									(Serializable) result);
						}
					}
				});
			}
		}
		return data;
	}

	public static void update(final String region, final Serializable key) {
		EhCacheManager.evict(region, key);
	}

	public static void evict(final String region, final Serializable key) {
		EhCacheManager.evict(region, key);
		MemcacheManager.evict(region, key);
	}

	public static void updateNow(final String region, final Serializable key,
			final Serializable value) {
		MemcacheManager.set(region, key, value);
		EhCacheManager.set(region, key, value);
	}

	static class CacheUpdater extends ThreadPoolExecutor {

		private List<String> runningThreadNames = new Vector<>();

		public CacheUpdater() {
			super(0, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),
					new ThreadPoolExecutor.DiscardOldestPolicy());
		}

		public void execute(Thread command) {
			if (runningThreadNames.contains(command.getName())) {
				logger.warn("{} ===================> Running.",
						command.getName());
				return;
			}
			logger.debug("{} ===================> Started.", command.getName());
			super.execute(command);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			super.afterExecute(r, t);
			Thread thread = (Thread) r;
			runningThreadNames.remove(thread.getName());
			logger.debug("{} ===================> Finished.", thread.getName());
		}

	}
}
