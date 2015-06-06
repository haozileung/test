package com.haozileung.web.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.utils.DataSourceUtils;
import com.haozileung.infra.utils.PropertiesUtils;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory
			.getLogger(AppInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PropertiesUtils.init();
		DataSourceUtils.init();
		CacheHelper.init();
		logger.info("项目已启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DataSourceUtils.destroy();
		PropertiesUtils.destroy();
		CacheHelper.destroy();
		logger.info("项目已停止...");
	}
}
