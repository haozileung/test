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
				logger.warn(command.getName()
						+ " ===================> Running.");
				return;
			}
			logger.info(command.getName() + " ===================> Started.");
			super.execute(command);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			super.afterExecute(r, t);
			Thread thread = (Thread) r;
			runningThreadNames.remove(thread.getName());
			logger.info(thread.getName() + " ===================> Finished.");
		}

	}

	private final static String CACHE_GLOBAL = "icache-global";

	/**
	 * 获取缓存数据
	 * 
	 * @param region
	 * @param key
	 * @param invoker
	 * @param args
	 * @return
	 */
	public static Object get(final String region, final Serializable key,
			final ICacheInvoker invoker, final Object... args) {
		// 1. 从正常缓存中获取数据
		Object data = CacheManager.get(region, key);
		if (data == null) {
			final String global_key = key + "@" + region;
			// 2. 从全局二级缓存中获取数据
			data = CacheManager.get(CACHE_GLOBAL, global_key);
			if (data == null) {// 2.1 取不到为第一次运行
				if (invoker != null) {
					data = invoker.callback(args);
					if (data != null) {
						CacheManager.set(region, key, (Serializable) data);
						CacheManager.set(CACHE_GLOBAL, global_key,
								(Serializable) data);
					}
				}
			} else if (invoker != null) {// 2.2 如果取到了则执行自动更新数据策略
				String thread_name = "CacheUpdater-" + region + "-" + key;
				g_ThreadPool.execute(new Thread(thread_name) {
					public void run() {
						Object result = invoker.callback(args);
						if (result != null) {
							CacheManager
									.set(region, key, (Serializable) result);
							CacheManager.set(CACHE_GLOBAL, global_key,
									(Serializable) result);
						}
					}
				});
			}
		}
		return data;
	}

}
