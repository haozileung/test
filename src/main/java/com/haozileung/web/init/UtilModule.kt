/**
 *
 */
package com.haozileung.web.init;

import com.google.inject.AbstractModule;
import com.haozileung.infra.utils.ThreadExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Efun
 */
class UtilModule : AbstractModule() {

    private final val logger: Logger = LoggerFactory.getLogger(UtilModule::class.java);

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

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    override fun configure() {
        val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(),
                ThreadPoolExecutor.AbortPolicy());
        val te: ThreadExecution = ThreadExecution();
        te.threadPoolExecutor = threadPoolExecutor;
        bind(ThreadExecution::class.java).toInstance(te);
        logger.debug("ThreadPoolExecutor inited...");
    }

}
