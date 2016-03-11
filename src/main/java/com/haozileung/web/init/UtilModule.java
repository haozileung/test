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
public class UtilModule extends AbstractModule {

    private final static Logger logger = LoggerFactory.getLogger(UtilModule.class);

    /**
     * 核心线程数
     */
    private final int CORE_POOL_SIZE = 1;

    /**
     * 最大线程数
     */
    private final int MAXIMUM_POOL_SIZE = 5;

    /**
     * 线程生存时间，单位秒
     */
    private final long KEEP_ALIVE_TIME = 10;

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
        ThreadExecution te = new ThreadExecution();
        te.setThreadPoolExecutor(threadPoolExecutor);
        bind(ThreadExecution.class).toInstance(te);
        logger.debug("ThreadPoolExecutor inited...");
    }

}
