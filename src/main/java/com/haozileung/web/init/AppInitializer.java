package com.haozileung.web.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

@WebListener
public class AppInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory.getLogger(AppInitializer.class);

	private static Injector injector;

	public static Injector getInjector() {
		return injector;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		injector = Guice.createInjector(Stage.PRODUCTION);
		logger.info("项目已启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("项目已停止...");
	}
}
