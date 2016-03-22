package com.haozileung.web.init;

import com.google.inject.Guice
import com.google.inject.Stage
import com.haozileung.infra.utils.ThreadUtil
import com.haozileung.infra.web.Initializer
import org.beetl.ext.servlet.ServletGroupTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@WebListener
class AppInitializer : ServletContextListener {
    private final val logger: Logger = LoggerFactory.getLogger(AppInitializer::class.java);
    override fun contextInitialized(sce: ServletContextEvent) {
        Initializer.instance = Guice.createInjector(Stage.PRODUCTION, DaoModule());
        logger.info("已启动...");
    }

    override fun contextDestroyed(sce: ServletContextEvent) {
        ThreadUtil.shutdown();
        ServletGroupTemplate.instance().groupTemplate.close();
        logger.info("已停止...");
    }
}
