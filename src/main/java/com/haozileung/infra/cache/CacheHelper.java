package com.haozileung.infra.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * 自动缓存数据重加载
 */
public class CacheHelper {

	private final CacheUpdater threadPool = new CacheUpdater();
	private static final Logger logger = LoggerFactory.getLogger(CacheHelper.class);

	@Inject
	@Named("ecache")
	private CacheManager l1cache;
	@Inject
	@Named("redisCache")
	private CacheManager l2cache;

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
					threadPool.execute(new Thread(thread_name) {
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

	class CacheUpdater extends ThreadPoolExecutor {

		private List<String> runningThreadNames = new Vector<>();

		public CacheUpdater() {
			super(2, 20, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),
					new ThreadPoolExecutor.DiscardOldestPolicy());
		}

		public void execute(Thread command) {
			if (runningThreadNames.contains(command.getName())) {
				logger.warn("{} ===================> Running.", command.getName());
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
	
	public void close(){
		l1cache.destroy();
		l2cache.destroy();
		threadPool.shutdown();
	}
}
