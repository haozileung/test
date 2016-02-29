package com.haozileung.web.init;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.CacheModule;
import com.haozileung.infra.dao.DaoModule;
import com.haozileung.infra.utils.ThreadExecution;
import com.haozileung.infra.utils.UtilModule;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory.getLogger(AppInitializer.class);

	private static Injector injector;

	public static Injector getInjector() {
		return injector;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		injector = Guice.createInjector(Stage.DEVELOPMENT, new UtilModule(), new CacheModule(), new DaoModule());
		logger.info("项目已启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		injector.getInstance(CacheHelper.class).close();
		injector.getInstance(ThreadExecution.class).shutdown();
		((DruidDataSource) injector.getInstance(DataSource.class)).close();
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
		logger.info("项目已停止...");
	}
}
