/**
 * 
 */
package com.haozileung.infra.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.AbstractModule;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.SimpleSqlFactory;
import com.haozileung.infra.dal.SqlFactory;
import com.haozileung.infra.dal.handler.AnnotationNameHandler;
import com.haozileung.infra.dal.handler.NameHandler;
import com.haozileung.infra.utils.PropertiesUtil;

/**
 * @author liang
 *
 */
public class DaoModule extends AbstractModule {
	private static final Logger logger = LoggerFactory.getLogger(DaoModule.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		DruidDataSource ds = new DruidDataSource();
		ds.setUrl(PropertiesUtil.getProperties().getProperty("db.url"));
		ds.setUsername(PropertiesUtil.getProperties().getProperty("db.username"));
		ds.setPassword(PropertiesUtil.getProperties().getProperty("db.password"));
		ds.setInitialSize(1);
		ds.setMinIdle(1);
		ds.setMaxActive(20);
		ds.setMaxWait(6000);
		ds.setTimeBetweenEvictionRunsMillis(6000);
		ds.setMinEvictableIdleTimeMillis(30000);
		ds.setValidationQuery("SELECT 'x'");
		ds.setTestWhileIdle(true);
		ds.setTestOnBorrow(false);
		ds.setTestOnReturn(false);
		ds.setUseGlobalDataSourceStat(true);
		try {
			ds.setFilters("stat,wall");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			ds.init();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		bind(JdbcDao.class).to(JdbcDaoDbUtilsImpl.class);
		bind(NameHandler.class).to(AnnotationNameHandler.class);
		bind(QueryRunner.class).toInstance(new QueryRunner(ds));
		bind(SqlFactory.class).to(SimpleSqlFactory.class);
		bind(DataSource.class).toInstance(ds);
	}

}
