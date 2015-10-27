package com.haozileung.infra.init;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.utils.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory
			.getLogger(AppInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("项目已启动...");
		DataSourceUtil.init();
		CacheHelper.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DataSourceUtil.destroy();
		CacheHelper.destroy();
		logger.info("项目已停止...");
	}
}
