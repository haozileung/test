/**
 * 
 */
package com.haozileung.infra.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.haozileung.infra.dal.ConnectionManager;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.SimpleSqlFactory;
import com.haozileung.infra.dal.SqlFactory;
import com.haozileung.infra.dal.TxManager;
import com.haozileung.infra.dal.handler.AnnotationNameHandler;
import com.haozileung.infra.dal.handler.NameHandler;

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
		initJNDIDataSource();
		bind(JdbcDao.class).to(JdbcDaoDbUtilsImpl.class);
		bind(NameHandler.class).to(AnnotationNameHandler.class);
		bind(QueryRunner.class).toInstance(new QueryRunner());
		bind(SqlFactory.class).to(SimpleSqlFactory.class);
		bind(ConnectionManager.class).toInstance(new ConnectionManager());
		bind(TxManager.class).toInstance(new TxManager());
	}

	public void initJNDIDataSource() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/test");
			logger.debug("Binding DataSouce to {}", ds);
			bind(DataSource.class).annotatedWith(Names.named("default")).toInstance(ds);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
