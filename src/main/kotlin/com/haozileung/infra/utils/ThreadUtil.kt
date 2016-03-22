package com.haozileung.infra.utils

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * 线程执行工厂类
 */
object ThreadUtil {
    /**
     * 核心线程数
     */
    private final val CORE_POOL_SIZE: Int = 1;

    /**
     * 最大线程数
     */
    private final val MAXIMUM_POOL_SIZE: Int = 5;

    /**
     * 线程生存时间，单位秒
     */
    private final val KEEP_ALIVE_TIME: Long = 10;

    /**
     * 线程池对象
     */
    val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(),
            ThreadPoolExecutor.AbortPolicy());
    /**
     * 添加线程执行任务，采用无界队列。 调用者添加线程任务完成之后，调用shutdown()方法关闭线程池

     * @param runnable
     */
    @Synchronized fun addTask(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    /**
     * 关闭线程池
     */
    fun shutdown() {
        if (threadPoolExecutor.isShutdown) {
            return
        }
        threadPoolExecutor.shutdown()
    }

}
