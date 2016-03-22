package com.haozileung.infra.utils

import java.util.concurrent.ThreadPoolExecutor

/**
 * 线程执行工厂类
 */
class ThreadExecution {

    /**
     * 线程池对象
     */
    var threadPoolExecutor: ThreadPoolExecutor? = null

    /**
     * 添加线程执行任务，采用无界队列。 调用者添加线程任务完成之后，调用shutdown()方法关闭线程池

     * @param runnable
     */
    @Synchronized fun addTask(runnable: Runnable) {

        threadPoolExecutor!!.execute(runnable)
    }

    /**
     * 关闭线程池
     */
    fun shutdown() {
        if (threadPoolExecutor == null || threadPoolExecutor!!.isShutdown) {
            return
        }
        threadPoolExecutor!!.shutdown()
    }

}
