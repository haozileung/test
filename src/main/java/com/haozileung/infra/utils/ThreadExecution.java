package com.haozileung.infra.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程执行工厂类
 * 
 */
public class ThreadExecution {

    /** 核心线程数 */
    private final int          CORE_POOL_SIZE    = 1;

    /** 最大线程数 */
    private final int          MAXIMUM_POOL_SIZE = 5;

    /** 线程生存时间，单位秒 */
    private final long         KEEP_ALIVE_TIME   = 10;

    /** 线程池对象 */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 添加线程执行任务，采用无界队列。 调用者添加线程任务完成之后，调用shutdown()方法关闭线程池
     * 
     * @param runnable
     */
    public synchronized void addTask(Runnable runnable) {

        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
        }
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            return;
        }
        threadPoolExecutor.shutdown();
    }

}