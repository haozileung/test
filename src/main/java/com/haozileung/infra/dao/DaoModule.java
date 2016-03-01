/**
 * 
 */
package com.haozileung.infra.dao;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.haozileung.infra.dal.ConnectionManager;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.SimpleSqlFactory;
import com.haozileung.infra.dal.SqlFactory;
import com.haozileung.infra.dal.TxManager;
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
		initDataSource();
		bind(JdbcDao.class).to(JdbcDaoDbUtilsImpl.class);
		bind(NameHandler.class).to(AnnotationNameHandler.class);
		bind(QueryRunner.class).toInstance(new QueryRunner());
		bind(SqlFactory.class).to(SimpleSqlFactory.class);
		bind(ConnectionManager.class).toInstance(new ConnectionManager());
		bind(TxManager.class).toInstance(new TxManager());
	}

	@SuppressWarnings("unchecked")
	public void initDataSource() {
		Pattern pattern = Pattern.compile("^db\\.(\\w+)\\.(url|username|password)$");
		Map<String, Map<String, String>> mapDataSource = new HashMap<String, Map<String, String>>();
		Enumeration<String> props = (Enumeration<String>) PropertiesUtil.getProperties().propertyNames();
		while (props.hasMoreElements()) {
			String keyProp = props.nextElement();
			Matcher matcher = pattern.matcher(keyProp);
			if (matcher.find()) {
				String dsName = matcher.group(1);
				String dsPropName = matcher.group(2);
				Map<String, String> ds;
				if (mapDataSource.containsKey(dsName)) {
					ds = mapDataSource.get(dsName);
				} else {
					ds = new HashMap<String, String>();
				}
				ds.put(dsPropName, PropertiesUtil.getProperties().getProperty(keyProp));
				mapDataSource.put(dsName, ds);
			}
		}
		for (Entry<String, Map<String, String>> m : mapDataSource.entrySet()) {
			DruidDataSource ds = new DruidDataSource();
			ds.setUrl(m.getValue().get("url"));
			ds.setUsername(m.getValue().get("username"));
			ds.setPassword(m.getValue().get("password"));
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
			bind(DataSource.class).annotatedWith(Names.named(m.getKey())).toInstance(ds);
		}
	}
}
