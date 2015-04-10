package com.haozileung.test.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.test.infra.DataSourceProvider;
import com.haozileung.test.infra.PropertiesProvider;
import com.haozileung.test.infra.cache.CacheHelper;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory
			.getLogger(AppInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PropertiesProvider.init();
		DataSourceProvider.init();
		CacheHelper.init();
		logger.info("项目已启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DataSourceProvider.destroy();
		PropertiesProvider.destroy();
		CacheHelper.destroy();
		logger.info("项目已停止...");
	}
}
