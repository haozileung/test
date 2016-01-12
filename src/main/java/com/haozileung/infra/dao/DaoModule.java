/**
 * 
 */
package com.haozileung.infra.dao;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.AbstractModule;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.SimpleSqlFactory;
import com.haozileung.infra.dal.SqlFactory;
import com.haozileung.infra.dal.handler.AnnotationNameHandler;
import com.haozileung.infra.dal.handler.NameHandler;

/**
 * @author liang
 *
 */
public class DaoModule extends AbstractModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(JdbcDao.class).to(JdbcDaoDbUtilsImpl.class);
		bind(NameHandler.class).to(AnnotationNameHandler.class);
		bind(QueryRunner.class).toInstance(new QueryRunner());
		bind(SqlFactory.class).to(SimpleSqlFactory.class);
		bind(DataSource.class).to(DruidDataSource.class);
		bind(DataSourceManager.class).toInstance(new DataSourceManager());
	}

}
