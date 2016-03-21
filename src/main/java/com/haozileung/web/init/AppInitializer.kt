package com.haozileung.web.init;

import com.google.inject.Guice
import com.google.inject.Stage
import com.haozileung.infra.cache.CacheHelper
import com.haozileung.infra.utils.ThreadExecution
import com.haozileung.infra.web.Initializer
import com.mysql.jdbc.AbandonedConnectionCleanupThread
import org.apache.tomcat.jdbc.pool.DataSource
import org.beetl.ext.servlet.ServletGroupTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Driver
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@WebListener
class AppInitializer : ServletContextListener {
    private final val logger: Logger = LoggerFactory.getLogger(AppInitializer::class.java);
    override fun contextInitialized(sce: ServletContextEvent) {
        Initializer.instance = Guice.createInjector(Stage.PRODUCTION, UtilModule(), CacheModule(), DaoModule());
        logger.info("已启动...");
    }

    override fun contextDestroyed(sce: ServletContextEvent) {
        Initializer.instance?.getInstance(CacheHelper::class.java)?.close();
        Initializer.instance?.getInstance(ThreadExecution::class.java)?.shutdown();
        Initializer.instance?.getInstance(DataSource::class.java)?.close();
        ServletGroupTemplate.instance().getGroupTemplate().close();
        var drivers: Enumeration<Driver> = DriverManager.getDrivers();
        var d: Driver? = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                logger.warn("Driver {} deregistered", d);
            } catch (ex: SQLException) {
                logger.warn("Error deregistering driver {} Message: {}", d, ex.message);
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (e: InterruptedException) {
            logger.warn("SEVERE problem cleaning up: {}", e.message);
        }
        logger.info("已停止...");
    }
}
