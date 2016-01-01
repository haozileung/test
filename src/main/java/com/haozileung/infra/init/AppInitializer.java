package com.haozileung.infra.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory
			.getLogger(AppInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//DataSourceUtil.init();
		//CacheHelper.init();
		logger.info("项目已启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//DataSourceUtil.destroy();
		//CacheHelper.destroy();
		logger.info("项目已停止...");
	}
}
