package com.haozileung.web.init;

import com.google.inject.Guice;
import com.google.inject.Stage;
import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.CacheModule;
import com.haozileung.infra.dao.DaoModule;
import com.haozileung.infra.utils.ThreadExecution;
import com.haozileung.infra.utils.UtilModule;
import com.haozileung.infra.web.Initializer;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class AppInitializer extends Initializer implements ServletContextListener {

    private final static Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        injector = Guice.createInjector(Stage.PRODUCTION, new UtilModule(), new CacheModule(), new DaoModule());
        logger.info("已启动...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        injector.getInstance(CacheHelper.class).close();
        injector.getInstance(ThreadExecution.class).shutdown();
        injector.getInstance(DataSource.class).close();
        ServletGroupTemplate.instance().getGroupTemplate().close();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                logger.warn("Driver {} deregistered", d);
            } catch (SQLException ex) {
                logger.warn("Error deregistering driver {} Message: {}", d, ex.getMessage());
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            logger.warn("SEVERE problem cleaning up: {}", e.getMessage());
        }
        logger.info("已停止...");
    }
}
