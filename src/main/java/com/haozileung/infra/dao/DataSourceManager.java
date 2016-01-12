package com.haozileung.infra.dao;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.Inject;
import com.haozileung.infra.utils.PropertiesUtil;

public class DataSourceManager {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
	private final ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	private DruidDataSource druidDataSource;

	private boolean show_sql = true;

	@Inject
	public void init(DruidDataSource datasource) {
		if (null == druidDataSource) {
			datasource.setUrl(PropertiesUtil.getProperties().getProperty("db.url"));
			datasource.setUsername(PropertiesUtil.getProperties().getProperty("db.username"));
			datasource.setPassword(PropertiesUtil.getProperties().getProperty("db.password"));
			datasource.setInitialSize(1);
			datasource.setMinIdle(1);
			datasource.setMaxActive(20);
			datasource.setMaxWait(6000);
			datasource.setTimeBetweenEvictionRunsMillis(6000);
			datasource.setMinEvictableIdleTimeMillis(30000);
			datasource.setValidationQuery("SELECT 'x'");
			datasource.setTestWhileIdle(true);
			datasource.setTestOnBorrow(false);
			datasource.setTestOnReturn(false);
			datasource.setUseGlobalDataSourceStat(true);
			try {
				datasource.setFilters("stat,wall");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				datasource.init();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			String show_sql_prop = PropertiesUtil.getProperties().getProperty("show_sql");
			show_sql = ((show_sql_prop != null) && show_sql_prop.equalsIgnoreCase("true"));
			druidDataSource = datasource;
		}
	}

	public DataSource getDataSource() {
		return druidDataSource;
	}

	public Connection getConnection() throws SQLException {
		Connection conn = conns.get();
		if (conn == null || conn.isClosed()) {
			conn = getDataSource().getConnection();
			conns.set(conn);
		}
		return (show_sql && !Proxy.isProxyClass(conn.getClass())) ? new DebugConnection(conn).getConnection() : conn;
	}

	/**
	 * 关闭连接
	 */
	public void closeConnection() {
		Connection conn = conns.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Unabled to close connection!!! ", e);
			}
		}
		conns.set(null);
	}

	public void destroy() {
		druidDataSource.close();
	}
}
