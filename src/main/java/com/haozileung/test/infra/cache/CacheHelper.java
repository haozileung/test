package com.haozileung.test.infra.cache;

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
 * 
 */
public class CacheHelper {

	public static void init() {
		L1CacheManager.init();
		L2CacheManager.init();
	}

	public static void destroy() {
		L1CacheManager.destroy();
		L2CacheManager.destroy();
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CacheHelper.class);

	final static CacheUpdater g_ThreadPool = new CacheUpdater();

	static class CacheUpdater extends ThreadPoolExecutor {

		private List<String> runningThreadNames = new Vector<String>();

		public CacheUpdater() {
			super(0, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
					20), new ThreadPoolExecutor.DiscardOldestPolicy());
		}

		public void execute(Thread command) {
			if (runningThreadNames.contains(command.getName())) {
				logger.warn("{} ===================> Running.",
						command.getName());
				return;
			}
			logger.info("{} ===================> Started.", command.getName());
			super.execute(command);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			super.afterExecute(r, t);
			Thread thread = (Thread) r;
			runningThreadNames.remove(thread.getName());
			logger.info("{} ===================> Finished.", thread.getName());
		}

	}

	private static final String GLOBAL_CACHE = "global_cache";

	/**
	 * 获取缓存数据
	 * 
	 * @param region
	 * @param key
	 * @param invoker
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(final String region, final Serializable key,
			final ICacheInvoker<T> invoker) {
		// 1. 从正常缓存中获取数据
		T data = (T) L1CacheManager.get(region, key);
		if (data == null) {
			logger.debug("在L1缓存中未找到内容！{} - {}", region, key);
			// 2. 从全局二级缓存中获取数据
			data = (T) L2CacheManager.get(GLOBAL_CACHE, key);
			if (data == null) {// 2.1 取不到为第一次运行
				logger.debug("在L2缓存中未找到内容！{} - {}", region, key);
				if (invoker != null) {
					data = invoker.callback();
					if (data != null) {
						L1CacheManager.set(region, key, (Serializable) data);
						L2CacheManager.set(GLOBAL_CACHE, key,
								(Serializable) data);
					}
				}
			} else if (invoker != null) {// 2.2 如果取到了则执行自动更新数据策略
				String thread_name = "CacheUpdater-" + region + " - " + key;
				g_ThreadPool.execute(new Thread(thread_name) {
					public void run() {
						Object result = invoker.callback();
						if (result != null) {
							L1CacheManager.set(region, key,
									(Serializable) result);
							L2CacheManager.set(GLOBAL_CACHE, key,
									(Serializable) result);
						}
					}
				});
			}
		}
		return data;
	}

	public static void update(final String region, final Serializable key) {
		L1CacheManager.evict(region, key);
	}

	public static void evict(final String region, final Serializable key) {
		L1CacheManager.evict(region, key);
		L2CacheManager.evict(region, key);
	}

	public static void updateNow(final String region, final Serializable key,
			final Serializable value) {
		L2CacheManager.set(region, key, value);
		L1CacheManager.set(region, key, value);
	}
}
