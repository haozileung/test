package com.haozileung.web.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.utils.DataSourceUtil;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory
			.getLogger(AppInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("项目已启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DataSourceUtil.destroy();
		CacheHelper.destroy();
		logger.info("项目已停止...");
	}
}
